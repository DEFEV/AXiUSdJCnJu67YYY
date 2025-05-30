package cl.bermann.tc.repository;

import java.util.List;
import cl.bermann.tc.model.Mensaje;

public interface MensajeRepo{
	List<Mensaje> mensajesPendientes(int estado, int vlimit);
	List<Mensaje> mensajesPendientes(int estado, int vlimit, String ultimoCaracter);

}
