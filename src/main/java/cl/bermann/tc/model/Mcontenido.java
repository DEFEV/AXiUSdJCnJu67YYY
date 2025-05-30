package cl.bermann.tc.model;

import org.json.JSONObject;

public class Mcontenido {
	
	private String mc_fecha;
	private String latitud;
	private String longitud;
	private int vel;
	private int motor;
	private String mc_patente;
	private String otro;
	private int id_cli;
	
	public String getMc_fecha() {
		return mc_fecha;
	}
	public void setMc_fecha(String mc_fecha) {
		this.mc_fecha = mc_fecha;
	}
	public String getLatitud() {
		return latitud;
	}
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	public String getLongitud() {
		return longitud;
	}
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	public int getVel() {
		return vel;
	}
	public void setVel(int velocidad) {
		this.vel = velocidad;
	}
	public int getMotor() {
		return motor;
	}
	public void setMotor(int motor) {
		this.motor = motor;
	}
	public String getMc_patente() {
		return mc_patente;
	}
	public void setMc_patente(String mc_patente) {
		this.mc_patente = mc_patente;
	}
	public String getOtro() {
		return otro;
	}
	public void setOtro(String otro) {
		this.otro = otro;
	}

	public JSONObject getOtroJson() {
		return new JSONObject(this.getOtro());
	}

	public int getId_cli() {
		return id_cli;
	}

	public void setId_cli(int id_cli) {
		this.id_cli = id_cli;
	}
	@Override
	public String toString() {
		return "Mcontenido [mc_fecha=" + mc_fecha + ", latitud=" + latitud + ", longitud=" + longitud + ", vel=" + vel
				+ ", motor=" + motor + ", mc_patente=" + mc_patente + ", otro=" + otro +", id_cli=" + id_cli + "]";
	}

	
}
