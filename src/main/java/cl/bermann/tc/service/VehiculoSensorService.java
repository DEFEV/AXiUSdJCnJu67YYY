package cl.bermann.tc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.bermann.tc.model.VehiculoSensor;
import cl.bermann.tc.repository.VehiculoSensorRepo;

@Service
public class VehiculoSensorService {
   
    @Autowired
	private VehiculoSensorRepo vsrepo;

	public List<VehiculoSensor> buscarSensores(Integer vhid){
		return vsrepo.findByVehid(vhid);
	}
	
	public VehiculoSensor registrar(VehiculoSensor vs) {
		return vsrepo.save(vs);
	}
}
