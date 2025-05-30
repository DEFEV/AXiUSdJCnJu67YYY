package cl.bermann.tc.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.bermann.tc.model.Sensor;

public interface SensorRepo extends JpaRepository<Sensor, Integer>{
    Optional<Sensor> findById(long id);

    List<Sensor> findByIdent(String ident);
}

