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
	 * M�todo Get para obtener la conexion del usuario a la Base de datos.
	 * 
	 * @return String - Datos de la conexion del usuario con la base de datos.
	 */
	public String getConStr() {
		return conStr; //Devolvemos los Datos de la conexion del usuario con la base de datos
	}
}
