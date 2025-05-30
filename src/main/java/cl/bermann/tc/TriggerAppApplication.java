package cl.bermann.tc;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cl.bermann.commons.enums.BermannApp;
import cl.bermann.commons.monitor.BermannMonitor;
import cl.bermann.commons.util.UtilReflection;
import cl.bermann.tc.model.AlertaCondicion;
import cl.bermann.tc.model.AlertaHistorial;
import cl.bermann.tc.model.EventoTemporal;
import cl.bermann.tc.model.Hispos;
import cl.bermann.tc.model.Mensaje;
import cl.bermann.tc.model.Ultpos;
import cl.bermann.tc.model.Vehiculo;
import cl.bermann.tc.model.Viaje;
import cl.bermann.tc.service.*;

@SpringBootApplication
public class TriggerAppApplication implements CommandLineRunner{

	@Autowired
	MsgMetodos mrc;
//	@Autowired
//	MsgRepositoryCustom mc;
	@Autowired
	HisposService histre;
	@Autowired
	AlertaHistorialService ahs;
	@Autowired
	ViajeService vs;
	@Autowired
	EventoTemporalService ets;
	@Autowired
	UltposService ups;
	@Autowired
	VehiculoSensorService vss;
    @Autowired
	VehiculoService vhs;
	@Autowired
	AlertaCondicionService acs;
	@Autowired
	SensorService ss;
	
	public final static String entorno = System.getenv("ENTORNO_APP");

	public static void main(String[] args) {
		// check if user is asking for version
		UtilReflection.checkVersion(args);
		// starts the keepalive concurrent task
		//String entorno = "TEST";
		if (entorno.equalsIgnoreCase("PROD") ){
			BermannMonitor.prtgStartMonitor(BermannApp.APP_TRIGGER);	
		}
	//	BermannMonitor.prtgStartMonitor(BermannApp.APP_TRIGGER);
		SpringApplication.run(TriggerAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("comenzamos con mongodb " + args[0]);
		
		int vlimit = Integer.parseInt(args[0]);
		String segm = "-1";
		if (args.length > 1) {
			segm = args[1];
		}
		
		List<Viaje> lv; 
		boolean fv;
		TcService ts = new TcService();
		List<Mensaje> lm;
		List<AlertaHistorial> lah;
		Hispos vhp, ahp; 
		List<EventoTemporal> ve;
		Ultpos vup, aup;
		List<AlertaCondicion> vac;
		boolean f_sinsenal = false;
		Timestamp t_perdidaSenal = new Timestamp(System.currentTimeMillis());
		while (true) {
			try {
				Timestamp limitViaje = Timestamp.from(t_perdidaSenal.toInstant().minus(1, ChronoUnit.DAYS));
				//lv = vs.buscarViaje(Integer.valueOf(190));
				System.out.println("INICIO");
				lv = vs.buscarViajexFecha(limitViaje);
				
				//System.out.println("LISTA VIAJES : " + lv.size());
				//for (Viaje cv : lv){
				//	System.out.println("viaje : " + cv.toString());
				//}
				//System.out.println("A_buscaevento");
				ve = ets.buscarEvento(0);
				Set<Integer> idsVehiculos = ve.stream().map(EventoTemporal::getId_vehiculo).collect(Collectors.toSet());
				fv = lv.size() >0;
				System.out.println("SEGMENTO "+ segm);

				lm = segm.equals("-1") ? mrc.mensajesPendientes(0, vlimit) : mrc.mensajesPendientes(0, vlimit, segm);
				//System.out.println("D_pendientes");
				lah = null;
				if (t_perdidaSenal.compareTo(new Timestamp(System.currentTimeMillis())) < 0){
					f_sinsenal = false;
					t_perdidaSenal = Timestamp.from(t_perdidaSenal.toInstant().plusSeconds(120));
				}
				for(Mensaje msg : lm) {
					try {
						System.out.println(msg.toString());
						Vehiculo vehiculo = vhs.buscarVehiculoPorPatente(msg.getMc_contenido().getMc_patente());
						if (vehiculo==null) {
							mrc.marcaListo(msg, 26);
							continue;
						}
						//ahp = ts.parsear(msg,vhs);
						ahp = ts.parsear(msg,vehiculo);
						//System.out.println("PASO2");
						ts.marcarSensores(ahp,vss,ss);
						System.out.println("PASO3");
						aup = ups.getUltpos(ahp.getId_dispositivo());
						vup = ups.registrarHp(aup, ahp, vehiculo);
						System.out.println("PASO4");
						ahp.setHp_intervalo_tiempo(vup.getUp_tiempo_intervalo());
						ahp.setHp_zonas_in(vup.getUp_zonas_in());
						ahp.setId_estado(-1);
						vhp = histre.registrar(ahp);
						System.out.println("PASO4-1");
						vup.setId_hispos(vhp.getId());
						System.out.println("PASO4-2");
						vup = ups.registrar(vup, ve, idsVehiculos);
						System.out.println("PASO5");
						//actualizo vehiculo
						//vehiculo = vhs.buscarVehiculoPorPatente(msg.getMc_contenido().getMc_patente());

						//System.out.println("Vehiculo estado: " + Integer.toString(vehiculo.getId_estado())+ " :: "+ vhp.toString());

						if (vehiculo != null &&  vehiculo.getId_estado()==1 ) {
							vac = acs.getCondiciones(vhp.getId_dispositivo(), f_sinsenal, vhp.getHp_fecha());
							System.out.println("PASO6");
							f_sinsenal = false;
							if (!vac.isEmpty()) {
								lah = ts.verificarAlertas(vhp, msg, lv, ve, ets, ups, vac, aup);
								for(AlertaHistorial vah : lah) {
									//if (vhp.getHp_patente())
									if(ahs.buscarAlertaActiva(vah))
										ahs.levantarAlerta(vah);
								}
								System.out.println(" Existen condiciones de Alertas "+ vhp.toString());
							}   
							mrc.marcaListo(msg, 26);
						} else {
							System.out.println(" Vehiculo no encontrado o no contiene estado valido ");
							mrc.marcaListo(msg, 26);
						}

					}catch (Exception e) {
						e.printStackTrace();
						mrc.marcaListo(msg, 23);
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (entorno.equalsIgnoreCase("PROD") ){
					BermannMonitor.prtgPushException(BermannApp.APP_TRIGGER, e);
				}
				try { Thread.sleep(1000*5); } catch (Exception ex) { }
			}
			
		}
//		version
		
	}
	
	// private void getNextMsg(int estado) {
	// 	try {
	// 		mrc.mensajesPendientes(estado).forEach(msg -> System.out.println(msg));
	// 		}catch(Exception e) {
	// 			e.printStackTrace();
	// 		}
		
	// }
	
	private void getMsg(String id) {
		try {
//			System.out.println(mc.findAll(3));
			}catch(Exception e) {
				e.printStackTrace();
			}
		
	}
	
	public static String FechaGps2utc(int semana, int dia, int segTrans)  {
		long t_semana = 7 * 24 * 3600 * 1000;
		long t_dia = 24 * 3600 * 1000;
		String fg2u = "";
		Calendar ah = Calendar.getInstance();
		ah.set(1980, 0, 6, 0, 0, 0);
		long totalMilis = ah.getTimeInMillis() + semana * t_semana + dia * t_dia + segTrans * 1000;
		Date ti = new Date(totalMilis);
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ti = new Date(totalMilis);
		fg2u = formateador.format(ti);
		return (fg2u);
	}
	
	public static String Vel_mi2km(String vel) {
		Float nVel = null;
		nVel = (float) (Double.valueOf(vel) * 1.609344);
		return nVel.toString();
	}

}
