package cl.bermann.tc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "vehiculo_conductor")
public class Conductor {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String cdr_nombre;
	private String ibutton;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCdr_nombre() {
		return cdr_nombre;
	}
	public void setCdr_nombre(String cdr_nombre) {
		this.cdr_nombre = cdr_nombre;
	}
	public String getIbutton() {
		return ibutton;
	}
	public void setIbutton(String ibutton) {
		this.ibutton = ibutton;
	}
	@Override
	public String toString() {
		return "Conductor [id=" + id + ", cdr_nombre=" + cdr_nombre + ", ibutton=" + ibutton + "]";
	} 
    
	
}
