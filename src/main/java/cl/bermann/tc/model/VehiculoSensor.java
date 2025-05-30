package cl.bermann.tc.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "vehiculo_sensor")
public class VehiculoSensor {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private int id_estado;
	@Column(name = "id_gps")
	private int vehid;
	@Column(name = "id_sensor")
	private int sensorid;
	@Column(name = "vse_ultima_fecha")
	private Timestamp ult_fecha;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getId_estado() {
		return id_estado;
	}
	public void setId_estado(int id_estado) {
		this.id_estado = id_estado;
	}
	public int getVehid() {
		return vehid;
	}
	public void setVehid(int vehid) {
		this.vehid = vehid;
	}
	public int getSensorid() {
		return sensorid;
	}
	public void setSensorid(int sensorid) {
		this.sensorid = sensorid;
	}
	public Timestamp getUlt_fecha() {
		return ult_fecha;
	}
	public void setUlt_fecha(Timestamp ult_fecha) {
		this.ult_fecha = ult_fecha;
	}
	@Override
	public String toString() {
		return "VehiculoSensor [id=" + id + ", id_estado=" + id_estado + ", id_veh=" + vehid + ", id_sensor="
				+ sensorid + ", ult_fecha=" + ult_fecha + "]";
	}
	
	
}
