/**
 * Clase que representa un Objeto login, que correspondera al inicio de sesi�n del usuario
 * en la base de datos.
 * 
 * @author Mario Gabriel N��ez Alc�zar de Velasco.
 */
public class Login {
	/**
	 * String con los Datos de acceso a la base de datos.
	 */
	private String conStr;
	
	/**
	 * Objeto Conexion.
	 */
	private Conexion conexion;

	/**
	 * Constructor con Datos que usaremos para establecer una sesi�n.
	 * 
	 * @param conStr - String con los Datos de acceso a la base de datos.
	 */
	public Login(String conStr) {
		this.conStr = conStr;//Establecemos el String con los Datos de acceso a la base de datos.
	}
	
	/**
	 * Constructor con Datos que usaremos para establecer una sesi�n ya iniciada.
	 * 
	 * @param conexion - Objeto Conexion.
	 */
	public Login(Conexion conexion) {
		this.conexion = conexion;//Establecemos el Objeto conexion
	}

	/**
	 * M�todo Get para obtener la conexion del usuario a la Base de datos.
	 * 
	 * @return Conexion - conexion del usuario con la base de datos.
	 */
	public Conexion getConexion() {
		return conexion; //Devolvemos el Objeto Conexion
	}
	
	/**
	 * M�todo Get para obtener la conexion del usuario a la Base de datos.
	 * 
	 * @return String - Datos de la conexion del usuario con la base de datos.
	 */
	public String getConStr() {
		return conStr; //Devolvemos los Datos de la conexion del usuario con la base de datos
	}

	/**
	 * M�todo Set para establecer los datos de conexion del usuario con la base de datos.
	 * 
	 * @param conStr - Datos de la conexion del usuario con la base de datos.
	 */
	public void setConStr(String conStr) {
		this.conStr = conStr;//Establecemos los Datos de la conexion del usuario con la base de datos
	}

	/**
	 * M�todo Set para establecer la conexion del usuario a la Base de datos.
	 * 
	 * @param conexion - Conexion del usuario con la base de datos.
	 */
	public void setConexion(Conexion conexion) {
		this.conexion = conexion;//Establecemos el Objeto Conexion
	}
}
