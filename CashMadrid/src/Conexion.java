import java.sql.*;

/**
 * Clase para conectar a la base de datos; esta clase es la que se encargar� de realizar al conexi�n a la base de datos.
 * 
 * @author Mario Gabriel N��ez Alc�zar de Velasco
 */
public class Conexion {
	/**
	 * Nombre de la base de datos.
	 */
	private String nombreDB = "cashMadrid"; 
	/**
	 * Nombre de usuario de acceso a la base de datos.
	 */
	private String usuario = "root"; 
	/**
	 * Contrase�a de acceso a la base de datos.
	 */
	private String contrase�a = "ladesiempre";
	/**
	 * URL de acceso a la base de datos.
	 */
	private String url = "jdbc:mysql://Localhost:3306/" + nombreDB;
	
	/**
	 * Variable de tipo Connection, se usar� para establecer la conexion a true o false, seg�n se haya podido o no conectar a la base de datos.
	 */
	Connection conexion = null; //Establecemos la conexion por defecto a null
	
	/**
	 * Constructor sin datos de entrada, se usar� para declarar el objeto Conexion
	 */
	public Conexion() {}
	
	/**
	 * Metodo conect, se usar� para establecer la conexion a la base de datos.
	 */
	public void conect() {
		try {
			//Cargamos el DriverManager con el Class Loader
			Class.forName("com.mysql.cj.jdbc.Driver");
			try {
				//Establecemos la conexion a la base de datos
				conexion = DriverManager.getConnection(url, usuario, contrase�a); 
				if (conexion != null) {
					//Si la conexion ha sucedido correctamente, mostramos mensaje de entrada
					System.out.println("Conected to: " + url); 
				}
			} catch (SQLException e) {
				//Mostramos error en el caso de no poder conectar al abase de datos solicitada
				System.out.println("Unable to conected to: " + url);
				System.out.println("SQL exception returned: " + e);
			}
		} catch (ClassNotFoundException e) {
			//En el caso de no cargar el DriverManager, mostramos error interno
			System.out.println("Internal:" + e);
		}
	}
	
	/**
	 * Metodo para recibir el HashCode de la conexion establecida.
	 * @return conexion - Connection (HashCode).
	 */
	public Connection getConexion(){
		return conexion; // devolvemos el HashCode de conexion
	}
	
	/**
	 * M�todo para romper la conexi�n a la base de datos.
	 */
	public void desconexion() {
		conexion = null; //Establecemos el valor de la conexion a null
	}
}