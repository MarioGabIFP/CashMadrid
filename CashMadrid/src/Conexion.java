import java.sql.*;

/**
 * Clase para conectar a la base de datos (Pruebas)
 */

/**
 * @author Mario Gabriel Núñez Alcázar de Velasco
 */
public class Conexion {
	/*
	 * valores predeterminados para realizar pruebas
	 */
	private String nombreDB = "cashMadrid";
	private String usuario = "root";
	private String contraseña = "ladesiempre";
	private String url = "jdbc:mysql://Localhost:3306/" + nombreDB;
	
	Connection conexion = null;
	
	public Conexion() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try {
				conexion = DriverManager.getConnection(url, usuario, contraseña);
				if (conexion != null) {
					System.out.println("Conexión establecida");
				}
			} catch (SQLException e) {
				System.out.println("BDD no encontrada");
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Error de conexion");
		}
	}
	
	public Connection getConexion(){
		return conexion;
	}
	
	public void desconexion() {
		conexion = null;
	}
}
