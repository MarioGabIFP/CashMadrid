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
	 * Inicializamos el panel en la ventana.
	 */
	public Panel() {
		initialize(); //llamamos al método que construirá la ventana
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
		JComboBox<String> cliComboBox = new JComboBox<String>();//Declaramos el Combobox con formato ComboBox Editable y argumentos String 

		//Declaramos el modelo de datos insertando el Array resultante de "obtnrNif(obtnrCli()))" y lo insertamos en el ComboBox
		cliComboBox.setModel(new DefaultComboBoxModel<String>(obtnrNif(obtnrCli())));
		
		cliComboBox.setEditable(true);//Establecemos el ComboBox como Editable
		cliSelLabel.setLabelFor(cliComboBox);//Enlazamos la etiqueta de Texto al ComboBox
		
		
		/*
		 * Construimos el ComboBox que contendrá la lista de Cuentas pertenecientes al cliente seleccionado.
		 */
		JLabel cueSelLabel = new JLabel("Cuentas");//Declaramos la etiqueta de texto
		cueSelLabel.setHorizontalAlignment(SwingConstants.TRAILING);//Establecemos la alineación Horizontal como colgante ("TRAILING")
		
		//Creamos el ComboBox con la lista de Cuentas que pertenecen al cliente seleccionado, selección por IBAN
		JComboBox<String> cueComboBox = new JComboBox<String>();//Declaramos el Combobox con formato de argumentos String 
		
		//Declaramos el modelo de datos insertando el Array resultante de "obtnrIBAN(obtnrCu(cliComboBox.getSelectedItem().toString())))" y lo insertamos en el ComboBox
		//Con "cliComboBox.getSelectedItem().toString()" Obtenemos el DNI seleccionado en el ComboBox "Clientes"
		cueComboBox.setModel(new DefaultComboBoxModel<String>(obtnrIBAN(obtnrCu(cliComboBox.getSelectedItem().toString()))));
		
		cueComboBox.setEditable(false);//Establecemos el ComboBox como no editable.
		cueSelLabel.setLabelFor(cueComboBox);//Enlazamos la etiqueta de Texto al ComboBox
		
		
		/*
		 * Construimos el Panel que contendrá la tabla con los datos del cliente y al cuenta seleccionados
		 */
		JPanel dtPanel = new JPanel();//Declaramos el panel

		//Obtenemos todos los datos del cliente seleccionado en el ComboBox
		//Con "cliComboBox.getSelectedItem().toString()" Obtenemos el DNI seleccionado en el ComboBox "Clientes"
		Cliente clie = obtnrUnCli(cliComboBox.getSelectedItem().toString());
		
		//Obtenemos todos los datos de la cuenta del cliente seleccionada
		//Con "cueComboBox.getSelectedItem().toString()" Obtenemos el IBAN seleccionado en el ComboBox "Cuentas"
		Cuenta cuen = obtnrUnCu(cueComboBox.getSelectedItem().toString());
		
		
		/*
		 * Creamos la Tabla que contendrá los datos del cliente y al cuenta seleccionados
		 */
		cliData = new JTable();//Declaramos la tabla 
		
		cliData.setModel(new DefaultTableModel( //Declaramos el Modelo de datos de la tabla
			new Object[][] {//Declaramos objeto Array Object[][] que contendrá los registros de la tabla con los datos correspondientes
				{"Datos de cliente", "****************"},//Cabecera que indicadora del inicio de los datos del cliente
				{"Nombre", clie.getNmbr()},//Nombre del cliente
				{"Apellidos", clie.getApllds()},//Apellidos del cliente
				{"DNI/NIF/NIE", clie.getNif()},//DNI-NIF-NIE del cliente
				{"Telefono", clie.getTlfn()},//Telefono del cliente
				{"Email", clie.getEml()},//Email del Cliente 
				{"Domicilio", clie.getDmcl()},//Domicilio del cliente
				{"Datos de la cuenta bancaria", "****************"},//Cabecera que indicadora del inicio de los datos de la cuenta.
				{"IBAN", cuen.getIban()},//IBAN de la cuenta.
				{"Entidad Bancaria", cuen.getNmbrbnc()},//Nombre de la entidad bancaria de la cuenta.
				{"Saldo", null},//Saldo de la cuenta.
				{"Fecha Apertura", cuen.getFechaApertura()},//Fecha de Apertura de la cuenta
				{"Fecha Cierre", cuen.getFechaCierre()}//Fecha de cliente de la cuenta (Si la cuenta esta activa, obtendremos null y la celda se mostrará vacía).
			},
			new String[] {//Declaramos el objeto Array String[] que contendrá los nombres internos de las columnas (estás no se mostrarán en la ventana).
				"Dato", //Columna que contiene el nombre de los datos (Columna de la izquierda)
				"Valor"//Columna que contiene el valor de los datos (Columna de la Derecha)
			}
		) {
			/**
			 * Serial Version de la Tabla
			 */
			private static final long serialVersionUID = 7927909933540332783L;//Declaramos el Serial Version como constante
			
			/*
			 * Declaramos los tipos de datos de todas las celdas de la tabla.
			 */
			Class[] columnTypes = new Class[] {//Declaramos la clase columnTypes para establecer los tipos de datos 
				String.class, //Para la primera columna establecemos las celdas como String 
				String.class //Para la Segunda columna establecemos las celdas como String 
			};
			public Class getColumnClass(int columnIndex) {//Declaramos la clase que obtendrá el tipo de dato de la celda seleccionada
				return columnTypes[columnIndex];//Devolvemos tipo de dato de la celda seleccionada
			}
		});
		
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
		 * Construimos la plantilla con todos los elementos construidos anteriormente y hubicandolo 
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
		Query queryOBJ = new Query("*", "Clientes", Statement.SELECT, null, Data.CLIENTES);
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
	 * Función para obtener todos los datos de un unico Cliente, buscando por DNI
	 *  
	 * @param dni - DNI/NIF/NIE del cliente a consultar
	 * @return Cliente - Datos del cliente solicitado
	 */
	private Cliente obtnrUnCli(String dni) {
		/*
		 * Select para rellenar los objetos Cliente con los datos de la tabla Cliente.
		 * Cada registro de la tabla corresponderá a un objeto. 
		 */
		//Establecemos los datos de la query y la modalidad de retorno
		Query queryOBJ = new Query("*", "Clientes", Statement.SELECT, "where DNI = '" + dni + "'", Data.CLIENTES);
		
		//declaramos el array de Clientes especificando como tamaño por defecto la cantidad de registros
		Cliente cli = (Cliente) queryOBJ.execute()[0];
		
		//Retornamos los datos del cliente
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
									Data.CUENTA);
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
	 * Función para obtener todos los datos de una unica Cuenta, buscando por IBAN
	 *  
	 * @param iban - iban de la cuenta a ocnsultar
	 * @return Cuenta - Datos de la Cuenta solicitada
	 */
	private Cuenta obtnrUnCu(String iban) {
		/*
		 * Select para rellenar los objetos Cliente con los datos de la tabla Cliente.
		 * Cada registro de la tabla corresponderá a un objeto. 
		 */
		//Establecemos los datos de la query y la modalidad de retorno
		Query queryOBJ = new Query("concat(DigCon,Ent,Ofi,DigContr,NCue) as Numero_cuenta, NBanc, FINI, FFIN", 
								   "asignacion", 
								   Statement.SELECT, 
								   "left join cuenta on asignacion.IdCu = cuenta.idcu where concat(DigCon,Ent,Ofi,DigContr,NCue) = '" + iban + "'",
								   Data.CUENTA);
		//declaramos el array de Clientes especificando como tamaño por defecto la cantidad de registros
		Cuenta cu = (Cuenta) queryOBJ.execute()[0];
		
		//devolvemos los datos de la cuenta obtenidos.
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
}
