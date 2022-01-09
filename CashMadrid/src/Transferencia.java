import java.util.*;

/**
 * 
 */

/**
 * Objeto que representa la cuenta de un cliente
 * 
 * @author Mario Gabriel Núñez Alcázar de Velasco
 */
public class Transferencia {
	/**
	 * Cuenta Origen de la transferencia
	 */
	private Cuenta origen;
	/**
	 * Cuenta Destino de la transferencia
	 */
	private Cuenta destino;
	/**
	 * Fecha de l atransferencia
	 */
	private Date fTransf;
	/**
	 * Importe de la transferencia.
	 */
	private double imp;
	
	/**
	 * Constructor vacío
	 */
	public Transferencia() {}

	/**
	 * Constructor con datos
	 * 
	 * @param origen - Cuenta Origen de la transferencia
	 * @param destino - Cuenta destino de la transferencia
	 * @param fTransf - Fecha de la transferencia
	 * @param imp - Importe de la transferencia
	 */
	public Transferencia(Cuenta origen, Cuenta destino, Date fTransf, double imp) {
		this.origen = origen; //obtenemos la cuenta origen de la transferencia
		this.destino = destino; //obtenemos la cuenta destino de la transferencia
		this.fTransf = fTransf;//obtenemos la Fecha de la transferencia
		this.imp = imp;//obtenemos el Importe de la transferencia
	}

	/**
	 * Clase para mostrar en consola los diferentes objetos creados con esta clase.
	 * @return String - datos del objeto.
	 */
	@Override
	public String toString() {
		return "Transferencia [origen=" + origen + ", \n\t\t\t\t\t\tdestino=" + destino + ", \n\t\t\t\t\t\tfTransf="
				+ fTransf + ", \n\t\t\t\t\t\timp=" + imp + "]";
	}
}
