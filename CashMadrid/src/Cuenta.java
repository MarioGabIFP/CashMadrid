import java.util.*;

/**
 * Objeto que representa la cuenta de un cliente
 */

/**
 * @author Mario Gabriel Núñez Alcázar de Velasco
 */
public class Cuenta {
	// **********************************************************
	// Formato del IBAN de la cuenta: ESXX XXXX XXXX XX XXXXXXXXX
	//                                 4     4    4   2     9
	private String iban; // Número de cliente
	// **********************************************************
	private String nmbrbnc; // Nombre del banco
	private Cliente titular; // El cliente como tal
	private double saldo; // NIF del Trabajador
	private Date fechaApertura; // Fecha de inicio de contrato Apertura de la cuenta
	private Date fechaCierre; // Fecha de inicio de contrato Apertura de la cuenta
	
	/**
	 * Constructor sin datos
	 */
	public Cuenta(){}
	
	/**
	 * Constructor con datos
	 * @param iban
	 * @param nmbrbnc
	 * @param titular
	 * @param saldo
	 * @param fechaApertura
	 * @param fechaCierre
	 */
	public Cuenta(String iban, String nmbrbnc, Cliente titular, double saldo) {
		this.iban = iban;
		this.nmbrbnc = nmbrbnc;
		this.titular = titular;
		this.saldo = saldo;
		this.fechaApertura = new Date();
	}

	@Override
	public String toString() {
		return "Cuenta [iban=" + iban + ", \n\t\t\t\t\t\tnmbrbnc=" + nmbrbnc + ", \n\t\t\t\t\t\ttitular=" + titular
				+ ", \n\t\t\t\t\t\tsaldo=" + saldo + ", \n\t\t\t\t\t\tfechaApertura=" + fechaApertura
				+ ", \n\t\t\t\t\t\tfechaCierre=" + fechaCierre + "]";
	}

	/**
	 * @return the iban
	 */
	public String getIban() {
		return iban;
	}

	/**
	 * @return the nmbrbnc
	 */
	public String getNmbrbnc() {
		return nmbrbnc;
	}

	/**
	 * @return the titular
	 */
	public Cliente getTitular() {
		return titular;
	}

	/**
	 * @return the saldo
	 */
	public double getSaldo() {
		return saldo;
	}

	/**
	 * @return the fechaApertura
	 */
	public Date getFechaApertura() {
		return fechaApertura;
	}

	/**
	 * @return the fechaCierre
	 */
	public Date getFechaCierre() {
		return fechaCierre;
	}

	/**
	 * @param iban the iban to set
	 */
	public void setIban(String iban) {
		this.iban = iban;
	}

	/**
	 * @param nmbrbnc the nmbrbnc to set
	 */
	public void setNmbrbnc(String nmbrbnc) {
		this.nmbrbnc = nmbrbnc;
	}

	/**
	 * @param titular the titular to set
	 */
	public void setTitular(Cliente titular) {
		this.titular = titular;
	}

	/**
	 * @param saldo the saldo to set
	 */
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	/**
	 * @param fechaApertura the fechaApertura to set
	 */
	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	/**
	 * @param fechaCierre the fechaCierre to set
	 */
	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
}
