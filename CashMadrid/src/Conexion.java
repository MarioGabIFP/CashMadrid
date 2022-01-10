import java.sql.*;

import javax.swing.JOptionPane;

/**
 * Clase para conectar a la base de datos; esta clase es la que se encargará de realizar al conexión a la base de datos.
 * 
 * @author Mario Gabriel Núñez Alcázar de Velasco
 */
public class Conexion {
	/**
	 * Nombre de la base de datos.
	 */
	private String nombreDB = "cashMadrid"; 
	/**
	 * Nombre de usuario de acceso a la base de datos.
	 */
	private String usuario; 
	/**
	 * Contraseña de acceso a la base de datos.
	 */
	private String contraseña;
	/**
	 * URL de acceso a la base de datos.
	 */
	private String url = "jdbc:mysql://Localhost:3306/" + nombreDB;
	
	/**
	 * Variable de tipo Connection, se usará para establecer la conexion a true o false, según se haya podido o no conectar a la base de datos.
	 */
	Connection conexion = null; //Establecemos la conexion por defecto a null
	
	/**
	 * Constructor sin datos de entrada, se usará para declarar el objeto Conexion
	 */
	public Conexion() {}
	
	/**
	 * Constructor con datos de entrada, se usará para establecer el objeto conexion.
	 * 
	 * @param usuario - el usuario de acceso a la base de datos.
	 * @param contraseña - la contraseña de acceso a la base de datos.
	 */
	public Conexion(String usuario, String contraseña) {
		this.usuario = usuario;
		this.contraseña = contraseña;
	}

	/**
	 * Metodo conect, se usará para establecer la conexion a la base de datos.
	 */
	public void conect() {
		try {
			//Cargamos el DriverManager con el Class Loader
			Class.forName("com.mysql.cj.jdbc.Driver");
			try {
				//Establecemos la conexion a la base de datos
				conexion = DriverManager.getConnection(url, usuario, contraseña); 
				if (conexion != null) {
					//Si la conexion ha sucedido correctamente, mostramos mensaje de entrada
					System.out.println("Conected to: " + url); 
				}
			} catch (SQLException e) {
				//Mostramos error en el caso de no poder conectar al abase de datos solicitada
				System.out.println("Unable to conected to: " + url);
				System.out.println("SQL exception returned: " + e);
				JOptionPane.showMessageDialog(null, 
						                      "Usuario o contraseña incorrectos.\nSi ha olvidado su contraseña, pongase en contacto con el administrador de sistemas", 
										      "CashMadrid", 
										      0, 
										      null);
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
	 * Método para romper la conexión a la base de datos.
	 */
	public void desconexion() {
		conexion = null; //Establecemos el valor de la conexion a null
	}
}
