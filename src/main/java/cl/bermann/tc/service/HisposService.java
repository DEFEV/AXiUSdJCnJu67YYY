package cl.bermann.tc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.bermann.tc.model.Hispos;
import cl.bermann.tc.repository.HisposRepo;

@Service
public class HisposService {
	
	@Autowired
	private HisposRepo hpRep;
	
	public Hispos registrar(Hispos hp) {
		try {
			return hpRep.save(hp);
		} catch (Exception e){
			System.out.println("ERROR BD :: " + e.getMessage());
			return null;
		}
	}
	
	public List<Hispos> findAllHispos(){
		try {
			return hpRep.findAll();
		} catch (Exception e){
			System.out.println("ERROR BD :: " + e.getMessage());
			return null;
		}
	}

}
