package cl.bermann.tc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "sensor")
public class Sensor {
@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private int id_estado;
	@Column(name = "se_nombre")
	private String nombre;
	@Column(name = "se_identificador")
	private String ident;
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
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getIdent() {
        return ident;
    }
    public void setIdent(String ident) {
        this.ident = ident;
    }
    @Override
    public String toString() {
        return "Sensor [id=" + id + ", id_estado=" + id_estado + ", nombre=" + nombre + ", ident=" + ident + "]";
    }


}
