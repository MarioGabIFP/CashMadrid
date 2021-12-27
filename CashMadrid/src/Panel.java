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
		
		JComboBox cliComboBox = new JComboBox();
		cliComboBox.setEditable(true);
		cliSelLabel.setLabelFor(cliComboBox);
		
		JLabel cueSelLabel = new JLabel("Cuentas");
		cueSelLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		
		JComboBox cueComboBox = new JComboBox();
		cueComboBox.setEditable(true);
		
		JTabbedPane movPane = new JTabbedPane(JTabbedPane.TOP);
		movPane.setToolTipText("");
		
		JButton crtCli = new JButton("Alta Cliente");
		
		JButton crtCu = new JButton("Alta Cuenta");
		
		JButton dltCli = new JButton("Baja Cliente");
		
		JButton dltCu = new JButton("Baja Cuenta");
		
		JButton ingsCash = new JButton("Realizar ingreso en efectivo");
		
		JPanel dtPanel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getFrame().getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(dtPanel, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(cliSelLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(cueSelLabel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(cliComboBox, 0, 155, Short.MAX_VALUE)
								.addComponent(cueComboBox, 0, 155, Short.MAX_VALUE)))
						.addComponent(ingsCash, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(crtCli, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(dltCli, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(crtCu, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
								.addComponent(dltCu, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))))
					.addGap(18)
					.addComponent(movPane, GroupLayout.PREFERRED_SIZE, 392, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
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
							.addComponent(dtPanel, GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(crtCli)
								.addComponent(crtCu))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(dltCli)
								.addComponent(dltCu))
							.addGap(18)
							.addComponent(ingsCash))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(movPane, GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)))
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
}
