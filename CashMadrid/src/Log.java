import java.text.*;
import java.util.*;
import javax.swing.JOptionPane;
import java.io.*;

/**
 * Objeto log en tiempo de ejecuci�n.
 * 
 * Genera un nuevo fichero con timeStamp de ejecuci�n.
 * Escribe un registro a la vez en el log.
 * 
 * @author Mario Gabriel N��ez Alc�zar de Velasco.
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
	 */
	public Log(){
		crte();//Llamamos a crear un nuevo log de ejecuci�n.
	}
	
	/**
	 * M�todo para crear el fichero log
	 */
	private void crte() {
		try {
			file.createNewFile();//Creamos el fichero
		} catch (IOException e) {//Si sucede alg�n error durante la creaci�n del mismo
			if (!dir.exists()) {//Si la carpeta donde se ubican no existe
				dir.mkdirs();//La creamos
				crte();//Nos volvemos a llamar a nosotros mismos para crear el fichero
			} else {//De lo contrario
				/*
				 * Mostramos mensaje de error interno
				 */
				JOptionPane.showMessageDialog(null, 
						                      "Imposible generar log de ejecuci�n\nCausa: " + e, 
						                      "Cash Madrid - Error", 
						                      0);
			}
		}
	}

	/**
	 * M�todo para escribir una entrada en el log de ejecuci�n
	 * 
	 * @param reg - Registro a escribir en el log de ejecuci�n
	 */
	public void newReg(String reg) {
		/*
		 * Declaraci�n del FileWriter para abrir el fichero a su escritura. 
		 */
		FileWriter wrtFch = null;
		/*
		 * Declaraci�n del PrintWriter para escrir un conjunto de caracteres en el fichero 
		 */
		PrintWriter wrtRg = null;
		
		/*
		 * Ejecuci�n de la escritura en el fichero
		 */
		try {
			wrtFch = new FileWriter(file,true);//Abrimos el fichero y lo preparamos para su escritura la final del mismo
			wrtRg = new PrintWriter(wrtFch);//Preparamos el fichero para recibir un conjunto de caracteres
			wrtRg.print(reg);//Escribimos en el fichero
			wrtFch.close();//Cerramos el fichero
		} catch (IOException e) {//Si surge alg�n error en tiempo de ejecuci�n
			if (!file.exists()) {//Si el fichero no existe
				crte();//Lo creamos
				newReg(reg);//Volvemos a intentar escribir el registro solicitado
			} else {//De lo contrario
				/*
				 * Mostramos mensaje de error interno
				 */
				JOptionPane.showMessageDialog(null, 
											  "Imposible escribir registro log de ejecuci�n\nCausa: " + e, 
											  "Cash Madrid - Error", 
											  0);
			}
		}
	}
}