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
	 * Modo de retorno de los datos.
	 */
	private Display to;
	
	/**
	 * Resultado de la Query realizada.
	 * Por defecto se establece a [null].
	 */
	public static ResultSet result = null;
	/**
	 * Metadata del resultado de la Query realizada.
	 * Por defecto se establece a [null].
	 */
	public static ResultSetMetaData resultmtdt = null;
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
	 * @param to - Modo de retorno de los datos.
	 */
	public Query(String cols, Tab tab, Statement type, Display to) {
		this.cols = cols;//Recojemos las columnas solicitadas
		this.tab = tab;//Recojemos la tabla solicitada
		this.type = type;//Recogemos el tipo de Query Solicitada
		this.to = to;//Recogemos el modo de retorno de los datos
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
			/*
			 * Llamamos a la funcion encargada de ejecutar el Select y guardamos el resultado
			 * en un array de Objetos
			 */
			Object [] sel = select(this.cols, this.tab, this.type);
			
			//Desconectamos de la base de datos
			conexion.desconexion();
			
			//Evaluamos el tipo de retorno solicitado
			if (this.to == Display.CONSOLE_LOG) {//Si se solicta mostrar el resultado en consola
				System.out.println(Arrays.toString(sel) + "\n");//Mostramos el resultado en consola
			}
			//De todas formas
			return sel;//Retornamos el resultado de la Query en formato Object
		default://En el caso de que no exista la opcion
			throw new IllegalArgumentException("Valor inesperado: " + this.type);//retornamos Excepcion
		}
	}
	
	
	private Object[] select(String cols, Tab tab, Statement type) {
		Object[] resul = new Object[1];
		String query = type + " " + cols + " FROM " + tab;
		PreparedStatement statment = null;
		result = null;
		
		try {
			try {
				statment = conexion.getConexion().prepareStatement(query);
				result = statment.executeQuery();
			} catch (SQLException e) {
				System.out.println("Error: " + e);
			}
			
			try {
				resultmtdt = result.getMetaData();
			} catch (SQLException e) {
				System.out.println("Error: " + e);
			}
			
			try {
				int i = 0;
				while(result.next() == true) {
					switch (this.to) {
						case CONSOLE_LOG:
							int y = 0;
							String[] reg = new String[1];
							
							for (int p = 1;p <= resultmtdt.getColumnCount();p++) {
								reg = Arrays.copyOf(reg, y + 1);
								reg[y] = result.getString(resultmtdt.getColumnName(p));
								y++;
							}
							
							resul = Arrays.copyOf(resul, i + 1);
							resul[i] = Arrays.toString(reg);
							i++;
							break;
						case OBJECT:
							Cliente cli = new Cliente();
							for (int p = 1;p <= resultmtdt.getColumnCount();p++) {
								switch (resultmtdt.getColumnName(p).toUpperCase()) {
									case "DNI":
										cli.setNif(result.getString(resultmtdt.getColumnName(p)));
										break;
									case "NOM":
										cli.setNmbr(result.getString(resultmtdt.getColumnName(p)));
										break;
									case "APEL":
										cli.setApllds(result.getString(resultmtdt.getColumnName(p)));
										break;
									case "TEL":
										cli.setTlfn(result.getString(resultmtdt.getColumnName(p)));
										break;
									case "EMAIL":
										cli.setEml(result.getString(resultmtdt.getColumnName(p)));
										break;
									case "DOM":
										cli.setDmcl(result.getString(resultmtdt.getColumnName(p)));
										break;
								}
							}
							resul = Arrays.copyOf(resul, i + 1);
							resul[i] = cli;
							i++;
							break;
						default:
							throw new IllegalArgumentException("Valor inesperado: " + to);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error: " + e);
			}
		} catch (NullPointerException i) {
			// TODO Auto-generated catch block
			System.out.println("Error: No hay una conexion establecida a la base de datos");
		}
		return resul;
	}
}
