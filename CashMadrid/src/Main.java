import java.awt.EventQueue;
import javax.swing.*;

/**
 * 
 */

/**
 * Clase principal del programa, esta es la primera que se ejecutar�
 * 
 * @author Mario Gabriel N��ez Alc�zar de Velasco
 */
public class Main {
	/**
	 * Declaramos el objeto login
	 */
	public static Login login;
	
	/**
	 * Declaramos el objeto conexion
	 */
	public static Conexion conexion;
	
	/**
	 * Funcion principal el programa
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * Nada m�s ejecutar el programa, pedimos al usuario que inicie sesi�n en el programa
		 * 
		 * la sesi�n se inicia con el nombre de usuario y contrase�a de acceso a la base de datos, ya que
		 * se entiende que cada empleado tendr� su propio usuario y contrase�a de acceso a la base de datos
		 */
		/*
		 * Datos de acceso a la base de datos.
		 */
		JTextField userBDD = new JTextField(); //Nombre de usuario
		JTextField passwordBDD = new JPasswordField(); //Contrase�a
		
		//Declaramos el objeto con los input de la pantalla.
		Object[] inputs = {"Usuario: ", userBDD, "Contrase�a: ", passwordBDD};
		
		//Mostramos input
		int inpBool = JOptionPane.showConfirmDialog(null, inputs, "Iniciar sesi�n", JOptionPane.OK_CANCEL_OPTION);
		
		//Evaluamos el boton pulsado por el usuario
		switch (inpBool) {
		case 0://Si pulsa en OK
			//Establecemos los datos de la conexion introducidos por el usuario
			login = new Login(userBDD.getText() + ";" + passwordBDD.getText());
			
			//Probamos la conexi�n y continuamos
			if (tryCxn()) { //Si todo va bien
				//Iniciamos la Aplicaci�n
				StartWin();
			}
		}
	}

	/**
	 * M�todo que inicia la ventana de la aplicaci�n
	 */
	private static void StartWin() {
		/*
		 * Ejecutamos la Ventana de Windows que contendra toda la funcionalidad del programa 
		 */
		try {//Lo encapsulamos todo en un TryCath para capturar todo tipo de errores que puedan surgir en tiempo de ejecuci�n
			EventQueue.invokeLater(new Runnable() {
				/**
				 * Funcion que ejecuta la ventana y la hace visible
				 */
				public void run() {
					try { //Lo encapsulamos todo en un TryCath para capturar todo tipo de errores que puedan surgir en tiempo de ejecuci�n
						Panel window = new Panel(conexion); //Declaramos el Panel Ventana (Clase Panel dentro de este mismo proyecto.)
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
	
	/**
	 * M�todo que comprueba si los datoas de la conexion son correctos o no 
	 * (simplemente probando que la conexion se realiza con normalidad).
	 * 
	 * @return Boolean - True/False:<br> True: Conexion realizada correctamente <br>False: Error en la conexion
	 */
	private static boolean tryCxn() {
		//Establecemos los datos de la conexion:
		conexion = new Conexion(login.getConStr().split(";")[0], login.getConStr().split(";")[1]);
		conexion.conect();//conectamos
		
		//probamos la conexion
		if (conexion.getConexion() != null) { //si la conexion se ha realizado correctamente
			conexion.desconexion(); //desconectamos
			return true;//devolvemos True
		} else {//sino
			return false;//devolvemos False
		}
		
	}
}
