package cl.bermann.tc.model;

import java.math.BigInteger;
import java.sql.Timestamp;

public class AlertaCondicion {

	private int id_alarma;
	private String ident;
	private String condicion;
	private String valor;
	private int tipo_alerta;
	private int id_vh;
	private int tobsoleto;
	private Timestamp tactual;

	public AlertaCondicion(int id_alarma, String ident, String condicion, String valor, int tipo_alerta, int id_vh,
			int tobsoleto, Timestamp tactual) {
		super();
		this.id_alarma = id_alarma;
		this.ident = ident;
		this.condicion = condicion;
		this.valor = valor;
		this.tipo_alerta = tipo_alerta;
		this.id_vh = id_vh;
		this.tobsoleto = tobsoleto;
		this.tactual = tactual;
	}

	public AlertaCondicion(Object[] columns) {

		this.id_alarma = (columns[0] != null) ? ((BigInteger) columns[0]).intValue() : 0;
		this.ident = (String) columns[1];
		this.condicion = (String) columns[2];
		this.valor = (String) columns[3];
		this.tipo_alerta = (columns[4] != null) ? ((Short) columns[4]).intValue() : 0;
		this.id_vh = (columns[5] != null) ? ((BigInteger) columns[5]).intValue() : 0;
		this.tobsoleto = (columns[6] != null) ? ((Integer) columns[6]).intValue() : -1;
		this.tactual = (Timestamp) columns[7];
	}

	public int getId_alarma() {
		return id_alarma;
	}

	public void setId_alarma(int id_alarma) {
		this.id_alarma = id_alarma;
	}

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public int getTipo_alerta() {
		return tipo_alerta;
	}

	public void setTipo_alerta(int tipo_alerta) {
		this.tipo_alerta = tipo_alerta;
	}

	public int getId_vh() {
		return id_vh;
	}

	public void setId_vh(int id_vh) {
		this.id_vh = id_vh;
	}

	public int getTobsoleto() {
		return tobsoleto;
	}

	public void setTobsoleto(int tobsoleto) {
		this.tobsoleto = tobsoleto;
	}

	public Timestamp getTactual() {
		return tactual;
	}

	public void setTactual(Timestamp tactual) {
		this.tactual = tactual;
	}

	@Override
	public String toString() {
		return "AlertaCondicion [id_alarma=" + id_alarma + ", ident=" + ident + ", condicion=" + condicion + ", valor="
				+ valor + ", tipo_alerta=" + tipo_alerta + ", id_vh=" + id_vh + ", tobsoleto=" + tobsoleto
				+ ", tactual=" + tactual + "]";
	}

}
