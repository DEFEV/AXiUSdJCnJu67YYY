package cl.bermann.tc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.bermann.tc.model.EventoTemporal;


public interface EventoTemporalRepo extends JpaRepository<EventoTemporal, Integer> {
	
	public List<EventoTemporal> findByEstado(int id_estado);

	//public List<EventoTemporal> findByEstadoAndIdVehiculo(int id_estado, int id_vehiculo);

}
