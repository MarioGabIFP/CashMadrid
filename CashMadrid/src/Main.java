import java.awt.EventQueue;
import java.util.Arrays;

/**
 * 
 */

/**
 * Clase principal del programa, esta es la primera que se ejecutará
 * 
 * @author Mario Gabriel Núñez Alcázar de Velasco
 */
public class Main {

	/**
	 * Funcion principal el programa
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*
		 * Ejecutamos la Ventana de Windows que contendra toda la funcionalidad del programa 
		 */
		try {//Lo encapsulamos todo en un TryCath para capturar todo tipo de errores que puedan surgir en tiempo de ejecución
			EventQueue.invokeLater(new Runnable() {
				/**
				 * Funcion que ejecuta la ventana y la hace visible
				 */
				public void run() {
					try { //Lo encapsulamos todo en un TryCath para capturar todo tipo de errores que puedan surgir en tiempo de ejecución
						Panel window = new Panel(); //Declaramos el Panel Ventana (Clase Panel dentro de este mismo proyecto.)
						window.getFrame().setVisible(true);//Establecemos el panel como visible.
					} catch (Exception e) {//en el caso de error
						System.out.println("Error: " + e);//mostramos el error en consola
					}
				}
			});
		} catch (Exception e) {//mostramos el error en consola
			System.out.println("Error: " + e);//mostramos el error en consola
		}
	}
}
