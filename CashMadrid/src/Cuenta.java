import java.util.*;

/**
 * Objeto que representa la cuenta de un cliente.
 * 
 * @author Mario Gabriel Núñez Alcázar de Velasco
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
	 * Número de identificación interno de la cuenta.
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
	 * Método Get para obtener el IBAN de la cuenta en el objeto.
	 * 
	 * @return iban - IBAN de la cuenta.
	 */
	public String getIban() {
		return iban;
	}

	/**
	 * Método Get para obtener el Nombre de la entidad bancaria en el objeto.
	 * 
	 * @return nmbrbnc - Nombre de la entidad bancaria.
	 */
	public String getNmbrbnc() {
		return nmbrbnc;
	}

	/**
	 * Método Get para obtener el Titular de la cuenta en el objeto.
	 * 
	 * @return titular - Titular de la cuenta.
	 */
	public Cliente getTitular() {
		return titular;
	}

	/**
	 * Método Get para obtener el Saldo de la cuenta en el objeto.
	 * 
	 * @return saldo - Saldo de la cuenta.
	 */
	public double getSaldo() {
		return saldo;
	}

	/**
	 * Método Get para obtener La Fecha de apertura de la cuenta en el objeto.
	 * 
	 * @return fechaApertura - Fecha de apertura de la cuenta.
	 */
	public Date getFechaApertura() {
		return fechaApertura;
	}

	/**
	 * Método Get para obtener La Fecha de Cierre de la cuenta en el objeto.
	 * 
	 * @return fechaCierre - Fecha de Cierre de la cuenta.
	 */
	public Date getFechaCierre() {
		return fechaCierre;
	}

	/**
	 * Método Set para establecer el IBAN de la cuenta en el objeto.
	 * 
	 * @param iban - IBAN de la cuenta.
	 */
	public void setIban(String iban) {
		this.iban = iban;
	}

	/**
	 * Método Set para establecer el Nombre de la entidad bancaria en el objeto.
	 * 
	 * @param nmbrbnc - Nombre de la entidad bancaria.
	 */
	public void setNmbrbnc(String nmbrbnc) {
		this.nmbrbnc = nmbrbnc;
	}

	/**
	 * Método Set para establecer el Titular de la cuenta en el objeto.
	 * 
	 * @param titular - Titular de la cuenta.
	 */
	public void setTitular(Cliente titular) {
		this.titular = titular;
	}

	/**
	 * Método Set para establecer el Saldo de la cuenta en el objeto.
	 * 
	 * @param saldo - Saldo de la cuenta.
	 */
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	/**
	 * Método Set para establecer la Fecha de apertura de la cuenta en el objeto.
	 * 
	 * @param fechaApertura - Fecha de apertura de la cuenta.
	 */
	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	/**
	 * Método Set para establecer la Fecha de cierre de la cuenta en el objeto.
	 * 
	 * @param fechaCierre - Fecha de cierre de la cuenta.
	 */
	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	/**
	 * Método Get para obtener el Número de identificación interno de la cuenta en el objeto.
	 * 
	 * @return idCu - Número de identificación interno de la cuenta.
	 */
	public int getIdCu() {
		return idCu;
	}
	
	/**
	 * Método Set para establecer el Número de identificación interno de la cuenta en el objeto.
	 * 
	 * @param idCu - Número de identificación interno de la cuenta.
	 */
	public void setIdCu(int idCu) {
		this.idCu = idCu;
	}
}
