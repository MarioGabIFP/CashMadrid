import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.GroupLayout.*;
import javax.swing.LayoutStyle.*;
import javax.swing.table.*;

/**
 * Clase que representa a la ventana principal del programa.
 * 
 * @author Mario Gabriel Núñez Alcázar de Velasco
 */
public class Panel {
	/**
	 * Ventana Activa. 
	 */
	private JFrame frame;
	/**
	 * Tabla que contendrá los datos del cliente.
	 */
	private JTable cliData;
	/**
	 * Conexion con la base de datos.
	 */
	private Conexion conexion;
	/**
	 * Seleccion del DNI por parte del usuario
	 */
	private String slctnDNI;
	/**
	 * Combo Box que contendra los IBAN de las cuentas de los clientes
	 */
	private JComboBox<String> cueComboBox;
	/**
	 * ComboBox que contrendra los DNI de los clientes
	 */
	private JComboBox<String> cliComboBox;
	/**
	 * Array de Clientes
	 */
	private Cliente[] cli;
	/**
	 * Array de cuentas
	 */
	private Cuenta[] cu = new Cuenta[0];
	
	/**
	 * Inicializamos el panel en la ventana.
	 */
	public Panel(Conexion conexion) {
		this.conexion = conexion;//Establecemos la conexion con la base de datos.
		crgrDts();//Cargamos los datos
		initialize(); //llamamos al método que construirá la ventana
	}

	private void crgrDts() {
		/*
		 * Cargamos los Datos de los Clientes
		 */
		cli = obtnrCli();
		
		
		/*
		 * cargamos los datos de las cuentas
		 */
		for (Cliente c: cli) {//por cada cliente
			String nif = c.getNif();//Obtenemos el dato NIF
			
			Cuenta[] tempArrCu = obtnrCu(nif);
			
			for (int y = 0;y < tempArrCu.length;y++) {
				tempArrCu[y].setTitular(c);
			}
			
			cu = Arrays.copyOf(cu, cu.length + tempArrCu.length);//aumentamos en 1 el tamaño del array
			
			int x = 0; //inicializamos un nuevo contador
			for (int z = 0;z < cu.length;z++) {
				if (cu[z] == null) {
					cu[z] = tempArrCu[x];
					x++;
				}
			}
		}
	}

	/**
	 * Construimos la ventana.
	 */
	private void initialize() {
		/*
		 * Contruimos el Frame
		 */
		setFrame(new JFrame());//Declaramos un nuevo Frame
		getFrame().setBounds(100, 100, 712, 506);//Damos dimensiones fijas al frame
		
		//Establecemos la accion a realizar cuando pulsamos el botón "Cerrar ventana" de Windows. 
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		
		/*
		 * Construimos la Barra de herramientas
		 */
		JMenuBar menuBar = new JMenuBar();//Declaramos una nueva barra de herramientas
		getFrame().setJMenuBar(menuBar);//Colocamos labarra de herramientas en el frame 
		
		//Creamos un nuevo elemento en la barra de herramientas 
		JMenu fileMenu = new JMenu("Archivo");//Creamos el elemento "Archivo" como Menú contextual 
		menuBar.add(fileMenu);//Añadimos el Menú Contextual a la barra de herramientas
		
		//Creamos un nuevo elemento en el menú contextual de la barra de herramientas.
		JMenuItem bddConfig = new JMenuItem("Establecer Sesión");//Creamos el elemento "Establecer sesión" como elemento de menú
		fileMenu.add(bddConfig);//Colocamos el elemento en el menú contextual.
		
		
		/*
		 * Construimos el ComboBox que contendrá la lista de clientes activos en la Base de Datos 
		 */
		//Creamos la etiqueta de Texto que enlazaremos al ComboBox; servirá para indicar al usuario el porqué de ese desplegable
		JLabel cliSelLabel = new JLabel("Clientes");//Declaramos la etiqueta de texto
		cliSelLabel.setHorizontalAlignment(SwingConstants.TRAILING);//Establecemos la alineación Horizontal como colgante ("TRAILING")
		
		//Creamos el ComboBox con la lista de Clientes en la base de datos, selección por NIF
		cliComboBox = new JComboBox<String>();//Declaramos el Combobox con formato ComboBox Editable y argumentos String 

		cliComboBox.setEditable(true);//Establecemos el ComboBox como Editable
		cliSelLabel.setLabelFor(cliComboBox);//Enlazamos la etiqueta de Texto al ComboBox
		
		
		/*
		 * Construimos el ComboBox que contendrá la lista de Cuentas pertenecientes al cliente seleccionado.
		 */
		JLabel cueSelLabel = new JLabel("Cuentas");//Declaramos la etiqueta de texto
		cueSelLabel.setHorizontalAlignment(SwingConstants.TRAILING);//Establecemos la alineación Horizontal como colgante ("TRAILING")
		
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
		JTabbedPane movPane = new JTabbedPane(JTabbedPane.TOP);//Declaramos el Panel de pestañas
		movPane.setToolTipText("");//Establecemos el Texto de información virtual a "vacio" ("") ya que no puede ser nulo.
		
		JList movDebe = new JList();//Declaramos la lista de elementos que contendrá los movimientos del Debe 
		movPane.addTab("Debe", null, movDebe, null);//Insertamos la lista "movDebe" en la primera pestaña 
		
		JList movHaber = new JList();//Declaramos la lista de elementos que contendrá los movimientos del Haber 
		movPane.addTab("Haber", null, movHaber, null);//Insertamos la lista "movHaber" en la primera pestaña 
		
		
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
		 * Aprietensé los machos, voy a colocar todo lo construido anteriormente en el Frame :') 
		 */
		GroupLayout groupLayout = new GroupLayout(getFrame().getContentPane());//Declaramos el objeto "GroupLayout" (Grupo de plantillas) rellenandolo con los datos del contenido del Frame. 

		
		
		/*
		 * Construimos la plantilla con todos los elementos construidos anteriormente y ubicandolo 
		 * en sus respectivas coordenadas (procuramos heredar añadiendo grupos secuenciales y paralelos)
		 */
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(cliSelLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(cueSelLabel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(cueComboBox, 0, 217, Short.MAX_VALUE)
										.addComponent(cliComboBox, 0, 217, Short.MAX_VALUE)))
								.addComponent(dtPanel, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(crtCli, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(dltCli, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(dltCu, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(crtCu, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE))))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(movPane, GroupLayout.PREFERRED_SIZE, 351, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(ingsCash, GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(crrTransf, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
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
								.addComponent(cliSelLabel)
								.addComponent(cliComboBox, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
							.addGap(13)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(cueSelLabel)
								.addComponent(cueComboBox, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(dtPanel, GroupLayout.PREFERRED_SIZE, 217, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(crtCu)
								.addComponent(crtCli))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(dltCu)
								.addComponent(dltCli)))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(movPane, GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(ingsCash)
						.addComponent(dscrTransf)
						.addComponent(crrTransf))
					.addGap(26))
		);
		
		getFrame().getContentPane().setLayout(groupLayout);//insertamos la plantilla.
		
		
		/*
		 * Inicializamos todos los datos con valores de la base de datos.
		 */
		//Declaramos el modelo de datos insertando el Array resultante de "obtnrNif(cli)" y lo insertamos en el ComboBox
		cliComboBox.setModel(new DefaultComboBoxModel<String>(obtnrNif(cli)));
		setDataCue(cliComboBox.getSelectedItem().toString());

		
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
				
				setDataCue(slctnDNI);//Establecemos los datos correspondientes al nuevo DNI seleccionado
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
	}

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
			String ibanCmplt = iban.getText();
			
			String dgtCn = ibanCmplt.substring(0, 4);
			String ent = ibanCmplt.substring(4, 8);
			String ofi = iban.getText().substring(8, 12);
			String dgtCntrl = iban.getText().substring(12, 14);
			String NCue = iban.getText().substring(14);
			
			
//			String values = "'" + nif.getText() + "', '" + nom.getText() + "', '" + apell.getText() + "', '" + tlfn.getText() + "', '" + email.getText() + "', '" + domic.getText() + "'";
//			nsrtrCli(values);
//			reload();
		}
	}

	/**
	 * Metodo para dar de anta un cliente en la base de datos.
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
			String values = "'" + nif.getText() + "', '" + nom.getText() + "', '" + apell.getText() + "', '" + tlfn.getText() + "', '" + email.getText() + "', '" + domic.getText() + "'";
			nsrtrCli(values);
			reload();
		}
	}

	private void reload() {
		/*
		 * Inicializamos todos los datos con valores de la base de datos.
		 */
		crgrDts();
		//Declaramos el modelo de datos insertando el Array resultante de "obtnrNif(obtnrCli()))" y lo insertamos en el ComboBox
		cliComboBox.setModel(new DefaultComboBoxModel<String>(obtnrNif(cli)));
		setDataCue(cliComboBox.getSelectedItem().toString());
	}

	private void nsrtrCli(String values) {
		/*
 		 * Select para rellenar los objetos Cliente con los datos de la tabla Cliente.
 		 * Cada registro de la tabla corresponderá a un objeto. 
 		 */
 		//Establecemos los datos de la query y la modalidad de retorno
		Query queryOBJ = new Query("DNI, Nom, Apel, Tel, Email, Dom", 
								   "Clientes", 
								   Statement.INSERT, 
								   null,
								   values,
								   null, 
								   conexion);
		//ejecutamos el insert y recuperamos el resultado en un array de Objetos
		queryOBJ.execute();
	}
	
	private void nsrtrCu(String values) {
		/*
 		 * Select para rellenar los objetos Cliente con los datos de la tabla Cliente.
 		 * Cada registro de la tabla corresponderá a un objeto. 
 		 */
 		//Establecemos los datos de la query y la modalidad de retorno
		Query queryOBJ = new Query("DNI, Nom, Apel, Tel, Email, Dom", 
								   "Clientes", 
								   Statement.INSERT, 
								   null,
								   values,
								   null, 
								   conexion);
		//ejecutamos el insert y recuperamos el resultado en un array de Objetos
		queryOBJ.execute();
	}

	/**
	 * Metodo para obtener el JFrame del Frame activo
	 * 
	 * @return JFrame - frame activo
	 */
	public JFrame getFrame() {
		return frame;//devolvemos el frame activo.
	}

	/**
	 * Metodo para establecer el Frame activo
	 * 
	 * @param frame - Frame que deseamos que se convierta en el Frame Activo 
	 */
	public void setFrame(JFrame frame) {
		this.frame = frame;//Establecemos el Frame activo
	}
	
	/**
	 * Función para obtener todos los datos de todos los clientes de la base de datos.
	 * @return Cliente[] - Array de clientes
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
								   conexion);
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
	 * Función para obtener todos los datos de todas las cuentas asociados al cliente
	 * 
	 * @param dni - DNI/NIF/NIE del cliente a consultar
	 * @return Cuenta[] - Array de cuentas
	 */
	private Cuenta[] obtnrCu(String dni) {
		/*
 		 * Select para rellenar los objetos Cliente con los datos de la tabla Cliente.
 		 * Cada registro de la tabla corresponderá a un objeto. 
 		 */
 		//Establecemos los datos de la query y la modalidad de retorno
		Query queryOBJ = new Query("concat(DigCon,Ent,Ofi,DigContr,NCue) as Numero_cuenta, NBanc, FINI, FFIN, DNI", 
									"asignacion", 
									Statement.SELECT, 
									"left join clientes on asignacion.IdCli = Clientes.IdCli left join cuenta on asignacion.IdCu = cuenta.idcu where DNI = '" + dni + "'",
									null,
									Data.CUENTA,
									conexion);
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
	 * Funcion para obtener la lista de todos los NIF/DNI/NIE de los clientes ubicados en un mismo Array de Clientes
	 *  
	 * @param cli - Array de Clientes a consultar
	 * @return String[] - Array de Strings que contendra los DNI/NIE/NIF de los clientes solicitados
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
	 * Funcion para obtener la lista de todos los IBAN de las Cuentas ubicadas en un mismo Array de Cuentas
	 *  
	 * @param cu - Array de Cuentas a consultar
	 * @return String[] - Array de Strings que contendra los IBAN de las cuentas solicitadas
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
	 * Funcion para rellenar los campos de la Ventana
	 * 
	 * @param slctnDNI - DNI del cliente seleccionado
	 */
	private void setDataCue(String slctnDNI) {
		Cuenta[] cueArr = gtCuTit(slctnDNI);
		
		//Obtenemos todos los datos del cliente seleccionado en el ComboBox
		Cliente clie = gtUnCli(slctnDNI);
		
		//Declaramos el modelo de datos insertando el Array resultante de "obtnrIBAN(obtnrCu(slctnDNI)" y lo insertamos en el ComboBox
		cueComboBox.setModel(new DefaultComboBoxModel<String>(obtnrIBAN(cueArr)));
						
		//Obtenemos todos los datos de la cuenta del cliente seleccionada
		//Con "cueComboBox.getSelectedItem().toString()" Obtenemos el IBAN seleccionado en el ComboBox "Cuentas"
		Object slctnIBAN = cueComboBox.getSelectedItem();
		
		if (slctnIBAN != null) {
			Cuenta cuen = gtUnCu(slctnIBAN.toString());
		
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
		} else {
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
	
				public Class getColumnClass(int columnIndex) {// Declaramos la clase que obtendrá el tipo de dato de la
																// celda seleccionada
					return columnTypes[columnIndex];// Devolvemos tipo de dato de la celda seleccionada
				}
			});
		}
	}

	private Cuenta[] gtCuTit(String DNI) {
		int x = 0;
		Cuenta[] ccc = new Cuenta[x];
		for (Cuenta c: cu) {
			if (c.getTitular().getNif().equals(DNI)) {
				ccc = Arrays.copyOf(ccc, x + 1);
				ccc[x] = c;
				x++;
			}
		}
		return ccc;
	}
	
	private Cuenta gtUnCu(String IBAN) {
		Cuenta ccc = null;
		for (Cuenta c: cu) {
			if (c.getIban().equals(IBAN)) {
				ccc = c;
			}
		}
		return ccc;
	}
	
	private Cliente gtUnCli(String DNI) {
		Cliente cl = null;
		for (Cliente c: cli) {
			if (c.getNif().equals(DNI)) {
				cl = c;
			}
		}
		return cl;
	}
}
