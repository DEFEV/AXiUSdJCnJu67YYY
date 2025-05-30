package cl.bermann.tc.service;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.bermann.tc.model.AlertaCondicion;
import cl.bermann.tc.repository.AlertaCondicionCustomRepo;


@Service
public class AlertaCondicionService {
	
	@Autowired
	AlertaCondicionCustomRepo accr;
//	
//	public List<AlertaCondicion> buscaAlertaVh(int vhid){
//		return acr.buscarAlertasxVh(vhid);
//	}

	public AlertaCondicion getAlertaCondicion(EntityManager em, String ndato) {
		String squery = "select va.id id_alarma, s.se_identificador ident, vae.vae_condicion condicion, vae.vae_valor_txt valor,  vae.vae_tipo tipo_alerta  from vehiculo_alarma_evento vae left "
				+ " join sensor s on vae.id_nev = s.id inner join vehiculo_alarma va on va.id = vae.id_alarma inner join cliente_vehiculo cv on cv.id_cliente = va.id_cliente where cv.id_vehiculo = ?";
		try {
			Query qry = em.createNativeQuery(squery);
			qry.setParameter(1, ndato);
			List<Object[]> resp = qry.getResultList();
			AlertaCondicion ac;
			for (Object[] elem : resp) {
				ac = new AlertaCondicion(elem);
				System.out.println(ac.toString());
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<AlertaCondicion> getCondiciones(int idDispositivo, boolean f_sinsenal){
		return accr.buscarAlertasxVh(idDispositivo, f_sinsenal);
	}
	
	public List<AlertaCondicion> getCondiciones(int idDispositivo, boolean f_sinsenal, Timestamp fechaevento){
		return accr.buscarAlertasxVh(idDispositivo, f_sinsenal, fechaevento);
	}
	
	
}
