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
@Table (name = "ultpos_up")
public class Ultpos {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		private int id_estado;
		private Timestamp up_fecha;
		@Column(name = "id_dispositivo")
		private Integer dispositivo;
		private String up_latitud;
		private String up_longitud;
		private int up_velocidad;
		private int up_estado_motor;
		private String up_direccion;
		@Column(columnDefinition = "bigint[]")
	    @Type(type = "cl.bermann.tc.model.CustomLongArrayType")
		private Long[] up_zonas_in;
		private int up_distancia;
		private int up_estado;
		private Timestamp up_fecha_ins;
		private String up_patente;
		private String av_serie;
		private int up_tiempo_intervalo;
		@Column(columnDefinition = "jsonb")
		private String up_datos;
		private int id_hispos;
		
		
	/*	public Ultpos(Timestamp hp_fecha, int id_dispositivo2, String hp_latitud, String hp_longitud, int hp_velocidad,
				String hp_direccion, int hp_estado_motor, String hp_zonas_in, int hp_distancia, int hp_estado,
				Timestamp hp_fecha_ins, String hp_patente) {
			// TODO Auto-generated constructor stub
		}*/
		
		
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
		public Timestamp getUp_fecha() {
			return up_fecha;
		}
		public void setUp_fecha(Timestamp up_fecha) {
			this.up_fecha = up_fecha;
		}
		
		public void setDispositivo(Integer dispositivo) {
			this.dispositivo = dispositivo;
		}
		public int getDispositivo() {
			return dispositivo;
		}
		public String getUp_latitud() {
			return up_latitud;
		}
		public void setUp_latitud(String up_latitud) {
			this.up_latitud = up_latitud;
		}
		public String getUp_longitud() {
			return up_longitud;
		}
		public void setUp_longitud(String up_longitud) {
			this.up_longitud = up_longitud;
		}
		public int getUp_velocidad() {
			return up_velocidad;
		}
		public void setUp_velocidad(int up_velocidad) {
			this.up_velocidad = up_velocidad;
		}
		public int getUp_estado_motor() {
			return up_estado_motor;
		}
		public void setUp_estado_motor(int up_estado_motor) {
			this.up_estado_motor = up_estado_motor;
		}
		public String getUp_direccion() {
			return up_direccion;
		}
		public void setUp_direccion(String up_direccion) {
			this.up_direccion = up_direccion;
		}
		public Long[] getUp_zonas_in() {
			return up_zonas_in;
		}
		public void setUp_zonas_in(Long[] up_zonas_in) {
			this.up_zonas_in = up_zonas_in;
		}
		public int getUp_distancia() {
			return up_distancia;
		}
		public void setUp_distancia(int up_distancia) {
			this.up_distancia = up_distancia;
		}
		public int getUp_estado() {
			return up_estado;
		}
		public void setUp_estado(int up_estado) {
			this.up_estado = up_estado;
		}
		public Timestamp getUp_fecha_ins() {
			return up_fecha_ins;
		}
		public void setUp_fecha_ins(Timestamp up_fecha_ins) {
			this.up_fecha_ins = up_fecha_ins;
		}
		public String getUp_patente() {
			return up_patente;
		}
		public void setUp_patente(String up_patente) {
			this.up_patente = up_patente;
		}
		
		public String getAv_serie() {
			return av_serie;
		}


		public void setAv_serie(String av_serie) {
			this.av_serie = av_serie;
		}


		public int getUp_tiempo_intervalo() {
			return up_tiempo_intervalo;
		}
		public void setUp_tiempo_intervalo(int up_tiempo_intervalo) {
			this.up_tiempo_intervalo = up_tiempo_intervalo;
		}
				
		public String getUp_datos() {
			return up_datos;
		}
		public void setUp_datos(String up_datos) {
			this.up_datos = up_datos;
		}
		@Override
		public String toString() {
			return "Ultpos [id=" + id + ", id_estado=" + id_estado + ", up_fecha=" + up_fecha + ", id_dispositivo="
					+ dispositivo + ", up_latitud=" + up_latitud + ", up_longitud=" + up_longitud + ", up_velocidad="
					+ up_velocidad + ", up_estado_motor=" + up_estado_motor + ", up_direccion=" + up_direccion
					+ ", up_zonas_in=" + up_zonas_in + ", up_distancia=" + up_distancia + ", up_estado=" + up_estado
					+ ", up_fecha_ins=" + up_fecha_ins + ", up_patente=" + up_patente + ", up_tiempo_intervalo=" + up_tiempo_intervalo + ", up_datos=" + up_datos + "]";
		}
		public int getId_hispos() {
			return id_hispos;
		}
		public void setId_hispos(int id_hispos) {
			this.id_hispos = id_hispos;
		}

		public JSONObject getDatosJson() {
			return new JSONObject(this.getUp_datos());
		}
		
		

}
