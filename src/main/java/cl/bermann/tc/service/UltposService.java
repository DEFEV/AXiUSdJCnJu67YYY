package cl.bermann.tc.service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.bermann.tc.model.EventoTemporal;
import cl.bermann.tc.model.Hispos;
import cl.bermann.tc.model.Ultpos;
import cl.bermann.tc.model.Vehiculo;
import cl.bermann.tc.repository.UltposRepo;

@Service
public class UltposService {
	@Autowired
	private UltposRepo ur;
	@Autowired
	private VehiculoService vhs;
	@Autowired
	private EventoTemporalService ets;
	
	public List<Ultpos> buscarUltpos(Integer dispositivo){
		
		return ur.findByDispositivo(dispositivo);
		
	}
	
	public Ultpos getUltpos(Integer dispositivo){
		Ultpos vup;
		List<Ultpos> lup = ur.findByDispositivo(dispositivo);
		vup= lup.isEmpty()?null:lup.get(0);
		return vup;
	}
	
	public Ultpos registrar(Ultpos up, List<EventoTemporal> ve, Set<Integer> idsVehiculos) {
		try {
			if (idsVehiculos.contains(up.getDispositivo())) {
				ve.stream()
				.filter(et -> et.getId_vehiculo() == up.getDispositivo() && et.isEt_tipo_perdidasenal())
				.forEach(et -> {
					et.setEstado(1);
					et.setEt_fecha_fin(up.getUp_fecha());
					ets.registrar(et);
				});
			}

			return ur.save(up);
		} catch (Exception e){
			System.out.println("ERROR BD :: " + e.getMessage());
			return null;
		}
	}

	public Ultpos registrarHp(Ultpos up, Hispos hp, Vehiculo vh) {
		Ultpos  upFinal = null;
		//List<Ultpos> lup = buscarUltpos(hp.getId_dispositivo()); 
		//if (lup.isEmpty()) {
		if (up == null) {
			up = new Ultpos();
//, , hp.getHp_latitud(), hp.getHp_longitud(), hp.getHp_velocidad(), hp.getHp_direccion(),
			//hp.getHp_estado_motor(), hp.getHp_zonas_in(), hp.getHp_distancia(), hp.getHp_estado(), hp.getHp_fecha_ins(), hp.getHp_patente()
			up.setUp_fecha(hp.getHp_fecha());
			up.setDispositivo(hp.getId_dispositivo());
			up.setUp_latitud(hp.getHp_latitud());
			up.setUp_longitud(hp.getHp_longitud());
			up.setUp_velocidad(hp.getHp_velocidad());
			up.setUp_estado_motor(hp.getHp_estado_motor());
			up.setUp_direccion(hp.getHp_direccion());
			up.setUp_zonas_in(hp.getHp_zonas_in());
			up.setUp_distancia(hp.getHp_distancia());
			up.setUp_estado(hp.getHp_estado());
			up.setUp_fecha_ins(hp.getHp_fecha_ins());
			up.setUp_patente(hp.getHp_patente());
			up.setUp_tiempo_intervalo(0);
			//Vehiculo vh = vhs.buscarVehiculoPorPatente(hp.getHp_patente());
			
			up.setUp_datos(hp.getHp_datos_sensor());
			//up.setUp_direccion(hp.getHp_direccion());
		}else {
			//up = lup.get(0);
			up.setUp_tiempo_intervalo((int) TimeUnit.MILLISECONDS.toSeconds(hp.getHp_fecha().getTime() - up.getUp_fecha().getTime()));
			up.setUp_fecha(hp.getHp_fecha());
			up.setUp_estado_motor(hp.getHp_estado_motor());
			up.setUp_fecha_ins(hp.getHp_fecha_ins());
			up.setUp_latitud(hp.getHp_latitud());
			up.setUp_longitud(hp.getHp_longitud());
			up.setUp_patente(hp.getHp_patente());
			up.setUp_velocidad(hp.getHp_velocidad());
			up.setUp_datos(hp.getHp_datos_sensor());
			//int lapso = ChronoUnit.MINUTES.between(up.getUp_fecha(), hp.getHp_fecha());	

			//up.setUp_direccion(hp.getHp_direccion());
		}
		if (vh != null && vh.getId_estado() != 1) {
			vh.setId_estado(1);
			vh = vhs.guardar(vh);			

		}
		try {
			//return ur.save(up);
			// up = ur.save(up);
			// var vup = ur.findById(up.getId());
			// upFinal = vup.isPresent() ? vup.get() : null;
			upFinal = ur.save(up);
			System.out.println("up modificado :: " + upFinal.toString());
		} catch (Exception e){
			System.out.println("ERROR BD :: " + e.getMessage());
			//return null;
			throw new RuntimeException(String.format("[UltposService.registrarHp] Error guardando hp. Exc:%s", e.getMessage()),e);
		}
		return upFinal;
	}
}
