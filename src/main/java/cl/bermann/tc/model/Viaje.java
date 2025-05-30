package cl.bermann.tc.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "viajes")
public class Viaje {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "estado_viaje")
	private Integer estado;
	private String nro_viaje;
	private int id_vehiculo;
	private Timestamp fecha_origen;
	private String cliente_del_cliente;

	public int getId_vehiculo() {
		return id_vehiculo;
	}
	public void setId_vehiculo(int id_vehiculo) {
		this.id_vehiculo = id_vehiculo;
	}
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
	public String getNro_viaje() {
		return nro_viaje;
	}
	public void setNro_viaje(String nro_viaje) {
		this.nro_viaje = nro_viaje;
	}
	public Timestamp getFecha_origen() {
		return fecha_origen;
	}
	public void setFecha_origen(Timestamp fecha_origen) {
		this.fecha_origen = fecha_origen;
	}
	
	public String getCliente_del_cliente() {
		return cliente_del_cliente;
	}
	public void setCliente_del_cliente(String cliente_del_cliente) {
		this.cliente_del_cliente = cliente_del_cliente;
	}
	@Override
	public String toString() {
		return "Viaje [id=" + id + ", id_vehiculo=" + id_vehiculo + ", estado_viaje=" + estado + ", nro_viaje=" + nro_viaje + ", fecha_origen="
				+ fecha_origen + ", clienteDelCliente=" + cliente_del_cliente + "]";
	}
	
	

}
