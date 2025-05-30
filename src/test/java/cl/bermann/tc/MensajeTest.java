package cl.bermann.tc;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import cl.bermann.tc.model.Mensaje;
import cl.bermann.tc.repository.MensajeRepo;
import cl.bermann.tc.service.MsgMetodos;

//@DataMongoTest
public class MensajeTest{
    // @Autowired
    // private TestEntityManager entityManager;
    // @Autowired
    // private MsgMetodos mr;
    
    // @Test
    // public void hayPendientes(){
    //     mr = new MsgMetodos();
    //     System.out.println("Por fin por aqui");
    //     List<Mensaje> lm = mr.mensajesPendientes(0);
    //     assertTrue("Quedan cadenas",lm.size()>0);
    // }
}
