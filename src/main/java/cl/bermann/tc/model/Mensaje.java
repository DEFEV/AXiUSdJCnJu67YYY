package cl.bermann.tc.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("mensaje_concentrador_mc")
public class Mensaje {

	@Id
	private String id;
	// agregar mc_id
	private Mcontenido mc_contenido;
	private int mc_estado;
	private String id_servicio;
	private String id_proveedor;
	private String id_vehiculo;
	private String mc_ip;
	private String mc_fecha_in;
	private String mc_delay;
	
	public Mensaje(String id, Mcontenido mc_contenido, int mc_estado, String id_servicio, String id_proveedor,
			String id_vehiculo, String mc_ip, String mc_fecha_in, String mc_delay) {
		super();
		this.id = id;
		this.mc_contenido = mc_contenido;
		this.mc_estado = mc_estado;
		this.id_servicio = id_servicio;
		this.id_proveedor = id_proveedor;
		this.id_vehiculo = id_vehiculo;
		this.mc_ip = mc_ip;
		this.mc_fecha_in = mc_fecha_in;
		this.mc_delay = mc_delay;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Mcontenido getMc_contenido() {
		return mc_contenido;
	}

	public void setMc_contenido(Mcontenido mc_contenido) {
		this.mc_contenido = mc_contenido;
	}

	public int getMc_estado() {
		return mc_estado;
	}

	public void setMc_estado(int mc_estado) {
		this.mc_estado = mc_estado;
	}

	public String getId_servicio() {
		return id_servicio;
	}

	public void setId_servicio(String id_servicio) {
		this.id_servicio = id_servicio;
	}

	public String getId_proveedor() {
		return id_proveedor;
	}

	public void setId_proveedor(String id_proveedor) {
		this.id_proveedor = id_proveedor;
	}

	public String getId_vehiculo() {
		return id_vehiculo;
	}

	public void setId_vehiculo(String id_vehiculo) {
		this.id_vehiculo = id_vehiculo;
	}

	public String getMc_ip() {
		return mc_ip;
	}

	public void setMc_ip(String mc_ip) {
		this.mc_ip = mc_ip;
	}

	public String getMc_fecha_in() {
		return mc_fecha_in;
	}

	public void setMc_fecha_in(String mc_fecha_in) {
		this.mc_fecha_in = mc_fecha_in;
	}

	public String getMc_delay() {
		return mc_delay;
	}

	public void setMc_delay(String mc_delay) {
		this.mc_delay = mc_delay;
	}

	@Override
	public String toString() {
		return "Mensaje [id=" + id + ",proovedor=" + id_proveedor + ",estado=" + mc_estado + ", mc_contenido=" + mc_contenido.toString() + ", id_vehiculo=" + id_vehiculo + "]";
	}
	
	
}
