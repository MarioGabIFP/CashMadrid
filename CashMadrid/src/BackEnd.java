import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * 
 */

/**
 * @author Mario Gabriel Núñez Alcázar de Velasco
 *
 */
public class BackEnd {
	/**
	 * Conexion con la base de datos.
	 */
	private Conexion conexion;
	
	/**
	 * Log de Eventos.
	 */
	private Log log;
	
	/**
	 * Formato fecha.
	 */
	private static SimpleDateFormat dtfrmt = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * Seleccion del DNI por parte del usuario.
	 */
	private static String slctnDNI;
	
	/**
	 * un solo cliente.
	 */
	private static Cliente clie;
	
	/**
	 * una sola cuenta.
	 */
	private static Cuenta cuen;
	
	/******************************************************
	 ********************** ARRAYS ************************
	 ******************************************************/
	
	/**
	 * Array de Clientes Activos.
	 */
	private static Cliente[] cli;
	
	/**
	 * Array con todos los clientes, activos y no activos.
	 */
	private static Cliente[] allCli;
	
	/**
	 * Array de cuentas.
	 */
	private static Cuenta[] cu;
	
	/**
	 * Array de Transferencias.
	 */
	private static Transferencia[] trans;
	
	/******************************************************
	 ********************** ARRAYS ************************
	 ******************************************************/

	/**
	 * Constructor para incializar la ejecución del BackEnd.
	 * 
	 * @param conexion - Datos de conexion a la base de datos
	 * @param log - Datos del fichero log de la ejecución actual
	 */
	public BackEnd(Conexion conexion, Log log) {
		this.conexion = conexion;//Recogemos los datos de la conexion
		this.log = log;//Recogemos los datos del fichero log
	}
	
	/**
	 * Método que se usará para recoger todos los datos de la base de datos en Arrays.
	 */
	public void crgrDts() {
		/*
		 * Reinicializamos los Array
		 */
		trans = null;
		cli = new Cliente[0];
		cu = new Cuenta[0];
		
		/*
		 * Cargamos todas las Transferencias
		 */
		trans = obtnrTrnsfrnc();
		
		/*
		 * Cargamos los Datos de los Clientes Activos
		 */
		allCli = obtnrCli();//cargamos los datos de todos los clientes, activos y no activos.

		for (Cliente c: allCli) {
			if (c.getStts()) {
				cli = Arrays.copyOf(cli, cli.length + 1);
				cli[cli.length - 1] = c;
			}
		}
		
		/*
		 * cargamos los datos de las cuentas
		 */
		for (Cliente c: allCli) {//por cada cliente
			String nif = c.getNif();//Obtenemos el dato NIF
			
			Cuenta[] tempArrCu = obtnrCu(nif);//Obtenemos las cuentas que pertenecen al cliente

			/*
			 * Para cada cuenta, obtenemos el saldo, el titular y lo introducimos en el Array de Cuentas 
			 */
			for (Cuenta s: tempArrCu) {
				s.setTitular(c);//establecemos el titular
				Double sld = btnrSld(s.getIdCu());//Obtenemos el saldo
				
				if (sld != null) {
					s.setSaldo(sld);//establecemos el Saldo
				}
				
				cu = Arrays.copyOf(cu, cu.length + 1);//aumentamos en 1 el tamaño del array
				
				if (cu[cu.length - 1] == null) {
					cu[cu.length - 1] = s;//Introducimos el array en el array de cuentas
				}
			}
		}
	}
}
