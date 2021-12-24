/**
 * Objeto que representa al cliente del banco.
 * 
 * @author Mario Gabriel Núñez Alcázar de Velasco
 */
public class Cliente {
	
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
	 * Constructor vacío, para llamar a la clase Cliente sin datos.
	 */
	public Cliente() {}

	/**
	 * Constructor con datos para crear objetos Cliente.
	 * 
	 * @param nif - Numero de Identificacion Fiscal del cliente.
	 * @param nmbr - Nombre del cliente.
	 * @param apllds - Apellidos del cliente.
	 * @param tlfn - Telefono del cliente.
	 * @param eml - Email del cliente.
	 * @param dmcl - Domicilio del cliente.
	 */
	public Cliente(String nif, String nmbr, String apllds, String tlfn, String eml, String dmcl) {
		this.nif = nif; //Establecemos el NIF cel cliente
		this.nmbr = nmbr; //Establecemos el Nombre del cliente
		this.apllds = apllds; //Establecemos los apellidos del cliente
		this.tlfn = tlfn; //Establecemos el Telefono del cliente
		this.eml = eml; //Establecemos el Email del cliente 
		this.dmcl = dmcl; //Establecemos el Domicilio del cliente
	}
	
	/**
	 * Clase para mostrar en consola los diferentes objetos creados con esta clase.
	 * @return String - datos del objeto.
	 */
	@Override
	public String toString() {
		return "Cliente [nif=" + nif + ", \n\t nmbr=" + nmbr + ", \n\t apllds=" + apllds
				+ ", \n\t tlfn=" + tlfn + ", \n\t eml=" + eml + ", \n\t dmcl=" + dmcl + "]\n";
	}

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
}
