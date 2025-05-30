package cl.bermann.tc.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.bermann.tc.model.Viaje;

public interface ViajeRepo extends JpaRepository<Viaje, Integer>{
	
	List<Viaje> findByEstado(Integer estado_viaje);

	@Query("select v from Viaje v where v.fecha_origen >= :fechaorigen")
	List<Viaje> findAllWithFecha_origenAfter(@Param("fechaorigen") Timestamp fecha_origen);

}
