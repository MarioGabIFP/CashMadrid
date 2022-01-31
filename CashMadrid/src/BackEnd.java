import java.awt.EventQueue;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * 
 */

/**
 * Motor interno de la aplicación.
 * 
 * @author Mario Gabriel Núñez Alcázar de Velasco
 */
public class BackEnd {
	/**
	 * Conexion con la base de datos.
	 */
	public Conexion conexion;
	
	/**
	 * Log de Eventos.
	 */
	public Log log;
	
	/**
	 * Formato fecha.
	 */
	private static SimpleDateFormat dtfrmt = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * timeStamp de ejecución.
	 */
	public static SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy/MM/dd.HH:mm:ss");
	
	/**
	 * un solo cliente.
	 */
	private static Cliente clie;
	
	/**
	 * una sola cuenta.
	 */
	private static Cuenta cuen;
	
	/*
	 * Vista Gestión de transferencias
	 */
	private Panel window = new Panel();
	
	/******************************************************
	 ********************** ARRAYS ************************
	 ******************************************************/
	
	/**
	 * Array de Clientes Activos.
	 */
	public ArrayList<Cliente> cli = new ArrayList<Cliente>();
	
	/**
	 * Array con todos los clientes, activos y no activos.
	 */
	private static ArrayList<Cliente> allCli = new ArrayList<Cliente>();
	
	/**
	 * Array de cuentas.
	 */
	private static ArrayList<Cuenta> cu = new ArrayList<Cuenta>();
	
	/**
	 * Array de Transferencias.
	 */
	private static ArrayList<Transferencia> trans = new ArrayList<Transferencia>();
	
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
		if (trans != null) {
			trans.clear();
			cli.clear();
			cu.clear();
		}
		
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
				cli.add(c);
			}
		}
		
		/*
		 * cargamos los datos de las cuentas
		 */
		for (Cliente c: allCli) {//por cada cliente
			String nif = c.getNif();//Obtenemos el dato NIF
			
			ArrayList<Cuenta> tempArrCu = obtnrCu(nif);//Obtenemos las cuentas que pertenecen al cliente

			/*
			 * Para cada cuenta, obtenemos el saldo, el titular y lo introducimos en el Array de Cuentas 
			 */
			for (Cuenta s: tempArrCu) {
				s.setTitular(c);//establecemos el titular
				Double sld = btnrSld(s.getIdCu());//Obtenemos el saldo
				
				if (sld != null) {
					s.setSaldo(sld);//establecemos el Saldo
				}
				
				cu.add(s);
			}
		}
	}
	
	/**
	 * Método para Obtener todas las transferencias registradas en la tabla.
	 * 
	 * @return Transferencia[] - Array con las transferencias existentes en la tabla.
	 */
	private ArrayList<Transferencia> obtnrTrnsfrnc() {
		/*
 		 * Select para rellenar los objetos Transferencia con los datos de la tabla Transferencia.
 		 * Cada registro de la tabla corresponderá a un objeto. 
 		 */
 		//Establecemos los datos de la query y la modalidad de retorno
		Query queryOBJ = new Query("*", 
								   "transferencias", 
								   Statement.SELECT, 
								   null, 
								   null, 
								   Data.TRANSFERENCIAS, 
								   conexion, 
								   null, 
								   log);
		
		//ejecutamos la query y recuperamos el resultado en un array de Objetos
		ArrayList<Object> obj = queryOBJ.execute();
		//declaramos el array de Transferencias especificando como tamaño por defecto la cantidad de registros
		List<Transferencia> trnsfrnc = new ArrayList<Transferencia>();
		
		//rellenamos el array de Transferencias; una Transferencia por registro, un objeto por Transferencia, todo en un mismo array
		for (Object o: obj) {
			trnsfrnc.add((Transferencia) o);
		}
		
		//retornamos el array de Transferencias.
		return (ArrayList<Transferencia>) trnsfrnc;
	}
	
	/**
	 * Función para obtener todos los datos de todos los clientes de la base de datos.
	 * 
	 * @return Cliente[] - Array de clientes.
	 */
	private ArrayList<Cliente> obtnrCli() {
		/*
 		 * Select para rellenar los objetos Cliente con los datos de la tabla Cliente.
 		 * Cada registro de la tabla corresponderá a un objeto. 
 		 */
 		//Establecemos los datos de la query y la modalidad de retorno
		Query queryOBJ = new Query("*", 
								   "Clientes", 
								   Statement.SELECT, 
								   null, 
								   null,
								   Data.CLIENTES, 
								   conexion, 
								   null, 
								   log);
		
		//ejecutamos la query y recuperamos el resultado en un array de Objetos
		ArrayList<Object> obj = queryOBJ.execute();
		//declaramos el array de Transferencias especificando como tamaño por defecto la cantidad de registros
		List<Cliente> clie = new ArrayList<Cliente>();
		
		//rellenamos el array de Transferencias; una Transferencia por registro, un objeto por Transferencia, todo en un mismo array
		for (Object c: obj) {
			clie.add((Cliente) c);
		}
		
		//retornamos el array de Transferencias.
		return (ArrayList<Cliente>) clie;
	}
	
	/**
	 * Función para obtener todos los datos de todas las cuentas asociados al cliente.
	 * 
	 * @param dni - DNI/NIF/NIE del cliente a consultar.
	 * @return Cuenta[] - Array de cuentas.
	 */
	private ArrayList<Cuenta> obtnrCu(String dni) {
		/*
 		 * Select para rellenar los objetos Cliente con los datos de la tabla Cliente.
 		 * Cada registro de la tabla corresponderá a un objeto. 
 		 */
 		//Establecemos los datos de la query y la modalidad de retorno
		Query queryOBJ = new Query("asignacion.IdCu, concat(DigCon,Ent,Ofi,DigContr,NCue) as Numero_cuenta, NBanc, FINI, FFIN, DNI", 
									"asignacion", 
									Statement.SELECT,
									"left join clientes on asignacion.IdCli = Clientes.IdCli left join cuenta on asignacion.IdCu = cuenta.idcu where DNI = '" + dni + "'",
									null,
									Data.CUENTA,
									conexion, 
									null, 
									log);
		
		//ejecutamos la query y recuperamos el resultado en un array de Objetos
		ArrayList<Object> obj = queryOBJ.execute();
		//declaramos el array de Transferencias especificando como tamaño por defecto la cantidad de registros
		List<Cuenta> cuen = new ArrayList<Cuenta>();
		
		//rellenamos el array de Transferencias; una Transferencia por registro, un objeto por Transferencia, todo en un mismo array
		for (Object c: obj) {
			cuen.add((Cuenta) c);
		}
		
		//retornamos el array de Transferencias.
		return (ArrayList<Cuenta>) cuen;
	}	
	
	/**
	 * Método para obtener el Saldo de la cuenta Seleccionada.
	 * 
	 * @param idC - Id de la cuenta seleccionada.
	 * @return Double - Saldo de la cuenta.
	 */
	private Double btnrSld(Integer idC) {
		Double sld = 0.00;//Establecemos el saldo a '0.00'
		for(Transferencia t: trans) {//para cada Tranferencia
			if (t.getDestino() == idC) {//si la cuenta destino es la cuenta a consultar
				sld = sld + t.getImp();//sumamos el importe de la transferencia al saldo
			} else if (t.getOrigen() == idC) {//por el contrario si la cuenta Origen es la cuenta a consultar
				sld = sld - t.getImp();//restamos el importe de la transferencia al saldo
			}
		}
		
		return sld;//devolvemos el Saldo.
	}
	
	/**
	 * Método para eliminar el registro de la transferencia seleccionado en el JList. 
	 */
	protected void dscrTransf() {
		// Deshacer Transferencia
		Object ibanObj = window.cueComboBox.getSelectedItem();//Obtenemos el Iban de la cuenta seleccionada
		if (window.cliData.getValueAt(12, 1) != null) {//si la fecha de baja está rellena
			/*
			 * Mostramos error
			 */
			JOptionPane.showMessageDialog(null, 
					  					  "Accion no disponible: Cuenta dada de baja", 
					  					  "CashMadrid", 
					  					  0, 
					  					  null);
		} else if (ibanObj == null){ //si el Iban seleccionado es null.
			/*
			 * Mostramos error
			 */
			JOptionPane.showMessageDialog(null, 
  					  					  "Accion no disponible: El Cliente no posee ninguna cuenta activa", 
  					  					  "CashMadrid", 
  					  					  0, 
  					  					  null);
		} else { //si esta todo Ok
			//obtenemos la pestaña activa.
			JList<String> selPane = (JList<String>) window.movPane.getSelectedComponent();
			String selTrans = selPane.getSelectedValue();//obtenemos el registro seleccionado

			if (selTrans == null) {//si no hay ningún registro seleccionado
				/*
				 * Mostramos error
				 */
				JOptionPane.showMessageDialog(null, 
	  					  					  "Debes seleccionar un registro de la lista", 
	  					  					  "CashMadrid", 
	  					  					  1, 
	  					  					  null);
			/******************************************************************************/
			/************** INICIO ***************
			 * Provisional a la espera de diseño *
			 *************************************/
			/*
			 * Caso en el que intenta revertir un ingreso: aún en Diseño.
			 */
			} else if (selPane == window.movDebe) {//Si está intentando devolver un ingreso
				/*
				 * Mostramos error
				 */
				JOptionPane.showMessageDialog(null, 
	  					  					  "Acción no disponible: No puedes rechazar un ingreso\nSi lo deseas puedes hablar con el emisor de la transferencía o con tu gestor bancario.", 
	  					  					  "CashMadrid",
	  					  					  1, 
	  					  					  null);

			/*************************************
			 * Provisional a la espera de diseño *
			 *************** FIN *****************/
			/******************************************************************************/
			} else {
				//mostramos mensaje de confirmación
				int opt = JOptionPane.showConfirmDialog(null, 
														"¿Estas seguro de querer eliminar el movimiento?\nEsta accion modificará el saldo Contable", 
														"CashMadrid", 
														JOptionPane.OK_CANCEL_OPTION, 
														2);
				//si selecciona "OK"
				if(opt == 0) {
					//Obtenemos el numero de referencia del registro seleccionado
					String[] selNRef = selTrans.toString().split(" ");
					int numRef = Integer.parseInt(selNRef[selNRef.length - 1]);
					
					//Establecemos los datos de la query y la modalidad de retorno
					Query queryOBJ = new Query(null, 
											   "transferencias", 
											   Statement.DELETE, 
											   "WHERE CodTran = " + numRef, 
											   null,
											   null, 
											   conexion, 
											   null, 
											   log);
					//ejecutamos la query
					queryOBJ.execute();
					load();//Recargamos los datos.
				}
			}
		}
	}

	/**
	 * Método para realizar una transferencia a cuenta.
	 */
	protected void crrTransf() {
		// si la cuenta de origen seleccionada no tiene la fecha fin rellena
		if (window.cliData.getValueAt(12, 1) == null) { 
			Object ibanObj = window.cueComboBox.getSelectedItem();//Obtenemos la cuenta seleccionada 
			
			if (ibanObj != null) {//si el Iban de la cuenta origen seleccionada no es null
				//Obtenemos Id de cuenta origen
				Integer idCnt = gtUnCu(ibanObj.toString()).getIdCu();
				
				/*
				 * Datos de la transferencia
				 */
				JTextField iban = new JTextField(); //IBAN de la cuenta a dar de alta
				JTextField imprt = new JTextField(); //Importe de la transferencia
				JTextField cncpt = new JTextField(); //Concepto de la transferencia
				
				//Declaramos el objeto con los input del ConfirmDialog
				Object[] inputs = {"IBAN del destinatario: ", iban,
								   "Importe: ", imprt,
								   "Concepto: ", cncpt};
				
				//Mostramos confirm dialog
				int inpBool = JOptionPane.showConfirmDialog(null, inputs, "CashMadrid - Realizar Transferencia", JOptionPane.OK_CANCEL_OPTION);

				
				switch (inpBool) {
				case 0://si el usuario pulsa "OK"
					String bn = iban.getText();//obtenemos el iban destino
					if (gtUnCu(bn).getFechaCierre() != null) {//si la cuenta destino esta cerrada
						//mostramos mensaje de error
						JOptionPane.showMessageDialog(null, 
													  "La cuenta referenciada se encuentra dada de baja", 
													  "CashMadrid", 
													  0, 
													  null);
					} else if (imprt != null) {//si el importe no es null
						String imp = imprt.getText();//obtenemos el importe
						//si el importe es numerico real o decimal (con coma o punto)
						if(imp.matches("[+-]?\\d*(,\\d+)?") || imp.matches("[+-]?\\d*(\\.\\d+)?")){
							Double im = suspc(imp);//reformateamos el importe con decimales por punto.
							if(im <= 0) {//si el importe es menor o igual a '0'
								//mostramos mensaje de error
								JOptionPane.showMessageDialog(null, 
															  "El importe a ingresar debe ser mayor que 0", 
															  "CashMadrid", 
															  0, 
															  null);
							//si el IBAN no esta entre los datos recuperados de la base de datos
							} else if (!srchArr(bn, obtnrIBAN(cu))) {
								//mostramos mensaje de error
								JOptionPane.showMessageDialog(null, 
														  "IBAN del destinatario no encontrado", 
														  "CashMadrid", 
														  0, 
														  null);
							} else {//de lo contrario
								//Obtenemos el ID de la cuenta destino
								int idCu = gtUnCu(bn).getIdCu();
								//Establecemos los datos de la query y la modalidad de retorno
								Query queryOBJ = new Query("COri, CDes, Imp, Cncpt",
														   "transferencias",
														   Statement.INSERT,
														   null,
														   idCnt + ", " + idCu + ", " + im + ", '" + cncpt.getText() + "'",
														   null,
														   conexion,
														   1, 
														   log);
								//ejecutamos el insert
								queryOBJ.execute();
								load();//volvemos a recoger los datos de la base de datos.
							}
						} else { // de lo contrario
							//mostramos error
							JOptionPane.showMessageDialog(null, 
														  "Valor no numerico no admitido", 
														  "CashMadrid", 
														  0, 
														  null);
						}
					}
					break;
				}
			} else {//de lo contrario
				//mostramos error
				JOptionPane.showMessageDialog(null, 
	  					  					  "Accion no disponible: El Cliente no posee ninguna cuenta activa", 
	  					  					  "CashMadrid", 
	  					  					  0, 
	  					  					  null);
			}
		} else {//de lo contrario
			//mostramos error
			JOptionPane.showMessageDialog(null, 
					  					  "Accion no disponible: Cuenta dada de baja", 
					  					  "CashMadrid", 
					  					  0, 
					  					  null);
		}
	}

	/**
	 * Método para Ingresar Efectivo en la cuenta.
	 */
	protected void ngrsFctv() {
		// si la cuenta de origen seleccionada no tiene la fecha fin rellena
		if (window.cliData.getValueAt(12, 1) == null) { 
			//obtenemos el objeto IBAN de la cuenta seleccionada
			Object iban = window.cueComboBox.getSelectedItem();
			
			//si el IBAN no es null
			if (iban != null) {
				//Obtenemos Id de cuenta
				Integer idCnt = gtUnCu(iban.toString()).getIdCu();
				
				//solicitamos la inserccion de la cantidad
				String imprt = JOptionPane.showInputDialog("Importe: ");
				
				//si el importe especificado no es nulo
				if (imprt != null) {
					//si el importe es numerico real o decimal con coma o con punto.
					if(imprt.matches("[+-]?\\d*(,\\d+)?") || imprt.matches("[+-]?\\d*(\\.\\d+)?")){
						Double im = suspc(imprt);//reformateamos el importe
						
						//si el importe es mayor de '0'
						if(im > 0) {
							//Establecemos los datos de la query y la modalidad de retorno
							Query queryOBJ = new Query("CDes, Imp, Cncpt",
													   "transferencias",
													   Statement.INSERT,
													   null,
													   idCnt + ", " + im + ", 'Ingreso a Cuenta'",
													   null,
													   conexion,
													   1, 
													   log);
							//ejecutamos el insert
							queryOBJ.execute();
							load();//Volvemos a obtener los datos de la base de datos.
						} else {//si no
							//mostramos error
							JOptionPane.showMessageDialog(null, 
														  "El importe a ingresar debe ser mayor que 0", 
														  "CashMadrid", 
														  0, 
														  null);
						}
					} else {//si no
						//mostramos error
						JOptionPane.showMessageDialog(null, 
								  					  "Valor no numerico no admitido", 
								  					  "CashMadrid", 
								  					  0, 
								  					  null);
					}
				}
			} else {//si no
				//mostramos error
				JOptionPane.showMessageDialog(null, 
	  					  					  "Accion no disponible: El Cliente no posee ninguna cuenta activa", 
	  					  					  "CashMadrid", 
	  					  					  0, 
	  					  					  null);
			}
		} else {//si no
			//mostramos error
			JOptionPane.showMessageDialog(null, 
					  					  "Accion no disponible: Cuenta dada de baja", 
					  					  "CashMadrid", 
					  					  0, 
					  					  null);
		}
	}

	/**
	 * Método para dar de baja un cliente.
	 */
	protected void bjclnt() {
		//mostramos mensaje de confirmación
		int opt = JOptionPane.showConfirmDialog(null, "¿Estas seguro de querer dar la baja al Cliente?", "CashMadrid", JOptionPane.OK_CANCEL_OPTION);
		
		//si selecciona 'OK'
		if (opt == 0) {
			/*
	 		 * Update para dar de baja un cliente y sus cuentas rellenando la fecha fin de cuenta 
	 		 * y estableciendo el campo activ a false (TinyInt = 0)
	 		 * Cada registro de la tabla corresponderá a un objeto. 
	 		 */
	 		//Establecemos los datos de la query y la modalidad de retorno
			int idClien = gtUnCli(window.cliComboBox.getSelectedItem().toString()).getIdCli();
			Query queryOBJ = new Query("FFIN;Activ",
					   				   "asignacion;Clientes",
					   				   Statement.UPDATE,
					   				   "WHERE IdCli = " + idClien + " AND FFIN is null" + ";WHERE IdCli = " + idClien,
					   				   dtfrmt.format(new Date()) + "; 0",
					   				   null,
					   				   conexion,
					   				   2, 
									   log);
			//ejecutamos el Update
			queryOBJ.execute();
			load();//Volvemos a obtener los datos desde la base de datos.
		}
	}
	
	/**
	 * Método para dar de baja a una cuenta.
	 */
	protected void bjcnt() {
		//si el la cuenta no está ya dada de baja
		if (window.cliData.getValueAt(12, 1) == null) { 
			//mostramos mensaje de confirmación
			int opt = JOptionPane.showConfirmDialog(null, "¿Estas seguro de querer cerrar la cuenta?", "CashMadrid", JOptionPane.OK_CANCEL_OPTION);
			
			//si el usuario pulsa en 'OK'
			if (opt == 0) {
				/*
		 		 * Update para dar de baja una cuenta rellenando la fecha fin de cuenta
		 		 * Cada registro de la tabla corresponderá a un objeto. 
		 		 */
		 		//Establecemos los datos de la query y la modalidad de retorno
				Query queryOBJ = new Query("FFIN",
										   "asignacion",
										   Statement.UPDATE,
										   "WHERE IdCli = " + gtUnCli(window.cliComboBox.getSelectedItem().toString()).getIdCli() + " AND IdCu = " + gtUnCu(window.cueComboBox.getSelectedItem().toString()).getIdCu(),
										   dtfrmt.format(new Date()),
										   null,
										   conexion,
										   1, 
										   log);
				//Ejecutamos el Update
				queryOBJ.execute();
				load();//volvemos a obtener los datos de la base de datos.
			}
		} else {//si no
			//mostramos error
			JOptionPane.showMessageDialog(null, 
										  "La cuenta seleccionada ya está dada de baja", 
										  "CashMadrid", 
										  0, 
										  null);
		}
	}

	/**
	 * Método para dar de alta una cuenta.
	 */
	protected void ltCnt() {
		/*
		 * Datos de la Cuenta
		 */
		JTextField iban = new JTextField(); //IBAN de la cuenta a dar de alta
		JTextField nBanc = new JTextField(); //Nombre de la entidad bancaria
		
		//Declaramos el objeto con los input de la pantalla.
		Object[] inputs = {"IBAN: ", iban,
						   "Entidad: ", nBanc};
		
		//Mostramos input
		int inpBool = JOptionPane.showConfirmDialog(null, inputs, "CashMadrid - Nuevo Cliente", JOptionPane.OK_CANCEL_OPTION);
		
		//Evaluamos el boton pulsado por el usuario
		switch (inpBool) { 
		case 0://Si pulsa en OK
			//Recojemos el Iban facilitado por el usuario
			String ibanCmplt = iban.getText();
			
			//si el campo IBAN no ha sido rellenado
			if(ibanCmplt.equals("")) {
				//mostramos error
				JOptionPane.showMessageDialog(null, 
						  					  "El Campo IBAN es obligatorio.", 
						  					  "CashMadrid", 
						  					  0, 
						  					  null);
			} else {//sino
				List<String> IBAN_s = new ArrayList<String>();//declaramos un ArrayList de String temporal
				
				//Obtenemos todas las cuentas actuales del cliente
				for (Cuenta c: cu) {
					IBAN_s.add(c.getIban());
				}
				
				//si la cuenta no existe (para ello buscamos el IBAN dentro del ArrayList de String obtenido)
				if (!srchArr(ibanCmplt, (ArrayList<String>) IBAN_s)) {
					/*
					 * revisamos el formato del IBAN
					 */
					if (ibanCmplt.length() == 24) {//si el IBAN tiene 24 digitos.
						//Dividimos el Iban en campos respetando el formato correspondiente
						String dgtCn = ibanCmplt.substring(0, 4);
						String ent = ibanCmplt.substring(4, 8);
						String ofi = iban.getText().substring(8, 12);
						String dgtCntrl = iban.getText().substring(12, 14);
						String NCue = iban.getText().substring(14);
						
						//si los dos primeros digitos del IBAN son letras y el resto numeros
						if (!dgtCn.substring(0,2).matches("[+-]?\\d*(\\.\\d+)?") && dgtCn.substring(2).matches("[+-]?\\d*(\\.\\d+)?")) {
							/*
							 * generamos el Identificador interno de cuenta a insertar
							 */
							Integer[] idCu_s = new Integer[0];//Declaramos un nuevo Array de Integer Temporal.
							
							int d = 0;//declaramos un nuevo contador
							
							//Obtenemos todos los Id de cuentas que hay en la base de datos.
							for (Cuenta c: cu) {
								idCu_s = Arrays.copyOf(idCu_s, d + 1);
								idCu_s[d] = c.getIdCu();
								d++;
							}
							
							//los ordenamos de menor a mayor.
							Arrays.sort(idCu_s);
							
							//calculamos el nuevo Id de Cuenta
							int idCu = idCu_s[idCu_s.length - 1] + 1;
							
							//obtenemos el Identificador del cliente al que vamos a asignar la cuenta
							int idCli = gtUnCli(window.cliComboBox.getSelectedItem().toString()).getIdCli();
							
							//Construimos la cadena con los valores de a insertar
							String val = idCu + ", '" + dgtCn + "', '" + ent + "', '" + ofi + "', '" + dgtCntrl + "', '" + NCue + "', '" + nBanc.getText() + "';" + idCli + ", " + idCu + ", '" + dtfrmt.format(new Date()) + "'";
							
							nsrtrCu(val);//llamamos a insertar la cuenta
							load();//recargamos los datos desde la base de datos
						} else {//si no
							//mostramos error
							JOptionPane.showMessageDialog(null, 
														  "Formato de IBAN incorrecto.\nEl IBAN debe estar compuesto unicamente de 22 numeros y 2 letras\nPara consultar el formato admitido, contacte con su administrador", 
														  "CashMadrid", 
														  0, 
														  null);
						}
					} else {//si no
						//mostramos error
						JOptionPane.showMessageDialog(null, 
			                      					  "Formato de IBAN incorrecto.\nEl IBAN debe estar compuesto unicamente de 24 cifras\nPara consultar el formato admitido, contacte con su administrador", 
			                      					  "CashMadrid", 
			                      					  0, 
			                      					  null);
					}
				} else {//si no
					//mostramos error
					JOptionPane.showMessageDialog(null, 
												  "El IBAN especificado ya existe", 
												  "CashMadrid", 
												  0, 
												  null);
				}
			}
		}
	}

	/**
	 * Metodo para dar de alta un cliente en la base de datos.
	 */
	protected void ltClnt() {
		/*
		 * Datos de acceso Cliente
		 */
		JTextField nif = new JTextField(); //DNI-NIF-NIE del cliente a dar de alta
		JTextField nom = new JTextField(); //Nombre del cliente
		JTextField apell = new JTextField(); //apellidos del cliente
		JTextField tlfn = new JTextField(); //Telefono del cliente
		JTextField email = new JTextField(); //Email del cliente
		JTextField domic = new JTextField(); //Domicilio del cliente
		
		//Declaramos el objeto con los input de la pantalla.
		Object[] inputs = {"NIF/DNI/NIE: ", nif,
						   "Nombre: ", nom,
						   "Apellidos: ", apell,
						   "Telefono", tlfn,
						   "Email", email,
						   "Domicilio", domic};
		
		//Mostramos input
		int inpBool = JOptionPane.showConfirmDialog(null, inputs, "CashMadrid - Nuevo Cliente", JOptionPane.OK_CANCEL_OPTION);
		
		//Evaluamos el boton pulsado por el usuario
		switch (inpBool) { 
		case 0://Si pulsa en OK
			
			//declaramos un nuevo Array de String para almacenar los DNI's Existentes
			List<String> DNI_s = new ArrayList<String>();
			//declaramos un nuevo Array de String para almacenar los Email's Existentes
			List<String> EMAIL_s = new ArrayList<String>();
			
			//Obtenemos los Email y los Dni de todos los clientes
			for (Cliente c: cli) {
				DNI_s.add(c.getNif());
				EMAIL_s.add(c.getEml());
			}
			
			//Obtenemos los datos introducidos por el usuario.
			String dni = nif.getText();
			String ml = email.getText();
			String nmbr = nom.getText();
			String tel = tlfn.getText();
			String dmcl = domic.getText();
			
			//si existe algun dato de los obligatorios Vacío.
			if (dni.equals("")|| ml.equals("") || nmbr.equals("") || tel.equals("") || dmcl.equals("")) {
				//mostramos error
				JOptionPane.showMessageDialog(null, 
											  "Falta alguno de los campos obligatorios.", 
											  "CashMadrid", 
											  0, 
											  null);
			} else {//sino
				//si el Email no existe en la base de datos.
				if (!srchArr(ml, (ArrayList<String>) EMAIL_s)) {
					//si el DNI no existe en la base de datos.
					if (!srchArr(dni, (ArrayList<String>) DNI_s)) {
						//mostamos la query con los valores introducidos.
						String values = "'" + dni + "', '" + nmbr + "', '" + apell.getText() + "', '" + tel + "', '" + ml + "', '" + dmcl + "', " + 1;
						nsrtrCli(values);//llamamos a procesar la petición.
						load();//Recargamos los datos de la base de datos.
					} else {//sino
						//mostramos error
						JOptionPane.showMessageDialog(null, 
								                      "El DNI/NIF/NIE especificado ya existe.", 
												      "CashMadrid", 
												      0, 
												      null);
					}
				} else {//sino
					//mostramos error
					JOptionPane.showMessageDialog(null, 
												  "El E-Mail especificado ya existe.", 
												  "CashMadrid", 
												  0, 
												  null);
				}
			}
		}
	}

	/**
	 * Método para Recargar los datos de la aplicación obteniendo los datos de la base de datos.
	 */
	public void load() {
		/*
		 * Inicializamos todos los datos con valores de la base de datos.
		 */
		crgrDts();
		//Declaramos el modelo de datos insertando el Array resultante de "obtnrNif(obtnrCli()))" y lo insertamos en el ComboBox
		ArrayList<String> nif_s = obtnrNif(cli);//obtenemos todos los DNI
		window.cliComboBox.setModel(new DefaultComboBoxModel<String>(nif_s.toArray(new String[nif_s.size()])));//lo mostramos en el comboBox
		setDataCli(window.cliComboBox.getSelectedItem().toString());
	}

	/**
	 * Método para insertar clientes en la base de datos.
	 * 
	 * @param values - Valores a insertar en la base de datos con la informacion del nuevo cliente.
	 */
	private void nsrtrCli(String values) {
 		//Establecemos los datos de la query y la modalidad de retorno
		Query queryOBJ = new Query("DNI, Nom, Apel, Tel, Email, Dom, Activ", 
								   "Clientes", 
								   Statement.INSERT, 
								   null,
								   values,
								   null, 
								   conexion, 
								   1, 
								   log);
		//ejecutamos el insert
		queryOBJ.execute();
	}
	
	/**
	 * Método para insertar cuentas en la base de datos y asiganrlas al cliente solicitado.
	 * 
	 * @param values - Valores a insertar con los datos de la cuenta y del cliente a asignar.
	 */
	private void nsrtrCu(String values) {
 		//Establecemos los datos de la query y la modalidad de retorno
		Query queryOBJ = new Query("IdCu, DigCon, Ent, Ofi, DigContr, NCue, NBanc;IdCli, IdCu, FINI",
								   "cuenta;asignacion", 
								   Statement.INSERT, 
								   null,
								   values,
								   null,
								   conexion,
								   2, 
								   log);
		//ejecutamos el insert y recuperamos el resultado en un array de Objetos
		queryOBJ.execute();
	}
	
	/**
	 * Funcion para obtener la lista de todos los NIF/DNI/NIE de los clientes ubicados en un mismo Array de Clientes.
	 *  
	 * @param cli - Array de Clientes a consultar.
	 * @return String[] - Array de Strings que contendra los DNI/NIE/NIF de los clientes solicitados.
	 */
	public ArrayList<String> obtnrNif(ArrayList<Cliente> cli) {
		List<String> nif = new ArrayList<String>(); //Declaramos el String con tamaño de Array a 0
		
		/*
		 * For para añadir en el Array de Strings el DNI de cada cliente
		 */
		for (Cliente c: cli) {//por cada cliente
			nif.add(c.getNif());//Obtenemos el dato NIF y lo introducimos en la posicion actual del array
		}
		
		return (ArrayList<String>) nif; //Devolvemos el Array resultante. 
	}
	
	/**
	 * Funcion para obtener la lista de todos los IBAN de las Cuentas ubicadas en un mismo Array de Cuentas.
	 *  
	 * @param cu - Array de Cuentas a consultar.
	 * @return String[] - Array de Strings que contendra los IBAN de las cuentas solicitadas.
	 */
	private ArrayList<String> obtnrIBAN(ArrayList<Cuenta> cu) {
		List<String> iban = new ArrayList<String>();//Declaramos el ArrayList de Stringç
		
		/*
		 * For para añadir en el Array de Strings el DNI de cada cliente
		 */
		for (Cuenta c: cu) {//por cada cuenta
			iban.add(c.getIban());//Obtenemos el dato IBAN y lo introducimos en la posicion actual del array
		}
		
		return (ArrayList<String>) iban;//Devolvemos el Array resultante. 
	}
	
	/**
	 * Funcion para rellenar los campos de la Ventana.
	 * 
	 * @param slctnDNI - DNI del cliente seleccionado.
	 */
	public void setDataCli(String slctnDNI) {
		//Obtenemos todos los datos del cliente seleccionado en el ComboBox
		clie = gtUnCli(slctnDNI);
		
		if (clie != null) {
			//obtenemos el listado de cuentas del cliente seleccionado
			ArrayList<Cuenta> cueArr = gtCuTit(slctnDNI);
			
			//Declaramos el modelo de datos insertando el Array resultante de "obtnrIBAN(obtnrCu(slctnDNI)" y lo insertamos en el ComboBox
			ArrayList<String> iban_s  = obtnrIBAN(cueArr);//obtenemos todos los IBAN del cliente Seleccionado
			//los mostramos en el ComboBox.
			window.cueComboBox.setModel(new DefaultComboBoxModel<String>(iban_s.toArray(new String[iban_s.size()])));
			
			//Establecemos los datos de la cuenta
			setDataCue(window.cueComboBox.getSelectedItem());
		} else {//sino
			//mostramos error
			JOptionPane.showMessageDialog(null, 
										  "El Cliente especificado no existe o no está activo", 
										  "CashMadrid", 
										  0, 
										  null);
		}
	}
	
	/**
	 * Método desarrollado para rellenar todos los datos de la cuenta.
	 * 
	 * @param slctnIBAN - IBAN Correspondiente a la cuenta a consultar.
	 */
	public void setDataCue(Object slctnIBAN) {
		//Establecemos el Objeto cuenta a nulo.
		cuen = null;
		
		//si el IBAN seleccionado no es nulo
		if (slctnIBAN != null) {
			//Obtenemos la cuenta correspondiente
			cuen = gtUnCu(slctnIBAN.toString());
		}
		
		//Rellenamos el JTable con los datos del cliente y de la cuenta seleccionados
		rllnrTbl(clie, cuen);
		
		//si la cuenta no es nula
		if (cuen != null) {
			//obtenemos todas las transferencias de la cuenta
			obtnrTrn(cuen.getIdCu());
		} else {//sino
			//Pintamos el JList de las transferencias en Vacio
			rllnrlst(new String[0], new String[0]);
		}
	}
	
	/**
	 * Método desarrollado para rellenar los datos del JTable.
	 * 
	 * @param clie - Cliente a pintar en el JTable.
	 * @param cuen - Cuenta a pintar en el JTable.
	 */
	private void rllnrTbl(Cliente clie, Cuenta cuen) {
		if (cuen != null) {//si existen datos de cuenta
			window.cliData.setModel(new DefaultTableModel( // Declaramos el Modelo de datos de la tabla.
					new Object[][] { // Declaramos objeto Array Object[][] que contendrá los registros de la tabla con los datos correspondientes.
							{ "Datos de cliente", "****************" }, // Cabecera que indicadora del inicio de los datos del cliente.
							{ "Nombre", clie.getNmbr() }, // Nombre del cliente.
							{ "Apellidos", clie.getApllds() }, // Apellidos del cliente.
							{ "DNI/NIF/NIE", clie.getNif() }, // DNI-NIF-NIE del cliente.
							{ "Telefono", clie.getTlfn() }, // Telefono del cliente.
							{ "Email", clie.getEml() }, // Email del Cliente.
							{ "Domicilio", clie.getDmcl() }, // Domicilio del cliente.
							{ "Datos de la cuenta bancaria", "****************" }, // Cabecera que indicadora del inicio de los datos de la cuenta.
							{ "IBAN", cuen.getIban() }, // IBAN de la cuenta.
							{ "Entidad Bancaria", cuen.getNmbrbnc() }, // Nombre de la entidad bancaria de la cuenta.
							{ "Saldo", cuen.getSaldo() }, // Saldo de la cuenta.
							{ "Fecha Apertura", cuen.getFechaApertura() }, // Fecha de Apertura de la cuenta.
							{ "Fecha Cierre", cuen.getFechaCierre() } // Fecha de cliente de la cuenta (Si la cuenta esta activa, obtendremos null y la celda se mostrará vacía).
					}, new String[] { // Declaramos el objeto Array String[] que contendrá los nombres internos de las columnas (estás no se mostrarán en la ventana).
							"Dato", // Columna que contiene el nombre de los datos (Columna de la izquierda).
							"Valor" // Columna que contiene el valor de los datos (Columna de la Derecha).
					}) {
				
				/**
				 * Serial Version de la Tabla
				 */
				private static final long serialVersionUID = 7927909933540332783L;// Declaramos el Serial Version como constante
	
				/*
				 * Declaramos los tipos de datos de todas las celdas de la tabla.
				 */
				Class[] columnTypes = new Class[] { // Declaramos la clase columnTypes para establecer los tipos de datos
						String.class, // Para la primera columna establecemos las celdas como String
						String.class // Para la Segunda columna establecemos las celdas como String
				};
	
				public Class getColumnClass(int columnIndex) {// Declaramos la clase que obtendrá el tipo de dato de la
															  // celda seleccionada
					return columnTypes[columnIndex];// Devolvemos tipo de dato de la celda seleccionada
				}
			});
		} else { //si no (Datos de la cuenta a null)
			window.cliData.setModel(new DefaultTableModel( // Declaramos el Modelo de datos de la tabla.
					new Object[][] { // Declaramos objeto Array Object que contendrá los registros de la tabla con los datos correspondientes.
							{ "Datos de cliente", "****************" }, // Cabecera que indicadora del inicio de los datos del cliente.
							{ "Nombre", clie.getNmbr() }, // Nombre del cliente.
							{ "Apellidos", clie.getApllds() }, // Apellidos del cliente.
							{ "DNI/NIF/NIE", clie.getNif() }, // DNI-NIF-NIE del cliente.
							{ "Telefono", clie.getTlfn() }, // Telefono del cliente.
							{ "Email", clie.getEml() }, // Email del Cliente.
							{ "Domicilio", clie.getDmcl() }, // Domicilio del cliente.
							{ "Datos de la cuenta bancaria", "****************" }, // Cabecera que indicadora del inicio de los datos de la cuenta.
							{ "IBAN", null }, // IBAN de la cuenta.
							{ "Entidad Bancaria", null }, // Nombre de la entidad bancaria de la cuenta.
							{ "Saldo", null }, // Saldo de la cuenta.
							{ "Fecha Apertura", null }, // Fecha de Apertura de la cuenta.
							{ "Fecha Cierre", null } // Fecha de cliente de la cuenta (Si la cuenta esta activa, obtendremos null y la celda se mostrará vacía).
					}, new String[] { // Declaramos el objeto Array String[] que contendrá los nombres internos de las columnas (estás no se mostrarán en la ventana).
							"Dato", // Columna que contiene el nombre de los datos (Columna de la izquierda).
							"Valor" // Columna que contiene el valor de los datos (Columna de la Derecha).
					}) {
				
				/**
				 * Serial Version de la Tabla
				 */
				private static final long serialVersionUID = 7927909933540332783L;// Declaramos el Serial Version como constante
	
				/*
				 * Declaramos los tipos de datos de todas las celdas de la tabla.
				 */
				Class[] columnTypes = new Class[] { // Declaramos la clase columnTypes para establecer los tipos de datos
						String.class, // Para la primera columna establecemos las celdas como String
						String.class // Para la Segunda columna establecemos las celdas como String
				};
	
				public Class getColumnClass(int columnIndex) {// Declaramos la clase que obtendrá el tipo de dato de la celda seleccionada
					return columnTypes[columnIndex];// Devolvemos tipo de dato de la celda seleccionada
				}
			});
		}
	}
	
	/**
	 * Método que Recupera las cuentas de un cliente partiendo del DNI del mismo.
	 * 
	 * @param DNI - Numero de Identificación del Titular de las cuentas.
	 * @return Cuenta[] - Array de Cuentas Resultante.
	 */
	private ArrayList<Cuenta> gtCuTit(String DNI) {
		List<Cuenta> ccc = new ArrayList<Cuenta>();//establecemos el Array de Cuentas a 0
		for (Cuenta c: cu) {//para cada cuenta
			//Si el DNI del titular de la cuenta es igual al DNI del cliente Solicitado 
			if (c.getTitular().getNif().equals(DNI)) {
				ccc.add(c);//Movemos la cuenta al Array
			}
		}
		return (ArrayList<Cuenta>) ccc;//devolvemos el Array resultante
	}
	
	/**
	 * Método que Recupera una sola cuenta partiendo del IBAN.
	 * 
	 * @param iban - IBAN de la cuenta a consultar.
	 * @return Cuenta - Cuenta resultante.
	 */
	private Cuenta gtUnCu(String iban) {
		Cuenta ccc = null;//establecemos la Cuenta a null
		for (Cuenta c: cu) {//para cada cuenta
			//si el IBAN de la cuenta es igual al IBAN solicitado
			if (c.getIban().equals(iban)) {
				ccc = c;//Movemos la Cuenta
			}
		}
		return ccc;//Retornamos la Cuenta
	}
	
	/**
	 * Método para Obtener el titular de la cuenta partiendo del Identificador de la misma.
	 * 
	 * @param idCu - Identificador interno de la cuenta.
	 * @return Cliente - Titular de la cuenta Consultada.
	 */
	private Cliente gtUnCliCu(int idCu) {
		Cliente cl = null;//Establecemos el Cliente a nulo
		for (Cuenta c: cu) {//Para cada cuenta
			if (c.getIdCu() == idCu) {//si el Id de Cuenta es igual al ID de cuenta solicitado
				cl = c.getTitular(); //movemos el titular al Objeto Cliente
			}
		}
		return cl;//Devolvemos el Cliente
	}
	
	/**
	 * Método para Obtener el Cliente de la cuenta Solcitada.
	 * 
	 * @param dni - DNI del cliente a extraer.
	 * @return Cliente - Cliente obtenido de la consulta.
	 */
	private Cliente gtUnCli(String dni) {
		Cliente cl = null;//Establecemos el Cliente a nulo
		for (Cliente c: cli) {//Para cada cliente
			if (c.getNif().equals(dni)) {//si el DNI del cliente es el DNI solicitado
				cl = c;//movemos el Cliente
			}
		}
		
		return cl;//Devolvemos el cliente
	}
	
	/**
	 * Método para Buscar un Dato en un Array de String. 
	 * 
	 * @param cdBscr - Dato a buscar.
	 * @param rrStrng - Array donde Buscar.
	 * @return Boolean - True para encontrado y False para no encontrado.
	 */
	private Boolean srchArr(String cdBscr, ArrayList<String> rrStrng) {
		return rrStrng.contains(cdBscr);
	}
	
	/**
	 * Método que sustituye el formato decimal con el separador ',' (coma), por el formato con el separador '.' (punto).
	 * 
	 * @param  num - Número real con el separador ',' (coma).
	 * @return Double - Número real con el separador '.' (punto). 
	 */
	private Double suspc(String num) {
		/*
		 * Realizamos un 'For' para recorrer el número en busca del carácter coma (',')
		 */
		for(int i = 0;i <= (num.length() - 1);i++) { // Contamos desde 0 hasta el número máximo de carácteres que contiene sumando uno cada vez
			char comma = num.charAt(i); // Recogemos el carácter actual del string
			
			if(comma == ',') { // Si el carácter actual es una coma (',')
				num = num.replace(',','.'); // Lo reemplazamos por punto
			}
		}
		return Double.parseDouble(num); // Retornamos como Double el número obtenido con el punto como separador de décimales
	}

	/**
	 * Método para obtener la lista de transferencias recibidas y enviadas de la cuenta seleccionada.
	 * 
	 * @param idC - Identificador interno de la cuenta.
	 */
	private void obtnrTrn(Integer idC) {
		String[] trnsfrncLstE = new String[0];//Declaramos el array de String para los Registros de las Transferencias Enviadas
		String[] trnsfrncLstR = new String[0];//Declaramos el array de String para los Registros de las Transferencias Recibidas
		for(Transferencia t: trans) { //Para cada transferencia
			if (t.getOrigen() == idC) {//si la cuenta solicitada es el origen
				//Formateamos los datos del destino
				String dest = gtUnCliCu(t.getDestino()).getNmbr() + " " + gtUnCliCu(t.getDestino()).getApllds();
				
				//aumentamos en uno el array de registros
				trnsfrncLstE = Arrays.copyOf(trnsfrncLstE, trnsfrncLstE.length + 1);
				//Guardamos el registro en el Array
				trnsfrncLstE[trnsfrncLstE.length - 1] = "Concepto: " + t.getCncpt() + " > Destino: " + dest + " > Importe: " + t.getImp() + " > Referencia: " + t.getRef();  
			} else if (t.getDestino() == idC) {//Si no, Si la cuenta solicitada es el origen
				/*
				 * Formateamos los datos del origen
				 */
				String ori = null;// inicializamos el origen
				int org = t.getOrigen();// recogemos el origen
				
				if(org == 0) {//si el origen es 0
					ori = "Efectivo";//establecemos el origen como "Efectivo", ya que es un ingreso de liquidez Efectivo a la cuenta
				} else {//sino
					//Establecemos los datos del origen
					ori = gtUnCliCu(org).getNmbr() + " " + gtUnCliCu(org).getApllds();
				}
				
				//Aumentamos en uno el array de registros
				trnsfrncLstR = Arrays.copyOf(trnsfrncLstR, trnsfrncLstR.length + 1);
				//Guardamos el registro en el array
				trnsfrncLstR[trnsfrncLstR.length - 1] = "Concepto: " + t.getCncpt() + " > Origen: " + ori + " > Importe: " + t.getImp() + " > Referencia: " + t.getRef();
			}
		}
		
		//llamamos al método que rellena el JList con los registros
		rllnrlst(trnsfrncLstR, trnsfrncLstE);
	}
	
	/**
	 * Método para rellenar los datos del JLList con los registros de las transferencias.
	 * 
	 * @param trnsfrncRlzds - Registros de las transferencias realizadas.
	 * @param trnsfrncRcbds - Registros de las transferencias emitidas.
	 */
	private void rllnrlst(String[] trnsfrncRlzds, String[] trnsfrncRcbds) {
		//Rellenamos el Jlist de las transferencias Realizadas
		window.movDebe.setModel(new AbstractListModel<String>() {
			/**
			 * Serial Version ID
			 */
			private static final long serialVersionUID = -640329577766975200L;
			
			String[] values = trnsfrncRlzds;//establecemos los valores con los registros
			
			/**
			 * Funcion Heredada pra obtener el tamaño del JList
			 */
			public int getSize() {
				return values.length;
			}
			
			/**
			 * Funcion heredada para obtener el valor de un registro en un indice X
			 */
			public String getElementAt(int index) {
				return values[index];
			}
		});
		
		//Rellenamos el Jlist de las transferencias Recibidas
		window.movHaber.setModel(new AbstractListModel<String>() {
			/**
			 * Serial Version ID
			 */
			private static final long serialVersionUID = -640329577766975200L;
			
			String[] values = trnsfrncRcbds;//establecemos los valores con los registros
			
			/**
			 * Funcion Heredada pra obtener el tamaño del JList
			 */
			public int getSize() {
				return values.length;
			}
			
			/**
			 * Funcion heredada para obtener el valor de un registro en un indice X
			 */
			public String getElementAt(int index) {
				return values[index];
			}
		});
	}

	
	/**
	 * Método que carga la vista de gestión de transferencias
	 *
	 * @param back - Motor de la aplicación
	 */
	public void crgrPnl(BackEnd back) {
		try {//Lo encapsulamos todo en un TryCatch para capturar todo tipo de errores que puedan surgir en tiempo de ejecución
			EventQueue.invokeLater(new Runnable() {
				/**
				 * Funcion que ejecuta la ventana y la hace visible
				 */
				public void run() {
					try { //Lo encapsulamos todo en un TryCath para capturar todo tipo de errores que puedan surgir en tiempo de ejecución
						window = new Panel(back); //Declaramos el Panel Ventana (Clase Panel dentro de este mismo proyecto.)
						window.getFrame().setVisible(true);//Establecemos el panel como visible.
					} catch (Exception e) {//en el caso de error
						log.newReg("\n" + timeStamp.format(new Date()) + " - Error: " + e);//escribimos el error en el log
					}
				}
			});
		} catch (Exception e) {//mostramos el error en consola
			log.newReg("\n" + timeStamp.format(new Date()) + " - Error: " + e);//escribimos el error en el log
		}
	}
}
