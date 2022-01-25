import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

/**
 * Cash Madrid.:
 * <br><br>
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
 * <br><br>
 * P.D.: por mi cuenta seguir� trabajando en la aplicaci�n para perfeccionarla, dando prieoridad a las entregas
 * activas eso s�.
 * <br><br>
 * <br><br>Ubicacion del Enunciado: .\CashMadrid\doc\Practica Banco v2.pdf
 * <br>Ubicaci�n del JavaDoc: .\CashMadrid\doc\JavaDoc
 * <br>Ubicacion de la definicion de la base de datos (SQL): .\CashMadrid\doc\SQL\cdb.sql
 * <br>Ubicacion del Ejecutable en .jar: .\CashMadrid\BIN\CashMadrid.jar
 * 
 * <br><br>Nota: en la misma definici�n de la base de datos se generan unos datos por defecto para poder 
 * usar la aplicacion con datos.
 * 
 * @author Mario Gabriel N��ez Alc�zar de Velasco.
 */
public class Main {
	/**
	 * Declaramos el objeto login.
	 */
	public static Login login;
	
	/**
	 * Declaramos el objeto conexion.
	 */
	public static Conexion conexion;
	
	/**
	 * Fichero Log
	 */
	public static Log log;
	
	/**
	 * timeStamp de ejecuci�n.
	 */
	public static SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy/MM/dd.HH:mm:ss");
	
	/**
	 * Cargamos el BackEnd del programa
	 */
	private static BackEnd back;;
	
	/**
	 * M�todo principal el programa.
	 * 
	 * @param args - Argumentos del Main.
	 */
	public static void main(String[] args) {
		/*
		 * creamos un fichero log, para almacenar todos los eventos de la app.
		 */
		log = new Log();//Declaramos el Objeto log
		
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
		Object[] inputs = {"Usuario: ", userBDD, //usuario de acceso a la base de datos
						   "Contrase�a: ", passwordBDD};//Contrase�a de acceso a la base de datos
		
		//Mostramos input
		int inpBool = JOptionPane.showConfirmDialog(null, //Objeto padre
				 									inputs, //Mensaje a mostrar (en este caso, solicitamos Datos)
				 									"CashMadrid - Iniciar sesi�n", //Titulo del Mensaje
				 									JOptionPane.OK_CANCEL_OPTION);//Opciones de confirmaci�n
		
		//Evaluamos el boton pulsado por el usuario
		switch (inpBool) {
		case 0://Si pulsa en OK
			/*
			 * Obtenemos los datos escritos por el usuar�o
			 */
			String user = userBDD.getText();//usuario
			String pass = passwordBDD.getText();//Contrase�a
			
			/*
			 * comprobamos que se hayan escrito los datos solicitados
			 */
			if (!user.isBlank() && !pass.isBlank()) {
				//Establecemos los datos de la conexion introducidos por el usuario
				login = new Login(user + ";" + pass);
				
				//Probamos la conexi�n y continuamos
				if (tryCxn()) { //Si todo va bien
					//Iniciamos la Aplicaci�n
					StartWin();
				} else {//si no
					//Escribimos el error en el Log
					log.newReg("\n" + timeStamp.format(new Date()) + " - Error: Imposible conectar a la base de datos");
				}
			} else {//Si no
				//Escribirmos error en el log
				log.newReg("\n" + timeStamp.format(new Date()) + " - Error: Se esperaban datos de inicio de ses�on");
			}
		}
	}

	/**
	 * M�todo que inicia la ventana de la aplicaci�n.
	 */
	private static void StartWin() {
		/*
		 * Cargamos el motor interno de la aplicaci�n
		 */
		back = new BackEnd(conexion, log);
		
		/*
		 * Cargamos los datos desde la BDD
		 */
		back.crgrDts();
		
		/*
		 * Ejecutamos la Ventana de Windows que contendra toda la funcionalidad del programa 
		 */
		back.crgrPnl(back);
	}
	
	/**
	 * M�todo que comprueba si los datos de la conexion son correctos o no 
	 * (simplemente probando que la conexion se realiza con normalidad).
	 * 
	 * @return Boolean - True/False.<br>True: Conexion realizada correctamente.<br>False: Error en la conexion.
	 */
	private static boolean tryCxn() {
		/*
		 * Establecemos los datos de la conexion:
		 */
		//Realizamos Split para separar el usuario de la contrase�a en un array
		String[] userPass = login.getConStr().split(";");
		
		conexion = new Conexion(userPass[0], userPass[1], log);//Establecemos la conexi�n
		conexion.conect();//Conectamos
		
		//Probamos la conexion
		if (conexion.getConexion() != null) { //Si la conexion se ha realizado correctamente
			conexion.desconexion(); //Desconectamos
			return true;//Devolvemos True
		} else {//Si no
			return false;//Devolvemos False
		}
		
	}
}
