package cl.bermann.tc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.bermann.tc.model.EventoTemporal;
import cl.bermann.tc.repository.EventoTemporalRepo;

@Service
public class EventoTemporalService {

	@Autowired
	private EventoTemporalRepo etr;
	
	public EventoTemporal registrar(EventoTemporal et) {
		return etr.save(et);
	}
	
	public List<EventoTemporal> buscarEvento(int id_estado){
		return etr.findByEstado(id_estado);
	}
	
}
