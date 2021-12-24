import java.sql.*;
import java.util.*;

/**
 * Objeto Query, aqui se encuentran las funciones correspondientes para realizar las querys a la base de datos.
 * 
 * @author Mario Gabriel Núñez Alcázar de Velasco
 */
public class Query {
	/**
	 * Columna/s sobre las que se realizará la query solicitada.
	 */
	private String cols;
	/**
	 * Tabla/s sobre la que se realizará la query solicitada.
	 */
	private Tab tab;
	/**
	 * Tipo de query solicitada.
	 */
	private Statement type;
	
	/**
	 * Resultado de la Query realizada.
	 * Contendrá los registros obtenidos tras la query, por defecto se establece a [null].
	 */
	public static ResultSet result = null;
	/**
	 * Metadata del resultado de la Query realizada.
	 * Por defecto se establece a [null].
	 */
	public static ResultSetMetaData resultmtdt = null;
	/**
	 * Declaramos el Array de Objetos en el que almacenaremos el resultado de la query
	 */
	public static Object[] resul = new Object[0];//por defecto lo declaramos con tamaño '0'
	/**
	 * Declaramos el Objeto PreparedStatment que se encargará de mandar la query al motor BDD para ejecutarlo
	 */
	public static PreparedStatement statment = null;
	
	/**
	 * Declaracion del objeto conexion.
	 * Realizará la conexion a la base de datos.
	 */
	Conexion conexion = new Conexion();
	
	/**
	 * Constructor de la Query.
	 * 
	 * <br>Se usará para establecer los datos con los que realizaremos la query. 
	 * 
	 * @param cols - Columna/s sobre las que se realizará la query solicitada.
	 * @param tab - Tabla/s sobre la que se realizará la query solicitada.
	 * @param type - Tipo de query solicitada.
	 */
	public Query(String cols, Tab tab, Statement type) {
		this.cols = cols;//Recojemos las columnas solicitadas
		this.tab = tab;//Recojemos la tabla solicitada
		this.type = type;//Recogemos el tipo de Query Solicitada
	}
	
	/**
	 * Metodo que se encargará de ejecutar la query solicitada y devolver los datos
	 * 
	 * @return Object[] - Array de objetos, retornará el Resultado de la Query realizada
	 */
	public Object[] execute() {
		//Conectamos con la base de datos
		conexion.conect();
		
		//Evaluamos el tipo de query solicitado
		switch (this.type) {
		case SELECT: //En el caso de haber solicitado un Select
			//Construimos la query tal y como se ha solicitado
			String query = this.type + " " + this.cols + " FROM " + this.tab;
			
			try {
				//Preparamos y ejecutamos la query construida
				prep(query);
				
				/*
				 * Evaluamos que la recogida de los datos se produzca correctamente
				 */
				try {
					int i = 0;//Damos de alta el contador para recorrer los registros obtenidos
					
					//Evaluamos la tabla sobre al que se ha hecho la Query
					switch (this.tab) {
					case CLIENTES://Si es la tabla clientes
						/**
						 * Bucle while por el que recorremos los registros obtenidos
						 */
						while(result.next() == true) {//Si hay datos en el registro siguiente 
							//Declaramos un objeto de tipo Cliente.
							Cliente cli = new Cliente();
							
							/*
							 * Recorremos las columnas del registro actual en busca de los datos 
							 * correespondientes para rellenar el objeto
							 */
							for (int p = 1;p <= resultmtdt.getColumnCount();p++) {
								//Evaluamos el nombre de la columna actual
								switch (resultmtdt.getColumnName(p).toUpperCase()) {
									case "DNI"://Si la columna es 'DNI'
										//extraemos el valor en el campo 'Nif' del objeto Cliente
										cli.setNif(result.getString(resultmtdt.getColumnName(p)));
										break;
									case "NOM"://Si la columna es 'NOM'
										//extraemos el valor en el campo 'Nmbr' del objeto Cliente
										cli.setNmbr(result.getString(resultmtdt.getColumnName(p)));
										break;
									case "APEL"://Si la columna es 'APEL'
										//extraemos el valor en el campo 'Apllds' del objeto Cliente
										cli.setApllds(result.getString(resultmtdt.getColumnName(p)));
										break;
									case "TEL"://Si la columna es 'TEL'
										//extraemos el valor en el campo 'Tlfn' del objeto Cliente
										cli.setTlfn(result.getString(resultmtdt.getColumnName(p)));
										break;
									case "EMAIL"://Si la columna es 'EMAIL'
										//extraemos el valor en el campo 'Eml' del objeto Cliente
										cli.setEml(result.getString(resultmtdt.getColumnName(p)));
										break;
									case "DOM"://Si la columna es 'DOM'
										//extraemos el valor en el campo 'Dmcl' del objeto Cliente
										cli.setDmcl(result.getString(resultmtdt.getColumnName(p)));
										break;
								}
							}
							
							//Aumentamos el tamaño del Array que contendra los registros Extraidos en formato Object
							resul = Arrays.copyOf(resul, i + 1);
							//Insertamos el Objeto Cliente con los datos extraidos del registro actual
							resul[i] = cli;
							//Aumentamos el contador de registro en '1'
							i++;
						}
						
						break;
					case CUENTAS:
						break;
					case TRANSFERENCIAS:
						break;
					}
				} catch (SQLException e) {//en el caso de error
					System.out.println("Error: " + e);//Mostramos el error en consola
				}
			} catch (NullPointerException i) {
				//Si se sucede un error durante la ejecución
				System.out.println("Error: No hay una conexion establecida a la base de datos");
			}
			
			//Desconectamos de la base de datos
			conexion.desconexion();
			
			//Retornamos el resultado de la Query en formato Object
			return resul;
			
		default://En el caso de que no exista la opcion
			throw new IllegalArgumentException("Valor inesperado: " + this.type);//Generamos Excepcion
		}
	}
	
	/**
	 * Metodo que prepara y ejecuta la Query.
	 * 
	 * @return Object - Retornamos el resultado de la query en formato Objeto.
	 */
	private void prep(String query) {
		try {
			//Preparamos la query
			statment = conexion.getConexion().prepareStatement(query);
			//Ejecutamos al query
			result = statment.executeQuery();
			//Obtenemos los metadatos de la query ejecutada
			resultmtdt = result.getMetaData();
		} catch (SQLException e) {//Si sucede error en la base de datos
			System.out.println("Error: " + e);//Mostramos el error en la consola
		}
	}
}
