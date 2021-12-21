import java.util.*;

/**
 * 
 */

/**
 * @author Mario Gabriel Núñez Alcázar de Velasco
 *
 */
public class Transferencia {
	private Cuenta origen;
	private Cuenta destino;
	private Date fTransf;
	private double imp;
	
	/**
	 * Constructor vacío
	 */
	public Transferencia() {}

	/**
	 * Constructor con datos
	 * @param origen
	 * @param destino
	 * @param fTransf
	 * @param imp
	 */
	public Transferencia(Cuenta origen, Cuenta destino, Date fTransf, double imp) {
		this.origen = origen;
		this.destino = destino;
		this.fTransf = fTransf;
		this.imp = imp;
	}

	@Override
	public String toString() {
		return "Transferencia [origen=" + origen + ", \n\t\t\t\t\t\tdestino=" + destino + ", \n\t\t\t\t\t\tfTransf="
				+ fTransf + ", \n\t\t\t\t\t\timp=" + imp + "]";
	}
}
