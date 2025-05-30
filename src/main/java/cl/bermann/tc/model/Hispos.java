package cl.bermann.tc.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.json.JSONObject;


@Entity
@Table (name = "hispos_hp")
public class Hispos {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private int id_estado;
	private int id_ultpos;
	private Timestamp hp_fecha;
	private int id_dispositivo;
	private String hp_latitud;
	private String hp_longitud;
	private int hp_velocidad;
	private int hp_estado_motor;
	private String hp_direccion;
	@Column(columnDefinition = "bigint[]")
    @Type(type = "cl.bermann.tc.model.CustomLongArrayType")
	private Long[] hp_zonas_in;
	private int hp_distancia;
	private int hp_estado;
	private Timestamp hp_fecha_ins;
	private String hp_patente;
	@Column(columnDefinition = "jsonb")
	private String hp_datos_sensor;
	private String av_serie;
	private int hp_intervalo_tiempo;
	
	
	
	public String getHp_patente() {
		return hp_patente;
	}
	public void setHp_patente(String hp_patente) {
		this.hp_patente = hp_patente;
	}
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
	public int getId_ultpos() {
		return id_ultpos;
	}
	public void setId_ultpos(int id_ultpos) {
		this.id_ultpos = id_ultpos;
	}
	public Timestamp getHp_fecha() {
		return hp_fecha;
	}
	public void setHp_fecha(Timestamp hp_fecha) {
		this.hp_fecha = hp_fecha;
	}
	public int getId_dispositivo() {
		return id_dispositivo;
	}
	public void setId_dispositivo(int id_dispositivo) {
		this.id_dispositivo = id_dispositivo;
	}
	public String getHp_latitud() {
		return hp_latitud;
	}
	public void setHp_latitud(String hp_latitud) {
		this.hp_latitud = hp_latitud;
	}
	public String getHp_longitud() {
		return hp_longitud;
	}
	public void setHp_longitud(String hp_longitud) {
		this.hp_longitud = hp_longitud;
	}
	public int getHp_velocidad() {
		return hp_velocidad;
	}
	public void setHp_velocidad(int hp_velocidad) {
		this.hp_velocidad = hp_velocidad;
	}
	public int getHp_estado_motor() {
		return hp_estado_motor;
	}
	public void setHp_estado_motor(int hp_estado_motor) {
		this.hp_estado_motor = hp_estado_motor;
	}
	public String getHp_direccion() {
		return hp_direccion;
	}
	public void setHp_direccion(String hp_direccion) {
		this.hp_direccion = hp_direccion;
	}
	public Long[] getHp_zonas_in() {
		return hp_zonas_in;
	}
	public void setHp_zonas_in(Long[] hp_zonas_in) {
		this.hp_zonas_in = hp_zonas_in;
	}
	public int getHp_distancia() {
		return hp_distancia;
	}
	public void setHp_distancia(int hp_distancia) {
		this.hp_distancia = hp_distancia;
	}
	public int getHp_estado() {
		return hp_estado;
	}
	public void setHp_estado(int hp_estado) {
		this.hp_estado = hp_estado;
	}
	public Timestamp getHp_fecha_ins() {
		return hp_fecha_ins;
	}
	public void setHp_fecha_ins(Timestamp hp_fecha_ins) {
		this.hp_fecha_ins = hp_fecha_ins;
	}
	public String getHp_datos_sensor() {
		return hp_datos_sensor;
	}
	public void setHp_datos_sensor(String hp_datos_sensor) {
		this.hp_datos_sensor = hp_datos_sensor;
	}
	
	public String getAv_serie() {
		return av_serie;
	}
	public void setAv_serie(String av_serie) {
		this.av_serie = av_serie;
	}
	
	
	public int getHp_intervalo_tiempo() {
		return hp_intervalo_tiempo;
	}
	public void setHp_intervalo_tiempo(int hp_intervalo_tiempo) {
		this.hp_intervalo_tiempo = hp_intervalo_tiempo;
	}
	
	public JSONObject getDatosJson() {
		return new JSONObject(this.getHp_datos_sensor());
	}
	
	@Override
	public String toString() {
		return "Hispos [id=" + id + ", id_estado=" + id_estado + ", id_ultpos=" + id_ultpos + ", hp_fecha=" + hp_fecha
				+ ", id_dispositivo=" + id_dispositivo + ", hp_latitud=" + hp_latitud + ", hp_longitud=" + hp_longitud
				+ ", hp_velocidad=" + hp_velocidad + ", hp_estado_motor=" + hp_estado_motor + ", hp_direccion="
				+ hp_direccion + ", hp_zonas_in=" + hp_zonas_in + ", hp_distancia=" + hp_distancia + ", hp_estado="
				+ hp_estado + ", hp_fecha_ins=" + hp_fecha_ins + ", hp_patente=" + hp_patente + ", hp_datos_sensor=" 
				+ hp_datos_sensor + ", av_serie=" + av_serie + ", hp_intervalo_tiempo=" + hp_intervalo_tiempo + "]";
	}
}
