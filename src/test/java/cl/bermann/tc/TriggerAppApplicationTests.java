package cl.bermann.tc;

import java.util.List;

import org.junit.Test; //org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cl.bermann.tc.model.Mensaje;
import cl.bermann.tc.service.MsgMetodos;

//@RunWith(SpringRunner.class) 
//@DataMongoTest
//@SpringBootTest
public class TriggerAppApplicationTests {

	// @Autowired
	// private MsgMetodos mm;

	/*@Test
	public void shouldReturnDefaultMessage() throws Exception {
		System.err.println("[TEST] shouldReturnDefaultMessage");
		//this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
		//.andExpect(content().string(containsString("Hello, World")));
	}
*/
	// @Test
	// public void testGetMensajesPendientes(){
	// 	System.out.println("TEST UNITARIOS");
	// 	List<Mensaje> lm = mm.mensajesPendientes(0);
	// 	System.out.println("Hay cadenas pendientes");
	// 	for (Mensaje mens : lm) {
	// 		System.out.println(mens.toString());
	// 	}
	// }

}
