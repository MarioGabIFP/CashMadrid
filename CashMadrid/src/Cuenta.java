import java.util.*;

/**
 * Objeto que representa la cuenta de un cliente.
 * 
 * @author Mario Gabriel N��ez Alc�zar de Velasco
 */
public class Cuenta {
	/**
	 * Numero IBAN de la cuenta del cliente.
	 * 
	 * <br><br>Formato del IBAN de la cuenta.<br>
	 * <img src="./resources/img/jpg/codigo-iban.jpg" alt="Esquema del formato" style="width: 50%">
	 */
	/*
	 * **********************************************************
	 * Formato del IBAN de la cuenta: ESXX XXXX XXXX XX XXXXXXXXX
	 *                                 4     4    4   2     10
	 * ********************************************************** 
	 */
	private String iban;
	
	/**
	 * N�mero de identificaci�n interno de la cuenta.
	 */
	private int idCu;
	
	/**
	 * Nombre del Banco.
	 */
	private String nmbrbnc;
	
	/**
	 * Titular de la cuenta bancaria.
	 */
	private Cliente titular;
	
	/**
	 * Saldo actual de la cuenta bancaria.
	 * 
	 * <br><br>Resultado de la suma de todos los movimientos de la cuenta.
	 */
	private double saldo;
	
	/**
	 * Fecha de Apertura de la cuenta.
	 */
	private Date fechaApertura;
	
	/**
	 * Fecha de cierre de la cuenta.
	 */
	private Date fechaCierre;
	
	/**
	 * Constructor sin datos, para llamar a la clase Cuenta sin datos.
	 */
	public Cuenta(){}

	/**
	 * M�todo Get para obtener el IBAN de la cuenta en el objeto.
	 * 
	 * @return iban - IBAN de la cuenta.
	 */
	public String getIban() {
		return iban;
	}

	/**
	 * M�todo Get para obtener el Nombre de la entidad bancaria en el objeto.
	 * 
	 * @return nmbrbnc - Nombre de la entidad bancaria.
	 */
	public String getNmbrbnc() {
		return nmbrbnc;
	}

	/**
	 * M�todo Get para obtener el Titular de la cuenta en el objeto.
	 * 
	 * @return titular - Titular de la cuenta.
	 */
	public Cliente getTitular() {
		return titular;
	}

	/**
	 * M�todo Get para obtener el Saldo de la cuenta en el objeto.
	 * 
	 * @return saldo - Saldo de la cuenta.
	 */
	public double getSaldo() {
		return saldo;
	}

	/**
	 * M�todo Get para obtener La Fecha de apertura de la cuenta en el objeto.
	 * 
	 * @return fechaApertura - Fecha de apertura de la cuenta.
	 */
	public Date getFechaApertura() {
		return fechaApertura;
	}

	/**
	 * M�todo Get para obtener La Fecha de Cierre de la cuenta en el objeto.
	 * 
	 * @return fechaCierre - Fecha de Cierre de la cuenta.
	 */
	public Date getFechaCierre() {
		return fechaCierre;
	}

	/**
	 * M�todo Set para establecer el IBAN de la cuenta en el objeto.
	 * 
	 * @param iban - IBAN de la cuenta.
	 */
	public void setIban(String iban) {
		this.iban = iban;
	}

	/**
	 * M�todo Set para establecer el Nombre de la entidad bancaria en el objeto.
	 * 
	 * @param nmbrbnc - Nombre de la entidad bancaria.
	 */
	public void setNmbrbnc(String nmbrbnc) {
		this.nmbrbnc = nmbrbnc;
	}

	/**
	 * M�todo Set para establecer el Titular de la cuenta en el objeto.
	 * 
	 * @param titular - Titular de la cuenta.
	 */
	public void setTitular(Cliente titular) {
		this.titular = titular;
	}

	/**
	 * M�todo Set para establecer el Saldo de la cuenta en el objeto.
	 * 
	 * @param saldo - Saldo de la cuenta.
	 */
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	/**
	 * M�todo Set para establecer la Fecha de apertura de la cuenta en el objeto.
	 * 
	 * @param fechaApertura - Fecha de apertura de la cuenta.
	 */
	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	/**
	 * M�todo Set para establecer la Fecha de cierre de la cuenta en el objeto.
	 * 
	 * @param fechaCierre - Fecha de cierre de la cuenta.
	 */
	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	/**
	 * M�todo Get para obtener el N�mero de identificaci�n interno de la cuenta en el objeto.
	 * 
	 * @return idCu - N�mero de identificaci�n interno de la cuenta.
	 */
	public int getIdCu() {
		return idCu;
	}
	
	/**
	 * M�todo Set para establecer el N�mero de identificaci�n interno de la cuenta en el objeto.
	 * 
	 * @param idCu - N�mero de identificaci�n interno de la cuenta.
	 */
	public void setIdCu(int idCu) {
		this.idCu = idCu;
	}
}
