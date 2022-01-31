import java.awt.EventQueue;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

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
 * Motor interno de la aplicaci�n.
 * 
 * @author Mario Gabriel N��ez Alc�zar de Velasco
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
	 * timeStamp de ejecuci�n.
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
	 * Vista Gesti�n de transferencias
	 */
	private Panel window = new Panel();
	
	/******************************************************
	 ********************** ARRAYS ************************
	 ******************************************************/
	
	/**
	 * Array de Clientes Activos.
	 */
	public Cliente[] cli;
	
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
	 * Constructor para incializar la ejecuci�n del BackEnd.
	 * 
	 * @param conexion - Datos de conexion a la base de datos
	 * @param log - Datos del fichero log de la ejecuci�n actual
	 */
	public BackEnd(Conexion conexion, Log log) {
		this.conexion = conexion;//Recogemos los datos de la conexion
		this.log = log;//Recogemos los datos del fichero log
	}
	
	/**
	 * M�todo que se usar� para recoger todos los datos de la base de datos en Arrays.
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
				
				cu = Arrays.copyOf(cu, cu.length + 1);//aumentamos en 1 el tama�o del array
				
				if (cu[cu.length - 1] == null) {
					cu[cu.length - 1] = s;//Introducimos el array en el array de cuentas
				}
			}
		}
	}
	
	/**
	 * M�todo para Obtener todas las transferencias registradas en la tabla.
	 * 
	 * @return Transferencia[] - Array con las transferencias existentes en la tabla.
	 */
	private Transferencia[] obtnrTrnsfrnc() {
		/*
 		 * Select para rellenar los objetos Transferencia con los datos de la tabla Transferencia.
 		 * Cada registro de la tabla corresponder� a un objeto. 
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
		Object[] obj = queryOBJ.execute();
		//declaramos el array de Transferencias especificando como tama�o por defecto la cantidad de registros
		Transferencia[] trnsfrnc = new Transferencia[obj.length];
		
		//rellenamos el array de Transferencias; una Transferencia por registro, un objeto por Transferencia, todo en un mismo array
		for (int i = 0;i < obj.length;i++) {
			trnsfrnc[i] = (Transferencia) obj[i];
		}
		
		//retornamos el array de Transferencias.
		return trnsfrnc;
	}
	
	/**
	 * Funci�n para obtener todos los datos de todos los clientes de la base de datos.
	 * 
	 * @return Cliente[] - Array de clientes.
	 */
	private Cliente[] obtnrCli() {
		/*
 		 * Select para rellenar los objetos Cliente con los datos de la tabla Cliente.
 		 * Cada registro de la tabla corresponder� a un objeto. 
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
		Object[] obj = queryOBJ.execute();
		//declaramos el array de Clientes especificando como tama�o por defecto la cantidad de registros
		Cliente[] cli = new Cliente[obj.length];
		
		//rellenamos el array de clientes; un cliente por registro, un objeto por cliente, todo en un mismo array
		for (int i = 0;i < obj.length;i++) {
			cli[i] = (Cliente) obj[i];
		}
		
		//retornamos el array de Clientes.
		return cli;
	}
	
	/**
	 * Funci�n para obtener todos los datos de todas las cuentas asociados al cliente.
	 * 
	 * @param dni - DNI/NIF/NIE del cliente a consultar.
	 * @return Cuenta[] - Array de cuentas.
	 */
	private Cuenta[] obtnrCu(String dni) {
		/*
 		 * Select para rellenar los objetos Cliente con los datos de la tabla Cliente.
 		 * Cada registro de la tabla corresponder� a un objeto. 
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
		Object[] obj = queryOBJ.execute();
		//declaramos el array de Clientes especificando como tama�o por defecto la cantidad de registros
		Cuenta[] cu = new Cuenta[obj.length];
		
		//rellenamos el array de clientes; un cliente por registro, un objeto por cliente, todo en un mismo array
		for (int i = 0;i < obj.length;i++) {
			cu[i] = (Cuenta) obj[i];
		}
		
		//retornamos el array de Cuentas.
		return cu;
	}	
	
	/**
	 * M�todo para obtener el Saldo de la cuenta Seleccionada.
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
	 * M�todo para eliminar el registro de la transferencia seleccionado en el JList. 
	 */
	protected void dscrTransf() {
		// Deshacer Transferencia
		Object ibanObj = window.cueComboBox.getSelectedItem();//Obtenemos el Iban de la cuenta seleccionada
		if (window.cliData.getValueAt(12, 1) != null) {//si la fecha de baja est� rellena
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
			//obtenemos la pesta�a activa.
			JList<String> selPane = (JList<String>) window.movPane.getSelectedComponent();
			String selTrans = selPane.getSelectedValue();//obtenemos el registro seleccionado

			if (selTrans == null) {//si no hay ning�n registro seleccionado
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
			 * Provisional a la espera de dise�o *
			 *************************************/
			/*
			 * Caso en el que intenta revertir un ingreso: a�n en Dise�o.
			 */
			} else if (selPane == window.movDebe) {//Si est� intentando devolver un ingreso
				/*
				 * Mostramos error
				 */
				JOptionPane.showMessageDialog(null, 
	  					  					  "Acci�n no disponible: No puedes rechazar un ingreso\nSi lo deseas puedes hablar con el emisor de la transferenc�a o con tu gestor bancario.", 
	  					  					  "CashMadrid",
	  					  					  1, 
	  					  					  null);

			/*************************************
			 * Provisional a la espera de dise�o *
			 *************** FIN *****************/
			/******************************************************************************/
			} else {
				//mostramos mensaje de confirmaci�n
				int opt = JOptionPane.showConfirmDialog(null, 
														"�Estas seguro de querer eliminar el movimiento?\nEsta accion modificar� el saldo Contable", 
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
	 * M�todo para realizar una transferencia a cuenta.
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
	 * M�todo para Ingresar Efectivo en la cuenta.
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
	 * M�todo para dar de baja un cliente.
	 */
	protected void bjclnt() {
		//mostramos mensaje de confirmaci�n
		int opt = JOptionPane.showConfirmDialog(null, "�Estas seguro de querer dar la baja al Cliente?", "CashMadrid", JOptionPane.OK_CANCEL_OPTION);
		
		//si selecciona 'OK'
		if (opt == 0) {
			/*
	 		 * Update para dar de baja un cliente y sus cuentas rellenando la fecha fin de cuenta 
	 		 * y estableciendo el campo activ a false (TinyInt = 0)
	 		 * Cada registro de la tabla corresponder� a un objeto. 
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
	 * M�todo para dar de baja a una cuenta.
	 */
	protected void bjcnt() {
		//si el la cuenta no est� ya dada de baja
		if (window.cliData.getValueAt(12, 1) == null) { 
			//mostramos mensaje de confirmaci�n
			int opt = JOptionPane.showConfirmDialog(null, "�Estas seguro de querer cerrar la cuenta?", "CashMadrid", JOptionPane.OK_CANCEL_OPTION);
			
			//si el usuario pulsa en 'OK'
			if (opt == 0) {
				/*
		 		 * Update para dar de baja una cuenta rellenando la fecha fin de cuenta
		 		 * Cada registro de la tabla corresponder� a un objeto. 
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
										  "La cuenta seleccionada ya est� dada de baja", 
										  "CashMadrid", 
										  0, 
										  null);
		}
	}

	/**
	 * M�todo para dar de alta una cuenta.
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
				int x = 0;//establecemos el contador a 0
				
				String[] IBAN_s = new String[0];//declaramos un Array de String temporal
				
				//Obtenemos todas las cuentas actuales del cliente
				for (Cuenta c: cu) {
					IBAN_s = Arrays.copyOf(IBAN_s, x + 1);
					IBAN_s[x] = c.getIban();
					x++;
				}
				
				//si la cuenta no existe (para ello buscamos el IBAN dentro del Array de String obtenido)
				if (!srchArr(ibanCmplt, IBAN_s)) {
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
			
			int x = 0;//declaramos un nuevo contador
			
			//declaramos un nuevo Array de String para almacenar los DNI's Existentes
			String[] DNI_s = new String[0];
			//declaramos un nuevo Array de String para almacenar los Email's Existentes
			String[] EMAIL_s = new String[0];
			
			//Obtenemos los Email y los Dni de todos los clientes
			for (Cliente c: cli) {
				DNI_s = Arrays.copyOf(DNI_s, x + 1);
				EMAIL_s = Arrays.copyOf(EMAIL_s, x + 1);
				DNI_s[x] = c.getNif();
				EMAIL_s[x] = c.getEml();
				x++;
			}
			
			//Obtenemos los datos introducidos por el usuario.
			String dni = nif.getText();
			String ml = email.getText();
			String nmbr = nom.getText();
			String tel = tlfn.getText();
			String dmcl = domic.getText();
			
			//si existe algun dato de los obligatorios Vac�o.
			if (dni.equals("")|| ml.equals("") || nmbr.equals("") || tel.equals("") || dmcl.equals("")) {
				//mostramos error
				JOptionPane.showMessageDialog(null, 
											  "Falta alguno de los campos obligatorios.", 
											  "CashMadrid", 
											  0, 
											  null);
			} else {//sino
				//si el Email no existe en la base de datos.
				if (!srchArr(ml, EMAIL_s)) {
					//si el DNI no existe en la base de datos.
					if (!srchArr(dni, DNI_s)) {
						//mostamos la query con los valores introducidos.
						String values = "'" + dni + "', '" + nmbr + "', '" + apell.getText() + "', '" + tel + "', '" + ml + "', '" + dmcl + "', " + 1;
						nsrtrCli(values);//llamamos a procesar la petici�n.
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
	 * M�todo para Recargar los datos de la aplicaci�n obteniendo los datos de la base de datos.
	 */
	public void load() {
		/*
		 * Inicializamos todos los datos con valores de la base de datos.
		 */
		crgrDts();
		//Declaramos el modelo de datos insertando el Array resultante de "obtnrNif(obtnrCli()))" y lo insertamos en el ComboBox
		window.cliComboBox.setModel(new DefaultComboBoxModel<String>(obtnrNif(cli)));
		setDataCli(window.cliComboBox.getSelectedItem().toString());
	}

	/**
	 * M�todo para insertar clientes en la base de datos.
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
	 * M�todo para insertar cuentas en la base de datos y asiganrlas al cliente solicitado.
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
	public String[] obtnrNif(Cliente[] cli) {
		String[] nif = new String[0]; //Declaramos el String con tama�o de Array a 0
		int x = 0;//declaramos el contador de Objetos
		
		/*
		 * For para a�adir en el Array de Strings el DNI de cada cliente
		 */
		for (Cliente c: cli) {//por cada cliente
			nif = Arrays.copyOf(nif, x + 1);//aumentamos en 1 el tama�o del array
			nif[x]= c.getNif();//Obtenemos el dato NIF y lo introducimos en la posicion actual del array
			x++;//aumentamos en 1 el contador
		}
		
		return nif; //Devolvemos el Array resultante. 
	}
	
	/**
	 * Funcion para obtener la lista de todos los IBAN de las Cuentas ubicadas en un mismo Array de Cuentas.
	 *  
	 * @param cu - Array de Cuentas a consultar.
	 * @return String[] - Array de Strings que contendra los IBAN de las cuentas solicitadas.
	 */
	private String[] obtnrIBAN(Cuenta[] cu) {
		String[] iban = new String[0];//Declaramos el String con tama�o de Array a 0
		int x = 0;//declaramos el contador de Objetos
		
		/*
		 * For para a�adir en el Array de Strings el DNI de cada cliente
		 */
		for (Cuenta c: cu) {//por cada cuenta
			iban = Arrays.copyOf(iban, x + 1);//aumentamos en 1 el tama�o del array
			iban[x]= c.getIban();//Obtenemos el dato IBAN y lo introducimos en la posicion actual del array
			x++;//aumentamos en 1 el contador
		}
		
		return iban;//Devolvemos el Array resultante. 
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
			Cuenta[] cueArr = gtCuTit(slctnDNI);
			
			//Declaramos el modelo de datos insertando el Array resultante de "obtnrIBAN(obtnrCu(slctnDNI)" y lo insertamos en el ComboBox
			window.cueComboBox.setModel(new DefaultComboBoxModel<String>(obtnrIBAN(cueArr)));
			
			//Establecemos los datos de la cuenta
			setDataCue(window.cueComboBox.getSelectedItem());
		} else {//sino
			//mostramos error
			JOptionPane.showMessageDialog(null, 
										  "El Cliente especificado no existe o no est� activo", 
										  "CashMadrid", 
										  0, 
										  null);
		}
	}
	
	/**
	 * M�todo desarrollado para rellenar todos los datos de la cuenta.
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
	 * M�todo desarrollado para rellenar los datos del JTable.
	 * 
	 * @param clie - Cliente a pintar en el JTable.
	 * @param cuen - Cuenta a pintar en el JTable.
	 */
	private void rllnrTbl(Cliente clie, Cuenta cuen) {
		if (cuen != null) {//si existen datos de cuenta
			window.cliData.setModel(new DefaultTableModel( // Declaramos el Modelo de datos de la tabla.
					new Object[][] { // Declaramos objeto Array Object[][] que contendr� los registros de la tabla con los datos correspondientes.
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
							{ "Fecha Cierre", cuen.getFechaCierre() } // Fecha de cliente de la cuenta (Si la cuenta esta activa, obtendremos null y la celda se mostrar� vac�a).
					}, new String[] { // Declaramos el objeto Array String[] que contendr� los nombres internos de las columnas (est�s no se mostrar�n en la ventana).
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
	
				public Class getColumnClass(int columnIndex) {// Declaramos la clase que obtendr� el tipo de dato de la
															  // celda seleccionada
					return columnTypes[columnIndex];// Devolvemos tipo de dato de la celda seleccionada
				}
			});
		} else { //si no (Datos de la cuenta a null)
			window.cliData.setModel(new DefaultTableModel( // Declaramos el Modelo de datos de la tabla.
					new Object[][] { // Declaramos objeto Array Object que contendr� los registros de la tabla con los datos correspondientes.
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
							{ "Fecha Cierre", null } // Fecha de cliente de la cuenta (Si la cuenta esta activa, obtendremos null y la celda se mostrar� vac�a).
					}, new String[] { // Declaramos el objeto Array String[] que contendr� los nombres internos de las columnas (est�s no se mostrar�n en la ventana).
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
	
				public Class getColumnClass(int columnIndex) {// Declaramos la clase que obtendr� el tipo de dato de la celda seleccionada
					return columnTypes[columnIndex];// Devolvemos tipo de dato de la celda seleccionada
				}
			});
		}
	}
	
	/**
	 * M�todo que Recupera las cuentas de un cliente partiendo del DNI del mismo.
	 * 
	 * @param DNI - Numero de Identificaci�n del Titular de las cuentas.
	 * @return Cuenta[] - Array de Cuentas Resultante.
	 */
	private Cuenta[] gtCuTit(String DNI) {
		int x = 0;//establecemos el contador a 0
		Cuenta[] ccc = new Cuenta[x];//establecemos el Array de Cuentas a 0
		for (Cuenta c: cu) {//para cada cuenta
			//Si el DNI del titular de la cuenta es igual al DNI del cliente Solicitado 
			if (c.getTitular().getNif().equals(DNI)) {
				ccc = Arrays.copyOf(ccc, x + 1);//aumentamos en '1' el tama�o del Array de Cuentas
				ccc[x] = c;//Movemos la cuenta al Array
				x++;//aumentamos en 1 el array
			}
		}
		return ccc;//devolvemos el Array resultante
	}
	
	/**
	 * M�todo que Recupera una sola cuenta partiendo del IBAN.
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
	 * M�todo para Obtener el titular de la cuenta partiendo del Identificador de la misma.
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
	 * M�todo para Obtener el Cliente de la cuenta Solcitada.
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
	 * M�todo para Buscar un Dato en un Array de String. 
	 * 
	 * @param cdBscr - Dato a buscar.
	 * @param rrStrng - Array donde Buscar.
	 * @return Boolean - True para encontrado y False para no encontrado.
	 */
	private Boolean srchArr(String cdBscr, String[] rrStrng) {
		return Arrays.asList(rrStrng).contains(cdBscr);
	}
	
	/**
	 * M�todo que sustituye el formato decimal con el separador ',' (coma), por el formato con el separador '.' (punto).
	 * 
	 * @param  num - N�mero real con el separador ',' (coma).
	 * @return Double - N�mero real con el separador '.' (punto). 
	 */
	private Double suspc(String num) {
		/*
		 * Realizamos un 'For' para recorrer el n�mero en busca del car�cter coma (',')
		 */
		for(int i = 0;i <= (num.length() - 1);i++) { // Contamos desde 0 hasta el n�mero m�ximo de car�cteres que contiene sumando uno cada vez
			char comma = num.charAt(i); // Recogemos el car�cter actual del string
			
			if(comma == ',') { // Si el car�cter actual es una coma (',')
				num = num.replace(',','.'); // Lo reemplazamos por punto
			}
		}
		return Double.parseDouble(num); // Retornamos como Double el n�mero obtenido con el punto como separador de d�cimales
	}

	/**
	 * M�todo para obtener la lista de transferencias recibidas y enviadas de la cuenta seleccionada.
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
		
		//llamamos al m�todo que rellena el JList con los registros
		rllnrlst(trnsfrncLstR, trnsfrncLstE);
	}
	
	/**
	 * M�todo para rellenar los datos del JLList con los registros de las transferencias.
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
			 * Funcion Heredada pra obtener el tama�o del JList
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
			 * Funcion Heredada pra obtener el tama�o del JList
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
	 * M�todo que carga la vista de gesti�n de transferencias
	 *
	 * @param back - Motor de la aplicaci�n
	 */
	public void crgrPnl(BackEnd back) {
		try {//Lo encapsulamos todo en un TryCatch para capturar todo tipo de errores que puedan surgir en tiempo de ejecuci�n
			EventQueue.invokeLater(new Runnable() {
				/**
				 * Funcion que ejecuta la ventana y la hace visible
				 */
				public void run() {
					try { //Lo encapsulamos todo en un TryCath para capturar todo tipo de errores que puedan surgir en tiempo de ejecuci�n
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
