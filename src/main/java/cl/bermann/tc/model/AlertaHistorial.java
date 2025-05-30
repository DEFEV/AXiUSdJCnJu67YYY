package cl.bermann.tc.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "alerta_historial")
public class AlertaHistorial {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private int id_alerta;
	private Timestamp ah_fecha_activacion;
	private int id_vehiculo;
	private int id_estado_atencion;
	private int id_hispos;
	private Integer id_viaje;
	
	
	
	public  AlertaHistorial(int id_alerta, Timestamp ah_fecha_activacion, int id_vehiculo, int id_estado_atencion,
			int id_hispos, int id_viaje) {
		super();
		this.id_alerta = id_alerta;
		this.ah_fecha_activacion = ah_fecha_activacion;
		this.id_vehiculo = id_vehiculo;
		this.id_estado_atencion = id_estado_atencion;
		this.id_hispos = id_hispos;
		this.id_viaje = id_viaje == -1?null:id_viaje;
	}
	
	public AlertaHistorial(){
		super();
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getId_alerta() {
		return id_alerta;
	}
	public void setId_alerta(int id_alerta) {
		this.id_alerta = id_alerta;
	}
	public Timestamp getAh_fecha_activacion() {
		return ah_fecha_activacion;
	}
	public void setAh_fecha_activacion(Timestamp ah_fecha_activacion) {
		this.ah_fecha_activacion = ah_fecha_activacion;
	}
	public int getId_vehiculo() {
		return id_vehiculo;
	}
	public void setId_vehiculo(int id_vehiculo) {
		this.id_vehiculo = id_vehiculo;
	}
	public int getId_estado_atencion() {
		return id_estado_atencion;
	}
	public void setId_estado_atencion(int id_estado_atencion) {
		this.id_estado_atencion = id_estado_atencion;
	}
	public int getId_hispos() {
		return id_hispos;
	}
	public void setId_hispos(int id_hispos) {
		this.id_hispos = id_hispos;
	}
	public Integer getId_viaje() {
		return id_viaje;
	}
	public void setId_viaje(Integer id_viaje) {
		this.id_viaje = id_viaje;
	}
	@Override
	public String toString() {
		return "AlertaHistorial [id=" + id + ", id_alerta=" + id_alerta + ", ah_fecha_activacion=" + ah_fecha_activacion
				+ ", id_vehiculo=" + id_vehiculo + ", id_estado_atencion=" + id_estado_atencion + ", id_hispos="
				+ id_hispos + ", id_viaje=" + id_viaje + "]";
	}
	
	

}
