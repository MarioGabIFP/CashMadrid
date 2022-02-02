import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

/**
 * Clase para conectar a la base de datos; esta clase es la que se encargar� de realizar la conexi�n a la base de datos.
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
	private String usuario; 
	/**
	 * Contrase�a de acceso a la base de datos.
	 */
	private String contrase�a;
	/**
	 * URL de acceso a la base de datos.
	 */
	private String url = "jdbc:mysql://Localhost:3306/" + nombreDB;
	/**
	 * log de eventos
	 */
	private Log log;
	
	/**
	 * Variable de tipo Connection, se usar� para establecer la conexion a true o false, seg�n se haya podido o no conectar a la base de datos.
	 */
	Connection conexion = null; //Establecemos la conexion por defecto a null
	
	/**
	 * Constructor con datos de entrada, se usar� para establecer el objeto conexion.
	 * 
	 * @param usuario - el usuario de acceso a la base de datos.
	 * @param contrase�a - la contrase�a de acceso a la base de datos.
	 */
	public Conexion(String usuario, String contrase�a, Log log) {
		this.usuario = usuario;
		this.contrase�a = contrase�a;
		this.log = log;
	}

	/**
	 * Metodo conect, se usar� para establecer la conexion a la base de datos.
	 */
	public void conect() throws SQLException, ClassNotFoundException {
		try {
			//Cargamos el DriverManager con el Class Loader
			Class.forName("com.mysql.cj.jdbc.Driver");
			try {
				//Establecemos la conexion a la base de datos
				conexion = DriverManager.getConnection(url, usuario, contrase�a); 
				if (conexion != null) {
					//Si la conexion ha sucedido correctamente, mostramos mensaje de entrada
					log.newReg("\n" + new SimpleDateFormat("yyyy/MM/dd.HH:mm:ss").format(new Date()) + " - Conected to: " + url);
				}
			} catch (SQLException e) {
				/*
				 * Mostramos error en el caso de no poder conectar al abase de datos solicitada
				 */
				//Escribimos en el log de eventos
				log.newReg("\n" + new SimpleDateFormat("yyyy/MM/dd.HH:mm:ss").format(new Date()) + " - Unable to conected to: " + url);
				log.newReg("\n" + new SimpleDateFormat("yyyy/MM/dd.HH:mm:ss").format(new Date()) + " - SQL exception returned: " + e);
				//Mostramos el Mensaje
				JOptionPane.showMessageDialog(null, 
						                      "Usuario o contrase�a incorrectos.\nSi ha olvidado su contrase�a, pongase en contacto con el administrador de sistemas", 
										      "CashMadrid", 
										      0, 
										      null);
			}
		} catch (ClassNotFoundException e) {
			//En el caso de no cargar el DriverManager, mostramos error interno
			log.newReg("\n" + new SimpleDateFormat("yyyy/MM/dd.HH:mm:ss").format(new Date()) + " - Internal:" + e);
		}
	}
	
	/**
	 * Metodo para recibir el Objeto de la conexion establecida.
	 * @return conexion - Connection.
	 */
	public Connection getConexion(){
		return conexion; // devolvemos el Objeto conexion
	}
	
	/**
	 * M�todo para romper la conexi�n a la base de datos.
	 */
	public void desconexion() {
		conexion = null; //Establecemos el valor de la conexion a null
	}
}
