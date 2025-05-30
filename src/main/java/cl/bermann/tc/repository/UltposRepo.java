package cl.bermann.tc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import cl.bermann.tc.model.Ultpos;

public interface UltposRepo extends JpaRepository<Ultpos, Integer> {
	
	List<Ultpos> findByDispositivo(Integer id_dispositivo);

}
