import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.GroupLayout.*;
import javax.swing.LayoutStyle.*;
import java.awt.Font;

/**
 * Clase que representa a la ventana principal del programa, Aqu� se realiza todo lo relativo a la recuperaci�n
 * y exposici�n de los datos.
 * 
 * @author Mario Gabriel N��ez Alc�zar de Velasco.
 */
public class Panel {
	/**
	 * Ventana Activa. 
	 */
	private JFrame frmCashMadrid;
	
	/**
	 * Tabla que contendr� los datos del cliente.
	 */
	public JTable cliData = new JTable();
	
	/**
	 * Combo Box que contendra los IBAN de las cuentas de los clientes.
	 */
	public JComboBox<String> cueComboBox = new JComboBox<String>();
	
	/**
	 * ComboBox que contrendra los DNI de los clientes.
	 */
	public JComboBox<String> cliComboBox = new JComboBox<String>();
	
	/**
	 * Panel de transferencias realizadas.
	 */
	public JList<String> movDebe = new JList<String>();
	
	/**
	 * Panel de transferencias Emitidas.
	 */
	public JList<String> movHaber = new JList<String>();
	
	/**
	 * Panel de Movimientos.
	 */
	public JTabbedPane movPane;
	
	/**
	 * Motor interno de la aplicaci�n
	 */
	private BackEnd back;
	
	/**
	 * Constructor para iniciar la Ventana con la respectiva conexion a la base de datos.
	 * 
	 * <br><br>la conexion se usar� para lanzar las respectivas query.
	 * 
	 * @param conexion - conexion a la base de datos. 
	 */
	public Panel(BackEnd back) {
		this.back = back;//Recogemos los datos del motor de la aplicaci�n
		initialize();// llamamos al m�todo que construir� la ventana
	}
	
	/**
	 * Constructor vac�o para declarar un objeto Panel generico
	 */
	public Panel() {}

	/**
	 * M�todo para construir la ventana.
	 */
	private void initialize() {
		/*
		 * Contruimos el Frame
		 */
		setFrame(new JFrame());//Declaramos un nuevo Frame
		getFrame().setBounds(100, 100, 1073, 506);//Damos dimensiones fijas al frame
		
		//Establecemos la accion a realizar cuando pulsamos el bot�n "Cerrar ventana" de Windows. 
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		/*
		 * Titulo de la aplicaci�n
		 */
		JLabel lblNewLabel = new JLabel("Gestionar Transferencias");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		
		/*
		 * Construimos el ComboBox que contendr� la lista de clientes activos en la Base de Datos 
		 */
		//Creamos la etiqueta de Texto que enlazaremos al ComboBox; servir� para indicar al usuario el porqu� de ese desplegable
		JLabel cliSelLabel = new JLabel("Clientes");//Declaramos la etiqueta de texto
		cliSelLabel.setHorizontalAlignment(SwingConstants.CENTER);//Establecemos la alineaci�n Horizontal como colgante ("TRAILING")
		
		//Creamos el ComboBox con la lista de Clientes en la base de datos, selecci�n por NIF
		cliComboBox = new JComboBox<String>();//Declaramos el Combobox con formato ComboBox Editable y argumentos String 

		cliComboBox.setEditable(true);//Establecemos el ComboBox como Editable
		cliSelLabel.setLabelFor(cliComboBox);//Enlazamos la etiqueta de Texto al ComboBox
		
		
		/*
		 * Construimos el ComboBox que contendr� la lista de Cuentas pertenecientes al cliente seleccionado.
		 */
		JLabel cueSelLabel = new JLabel("Cuentas");//Declaramos la etiqueta de texto
		cueSelLabel.setHorizontalAlignment(SwingConstants.CENTER);//Establecemos la alineaci�n Horizontal como colgante ("TRAILING")
		
		//Creamos el ComboBox con la lista de Cuentas que pertenecen al cliente seleccionado, selecci�n por IBAN
		cueComboBox = new JComboBox<String>();//Declaramos el Combobox con formato de argumentos String 
		
		cueComboBox.setEditable(false);//Establecemos el ComboBox como no editable.
		cueSelLabel.setLabelFor(cueComboBox);//Enlazamos la etiqueta de Texto al ComboBox
		
		
		/*
		 * Construimos el Panel que contendr� la tabla con los datos del cliente y al cuenta seleccionados
		 */
		JPanel dtPanel = new JPanel();//Declaramos el panel

		
		/*
		 * Creamos la Tabla que contendr� los datos del cliente y al cuenta seleccionados
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
				.addGroup(gl_dtPanel.createSequentialGroup()//a�adimos un grupo secuencial de datos.
					.addGap(1)//establecemos la tabla como el primer elemento del panel (se mostrar� arriba del todo dentro del "dtPanel")
					.addComponent(cliData, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)//a�adimos el componente
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))//a�adimos el contenedor
		);
		
		dtPanel.setLayout(gl_dtPanel);//Insertamos la plantilla dentor del dtPanel. 

		
		/*
		 * Construimos el panel de pesta�as
		 */
		movPane = new JTabbedPane(JTabbedPane.TOP);//Declaramos el Panel de pesta�as
		movPane.setToolTipText("");//Establecemos el Texto de informaci�n virtual a "vacio" ("") ya que no puede ser nulo.
		
		movDebe = new JList<String>();//Declaramos la lista de elementos que contendr� los movimientos del Debe 
		
		movPane.addTab("Recibidas", null, movDebe, null);//Insertamos la lista "movDebe" en la primera pesta�a 
		
		movHaber = new JList<String>();//Declaramos la lista de elementos que contendr� los movimientos del Haber 
		movPane.addTab("Emitidas", null, movHaber, null);//Insertamos la lista "movHaber" en la primera pesta�a 
		
		
		/*
		 * Declaramos los botones para cada una de las acciones Posibles
		 */
		JButton crtCli = new JButton("Alta Cliente");//Declaramos el bot�n "Alta Cliente" Para el alta de los Clientes
		JButton crtCu = new JButton("Alta Cuenta");//Declaramos el bot�n "Alta Cuenta" Para el alta de las Cuentas
		JButton dltCli = new JButton("Baja Cliente");//Declaramos el bot�n "Baja Cliente" Para la Baja de los Clientes
		JButton dltCu = new JButton("Baja Cuenta");//Declaramos el bot�n "Baja Cuenta" Para la Baja de las Cuentas
		JButton ingsCash = new JButton("Realizar ingreso en efectivo");//Declaramos el bot�n "Realizar ingreso en efectivo" Para la Realizaci�n de los ingresos en efectivo
		JButton crrTransf = new JButton("Crear transferencia");//Declaramos el bot�n "Crear transferencia" Para la Realizaci�n de las Transferencias
		JButton dscrTransf = new JButton("Revertir Transferencia");//Declaramos el boton "Revertir Transferencia" para revertir una Transferenc�a
		
		
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
		ArrayList<String> nif_s = back.obtnrNif(back.cli);//Obtenemos todos los NIF a mostrar
		//los mostramos en el ComboBox
		cliComboBox.setModel(new DefaultComboBoxModel<String>(nif_s.toArray(new String[nif_s.size()])));
		
		/*
		 * Capturamos el evento Load de la ventana
		 */
		frmCashMadrid.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				back.setDataCli(cliComboBox.getSelectedItem().toString());
			}

			@Override
			public void windowClosing(WindowEvent e) {}

			@Override
			public void windowClosed(WindowEvent e) {}

			@Override
			public void windowIconified(WindowEvent e) {}

			@Override
			public void windowDeiconified(WindowEvent e) {}

			@Override
			public void windowActivated(WindowEvent e) {}

			@Override
			public void windowDeactivated(WindowEvent e) {}
		});
		
		
		/*
		 * Creamos los ActionListener para escuchar los cambios realizados por parte del usuario
		 */
		/*
		 * Capturar seleccion de DNI por parte del usuario
		 */
		cliComboBox.addActionListener(new ActionListener() {
					
			//Aplicamos el actionPerformed 
			@Override
			public void actionPerformed(ActionEvent event) {
				JComboBox<String> slctn = (JComboBox<String>) event.getSource();//Recojemos el evento y lo clonamos
				String slctnDNI = (String) slctn.getSelectedItem();//Obtenemos el evento seleccionado del ComboBox
				
				back.setDataCli(slctnDNI);//Establecemos los datos correspondientes al DNI seleccionado
			}
		});
		
		/*
		 * Capturar seleccion de IBAN por parte del usuario
		 */
		cueComboBox.addActionListener(new ActionListener() {
					
			//Aplicamos el actionPerformed 
			@Override
			public void actionPerformed(ActionEvent event) {
				JComboBox<String> slctn = (JComboBox<String>) event.getSource();//Recojemos el evento y lo clonamos
				Object slctnIBAN = slctn.getSelectedItem();//Obtenemos el evento seleccionado del ComboBox
				
				back.setDataCue(slctnIBAN);//Establecemos los datos correspondientes al nuevo DNI seleccionado
			}
		});
		
		/*
		 * Capturar evento click en bot�n "Alta Cliente"
		 */
		crtCli.addActionListener(new ActionListener() {
					
			//Aplicamos el actionPerformed 
			@Override
			public void actionPerformed(ActionEvent event) {
				back.ltClnt();
			}
		});
		
		/*
		 * Capturar evento click en bot�n "Alta Cuenta"
		 */
		crtCu.addActionListener(new ActionListener() {
					
			//Aplicamos el actionPerformed 
			@Override
			public void actionPerformed(ActionEvent event) {
				back.ltCnt();
			}
		});
		
		/*
		 * Capturar evento click en bot�n "Baja Cuenta"
		 */
		dltCu.addActionListener(new ActionListener() {
					
			//Aplicamos el actionPerformed 
			@Override
			public void actionPerformed(ActionEvent event) {
				back.bjcnt();
			}
		});
		
		/*
		 * Capturar evento click en bot�n "Baja Cuenta"
		 */
		dltCli.addActionListener(new ActionListener() {
					
			//Aplicamos el actionPerformed 
			@Override
			public void actionPerformed(ActionEvent event) {
				back.bjclnt();
			}
		});
		
		/*
		 * Capturar evento click en bot�n "Realizar Ingreso en Efectivo"
		 */
		ingsCash.addActionListener(new ActionListener() {
					
			//Aplicamos el actionPerformed 
			@Override
			public void actionPerformed(ActionEvent event) {
				back.ngrsFctv();
			}
		});
		
		/*
		 * Crear Transferencia
		 */
		crrTransf.addActionListener(new ActionListener() {
			
			//Aplicamos el actionPerformed 
			@Override
			public void actionPerformed(ActionEvent event) {
				back.crrTransf();
			}
		});
		
		/*
		 * Deshacer transfrencia
		 */
		dscrTransf.addActionListener(new ActionListener() {
			
			//Aplicamos el actionPerformed 
			@Override
			public void actionPerformed(ActionEvent event) {
				back.dscrTransf();
			}
		});
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
}
