package cl.bermann.tc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import cl.bermann.tc.model.AlertaHistorial;

@Repository
public interface AlertaHistorialRepo extends JpaRepository<AlertaHistorial, Integer> {

    @Query("SELECT a FROM AlertaHistorial a WHERE a.id_estado_atencion != 189 and a.id_vehiculo = :id_vehiculo  and a.id_alerta = :id_alerta")
    public List<AlertaHistorial> buscarAlertaActiva (@Param("id_alerta") int id_alerta,@Param("id_vehiculo") int id_vehiculo);

}
