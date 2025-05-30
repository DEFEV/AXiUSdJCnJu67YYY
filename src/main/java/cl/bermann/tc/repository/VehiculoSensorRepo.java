package cl.bermann.tc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import cl.bermann.tc.model.VehiculoSensor;

public interface VehiculoSensorRepo extends JpaRepository<VehiculoSensor, Integer> {

    List<VehiculoSensor> findByVehid(Integer vehid);
}
    

