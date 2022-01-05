import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * 
 * @author Mario Gabriel Núñez Alcázar de Velasco
 */
public class Panel {

	private JFrame frame;
	private JTable cliData;

	/**
	 * Create the application.
	 */
	public Panel() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setFrame(new JFrame());
		getFrame().setBounds(100, 100, 670, 452);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		getFrame().setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("Archivo");
		menuBar.add(fileMenu);
		
		JMenuItem bddConfig = new JMenuItem("Conexion");
		fileMenu.add(bddConfig);
		
		JLabel cliSelLabel = new JLabel("Clientes");
		cliSelLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		
//		//Combo Box con lista de Clientes en la base de datos, seleccion por NIF
//		JComboBox<String> cliComboBox = new JComboBox<String>(obtnrNif(obtnrCli()));
//		cliComboBox.setEditable(true);
//		cliSelLabel.setLabelFor(cliComboBox);
		
		//Combo Box con lista de Clientes en la base de datos, seleccion por NIF
		JComboBox cliComboBox = new JComboBox();
		cliComboBox.setEditable(true);
		cliSelLabel.setLabelFor(cliComboBox);
		
		JLabel cueSelLabel = new JLabel("Cuentas");
		cueSelLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		
//		//Combo Box con la lista de las Cuentas que pertenecen al cliente seleccionado 
//		JComboBox<String> cueComboBox = new JComboBox<String>(obtnrIBAN(obtnrCu()));
//		cueComboBox.setEditable(false);
//		cueSelLabel.setLabelFor(cueComboBox);
		
		//Combo Box con la lista de las Cuentas que pertenecen al cliente seleccionado 
		JComboBox cueComboBox = new JComboBox();
		cueComboBox.setEditable(false);
		cueSelLabel.setLabelFor(cueComboBox);
		
		
		
		JTabbedPane movPane = new JTabbedPane(JTabbedPane.TOP);
		movPane.setToolTipText("");
		
		JButton crtCli = new JButton("Alta Cliente");
		
		JButton crtCu = new JButton("Alta Cuenta");
		
		JButton dltCli = new JButton("Baja Cliente");
		
		JButton dltCu = new JButton("Baja Cuenta");
		
		JButton ingsCash = new JButton("Realizar ingreso en efectivo");
		
		JPanel dtPanel = new JPanel();
		
		JButton crrTransf = new JButton("Crear transferencia");
		
		JButton dscrTransf = new JButton("Revertir Transferencia");
		GroupLayout groupLayout = new GroupLayout(getFrame().getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(dtPanel, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(cliSelLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(cueSelLabel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(cliComboBox, 0, 167, Short.MAX_VALUE)
								.addComponent(cueComboBox, 0, 167, Short.MAX_VALUE)))
						.addComponent(ingsCash, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(crtCli, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(dltCli, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(crtCu, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
								.addComponent(dltCu, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))))
					.addGap(6)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(movPane, GroupLayout.PREFERRED_SIZE, 392, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(crrTransf, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(dscrTransf, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(movPane, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
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
							.addComponent(dtPanel, GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(crtCli)
								.addComponent(crtCu))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(dltCli)
								.addComponent(dltCu))))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(ingsCash)
						.addComponent(crrTransf)
						.addComponent(dscrTransf))
					.addContainerGap())
		);
		dtPanel.setLayout(new GridLayout(6, 2, 0, 0));
		
		cliData = new JTable();
		dtPanel.add(cliData);
		
		JList movDebe = new JList();
		movPane.addTab("Debe", null, movDebe, null);
		
		JList movHaber = new JList();
		movPane.addTab("Haber", null, movHaber, null);
		getFrame().getContentPane().setLayout(groupLayout);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
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
		
		return cli;
	}
	
	private Cuenta[] obtnrCu() {
		/*
 		 * Select para rellenar los objetos Cliente con los datos de la tabla Cliente.
 		 * Cada registro de la tabla corresponderá a un objeto. 
 		 */
 		//Establecemos los datos de la query y la modalidad de retorno
		Query queryOBJ = new Query("concat(DigCon,Ent,Ofi,DigContr,NCue) as Numero_cuenta, NBanc, FINI, FFIN, DNI", 
									"asignacion", 
									Statement.SELECT, 
									"left join clientes on asignacion.IdCli = Clientes.IdCli left join cuenta on asignacion.IdCu = cuenta.idcu where DNI = '53447382J'",
									Data.CUENTA);
		//ejecutamos la query y recuperamos el resultado en un array de Objetos
		Object[] obj = queryOBJ.execute();
		//declaramos el array de Clientes especificando como tamaño por defecto la cantidad de registros
		Cuenta[] cu = new Cuenta[obj.length];
		
		//rellenamos el array de clientes; un cliente por registro, un objeto por cliente, todo en un mismo array
		for (int i = 0;i < obj.length;i++) {
			cu[i] = (Cuenta) obj[i];
		}
		
		return cu;
	}
	
	private String[] obtnrNif(Cliente[] cli) {
		String[] nif = new String[0];
		int x = 0;
		
		for (Cliente c: cli) {
			nif = Arrays.copyOf(nif, x + 1);
			nif[x]= c.getNif();
			x++;
		}
		
		return nif;
	}
	
	private String[] obtnrIBAN(Cuenta[] cu) {
		String[] iban = new String[0];
		int x = 0;
		
		for (Cuenta c: cu) {
			iban = Arrays.copyOf(iban, x + 1);
			iban[x]= c.getIban();
			x++;
		}
		
		return iban;
	}
}
