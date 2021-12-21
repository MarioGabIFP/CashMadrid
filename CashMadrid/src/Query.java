import java.sql.*;
import java.util.*;

/**
 * 
 */

/**
 * @author Mario Gabriel Núñez Alcázar de Velasco
 *
 */
public class Query {
	private String cols;
	private String tab;
	private String type;
	private Display to;
	
	public static ResultSet result = null;
	public static ResultSetMetaData resultmtdt = null;
	public static Conexion conexion = new Conexion();
	
	/**
	 * @param cols
	 * @param tab
	 */
	public Query(String cols, String tab, String type, Display to) {
		this.cols = cols;
		this.tab = tab;
		this.type = type.toUpperCase();
		this.to = to;
	}
	
	public Object[] execute() {
		switch (this.type) {
		case "SELECT":
			return select(this.cols, this.tab);
		default:
			throw new IllegalArgumentException("Valor inesperado: " + this.type);
		}
	}
	
	private Object[] select(String cols, String tab) {
		Object[] resul = new Object[1];
		String query = "SELECT " + cols + " FROM " + tab;
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
					switch (this.to.toString()) {
						case "CONSOLE_LOG":
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
						case "OBJECT":
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
									default:
										throw new IllegalArgumentException("Unexpected value: " + resultmtdt.getColumnName(p));
								}
								resul = Arrays.copyOf(resul, i + 1);
								resul[i] = cli;
								i++;
							}
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
