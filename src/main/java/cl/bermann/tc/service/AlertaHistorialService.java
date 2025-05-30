package cl.bermann.tc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.bermann.tc.model.AlertaHistorial;
import cl.bermann.tc.repository.AlertaHistorialRepo;

@Service
public class AlertaHistorialService {
	
	@Autowired
	private AlertaHistorialRepo ahr;

	public AlertaHistorial levantarAlerta(AlertaHistorial ah) {
		try {
			return ahr.save(ah);
		} catch (Exception e) {
			System.out.println("Error al levantar alerta: no existe vh ID: " + ah.getId_vehiculo());
			return null;
		}
//		return ahr.save(ah);
	}

	public boolean buscarAlertaActiva(AlertaHistorial ah){
		try {
			List<AlertaHistorial> lal = ahr.buscarAlertaActiva(ah.getId_alerta(), ah.getId_vehiculo());
			if (lal.isEmpty()){
				System.out.println("SIN ALERTAS PENDIENTES");
			}
			return (lal.isEmpty());

		} catch (Exception e){
			System.out.println("[buscarAlertaActiva]Error: " + e.getMessage());
			return false;
		}
	}
}
