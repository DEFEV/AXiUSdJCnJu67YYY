package cl.bermann.tc.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.bermann.tc.model.Hispos;
import cl.bermann.tc.model.Sensor;
import cl.bermann.tc.repository.SensorRepo;

@Service
public class SensorService {

    @Autowired
    private SensorRepo sr;

    public Sensor buscarSensorxId(Integer id){
        @SuppressWarnings("null")
        Optional<Sensor> lsensor = sr.findById(id);
        Sensor resps = lsensor.orElse(null);
        return resps;
    }

    public Sensor registrar(Sensor sensor) {
		try {
			return sr.save(sensor);
		} catch (Exception e){
			System.out.println("ERROR BD :: " + e.getMessage());
			return null;
		}
	}

    public Sensor findSensorByIdent(String ident) {
        List<Sensor> sensores = sr.findByIdent(ident);
        if (sensores != null && !sensores.isEmpty()) {
            // Se encontró al menos un sensor, devolvemos el primero
            return sensores.get(0);
        } else {
            // No se encontro sensor con el identificador dado
            return null;
        }
    }

}
