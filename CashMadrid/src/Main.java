import java.awt.EventQueue;
import java.util.Arrays;

/**
 * 
 */

/**
 * @author Mario Gabriel Núñez Alcázar de Velasco
 *
 */
public class Main {

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Panel window = new Panel();
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void obtnrCli() {
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
