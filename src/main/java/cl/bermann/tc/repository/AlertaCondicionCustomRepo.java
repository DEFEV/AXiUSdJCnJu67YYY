package cl.bermann.tc.repository;

import java.sql.Timestamp;
import java.util.List;

import cl.bermann.tc.model.AlertaCondicion;

public interface AlertaCondicionCustomRepo {
	
	List<AlertaCondicion> buscarAlertasxVh(int idvh, boolean f_sinsenal);

	List<AlertaCondicion> buscarAlertasxVh(int idvh, boolean f_sinsenal, Timestamp fechaevento);

}
