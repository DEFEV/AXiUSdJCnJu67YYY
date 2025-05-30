package cl.bermann.tc.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.json.JSONException;
import org.json.JSONObject;

import cl.bermann.tc.TriggerAppApplication;
import cl.bermann.tc.model.AlertaCondicion;
import cl.bermann.tc.model.AlertaHistorial;
import cl.bermann.tc.model.EventoTemporal;
import cl.bermann.tc.model.Hispos;
import cl.bermann.tc.model.Mcontenido;
import cl.bermann.tc.model.Mensaje;
import cl.bermann.tc.model.Sensor;
import cl.bermann.tc.model.Ultpos;
import cl.bermann.tc.model.Vehiculo;
import cl.bermann.tc.model.VehiculoSensor;
import cl.bermann.tc.model.Viaje;
import cl.bermann.tc.utils.Utils;

public class TcService {

	private ScriptEngine interprete ;

	public TcService() {
		super();
		ScriptEngineManager manager = new ScriptEngineManager(); 
		interprete = manager.getEngineByName("graal.js");
	}

	/*public Hispos parsear(Mensaje msj) {
		Hispos hp = new Hispos();
		//int ivh = vs.buscarIdVehiculo(msj.getMc_contenido().getMc_patente());

		int idisp = lavs.get(msj.getId_vehiculo())== null?0:lavs.get(msj.getId_vehiculo()); 
		Mcontenido mc = msj.getMc_contenido();
		hp.setHp_patente(mc.getMc_patente());
		hp.setAv_serie(msj.getId_vehiculo());
		List<Integer> gpstipo1    = Arrays.asList(67,69,71,73,75,82,86,90,96,97,98,99,101,102,103,105,107,110,111,112,114,116,122,123,127,129,290,306);
		//int hajuste = idisp == 97? 6 : 5;
		int hajuste = gpstipo1.contains(idisp)? 6 : 0;
		Timestamp tajuste = Timestamp.valueOf(Timestamp.valueOf(mc.getMc_fecha()).toLocalDateTime().plusHours(-hajuste));
		System.out.println(mc.getMc_fecha() + " :: "+tajuste.toString());
		hp.setHp_fecha(tajuste);
		hp.setHp_latitud(mc.getLatitud());
		hp.setHp_longitud(mc.getLongitud());
		hp.setHp_velocidad(mc.getVel());
		//hp.setHp_estado_motor(mc.getMotor());
		hp.setHp_estado_motor((int) mc.getOtroJson().get("motor"));
		//corregir hora desde mongo
		//hp.setHp_fecha_ins(convertirFecha(msj.getMc_fecha_in()));
		hp.setHp_direccion("Santiago, RM");
		//hp.setHp_zonas_in("");
		hp.setId_dispositivo(idisp);
		hp.setHp_datos_sensor(msj.getMc_contenido().getOtro());
		
		
		return hp;
	}*/

	public Hispos parsear(Mensaje msj, Vehiculo veh) {
		Hispos hp = new Hispos();
		//int ivh = vs.buscarIdVehiculo(msj.getMc_contenido().getMc_patente());
		//System.out.println("[IDVH] " + ivh);
		//int idisp = lavs.get(msj.getId_vehiculo())== null?0:lavs.get(msj.getId_vehiculo()); 
		//int idisp = vs.buscarIdVehiculo(msj.getMc_contenido().getMc_patente());
		int idisp = veh != null ? veh.getId():0;
		//System.out.println("[IDVH] " + idisp + " [PATENTE] " + msj.getMc_contenido().getMc_patente());
		Mcontenido mc = msj.getMc_contenido();
		hp.setHp_patente(mc.getMc_patente());
		hp.setAv_serie(msj.getId_vehiculo());
		//List<Integer> gpstipo1    = Arrays.asList(67,69,71,73,75,82,86,90,96,97,98,99,101,102,103,105,107,110,111,112,114,116,122,123,127,129,290,306);
		//int hajuste = idisp == 97? 6 : 5;
		int hajuste = 5; //gpstipo1.contains(idisp)? 6 : 0;
		Timestamp tajuste = Timestamp.valueOf(Timestamp.valueOf(mc.getMc_fecha().replace("T", " ")).toLocalDateTime().plusHours(-hajuste));
		String zona = msj.getId_vehiculo().startsWith("357")? "GMT+2" : msj.getId_proveedor().equalsIgnoreCase("101")?"GMT+0":"GMT+1";
		Instant ahora = Timestamp.valueOf(mc.getMc_fecha().replace("T", " ")).toLocalDateTime().atZone(ZoneId.of("GMT+0")).toInstant();
		// if (msj.getId_vehiculo().equalsIgnoreCase("176160")){
		// 	ahora = Timestamp.valueOf(mc.getMc_fecha()).toInstant();
		// }
		LocalDateTime ldt = LocalDateTime.ofInstant(ahora, ZoneId.of("Chile/Continental"));
		System.out.println(mc.getMc_fecha() + " :: "+tajuste.toString()+" :: "+ldt);
		hp.setHp_fecha(Timestamp.valueOf(ldt));
		hp.setHp_latitud(mc.getLatitud());
		hp.setHp_longitud(mc.getLongitud());
		hp.setHp_velocidad(mc.getVel());
		//hp.setHp_estado_motor(mc.getMotor());
		hp.setHp_estado_motor((int) mc.getOtroJson().get("motor"));
		hp.setHp_fecha_ins(msj.getId_proveedor().equalsIgnoreCase("101") ? Timestamp.valueOf(ldt) : convertirFecha(msj.getMc_fecha_in()));
		//hp.setHp_direccion("Santiago, RM");
		if (TriggerAppApplication.entorno.equalsIgnoreCase("PROD")){
			//hp.setHp_direccion(Utils.getDireccion(hp.getHp_latitud(), hp.getHp_longitud()));
			hp.setHp_direccion("-1");
		} else {
			hp.setHp_direccion("Ambiente Test");
		}
		//System.out.println("[DIRECCION "+ hp.getAv_serie() +" ] " + Utils.getDireccion(hp.getHp_latitud(), hp.getHp_longitud()));
		//hp.setHp_zonas_in("");
		hp.setId_dispositivo(idisp);


		hp.setHp_datos_sensor(limpiarNcomb(msj.getMc_contenido().getOtro()));
		
		
		return hp;
	}
	
    public static String limpiarNcomb(String jsonString) {
        try {
            // Parsear la cadena JSON
            JSONObject jsonObject = new JSONObject(jsonString);

            // Verificar si la clave "ncomb" existe en el JSON
            if (jsonObject.has("ncomb")) {
                // Obtener el valor de "ncomb" como cadena
                String ncomb = jsonObject.optString("ncomb", null);

                // Si optString no devuelve una cadena válida, intentamos obtener el valor como número
                if (ncomb == null) {
                    try {
                        // Convertir el valor numérico a cadena
                        ncomb = String.valueOf(jsonObject.get("ncomb"));
                    } catch (JSONException e) {
                        // Manejar el caso donde "ncomb" no es ni cadena ni número
                        e.printStackTrace();
                    }
                }

                // Modificar el valor de "ncomb" si contiene "-1"
                if (ncomb != null && ncomb.contains("-1")) {
                    ncomb = ncomb.replace("-1", "");  // Eliminar "-1" del valor de "ncomb"
                    jsonObject.put("ncomb", ncomb);   // Actualizar el valor en el JSONObject
                }
            }

            // Convertir el JSONObject de nuevo a una cadena JSON
            return jsonObject.toString();
        } catch (JSONException e) {
            // Manejar la excepción si ocurre un error de parsing JSON
            e.printStackTrace();
            return jsonString; // Retornar el JSON original en caso de error
        }
    }


	public Timestamp convertirFecha(String fechaIn) {
		Timestamp ts = null;
		SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);	
		try {
			Date d;
			d = df.parse(fechaIn);
			ts = new Timestamp(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ts;
	}


	public List<AlertaHistorial> verificarAlertas(Hispos vhp, Mensaje msg, List<Viaje> lv, List<EventoTemporal> ve, EventoTemporalService ets, UltposService us) {
		
		
		//int idis = lavs.get(msg.getId_vehiculo())== null?0:lavs.get(msg.getId_vehiculo());
		int idis = vhp.getId_dispositivo();
		boolean fv = idis != 0 ? verificaViaje(lv, idis):false;
		List<AlertaHistorial> lah = new ArrayList<AlertaHistorial>();
		int bp = 0;
		int p1 = 0;
		int idAlert = 0;
		if (msg.getMc_contenido().getOtroJson().has("bp1")) {
			bp =  Integer.parseInt( (String) msg.getMc_contenido().getOtroJson().get("bp1"));
		}if (msg.getMc_contenido().getOtroJson().has("p1")){
			p1 = Integer.parseInt( (String) msg.getMc_contenido().getOtroJson().get("p1"));
		}
	//	int bp =  (int) msg.getMc_contenido().getOtroJson().get("bp1");
	//	int p1 =  (int) msg.getMc_contenido().getOtroJson().get("p1");
	//	System.out.println(msg.getMc_contenido().getOtroJson().get("bp1")+ " :: "+ (bp == 1));
	//	if (msg.getId_vehiculo().equalsIgnoreCase("357042066577603") && (bp == 1 || p1 == 1 )){
		
		List<String> lidS = Arrays.asList("154511", "239379");

	/*  comentado 20221124 sitrack	
	if (bp==1) {
			idAlert = msg.getMc_contenido().getId_cli() == 4960 ? 318 : msg.getId_proveedor().equalsIgnoreCase("101") ?  324 : 313;
			lah.add(new AlertaHistorial(idAlert, vhp.getHp_fecha(), idis, 187, vhp.getId()));
		}
*/
	//	if (lavs.get(msg.getId_vehiculo())!= null && (bp == 1 || p1 == 1 )){
		if (idis > 0 && (bp == 1 || p1 == 1 )){
			if (bp==1) {
				idAlert = msg.getMc_contenido().getId_cli() == 4960 ? 318 : msg.getId_proveedor().equalsIgnoreCase("101") ? (lidS.contains(vhp.getHp_patente()) && fv ? 347 : 324 ): 313;
				lah.add(new AlertaHistorial(idAlert, vhp.getHp_fecha_ins(), idis, 187, vhp.getId(), -1));
			} else if(p1 == 1 && fv) {
				lah.add(new AlertaHistorial(314, vhp.getHp_fecha_ins(), idis, 187, vhp.getId(), -1));	
			} 
			System.out.println("LEVANTO ALERTA");
		}
		

		EventoTemporal fve = verificarEtemp(ve, idis);

		Timestamp vts = new Timestamp(System.currentTimeMillis());
		System.out.println("HORA ACTUAL : " + vts.toString());
		
//		if (lavs.get(msg.getId_vehiculo())!= null) {
		if (idis < 0) { // habilitar
			if (vhp.getHp_velocidad() < 5 && fv) {
				if (fve == null) {
					EventoTemporal vet = new EventoTemporal();
					idAlert = msg.getMc_contenido().getId_cli() == 4960 ? 320 : 315;
					vet.setVa_id(idAlert);
					vet.setEt_fecha_inicio(vhp.getHp_fecha());
					vet.setEt_lapso_tiempo(60);
					vet.setId_hispos(vhp.getId());
					vet.setId_vehiculo(idis);
					ets.registrar(vet);
				}else  {
					int ft = Timestamp.from(fve.getEt_fecha_inicio().toInstant().plusSeconds(fve.getEt_lapso_tiempo()*60)).compareTo(vts);
					if (ft < 0 && fve.getEt_estado_alarma() == 0) {
						idAlert = msg.getMc_contenido().getId_cli() == 4960 ? 320 : 315;
						lah.add(new AlertaHistorial(idAlert, vhp.getHp_fecha_ins(), idis, 187, vhp.getId(), -1));
						fve.setEt_estado_alarma(1);
						ets.registrar(fve);
					}

				}
			} 
			if(vhp.getHp_velocidad() >= 5 && fve != null) {
				fve.setEt_fecha_fin(vhp.getHp_fecha());
				fve.setEstado(1);
				ets.registrar(fve);
			} 
		}
 
		List<Ultpos> vup = us.buscarUltpos(97);
		if (!vup.isEmpty()) {
			Ultpos up = vup.get(0);
			int fult = Timestamp.from(up.getUp_fecha().toInstant().plusSeconds(5*60)).compareTo(vts);
			if (fult < 0 ) {
				lah.add(new AlertaHistorial(317, vts, up.getDispositivo(), 187, up.getId_hispos(), -1));
			}
		}

		vup = us.buscarUltpos(102);
		if (!vup.isEmpty()) {
			Ultpos up = vup.get(0);
			int fult = Timestamp.from(up.getUp_fecha().toInstant().plusSeconds(5*60)).compareTo(vts);
			if (fult < 0 ) {
				lah.add(new AlertaHistorial(317, vts, up.getDispositivo(), 187, up.getId_hispos(), -1));
			}
		}

		// vup = us.buscarUltpos(1124);
		// if (!vup.isEmpty()) {
		// 	Ultpos up = vup.get(0);
		// 	int fult = Timestamp.from(up.getUp_fecha().toInstant().plusSeconds(10*60)).compareTo(vts);
		// 	if (fult < 0 ) {
		// 		lah.add(new AlertaHistorial(323, vts, up.getDispositivo(), 187, up.getId_hispos()));
		// 	}
		// }

		// vup = us.buscarUltpos(1121);
		// if (!vup.isEmpty()) {
		// 	Ultpos up = vup.get(0);
		// 	int fult = Timestamp.from(up.getUp_fecha().toInstant().plusSeconds(10*60)).compareTo(vts);
		// 	if (fult < 0 ) {
		// 		lah.add(new AlertaHistorial(323, vts, up.getDispositivo(), 187, up.getId_hispos()));
		// 	}
		// }

		return lah;
	}

	private EventoTemporal verificarEtemp(List<EventoTemporal> ve, int idis) {
		EventoTemporal etemp = null;
		for(EventoTemporal et : ve) {
			if (et.getId_vehiculo() == idis) {
				etemp = et;
				break;
			}
		}
		return etemp;
	}

	private EventoTemporal verificarEtemp(List<EventoTemporal> ve, int id_dispositivo, int n_alarma) {
		EventoTemporal etemp = null;
		for(EventoTemporal et : ve) {
			if (et.getId_vehiculo() == id_dispositivo && et.getVa_id() == n_alarma) {
				etemp = et;
				break;
			}
		}
		return etemp;
	}

	private boolean verificaViaje(List<Viaje> lv, int idis) {
		boolean viajeOn = false;
		for(Viaje v : lv) {
			if (v.getId_vehiculo() == idis) {
				viajeOn = true;
				break;
			}
		}
		return viajeOn;
	}
	
	private int buscaViaje(List<Viaje> lv, int idis) {
		int viajen = -1;
		for(Viaje v : lv) {
			if (v.getId_vehiculo() == idis && v.getEstado() == 190) {
				viajen = v.getId();
				break;
			}
		}
		return viajen;
	}

	private int buscaViaje(List<Viaje> lv, int idis, List<Long> listEstados) {
		int viajen = 0;
		for(Viaje v : lv) {
			if (v.getId_vehiculo() == idis && listEstados.contains( Long.valueOf( v.getEstado() ) ) ) {
				viajen = v.getId();
				break;
			}
		}
		return viajen;
	}

	private String buscaClienteViaje(List<Viaje> lv, int idis) {
		String cviaje = null;
		for(Viaje v : lv) {
			if (v.getId_vehiculo() == idis) {
				cviaje = v.getCliente_del_cliente();
				break;
			}
		}
		return cviaje;
	}

	public void marcarSensores(Hispos ahp, VehiculoSensorService vss) {
		List<VehiculoSensor> lvs;
		lvs = vss.buscarSensores(ahp.getId_dispositivo());
		for(VehiculoSensor vs : lvs) {
			switch (vs.getSensorid()) {
			case 16:
				if (ahp.getDatosJson().has("bp1") ) {
					vs.setUlt_fecha(ahp.getHp_fecha());
					vss.registrar(vs);
				}
				break;
			case 17:
				if (ahp.getDatosJson().has("p1") ) {
					vs.setUlt_fecha(ahp.getHp_fecha());
					vss.registrar(vs);
				}
				break;
			case 18:
				if (ahp.getDatosJson().has("p2")) {
					vs.setUlt_fecha(ahp.getHp_fecha());
					vss.registrar(vs);
				}
				break;
			case 19:
				if (ahp.getDatosJson().has("p3") ) {
					vs.setUlt_fecha(ahp.getHp_fecha());
					vss.registrar(vs);
				}
				break;
			case 20:
				if (ahp.getDatosJson().has("motor") ) {
					vs.setUlt_fecha(ahp.getHp_fecha());
					vss.registrar(vs);
				}
				break;
			}
		}
		System.out.println("ACTUALIZA SENSOR : " + lvs.toString());
		
	}

	public void marcarSensores(Hispos ahp, VehiculoSensorService vss, SensorService ss) {
		List<VehiculoSensor> lvs;

		//Primero: Actualizar estado sensores


		//System.out.println("******* ====>>> SENSORES en MSG: ");

		/* se comanta para ver reduccion en tiempo de proceso
		for (String keySensor : ahp.getDatosJson().keySet()) {
			//Object keySensorValue = ahp.getDatosJson().get(keySensor);
	
			// Actualizar estado de la clase sensor a 1 Activo
			Sensor sensor = ss.findSensorByIdent(keySensor);
			if (sensor != null) {
				//System.out.println("**** Sensor key: "+ sensor.getIdent());
				if (sensor.getId_estado()==2) {
					sensor.setId_estado(1);
					ss.registrar(sensor);
				}
			} else {
				System.out.println("**** Sensor not found key: "+ keySensor);
			}
				
			// System.out.println("key: "+ keyStr + " value: " + keyvalue);
	
		} fin comentario */

		//System.out.println("======>>>>>>>>>>>>>>>>>>>>>> BUSCANDO SENSOR : " + ahp.getId_dispositivo());

		lvs = vss.buscarSensores(ahp.getId_dispositivo());
		for(VehiculoSensor vs : lvs) {
			
			if (ahp.getDatosJson().has(ss.buscarSensorxId(vs.getSensorid()).getIdent())){
				vs.setUlt_fecha(ahp.getHp_fecha());
				vss.registrar(vs);
			}
		}
		System.out.println("ACTUALIZA NEW SENSOR : " + lvs.toString());

		
		
	}

	public List<AlertaHistorial> verificarAlertas(Hispos vhp, Mensaje msg, List<Viaje> lv, List<EventoTemporal> ve,
			EventoTemporalService ets, UltposService ups, List<AlertaCondicion> vac, Ultpos aup) {
	
				List<AlertaHistorial> lah = new ArrayList<AlertaHistorial>();
				//boolean fv = vhp.getId_dispositivo() != 0 ? verificaViaje(lv, vhp.getId_dispositivo()):false;
				boolean f_alarma = true; 
				boolean f_temporal = false;
				boolean f_perdida = false;
				int t_temporal = 0;
				//int n_alarma = vac.get(0).getId_alarma();
				AlertaCondicion n_alarma = vac.get(0);
				EventoTemporal fve ;
				Ultpos vup = null;
		
				//ScriptEngineManager manager = new ScriptEngineManager(); 
				//ScriptEngine interprete = manager.getEngineByName("graal.js");
			
				String sentencia = "";
				//Timestamp vts = new Timestamp(System.currentTimeMillis());
				Timestamp vts = vac.get(0).getTactual();
				int nviaje = -1;

				for (AlertaCondicion ac : vac) {
					//if (n_alarma != ac.getId_alarma()) {
					System.out.println("CHECK COND : " + ac.toString() + " - "+ f_alarma);
					if (n_alarma.getId_alarma() != ac.getId_alarma() || n_alarma.getId_vh() != ac.getId_vh()) {
						if (f_temporal ){
							fve = verificarEtemp(ve, vhp.getId_dispositivo(), n_alarma.getId_alarma());
							//fve = verificarEtemp(ve, n_alarma.getId_vh(), n_alarma.getId_alarma());
							if (fve == null) {
								if (f_alarma ) {
									fve = new EventoTemporal();
									fve.setVa_id(n_alarma.getId_alarma());
									fve.setEt_fecha_inicio(vhp.getHp_fecha());
									fve.setEt_lapso_tiempo(t_temporal);
									fve.setId_hispos(vhp.getId());
									fve.setId_vehiculo(vhp.getId_dispositivo());
									ets.registrar(fve);
									f_alarma = false;						
								}
							}else  {
								if (f_alarma ) {
									System.out.println("HORA ACTUAL : " + vts.toString());
									int ft = Timestamp.from(fve.getEt_fecha_inicio().toInstant().plusSeconds(fve.getEt_lapso_tiempo()*60)).compareTo(vts);
									if (ft < 0 && fve.getEt_estado_alarma() == 0) {					
										fve.setEt_estado_alarma(1);
										ets.registrar(fve);
									} else {
										f_alarma = false;
									}						
								} else {
									fve.setEt_fecha_fin(vhp.getHp_fecha());
									fve.setEstado(1);
									ets.registrar(fve);
								}
							}
						}
						if (f_perdida){ // revisar cierre del evento temporal
							fve = verificarEtemp(ve, n_alarma.getId_vh(), n_alarma.getId_alarma());
							if (fve == null) {
								if (f_alarma ) {
									fve = new EventoTemporal();
									fve.setVa_id(n_alarma.getId_alarma());
									fve.setEt_fecha_inicio(vup.getUp_fecha());
									//fve.setEt_lapso_tiempo(Integer.parseInt(ac.getValor()));
									fve.setEt_lapso_tiempo(t_temporal);
									fve.setId_hispos(vup.getId_hispos());
									fve.setId_vehiculo(n_alarma.getId_vh());
									fve.setEt_estado_alarma(1);
									fve.setEt_tipo_perdidasenal(true);
									ets.registrar(fve);						
								}
							} else {
								f_alarma = false;
							}
						}
						if (f_alarma) {

							if (nviaje==-1) {
								nviaje = buscaViaje(lv, ac.getId_vh());
							}

							if (n_alarma.getId_vh()!= vhp.getId_dispositivo()){
								lah.add(new AlertaHistorial(n_alarma.getId_alarma(), vts, n_alarma.getId_vh(), 187, vup == null ? 0:vup.getId_hispos(),nviaje));
							} else {
								lah.add(new AlertaHistorial(n_alarma.getId_alarma(), vhp.getHp_fecha_ins(), n_alarma.getId_vh(), 187, vhp.getId(),nviaje));
								//logica numero viaje
							}
							System.out.println("\n\nAQUI LEVANTO ALARMA :: "+ n_alarma);
						}
						f_alarma = true;
						//n_alarma = ac.getId_alarma();
						n_alarma = ac;
						nviaje = -1;
						f_perdida = f_temporal = false;
						t_temporal = 0;
						vup = null;
					}
					//obsolecencia
					if (ac.getTobsoleto()>=0){
						int f_tobsoleto = Timestamp.from(vhp.getHp_fecha().toInstant().plus(ac.getTobsoleto(), ChronoUnit.HOURS)).compareTo(vts);
						f_alarma = f_tobsoleto < 0 ? false : f_alarma;
					}
					System.out.println("CONDICION:: " + ac.toString());
					if (!f_alarma && ac.getTipo_alerta() != 311) continue;
					/* comprobando por tipo de condicion */



					switch (ac.getTipo_alerta()) { 
					case 308:
						sentencia = (ac.getIdent().compareToIgnoreCase("vel") == 0) ? String.valueOf(vhp.getHp_velocidad()) : vhp.getDatosJson().has(ac.getIdent()) ? String.valueOf(vhp.getDatosJson().get(ac.getIdent())) : "";
						System.out.println("*****************************  SENTENCIA INICO:" + sentencia + " :: " + vhp.getDatosJson().toString() + " :: "+ ac.getIdent());
						if (sentencia.compareTo("") != 0) {
							if (ac.getCondicion().startsWith("v")){
								String diferenciaEval = ac.getCondicion().split("\\|")[0];

								System.out.println("======================== test condicion baja combustible ======================" + diferenciaEval);
							    // vup = ups.getUltpos(ac.getId_vh());
								System.out.println(aup.getDatosJson().toString());

								String valorEval =  ac.getValor().contains(".") ? ac.getValor().replace(".", "") : ac.getValor() + "0";
								valorEval =  ac.getValor().contains("-1") ? ac.getValor().replace("-1", "") : ac.getValor();
								
								// To do: Corregir que el atributo ncomb siempre venga en el Json
								String sentenciaDiff = "("+ sentencia + " - " + (aup.getDatosJson().has(ac.getIdent()) ? String.valueOf(aup.getDatosJson().get(ac.getIdent())) : sentencia) + ") ";

								String presentencia = diferenciaEval.contains("-") ? sentenciaDiff + " < 0 " : diferenciaEval.contains("+") ? sentenciaDiff + " > 0 " : " true " ;
								sentencia = presentencia + " && Math.abs" + sentenciaDiff + " " + ac.getCondicion().split("\\|")[1] + " " + valorEval;
									
								// sentencia = "("+ String.valueOf(aup.getDatosJson().get(ac.getIdent())) + " - "+ sentencia + ") "+ ac.getCondicion().split("\\|")[1] + " " + valorEval;
								
								
							} else {
								sentencia += " " + ac.getCondicion() + " " + ac.getValor();	
							}
							System.out.println("******************************  SENTENCIA EVAL : "+ sentencia );
							
							try {
								f_alarma = (boolean) this.interprete.eval(sentencia);
							} catch (ScriptException e) {
								e.printStackTrace();
								f_alarma = false;
							}



						} else {
							f_alarma = false;
						}		
						break;
					case 309:
						//f_alarma = verificaViaje(lv, vhp.getId_dispositivo());
						nviaje = buscaViaje(lv, ac.getId_vh(), convertirArray(ac.getValor()));
						//n_alarma.se
						f_alarma = nviaje != 0; 
						if (f_alarma){
							System.out.println("NRO VIAJE : " + f_alarma);
						}
						break;
					case 310:
						List<Long> lzonas = vhp.getHp_zonas_in() == null ? new ArrayList<Long>() :Arrays.asList(vhp.getHp_zonas_in());
						if (ac.getValor() == null) {
							f_alarma = false;
						}else {
							if (ac.getCondicion().split(":")[1].compareToIgnoreCase("OUT") == 0){
								f_alarma = !existeIntersect(lzonas, convertirArray(ac.getValor()));
							} else {
								f_alarma = existeIntersect(lzonas, convertirArray(ac.getValor()));							
							}
						}
						break;
					case 311:
						f_temporal = true;
						t_temporal = Integer.parseInt(ac.getValor());
						/*fve = verificarEtemp(ve, vhp.getId_dispositivo(), n_alarma.getId_alarma());
						if (fve == null) {
							if (f_alarma ) {
								fve = new EventoTemporal();
								fve.setVa_id(n_alarma.getId_alarma());
								fve.setEt_fecha_inicio(vhp.getHp_fecha());
								fve.setEt_lapso_tiempo(Integer.parseInt(ac.getValor()));
								fve.setId_hispos(vhp.getId());
								fve.setId_vehiculo(vhp.getId_dispositivo());
								ets.registrar(fve);
								f_alarma = false;						
							}
						}else  {
							if (f_alarma ) {
								System.out.println("HORA ACTUAL : " + vts.toString());
								int ft = Timestamp.from(fve.getEt_fecha_inicio().toInstant().plusSeconds(fve.getEt_lapso_tiempo()*60)).compareTo(vts);
								if (ft < 0 && fve.getEt_estado_alarma() == 0) {					
									fve.setEt_estado_alarma(1);
									ets.registrar(fve);
								} else {
									f_alarma = false;
								}						
							} else {
								fve.setEt_fecha_fin(vhp.getHp_fecha());
								fve.setEstado(1);
								ets.registrar(fve);
							}
						}    parametro temporal en cualquier posicion */
						break;
					case 405:
						f_perdida = true;
						t_temporal = Integer.parseInt(ac.getValor());
						vup = ups.getUltpos(ac.getId_vh());
						/*fve = verificarEtemp(ve, n_alarma.getId_vh(), n_alarma.getId_alarma());
						if (fve == null) {
							if (f_alarma ) {
								fve = new EventoTemporal();
								fve.setVa_id(n_alarma.getId_alarma());
								fve.setEt_fecha_inicio(vup.getUp_fecha());
								fve.setEt_lapso_tiempo(Integer.parseInt(ac.getValor()));
								fve.setId_hispos(vup.getId_hispos());
								fve.setId_vehiculo(n_alarma.getId_vh());
								fve.setEt_estado_alarma(1);
								fve.setEt_tipo_perdidasenal(true);
								ets.registrar(fve);						
							}
						} else {
							f_alarma = false;
						}*/
						break;
					case 430:
						String clienteF = buscaClienteViaje(lv, ac.getId_vh());
						if (ac.getValor() == null) {
							f_alarma = false;
						}else {
							if (ac.getCondicion().split(":")[1].compareToIgnoreCase("OUT") == 0){
								f_alarma = !existeInterString(clienteF, convertirArrayString(ac.getValor()));
							} else {
								f_alarma = existeInterString(clienteF, convertirArrayString(ac.getValor()));							
							}
						}
						break;
					}
				}
				if (f_temporal ){
					fve = verificarEtemp(ve, vhp.getId_dispositivo(), n_alarma.getId_alarma());
					if (fve == null) {
						if (f_alarma ) {
							fve = new EventoTemporal();
							fve.setVa_id(n_alarma.getId_alarma());
							fve.setEt_fecha_inicio(vhp.getHp_fecha());
							fve.setEt_lapso_tiempo(t_temporal);
							fve.setId_hispos(vhp.getId());
							fve.setId_vehiculo(vhp.getId_dispositivo());
							ets.registrar(fve);
							f_alarma = false;						
						}
					}else  {
						if (f_alarma ) {
							System.out.println("HORA ACTUAL : " + vts.toString());
							int ft = Timestamp.from(fve.getEt_fecha_inicio().toInstant().plusSeconds(fve.getEt_lapso_tiempo()*60)).compareTo(vts);
							if (ft < 0 && fve.getEt_estado_alarma() == 0) {					
								fve.setEt_estado_alarma(1);
								ets.registrar(fve);
							} else {
								f_alarma = false;
							}						
						} else {
							fve.setEt_fecha_fin(vhp.getHp_fecha());
							fve.setEstado(1);
							ets.registrar(fve);
						}
					}
				}
				if (f_perdida){
					fve = verificarEtemp(ve, n_alarma.getId_vh(), n_alarma.getId_alarma());
					if (fve == null) {
						if (f_alarma ) {
							fve = new EventoTemporal();
							fve.setVa_id(n_alarma.getId_alarma());
							fve.setEt_fecha_inicio(vup.getUp_fecha());
							fve.setEt_lapso_tiempo(t_temporal);
							fve.setId_hispos(vup.getId_hispos());
							fve.setId_vehiculo(n_alarma.getId_vh());
							fve.setEt_estado_alarma(1);
							fve.setEt_tipo_perdidasenal(true);
							ets.registrar(fve);						
						}
					} else {
						f_alarma = false;
					}
				}
				if (f_alarma) {

					if (nviaje==-1) {
						nviaje = buscaViaje(lv, n_alarma.getId_vh());
					}

					if (n_alarma.getId_vh()!= vhp.getId_dispositivo()){
						lah.add(new AlertaHistorial(n_alarma.getId_alarma(), vts, n_alarma.getId_vh(), 187, vup == null ? 0:vup.getId_hispos(),nviaje));
					} else {
						lah.add(new AlertaHistorial(n_alarma.getId_alarma(), vhp.getHp_fecha_ins(), n_alarma.getId_vh(), 187, vhp.getId(),nviaje));
						//logica numero viaje
					}
					System.out.println("\n\nAQUI LEVANTO ALARMA :: "+ n_alarma);
				}
				
				return lah;
	}

	private boolean existeIntersect(List<Long> lzonas, List<Long> convertirArray) {
		boolean resp = false;
		for (Long vl : lzonas) {
			resp = convertirArray.contains(vl);
			if (resp) break;
		}
		return resp;
	}

	private boolean existeInterString(String lzonas, List<String> convertirArray) {
		boolean resp = false;
		//for (String vl : lzonas) {
			resp = convertirArray.contains(lzonas);
		//	if (resp) break;
		//}
		return resp;
	}

	private List<Long> convertirArray(String valor) {
		valor = valor.replace("{", "").replace("}", "");
		long[] along =Stream.of(valor.split(",")).mapToLong(Long::parseLong).toArray();
		List<Long> l_ar = Arrays.stream(along).boxed().collect(Collectors.toList());
		return l_ar;
	}

	private List<String> convertirArrayString(String valor) {
		List<String> l_ar = Arrays.asList(valor.split(",", -1));
		return l_ar;
	}

	public static void main(String[] args) {
		ScriptEngineManager manager = new ScriptEngineManager(); 
		ScriptEngine interpr = manager.getEngineByName("graal.js");

		String parte1 = "v+";
		String presentencia = parte1.contains("-") ? "(1000 - 900)<0 " : parte1.contains("+") ? "(1000 - 900) > 0 " : " true " ;
		String sentencia = presentencia + " && Math.abs(1000 - 900) > 40 ";
		boolean resp = false;
		try {
			resp = (boolean) interpr.eval(sentencia);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		System.out.println("se puede probar aqui? : " + resp);
	}

}
