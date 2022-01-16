/**
 * Objeto que representa al cliente del banco.
 * 
 * @author Mario Gabriel Núñez Alcázar de Velasco
 */
public class Cliente {
	
	/**
	 * Numero interno de Identificacion
	 */
	private int idCli;
	
	/**
	 * Numero de Identificacion Fiscal del cliente.
	 */
	private String nif;
	
	/**
	 * Nombre del cliente.
	 */
	private String nmbr;
	
	/**
	 * Apellidos del cliente.
	 */
	private String apllds;
	
	/**
	 * Telefono del cliente.
	 */
	private String tlfn;
	
	/**
	 * Email del cliente.
	 */
	private String eml;
	
	/**
	 * Domicilio del cliente.
	 */
	private String dmcl;
	
	/**
	 * Estado del cliente; True - Cliente activo, False - Cliente dado de baja
	 */
	private Boolean stts;
	
	/**
	 * Constructor vacío, para llamar a la clase Cliente sin datos.
	 */
	public Cliente() {}

	/**
	 * Método Get para obtener el Numero de Identificacion Fiscal del cliente en el objeto.
	 * @return nif - Numero de Identificacion Fiscal del cliente.
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * Método Get para obtener el Nombre del cliente en el objeto.
	 * @return nmbr - Nombre del cliente.
	 */
	public String getNmbr() {
		return nmbr;
	}

	/**
	 * Método Get para obtener los apellidos del cliente en el objeto.
	 * @return apllds - Apellidos del cliente.
	 */
	public String getApllds() {
		return apllds;
	}

	/**
	 * Método Get para obtener el Telefono del cliente en el objeto.
	 * @return tlfn - Telefono del cliente.
	 */
	public String getTlfn() {
		return tlfn;
	}

	/**
	 * Método Get para obtener el E-Mail del cliente en el objeto.
	 * @return eml - E-Mail del cliente.
	 */
	public String getEml() {
		return eml;
	}

	/**
	 * Método Get para obtener el Domicilio del cliente en el objeto.
	 * @return dmcl - Domicilio del cliente.
	 */
	public String getDmcl() {
		return dmcl;
	}

	/**
	 * Método Set para establecer el NIF del cliente en el objeto.
	 * @param nif - NIF del cliente.
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}

	/**
	 * Método Set para establecer el Nombre del cliente en el objeto.
	 * @param nmbr - Nombre del cliente.
	 */
	public void setNmbr(String nmbr) {
		this.nmbr = nmbr;
	}

	/**
	 * Método Set para establecer los Apellidos del cliente en el objeto.
	 * @param apllds - Apellidos del cliente.
	 */
	public void setApllds(String apllds) {
		this.apllds = apllds;
	}

	/**
	 * Método Set para establecer el Telefono del cliente en el objeto.
	 * @param tlfn - Telefono del cliente.
	 */
	public void setTlfn(String tlfn) {
		this.tlfn = tlfn;
	}

	/**
	 * Método Set para establecer el E-Mail del cliente en el objeto.
	 * @param eml - E-Mail del cliente.
	 */
	public void setEml(String eml) {
		this.eml = eml;
	}

	/**
	 * Método Set para establecer el domicilio del cliente en el objeto.
	 * @param dmcl - Domicilio del cliente.
	 */
	public void setDmcl(String dmcl) {
		this.dmcl = dmcl;
	}

	/**
	 * Método Get para obtener el Identificador interno del cliente en el objeto.
	 * @return idCli - Identificador interno del cliente.
	 */
	public int getIdCli() {
		return idCli;
	}

	/**
	 * Método Set para establecer el Identificador interno del cliente en el objeto.
	 * @param idCli - Identificador interno del cliente.
	 */
	public void setIdCli(int idCli) {
		this.idCli = idCli;
	}

	/**
	 * Método Get para obtener el Estado del cliente en el objeto.
	 * @return stts - Estado del cliente.
	 */
	public Boolean getStts() {
		return stts;
	}

	/**
	 * Método Set para establecer el Estado interno del cliente en el objeto.
	 * @param stts - Estado del cliente.
	 */
	public void setStts(Boolean stts) {
		this.stts = stts;
	}
}
