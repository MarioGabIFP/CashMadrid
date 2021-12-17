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
	private double saldo;
	
	/**
	 * @param origen
	 * @param destino
	 * @param saldo
	 */
	public Transferencia(Cuenta origen, Cuenta destino, double saldo) {
		this.origen = origen;
		this.destino = destino;
		this.saldo = saldo;
	}

	/**
	 * @return the origen
	 */
	public Cuenta getOrigen() {
		return origen;
	}

	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(Cuenta origen) {
		this.origen = origen;
	}

	/**
	 * @return the destino
	 */
	public Cuenta getDestino() {
		return destino;
	}

	/**
	 * @param destino the destino to set
	 */
	public void setDestino(Cuenta destino) {
		this.destino = destino;
	}

	/**
	 * @return the saldo
	 */
	public double getSaldo() {
		return saldo;
	}

	/**
	 * @param saldo the saldo to set
	 */
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	@Override
	public String toString() {
		return "Transferencia [origen=" + origen + ", destino=" + destino + ", saldo=" + saldo + "]";
	}
}
