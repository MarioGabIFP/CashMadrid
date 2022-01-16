import java.awt.EventQueue;
import javax.swing.*;

/**
 * Cash Madrid.:
 * 
 * Programa desarrollado para la realizaci�n del primer trabajo de Programacion en el 2� Trimestre
 * del 1� Curso de Desarrollo de aplicaciones Multiplataforma.
 * <br><br>
 * Visi�n: la aplicacion se ha desarrollado entendiendo que se destina a una entidad bancaria o a una empresa
 * Gestora de activos; as� que, lo desarrollado desde la vision del currito que esta en la ventanilla y que 
 * gestiona las peticiones de los clientes, de ah� el desarollo de la interfaz y del funcionamiento interno, asi como 
 * la estructura de la base de datos.
 * <br><br>
 * Cosas a tener en cuenta:
 * 	- La aplicaci�n a�n hay que optimizarla un poco mas, no he podido optimizarlo m�s dado las fechas de entrega
 *  - Me Huebra gustado a�adir mas elementos de interaccion con el usuario, pero al ser algo a�adido de m�s a la
 *    aplicaci�n, me he centrado en terminar lo basico y luego he a�adido m�s cosas por mi cuenta y riesgo
 *    
 * P.D.: por mi cuenta seguir� trabajando en la aplicaci�n para perfeccionarla, dando prieoridad a las entregas
 * activas eso s�.
 * 
 * Ubicacion del Enunciado: .\CashMadrid\doc\Practica Banco v2.pdf
 * Ubicaci�n del JavaDoc: .\CashMadrid\doc\JavaDoc
 * Ubicacion de la definicion de la base de datos (SQL): .\CashMadrid\doc\SQL\cdb.sql
 * Ubicacion del Ejecutable en .jar: .\CashMadrid\BIN\CashMadrid.jar
 * 
 * Nota: en la misma definici�n de la base de datos se generan unos datos por defecto para poder 
 * usar la aplicacion con datos.
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
	 * @param args - Argumentos del Main
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
		int inpBool = JOptionPane.showConfirmDialog(null, inputs, "CashMadrid - Iniciar sesi�n", JOptionPane.OK_CANCEL_OPTION);
		
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
	 * M�todo que comprueba si los datos de la conexion son correctos o no 
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
