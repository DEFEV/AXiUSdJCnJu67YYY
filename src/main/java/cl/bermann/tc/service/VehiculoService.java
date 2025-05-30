package cl.bermann.tc.service;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.bermann.tc.model.Vehiculo;
import cl.bermann.tc.repository.VehiculoRepo;

@Service
public class VehiculoService {
    @Autowired
    private VehiculoRepo vr;

    public int buscarIdVehiculo(String patente){
        Iterator<Vehiculo> lv = vr.findByVehpatente(patente).iterator();
        Vehiculo veh = lv.hasNext() ? lv.next() : null;
        if (veh != null && veh.getId_estado() == 2) {
            veh.setId_estado(1);
            this.guardar(veh);
        }
        //int resp = lv.hasNext()? lv.next().getId():0;
        int resp = (veh != null) ? veh.getId() : 0;
        System.out.println("[IDVH] "+ patente + " :: " + resp);
        return resp;
	}

    public Vehiculo buscarVehiculoPorPatente(String patente){
        Iterator<Vehiculo> lv = vr.findByVehpatente(patente).iterator();
        Vehiculo resp = lv.hasNext()? lv.next():null;
        System.out.println("[IDVH] "+ patente + " :: " + patente);
        return resp;
	}

	public Vehiculo guardar(Vehiculo vh) {
		return vr.save(vh);
	}
    
}
