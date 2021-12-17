import java.util.*;

/**
 * Objeto que representa al cliente
 */

/**
 * @author Mario Gabriel Núñez Alcázar de Velasco
 *
 */
public class Cliente {
	private String nif; // NIF del cliente
	private String nmbr; // Nombre del cliente
	private String apllds; // Apellidos del cliente
	private String tlfn; // Telefono del cliente
	private String eml; // Email del cliente
	private String dmcl; // Domicilio del cliente	
	
	/**
	 * Constructor vacío
	 */
	public Cliente() {}

	/**
	 * @param nif
	 * @param nmbr
	 * @param apllds
	 * @param tlfn
	 * @param eml
	 * @param dmcl
	 */
	public Cliente(String nif, String nmbr, String apllds, String tlfn, String eml, String dmcl) {
		this.nif = nif;
		this.nmbr = nmbr;
		this.apllds = apllds;
		this.tlfn = tlfn;
		this.eml = eml;
		this.dmcl = dmcl;
	}

	@Override
	public String toString() {
		return "Cliente [nif=" + nif + ", \n\t\t\t\t\t\tnmbr=" + nmbr + ", \n\t\t\t\t\t\tapllds=" + apllds
				+ ", \n\t\t\t\t\t\ttlfn=" + tlfn + ", \n\t\t\t\t\t\teml=" + eml + ", \n\t\t\t\t\t\tdmcl=" + dmcl + "]";
	}

	/**
	 * @return the nif
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * @return the nmbr
	 */
	public String getNmbr() {
		return nmbr;
	}

	/**
	 * @return the apllds
	 */
	public String getApllds() {
		return apllds;
	}

	/**
	 * @return the tlfn
	 */
	public String getTlfn() {
		return tlfn;
	}

	/**
	 * @return the eml
	 */
	public String getEml() {
		return eml;
	}

	/**
	 * @return the dmcl
	 */
	public String getDmcl() {
		return dmcl;
	}

	/**
	 * @param nif the nif to set
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}

	/**
	 * @param nmbr the nmbr to set
	 */
	public void setNmbr(String nmbr) {
		this.nmbr = nmbr;
	}

	/**
	 * @param apllds the apllds to set
	 */
	public void setApllds(String apllds) {
		this.apllds = apllds;
	}

	/**
	 * @param tlfn the tlfn to set
	 */
	public void setTlfn(String tlfn) {
		this.tlfn = tlfn;
	}

	/**
	 * @param eml the eml to set
	 */
	public void setEml(String eml) {
		this.eml = eml;
	}

	/**
	 * @param dmcl the dmcl to set
	 */
	public void setDmcl(String dmcl) {
		this.dmcl = dmcl;
	}
}
