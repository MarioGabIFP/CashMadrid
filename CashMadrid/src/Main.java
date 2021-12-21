import java.sql.*;
import java.util.*;

/**
 * 
 */

/**
 * @author Mario Gabriel Núñez Alcázar de Velasco
 *
 */
public class Main {
	public static ResultSet result = null;
	public static ResultSetMetaData resultmtdt = null;
	public static Conexion conexion = new Conexion();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Query query = new Query("nom,dni", "clientes", "Select", Display.OBJECT);
//		System.out.println(Arrays.toString(query.execute()));
		Cliente cli1 = new Cliente();
		Cliente cli2 = new Cliente();
		cli1 = (Cliente) query.execute()[0];
		cli2 = (Cliente) query.execute()[1];
		System.out.println(cli1.toString());
		System.out.println(cli2.toString());
//		Cliente cli1 = new Cliente();
//		Cliente cli2 = new Cliente();
//		cli1 = select("NOM, DNI", "CLIENTES")[0];
//		cli2 = select("NOM, DNI", "CLIENTES")[1];
//		System.out.println(cli1.toString());
//		System.out.println(cli2.toString());
	}
//
//	public static Cliente[] select(String cols, String tab) {
//		Cliente[] clis = new Cliente[1];
//		String query = "SELECT " + cols + " FROM " + tab;
//		PreparedStatement statment = null;
//		result = null;
//		
//		try {
//			try {
//				statment = conexion.getConexion().prepareStatement(query);
//				result = statment.executeQuery();
//			} catch (SQLException e) {
//				System.out.println("Error: " + e);
//			}
//			
//			try {
//				resultmtdt = result.getMetaData();
//			} catch (SQLException e) {
//				System.out.println("Error: " + e);
//			}
//			
//			try {
//				int i = 0;
//				while(result.next() == true) {
//					Cliente cli = new Cliente();
//					for (int p = 1;p <= resultmtdt.getColumnCount();p++) {
//						switch (resultmtdt.getColumnName(p).toUpperCase()) {
//							case "DNI":
//								cli.setNif(result.getString(resultmtdt.getColumnName(p)));
//								break;
//							case "NOM":
//								cli.setNmbr(result.getString(resultmtdt.getColumnName(p)));
//								break;
//							case "APEL":
//								cli.setApllds(result.getString(resultmtdt.getColumnName(p)));
//								break;
//							case "TEL":
//								cli.setTlfn(result.getString(resultmtdt.getColumnName(p)));
//								break;
//							case "EMAIL":
//								cli.setEml(result.getString(resultmtdt.getColumnName(p)));
//								break;
//							case "DOM":
//								cli.setDmcl(result.getString(resultmtdt.getColumnName(p)));
//								break;
//							default:
//								throw new IllegalArgumentException("Unexpected value: " + resultmtdt.getColumnName(p));
//						}
//						clis = Arrays.copyOf(clis, i + 1);
//						clis[i] = cli;
//						i++;
//					}
//				}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				System.out.println("Error: " + e);
//			}
//		} catch (NullPointerException i) {
//			// TODO Auto-generated catch block
//			System.out.println("Error: No hay una conexion establecida a la base de datos");
//		}
//		return clis;
//	}
}
