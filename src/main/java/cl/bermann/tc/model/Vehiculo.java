package cl.bermann.tc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "vehiculo_transportista")
public class Vehiculo {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    @Column(name= "veh_patente")
    private String vehpatente;
    //private int id_vehiculo_conductor;
    private int id_estado;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getVehpatente() {
        return vehpatente;
    }
    public void setVehpatente(String vehpatente) {
        this.vehpatente = vehpatente;
    }
    /*  
    public int getId_vehiculo_conductor() {
        return id_vehiculo_conductor;
    }
    public void setId_vehiculo_conductor(int id_vehiculo_conductor) {
        this.id_vehiculo_conductor = id_vehiculo_conductor;
    }
    */
	public int getId_estado() {
		return id_estado;
	}
	public void setId_estado(int id_estado) {
		this.id_estado = id_estado;
	}

    
}
