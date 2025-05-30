package cl.bermann.tc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import cl.bermann.tc.model.Conductor;

public interface ConductorRepo  extends JpaRepository<Conductor, String>{
    
    List<Conductor> findByIbutton(String ibutton);
}
