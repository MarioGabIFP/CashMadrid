import java.text.*;
import java.util.*;
import javax.swing.JOptionPane;
import java.io.*;

/**
 * 
 */

/**
 * @author Mario Gabriel N��ez Alc�zar de Velasco
 *
 */
public class Log {
	/**
	 * Ruta del fichero log
	 */
	private File dir = new File(".\\", "temp");
	/**
	 * Nombre del fichero log
	 */
	private String name = new SimpleDateFormat("yyyy_MM_dd_HH-mm-ss").format(new Date()) + ".log";
	/**
	 * Declaracion del fichero
	 */
	private File file = new File(dir, name); 
	
	/**
	 * Constructor Vac�o Para crear un nuevo Log al principio de cada ejecuci�n
	 * 
	 * @throws IOException 
	 */
	public Log(){
		crte();
	}
	
	/*
	 * M�todo para crear el fichero log
	 */
	private void crte() {
		try {
			file.createNewFile();
		} catch (IOException e) {
			if (!dir.exists()) {
				dir.mkdirs();
				crte();
			} else {
				JOptionPane.showMessageDialog(null, 
						                      "Imposible generar log de ejecuci�n\nCausa: " + e, 
						                      "Cash Madrid - Error", 
						                      0);
			}
		}
	}

	/**
	 * M�todo para escribir una entrada en el log
	 * 
	 * @param reg - Registro a escribir en el log
	 */
	public void newReg(String reg) {
		FileWriter wrtFch = null;
		PrintWriter wrtRg = null;
		try {
			wrtFch = new FileWriter(file,true);
			wrtRg = new PrintWriter(wrtFch);
			wrtRg.print(reg);
			wrtFch.close();
		} catch (IOException e) {
			if (!file.exists()) {
				crte();
				newReg(reg);
			} else {
				JOptionPane.showMessageDialog(null, 
											  "Imposible escribir registro log de ejecuci�n\nCausa: " + e, 
											  "Cash Madrid - Error", 
											  0);
			}
		}
	}
}