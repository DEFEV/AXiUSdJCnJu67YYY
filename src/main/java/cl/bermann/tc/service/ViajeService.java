package cl.bermann.tc.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.bermann.tc.model.Viaje;
import cl.bermann.tc.repository.ViajeRepo;

@Service
public class ViajeService {

	@Autowired
	private ViajeRepo vr;
	
	public List<Viaje> buscarViaje(Integer estado_viaje){
		return vr.findByEstado(estado_viaje);
	}
	
	public List<Viaje> buscarViajexFecha(Timestamp fecha){
		return vr.findAllWithFecha_origenAfter(fecha);
	}
}
