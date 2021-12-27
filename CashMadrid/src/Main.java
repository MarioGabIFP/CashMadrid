import java.util.*;
import javax.swing.*;

/**
 * Clase principal
 *  
 * @author Mario Gabriel Núñez Alcázar de Velasco
 */
public class Main {
	/**
	 * Datos de conexion a la base de datos
	 */
	public static String ConStr;
	
	/**
	 * Método principal
	 * 
	 * @param args - Argumento principal
	 */
	public static void main(String[] args) {
		/*
		 * Nada más ejecutar el programa, pedimos al usuario que especifique los 
		 * datos de conexion a la base de datos.
		 */
		/*
		 * Datos de acceso a la base de datos.
		 */
		JTextField nomBDD = new JTextField(); 
		JTextField userBDD = new JTextField(); 
		JTextField passwordBDD = new JPasswordField();
		
		//Declaramos el objeto con los input de la pantalla.
		Object[] inputs = {"Nombre: ", nomBDD, "Usuario: ", userBDD, "Contraseña: ", passwordBDD};
		
		//Mostramos input
		int inpBool = JOptionPane.showConfirmDialog(null, inputs, "Introduce los datos de conexion a la base de datos", JOptionPane.OK_OPTION);
		
		ConStr = nomBDD.getText() + ";" + userBDD.getText() + ";" + passwordBDD.getText();
 		/*
 		 * Select para rellenar los objetos Cliente con los datos de la tabla Cliente.
 		 * Cada registro de la tabla corresponderá a un objeto. 
 		 */
 		//Establecemos los datos de la query y la modalidad de retorno
		Query queryOBJ = new Query("*", Tab.CLIENTES, Statement.SELECT);
		//ejecutamos la query y recuperamos el resultado en un array de Objetos
		Object[] obj = queryOBJ.execute();
		//declaramos el array de Clientes especificando como tamaño por defecto la cantidad de registros
		Cliente[] cli = new Cliente[obj.length];
		
		//rellenamos el array de clientes; un cliente por registro, un objeto por cliente, todo en un mismo array
		for (int i = 0;i < obj.length;i++) {
			cli[i] = (Cliente) obj[i];
		}
		
		//lo mostramos en consola para demostrar que se ha hecho correctamente
		System.out.println(Arrays.toString(cli));
	}
}
