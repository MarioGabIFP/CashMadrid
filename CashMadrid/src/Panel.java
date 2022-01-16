import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.GroupLayout.*;
import javax.swing.LayoutStyle.*;
import javax.swing.table.*;
import java.awt.Font;

/**
 * Clase que representa a la ventana principal del programa, Aquí se realiza todo lo relativo a la recuperación
 * y exposición de los datos.
 * 
 * @author Mario Gabriel Núñez Alcázar de Velasco.
 */
public class Panel {
	/**
	 * Ventana Activa. 
	 */
	private JFrame frmCashMadrid;
	/**
	 * Tabla que contendrá los datos del cliente.
	 */
	private JTable cliData;
	/**
	 * Conexion con la base de datos.
	 */
	private Conexion conexion;
	/**
	 * Seleccion del DNI por parte del usuario.
	 */
	private String slctnDNI;
	/**
	 * Combo Box que contendra los IBAN de las cuentas de los clientes.
	 */
	private JComboBox<String> cueComboBox;
	/**
	 * ComboBox que contrendra los DNI de los clientes.
	 */
	private JComboBox<String> cliComboBox;
	/**
	 * Array de Clientes Activos.
	 */
	private Cliente[] cli;
	/**
	 * Array con todos los clientes, activos y no activos.
	 */
	private Cliente[] allCli;
	/**
	 * Array de cuentas.
	 */
	private Cuenta[] cu = new Cuenta[0];
	/**
	 * Array de Transferencias.
	 */
	private Transferencia[] trans;
	/**
	 * un solo cliente.
	 */
	private Cliente clie;
	/**
	 * una sola cuenta.
	 */
	private Cuenta cuen;
	/**
	 * Panel de transferencias realizadas.
	 */
	private JList<String> movDebe;
	/**
	 * Panel de transferencias Emitidas.
	 */
	private JList<String> movHaber;
	/**
	 * Panel de Movimientos.
	 */
	private JTabbedPane movPane;
	
	/**
	 * Formato fecha.
	 */
	SimpleDateFormat dtfrmt = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * Constructor para iniciar la Ventana con la respectiva conexion a la base de datos.
	 * 
	 * <br><br>la conexion se usará para lanzar las respectivas query.
	 * 
	 * @param conexion - conexion a la base de datos. 
	 */
	public Panel(Conexion conexion) {
		this.conexion = conexion;//Establecemos la conexion con la base de datos.
		crgrDts();//Cargamos los datos
		initialize(); //llamamos al método que construirá la ventana
	}

	/**
	 * Método que se usará para recoger todos los datos de la base de datos en Arrays.
	 */
	private void crgrDts() {
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
		allCli = obtnrCli();

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

	/**
	 * Método para construir la ventana.
	 */
	private void initialize() {
		/*
		 * Contruimos el Frame
		 */
		setFrame(new JFrame());//Declaramos un nuevo Frame
		getFrame().setBounds(100, 100, 1073, 506);//Damos dimensiones fijas al frame
		
		//Establecemos la accion a realizar cuando pulsamos el botón "Cerrar ventana" de Windows. 
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		/*
		 * Titulo de la aplicación
		 */
		JLabel lblNewLabel = new JLabel("Gestionar Transferencias");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		
		/*
		 * Construimos el ComboBox que contendrá la lista de clientes activos en la Base de Datos 
		 */
		//Creamos la etiqueta de Texto que enlazaremos al ComboBox; servirá para indicar al usuario el porqué de ese desplegable
		JLabel cliSelLabel = new JLabel("Clientes");//Declaramos la etiqueta de texto
		cliSelLabel.setHorizontalAlignment(SwingConstants.CENTER);//Establecemos la alineación Horizontal como colgante ("TRAILING")
		
		//Creamos el ComboBox con la lista de Clientes en la base de datos, selección por NIF
		cliComboBox = new JComboBox<String>();//Declaramos el Combobox con formato ComboBox Editable y argumentos String 

		cliComboBox.setEditable(true);//Establecemos el ComboBox como Editable
		cliSelLabel.setLabelFor(cliComboBox);//Enlazamos la etiqueta de Texto al ComboBox
		
		
		/*
		 * Construimos el ComboBox que contendrá la lista de Cuentas pertenecientes al cliente seleccionado.
		 */
		JLabel cueSelLabel = new JLabel("Cuentas");//Declaramos la etiqueta de texto
		cueSelLabel.setHorizontalAlignment(SwingConstants.CENTER);//Establecemos la alineación Horizontal como colgante ("TRAILING")
		
		//Creamos el ComboBox con la lista de Cuentas que pertenecen al cliente seleccionado, selección por IBAN
		cueComboBox = new JComboBox<String>();//Declaramos el Combobox con formato de argumentos String 
		
		cueComboBox.setEditable(false);//Establecemos el ComboBox como no editable.
		cueSelLabel.setLabelFor(cueComboBox);//Enlazamos la etiqueta de Texto al ComboBox
		
		
		/*
		 * Construimos el Panel que contendrá la tabla con los datos del cliente y al cuenta seleccionados
		 */
		JPanel dtPanel = new JPanel();//Declaramos el panel

		
		/*
		 * Creamos la Tabla que contendrá los datos del cliente y al cuenta seleccionados
		 */
		cliData = new JTable();//Declaramos la tabla 
		
		
		/*
		 * Agrupamos la tabla dentro del Panel "dtPanel".
		 */
		GroupLayout gl_dtPanel = new GroupLayout(dtPanel);//Declaramos el objeto "GroupLayout" (Grupo de plantillas) rellenandolo con los datos del panel "dtPanel". 
		
		gl_dtPanel.setHorizontalGroup(//Establecemos la agrupacion horizontal
			gl_dtPanel.createParallelGroup(Alignment.LEADING)//Creamos un grupo paralelo para heredar la alineacion de la tabla.
				.addComponent(cliData, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)//Insertamos la tabla dentro del Panel
		);
		
		gl_dtPanel.setVerticalGroup(//Establecemos la agrupacion Vertical
			gl_dtPanel.createParallelGroup(Alignment.LEADING)//Creamos un grupo paralelo para heredar la alineacion de la tabla.
				.addGroup(gl_dtPanel.createSequentialGroup()//añadimos un grupo secuencial de datos.
					.addGap(1)//establecemos la tabla como el primer elemento del panel (se mostrará arriba del todo dentro del "dtPanel")
					.addComponent(cliData, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)//añadimos el componente
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))//añadimos el contenedor
		);
		
		dtPanel.setLayout(gl_dtPanel);//Insertamos la plantilla dentor del dtPanel. 

		
		/*
		 * Construimos el panel de pestañas
		 */
		movPane = new JTabbedPane(JTabbedPane.TOP);//Declaramos el Panel de pestañas
		movPane.setToolTipText("");//Establecemos el Texto de información virtual a "vacio" ("") ya que no puede ser nulo.
		
		movDebe = new JList<String>();//Declaramos la lista de elementos que contendrá los movimientos del Debe 
		
		movPane.addTab("Recibidas", null, movDebe, null);//Insertamos la lista "movDebe" en la primera pestaña 
		
		movHaber = new JList<String>();//Declaramos la lista de elementos que contendrá los movimientos del Haber 
		movPane.addTab("Emitidas", null, movHaber, null);//Insertamos la lista "movHaber" en la primera pestaña 
		
		
		/*
		 * Declaramos los botones para cada una de las acciones Posibles
		 */
		JButton crtCli = new JButton("Alta Cliente");//Declaramos el botón "Alta Cliente" Para el alta de los Clientes
		JButton crtCu = new JButton("Alta Cuenta");//Declaramos el botón "Alta Cuenta" Para el alta de las Cuentas
		JButton dltCli = new JButton("Baja Cliente");//Declaramos el botón "Baja Cliente" Para la Baja de los Clientes
		JButton dltCu = new JButton("Baja Cuenta");//Declaramos el botón "Baja Cuenta" Para la Baja de las Cuentas
		JButton ingsCash = new JButton("Realizar ingreso en efectivo");//Declaramos el botón "Realizar ingreso en efectivo" Para la Realización de los ingresos en efectivo
		JButton crrTransf = new JButton("Crear transferencia");//Declaramos el botón "Crear transferencia" Para la Realización de las Transferencias
		JButton dscrTransf = new JButton("Revertir Transferencia");//Declaramos el boton "Revertir Transferencia" para revertir una Transferencía
		
		
		/*
		 * Coloco todo lo construido anteriormente en el Frame.
		 */
		GroupLayout groupLayout = new GroupLayout(getFrame().getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(ingsCash, GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(cliSelLabel, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
								.addComponent(cueSelLabel, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(cliComboBox, 0, 269, Short.MAX_VALUE)
								.addComponent(cueComboBox, 0, 269, Short.MAX_VALUE)))
						.addComponent(dtPanel, GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(crtCli, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
								.addComponent(dltCli, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(dltCu, GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
								.addComponent(crtCu, GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(movPane, GroupLayout.PREFERRED_SIZE, 672, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(crrTransf, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(dscrTransf, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(47)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(cliComboBox, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(cliSelLabel))
							.addGap(13)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(cueComboBox, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(cueSelLabel)))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(movPane, GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNewLabel)
									.addGap(73)
									.addComponent(dtPanel, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(crtCu)
										.addComponent(crtCli))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(dltCu)
										.addComponent(dltCli))))))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(ingsCash)
						.addComponent(dscrTransf)
						.addComponent(crrTransf))
					.addGap(26))
		);
		
		getFrame().getContentPane().setLayout(groupLayout);//insertamos la plantilla.
		
		
		/*
		 * Inicializamos todos los datos con valores recogidos de la base de datos.
		 */
		cliComboBox.setModel(new DefaultComboBoxModel<String>(obtnrNif(cli)));
		setDataCli(cliComboBox.getSelectedItem().toString());

		
		/*
		 * Creamos los ActionListener para escuchar los cambios realizados por parte del usuario
		 */
		/*
		 * Capturar seleccion de DNI por parte del usuario
		 */
		cliComboBox.addActionListener(new ActionListener() {
					
			//Heredamos la SuperClass actionPerformed 
			@Override
			public void actionPerformed(ActionEvent event) {
				JComboBox<String> slctn = (JComboBox<String>) event.getSource();//Recojemos el evento y lo clonamos
				slctnDNI = (String) slctn.getSelectedItem();//Obtenemos el evento seleccionado del ComboBox
				
				setDataCli(slctnDNI);//Establecemos los datos correspondientes al DNI seleccionado
			}
		});
		
		/*
		 * Capturar seleccion de IBAN por parte del usuario
		 */
		cueComboBox.addActionListener(new ActionListener() {
					
			//Heredamos la SuperClass actionPerformed 
			@Override
			public void actionPerformed(ActionEvent event) {
				JComboBox<String> slctn = (JComboBox<String>) event.getSource();//Recojemos el evento y lo clonamos
				Object slctnIBAN = slctn.getSelectedItem();//Obtenemos el evento seleccionado del ComboBox
				
				setDataCue(slctnIBAN);//Establecemos los datos correspondientes al nuevo DNI seleccionado
			}
		});
		
		/*
		 * Capturar evento click en botón "Alta Cliente"
		 */
		crtCli.addActionListener(new ActionListener() {
					
			//Heredamos la SuperClass actionPerformed 
			@Override
			public void actionPerformed(ActionEvent event) {
				ltClnt();
			}
		});
		
		/*
		 * Capturar evento click en botón "Alta Cuenta"
		 */
		crtCu.addActionListener(new ActionListener() {
					
			//Heredamos la SuperClass actionPerformed 
			@Override
			public void actionPerformed(ActionEvent event) {
				ltCnt();
			}
		});
		
		/*
		 * Capturar evento click en botón "Baja Cuenta"
		 */
		dltCu.addActionListener(new ActionListener() {
					
			//Heredamos la SuperClass actionPerformed 
			@Override
			public void actionPerformed(ActionEvent event) {
				bjcnt();
			}
		});
		
		/*
		 * Capturar evento click en botón "Baja Cuenta"
		 */
		dltCli.addActionListener(new ActionListener() {
					
			//Heredamos la SuperClass actionPerformed 
			@Override
			public void actionPerformed(ActionEvent event) {
				bjclnt();
			}
		});
		
		/*
		 * Capturar evento click en botón "Realizar Ingreso en Efectivo"
		 */
		ingsCash.addActionListener(new ActionListener() {
					
			//Heredamos la SuperClass actionPerformed 
			@Override
			public void actionPerformed(ActionEvent event) {
				ngrsFctv();
			}
		});
		
		/*
		 * Crear Transferencia
		 */
		crrTransf.addActionListener(new ActionListener() {
			
			//Heredamos la SuperClass actionPerformed 
			@Override
			public void actionPerformed(ActionEvent event) {
				crrTransf();
			}
		});
		
		/*
		 * Deshacer transfrencia
		 */
		dscrTransf.addActionListener(new ActionListener() {
			
			//Heredamos la SuperClass actionPerformed 
			@Override
			public void actionPerformed(ActionEvent event) {
				dscrTransf();
			}
		});
	}

	/**
	 * Método para eliminar el registro de la transferencia seleccionado en el JList. 
	 */
	protected void dscrTransf() {
		// Deshacer Transferencia
		Object ibanObj = cueComboBox.getSelectedItem();//Obtenemos el Iban de la cuenta seleccionada
		if (cliData.getValueAt(12, 1) != null) {//si la fecha de baja está rellena
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
			JList<String> selPane = (JList<String>) movPane.getSelectedComponent();
			String selTrans = selPane.getSelectedValue();//obtenemos el registro seleccionado

			//siempre y cuando haya un registro seleccionado
			if (selTrans != null) {
				//mostramos mensaje de confirmación
				int opt = JOptionPane.showConfirmDialog(null, "¿Estas seguro de querer eliminar el movimiento?\nEsta accion modificará el saldo Contable", "CashMadrid", JOptionPane.OK_CANCEL_OPTION);
				
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
											   null);
					//ejecutamos la query
					queryOBJ.execute();
					reload();//Recargamos los datos.
				}
			}
		}
	}

	/**
	 * Método para realizar una transferencia a cuenta.
	 */
	protected void crrTransf() {
		// si la cuenta de origen seleccionada no tiene la fecha fin rellena
		if (cliData.getValueAt(12, 1) == null) { 
			Object ibanObj = cueComboBox.getSelectedItem();//Obtenemos la cuenta seleccionada 
			
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
					if (imprt != null) {//si el importe no es null
						String imp = imprt.getText();//obtenemos el importe
						String bn = iban.getText();//obtenemos el iban destino
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
														   1);
								//ejecutamos el insert
								queryOBJ.execute();
								reload();//volvemos a recoger los datos de la base de datos.
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
		if (cliData.getValueAt(12, 1) == null) { 
			//obtenemos el objeto IBAN de la cuenta seleccionada
			Object iban = cueComboBox.getSelectedItem();
			
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
													   1);
							//ejecutamos el insert
							queryOBJ.execute();
							reload();//Volvemos a obtener los datos de la base de datos.
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
			int idClien = gtUnCli(cliComboBox.getSelectedItem().toString()).getIdCli();
			Query queryOBJ = new Query("FFIN;Activ",
					   				   "asignacion;Clientes",
					   				   Statement.UPDATE,
					   				   "WHERE IdCli = " + idClien + " AND FFIN is null" + ";WHERE IdCli = " + idClien,
					   				   dtfrmt.format(new Date()) + "; 0",
					   				   null,
					   				   conexion,
					   				   2);
			//ejecutamos el Update
			queryOBJ.execute();
			reload();//Volvemos a obtener los datos desde la base de datos.
		}
	}
	
	/**
	 * Método para dar de baja a una cuenta.
	 */
	protected void bjcnt() {
		//si el la cuenta no está ya dada de baja
		if (cliData.getValueAt(12, 1) == null) { 
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
										   "WHERE IdCli = " + gtUnCli(cliComboBox.getSelectedItem().toString()).getIdCli() + " AND IdCu = " + gtUnCu(cueComboBox.getSelectedItem().toString()).getIdCu(),
										   dtfrmt.format(new Date()),
										   null,
										   conexion,
										   1);
				//Ejecutamos el Update
				queryOBJ.execute();
				reload();//volvemos a obtener los datos de la base de datos.
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
							int idCli = gtUnCli(cliComboBox.getSelectedItem().toString()).getIdCli();
							
							//Construimos la cadena con los valores de a insertar
							String val = idCu + ", '" + dgtCn + "', '" + ent + "', '" + ofi + "', '" + dgtCntrl + "', '" + NCue + "', '" + nBanc.getText() + "';" + idCli + ", " + idCu + ", '" + dtfrmt.format(new Date()) + "'";
							
							nsrtrCu(val);//llamamos a insertar la cuenta
							reload();//recargamos los datos desde la base de datos
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
				if (!srchArr(ml, EMAIL_s)) {
					//si el DNI no existe en la base de datos.
					if (!srchArr(dni, DNI_s)) {
						//mostamos la query con los valores introducidos.
						String values = "'" + dni + "', '" + nmbr + "', '" + apell.getText() + "', '" + tel + "', '" + ml + "', '" + dmcl + "', " + 1;
						nsrtrCli(values);//llamamos a procesar la petición.
						reload();//Recargamos los datos de la base de datos.
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
	private void reload() {
		/*
		 * Inicializamos todos los datos con valores de la base de datos.
		 */
		crgrDts();
		//Declaramos el modelo de datos insertando el Array resultante de "obtnrNif(obtnrCli()))" y lo insertamos en el ComboBox
		cliComboBox.setModel(new DefaultComboBoxModel<String>(obtnrNif(cli)));
		setDataCli(cliComboBox.getSelectedItem().toString());
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
								   1);
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
								   2);
		//ejecutamos el insert y recuperamos el resultado en un array de Objetos
		queryOBJ.execute();
	}

	/**
	 * Metodo para obtener el JFrame del Frame activo.
	 * 
	 * @return JFrame - frame activo.
	 */
	public JFrame getFrame() {
		return frmCashMadrid;//devolvemos el frame activo.
	}

	/**
	 * Metodo para establecer el Frame activo.
	 * 
	 * @param frame - Frame que deseamos que se convierta en el Frame Activo. 
	 */
	public void setFrame(JFrame frame) {
		this.frmCashMadrid = frame;//Establecemos el Frame activo
		frmCashMadrid.setTitle("Cash Madrid - Gestionar transferencias");//Ponemos el titulo a la ventana
		frame.setResizable(false);//Establecemos que la ventana no se pueda reescalar
	}
	
	/**
	 * Función para obtener todos los datos de todos los clientes de la base de datos.
	 * @return Cliente[] - Array de clientes.
	 */
	private Cliente[] obtnrCli() {
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
								   null);
		//ejecutamos la query y recuperamos el resultado en un array de Objetos
		Object[] obj = queryOBJ.execute();
		//declaramos el array de Clientes especificando como tamaño por defecto la cantidad de registros
		Cliente[] cli = new Cliente[obj.length];
		
		//rellenamos el array de clientes; un cliente por registro, un objeto por cliente, todo en un mismo array
		for (int i = 0;i < obj.length;i++) {
			cli[i] = (Cliente) obj[i];
		}
		
		//retornamos el array de Clientes.
		return cli;
	}
	
	/**
	 * Función para obtener todos los datos de todas las cuentas asociados al cliente.
	 * 
	 * @param dni - DNI/NIF/NIE del cliente a consultar.
	 * @return Cuenta[] - Array de cuentas.
	 */
	private Cuenta[] obtnrCu(String dni) {
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
									null);
		//ejecutamos la query y recuperamos el resultado en un array de Objetos
		Object[] obj = queryOBJ.execute();
		//declaramos el array de Clientes especificando como tamaño por defecto la cantidad de registros
		Cuenta[] cu = new Cuenta[obj.length];
		
		//rellenamos el array de clientes; un cliente por registro, un objeto por cliente, todo en un mismo array
		for (int i = 0;i < obj.length;i++) {
			cu[i] = (Cuenta) obj[i];
		}
		
		//retornamos el array de Cuentas.
		return cu;
	}
	
	/**
	 * Funcion para obtener la lista de todos los NIF/DNI/NIE de los clientes ubicados en un mismo Array de Clientes.
	 *  
	 * @param cli - Array de Clientes a consultar.
	 * @return String[] - Array de Strings que contendra los DNI/NIE/NIF de los clientes solicitados.
	 */
	private String[] obtnrNif(Cliente[] cli) {
		String[] nif = new String[0]; //Declaramos el String con tamaño de Array a 0
		int x = 0;//declaramos el contador de Objetos
		
		/*
		 * For para añadir en el Array de Strings el DNI de cada cliente
		 */
		for (Cliente c: cli) {//por cada cliente
			nif = Arrays.copyOf(nif, x + 1);//aumentamos en 1 el tamaño del array
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
		String[] iban = new String[0];//Declaramos el String con tamaño de Array a 0
		int x = 0;//declaramos el contador de Objetos
		
		/*
		 * For para añadir en el Array de Strings el DNI de cada cliente
		 */
		for (Cuenta c: cu) {//por cada cuenta
			iban = Arrays.copyOf(iban, x + 1);//aumentamos en 1 el tamaño del array
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
	private void setDataCli(String slctnDNI) {
		//Obtenemos todos los datos del cliente seleccionado en el ComboBox
		clie = gtUnCli(slctnDNI);
		
		if (clie != null) {
			//obtenemos el listado de cuentas del cliente seleccionado
			Cuenta[] cueArr = gtCuTit(slctnDNI);
			
			//Declaramos el modelo de datos insertando el Array resultante de "obtnrIBAN(obtnrCu(slctnDNI)" y lo insertamos en el ComboBox
			cueComboBox.setModel(new DefaultComboBoxModel<String>(obtnrIBAN(cueArr)));
			
			//Establecemos los datos de la cuenta
			setDataCue(cueComboBox.getSelectedItem());
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
	private void setDataCue(Object slctnIBAN) {
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
			cliData.setModel(new DefaultTableModel( // Declaramos el Modelo de datos de la tabla.
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
			cliData.setModel(new DefaultTableModel( // Declaramos el Modelo de datos de la tabla.
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
	private Cuenta[] gtCuTit(String DNI) {
		int x = 0;//establecemos el contador a 0
		Cuenta[] ccc = new Cuenta[x];//establecemos el Array de Cuentas a 0
		for (Cuenta c: cu) {//para cada cuenta
			//Si el DNI del titular de la cuenta es igual al DNI del cliente Solicitado 
			if (c.getTitular().getNif().equals(DNI)) {
				ccc = Arrays.copyOf(ccc, x + 1);//aumentamos en '1' el tamaño del Array de Cuentas
				ccc[x] = c;//Movemos la cuenta al Array
				x++;//aumentamos en 1 el array
			}
		}
		return ccc;//devolvemos el Array resultante
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
	private Boolean srchArr(String cdBscr, String[] rrStrng) {
		return Arrays.asList(rrStrng).contains(cdBscr);
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
	 * Método para Obtener todas las transferencias registradas en la tabla.
	 * 
	 * @return Transferencia[] - Array con las transferencias existentes en la tabla.
	 */
	private Transferencia[] obtnrTrnsfrnc() {
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
								   null);
		//ejecutamos la query y recuperamos el resultado en un array de Objetos
		Object[] obj = queryOBJ.execute();
		//declaramos el array de Transferencias especificando como tamaño por defecto la cantidad de registros
		Transferencia[] trnsfrnc = new Transferencia[obj.length];
		
		//rellenamos el array de Transferencias; una Transferencia por registro, un objeto por Transferencia, todo en un mismo array
		for (int i = 0;i < obj.length;i++) {
			trnsfrnc[i] = (Transferencia) obj[i];
		}
		
		//retornamos el array de Transferencias.
		return trnsfrnc;
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
		movDebe.setModel(new AbstractListModel<String>() {
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
		movHaber.setModel(new AbstractListModel<String>() {
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
}
