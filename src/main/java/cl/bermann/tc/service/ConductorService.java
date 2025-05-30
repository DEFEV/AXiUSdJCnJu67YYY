package cl.bermann.tc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.bermann.tc.model.Conductor;
import cl.bermann.tc.repository.ConductorRepo;

@Service
public class ConductorService {

    @Autowired
    private ConductorRepo cr;
    public Conductor buscarConductorxIbutton(String ibutton){
        Conductor vc = null;
        List<Conductor> lc = cr.findByIbutton(ibutton);
        vc = lc.isEmpty() ? null : lc.get(0);
        return vc;
    }
    
}
