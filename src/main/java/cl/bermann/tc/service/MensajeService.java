package cl.bermann.tc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cl.bermann.tc.model.Mensaje;
import cl.bermann.tc.repository.MsgRepositoryCustom;

public class MensajeService {
	@Autowired
	MsgRepositoryCustom mrc;

	public List<Mensaje> getPendientesEstado(Integer estado){
		try {
			return mrc.findAll(estado);
		} catch (Exception e){
			System.out.println("ERROR BD :: " + e.getMessage());
			return null;
		}
	}
	
	
}
