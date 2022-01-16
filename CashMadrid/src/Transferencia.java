import java.util.*;

/**
 * Objeto que representa los datos de una Transferencia Existente en la Base de Datos
 * 
 * @author Mario Gabriel Núñez Alcázar de Velasco
 */
public class Transferencia {
	/**
	 * Referencia de la transferencia.
	 */
	private int ref;
	/**
	 * Cuenta Origen de la transferencia.
	 */
	private int origen;
	/**
	 * Cuenta Destino de la transferencia.
	 */
	private int destino;
	/**
	 * Fecha de la transferencia.
	 */
	private Date fTransf;
	/**
	 * Importe de la transferencia.
	 */
	private Double imp;
	/**
	 * Concepto de la transferencia.
	 */
	private String cncpt;
	
	/**
	 * Constructor vacío
	 */
	public Transferencia() {}

	/**
	 * Método Get para obtener el Número interno de la cuenta Origen de la transferencia.
	 * @return Origen - Número interno de la cuenta Origen
	 */
	public int getOrigen() {
		return origen;
	}

	/**
	 * Método Get para obtener el Número interno de la cuenta Destino de la transferencia.
	 * @return Destino - Número interno de la cuenta Destino
	 */
	public int getDestino() {
		return destino;
	}

	/**
	 * Método Get para obtener la Fecha de la transferencia.
	 * @return fTransf - Fecha de la transferencia.
	 */
	public Date getfTransf() {
		return fTransf;
	}

	/**
	 * Método Get para obtener el Importe de la transferencia.
	 * @return imp - Importe de la transferencia.
	 */
	public Double getImp() {
		return imp;
	}

	/**
	 * Método Get para obtener el Concepto de la transferencia.
	 * @return cncpt - Concepto de la transferencia.
	 */
	public String getCncpt() {
		return cncpt;
	}

	/**
	 * Método Set para establecer el Número interno de la cuenta Origen de la transferencia.
	 * @param origen - Número interno de la cuenta Origen de la transferencia.
	 */
	public void setOrigen(int origen) {
		this.origen = origen;
	}

	/**
	 * Método Set para establecer el Número interno de la cuenta Destino de la transferencia.
	 * @param destino - Número interno de la cuenta Destino de la transferencia.
	 */
	public void setDestino(int destino) {
		this.destino = destino;
	}

	/**
	 * Método Set para establecer la Fecha de la transferencia.
	 * @param fTransf - la Fecha de la transferencia.
	 */
	public void setfTransf(Date fTransf) {
		this.fTransf = fTransf;
	}

	/**
	 * Método Set para establecer el Importe de la transferencia.
	 * @param imp - el Importe de la transferencia.
	 */
	public void setImp(Double imp) {
		this.imp = imp;
	}

	/**
	 * Método Set para establecer el Concepto de la transferencia.
	 * @param cncpt - Concepto de la transferencia.
	 */
	public void setCncpt(String cncpt) {
		this.cncpt = cncpt;
	}

	/**
	 * Método Get para obtener la Referencia de la transferencia.
	 * @return ref - Referencia de la transferencia.
	 */
	public int getRef() {
		return ref;
	}

	/**
	 * Método Set para establecer la Referencia de la transferencia.
	 * @param ref - la Referencia de la transferencia.
	 */
	public void setRef(int ref) {
		this.ref = ref;
	}
}
