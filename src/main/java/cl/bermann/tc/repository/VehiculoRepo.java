package cl.bermann.tc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import cl.bermann.tc.model.Vehiculo;;

public interface VehiculoRepo extends JpaRepository<Vehiculo, String> {

    List<Vehiculo> findByVehpatente(String  veh_patente);
    
}
