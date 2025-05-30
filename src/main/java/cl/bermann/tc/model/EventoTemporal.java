package cl.bermann.tc.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "evento_temporal")
public class EventoTemporal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "id_estado")
	private int estado;
	private int va_id;
	private Timestamp et_fecha_inicio;
	private Timestamp et_fecha_fin;
	private int et_lapso_tiempo;
	private int id_hispos;
	private int id_vehiculo;
	private int et_estado_alarma;
	private boolean et_tipo_perdidasenal;
	
	
	public int getId_hispos() {
		return id_hispos;
	}

	public void setId_hispos(int id_hispos) {
		this.id_hispos = id_hispos;
	}

//	public EventoTemporal(int va_id, Timestamp et_fecha_inicio, int et_lapso_tiempo, int id_hispos, int id_vehiculo) {
//		super();
//		this.va_id = va_id;
//		this.et_fecha_inicio = et_fecha_inicio;
//		this.et_lapso_tiempo = et_lapso_tiempo;
//		this.id_hispos = id_hispos;
//		this.id_vehiculo = id_vehiculo;
//	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public int getVa_id() {
		return va_id;
	}
	public void setVa_id(int va_id) {
		this.va_id = va_id;
	}
	public Timestamp getEt_fecha_inicio() {
		return et_fecha_inicio;
	}
	public void setEt_fecha_inicio(Timestamp et_fecha_inicio) {
		this.et_fecha_inicio = et_fecha_inicio;
	}
	public Timestamp getEt_fecha_fin() {
		return et_fecha_fin;
	}
	public void setEt_fecha_fin(Timestamp et_fecha_fin) {
		this.et_fecha_fin = et_fecha_fin;
	}
	public int getEt_lapso_tiempo() {
		return et_lapso_tiempo;
	}
	public void setEt_lapso_tiempo(int et_lapso_tiempo) {
		this.et_lapso_tiempo = et_lapso_tiempo;
	}
	
	
	public int getId_vehiculo() {
		return id_vehiculo;
	}

	public void setId_vehiculo(int id_vehiculo) {
		this.id_vehiculo = id_vehiculo;
	}

	public int getEt_estado_alarma() {
		return et_estado_alarma;
	}

	public void setEt_estado_alarma(int et_estado_alarma) {
		this.et_estado_alarma = et_estado_alarma;
	}

	public boolean isEt_tipo_perdidasenal() {
		return et_tipo_perdidasenal;
	}

	public void setEt_tipo_perdidasenal(boolean et_tipo_perdidasenal) {
		this.et_tipo_perdidasenal = et_tipo_perdidasenal;
	}

	@Override
	public String toString() {
		return "EventoTemporal [id=" + id + ", estado=" + estado + ", va_id=" + va_id + ", et_fecha_inicio="
				+ et_fecha_inicio + ", et_fecha_fin=" + et_fecha_fin + ", et_lapso_tiempo=" + et_lapso_tiempo + ", id_vehiculo=" + id_vehiculo 
				+ ", et_estado_alarma=" + et_estado_alarma + ", et_tipo_perdidasenal=" + et_tipo_perdidasenal + "]";
	}
	
	
}
