import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Objeto Query, aqui se encuentran las funciones correspondientes para realizar las querys a la base de datos.
 * 
 * @author Mario Gabriel Núñez Alcázar de Velasco.
 */
public class Query {
	/**
	 * Columna/s sobre las que se realizará la query solicitada.
	 */
	private String cols;
	
	/**
	 * Tabla/s sobre la que se realizará la query solicitada.
	 */
	private String tab;
	
	/**
	 * Tipo de query solicitada.
	 */
	private Statement type; 
	
	/**
	 * Modificador de la query.
	 */
	private String mod;
	
	/**
	 * Valores a insertar en la Base de Datos.
	 */
	private String valIns;
	
	/**
	 * Dato a Extraer.
	 */
	private Data dat;
	
	/**
	 * Cantidad de querys a concatenar.
	 */
	private Integer cant;
	
	/**
	 * Resultado de la Query realizada.
	 * <br>
	 * Contendrá los registros obtenidos tras la query, por defecto se establece a [null].
	 */
	private ResultSet result = null;
	
	/**
	 * Metadata del resultado de la Query realizada.
	 * <br>
	 * Por defecto se establece a [null].
	 */
	private ResultSetMetaData resultmtdt = null;
	
	/**
	 * Declaramos el Array de Objetos en el que almacenaremos el resultado de la query.
	 */
	private ArrayList<Object> resul = new ArrayList<Object>();//por defecto lo declaramos con tamaño '0'
	
	/**
	 * Declaramos el Objeto PreparedStatment que se encargará de mandar la query al motor BDD para ejecutarlo.
	 */
	private PreparedStatement statment = null;
	
	/**
	 * Declaracion del objeto conexion.
	 * <br>
	 * Realizará la conexion a la base de datos.
	 */
	private Conexion conexion;
	
	/**
	 * Declaración del log de eventos
	 */
	private Log log;
	
	/**
	 * timeStamp de ejecución.
	 */
	private SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy/MM/dd.HH:mm:ss");
	
	/**
	 * Constructor de la Query.
	 * 
	 * <br>Se usará para establecer los datos con los que realizaremos la query. 
	 * 
	 * @param cols - Columna/s sobre las que se realizará la query solicitada.
	 * @param tab - Tabla/s sobre la que se realizará la query solicitada.
	 * @param type - Tipo de query solicitada.
	 * @param mod - Modificadores de la Query.
	 * @param valIns - Valores a insertar (en el caso de un Insert).
	 * @param dat - tipo de dato a extraer (En el caso del Select).
	 * @param conexion - Conexion a la base de datos.
	 * @param cant - Cantidad de querys a realizar (Para los Update y los Insert en varias tablas al mismo tiempo).
	 */
	public Query(String cols, String tab, Statement type, String mod, String valIns, Data dat, Conexion conexion, Integer cant, Log log) {
		this.cols = cols;//Recogemos las columnas solicitadas
		this.tab = tab.toUpperCase();//Recojemos la tabla solicitada
		this.type = type;//Recogemos el tipo de Query Solicitada
		this.mod = mod;//Recogemos los modificadores especificados en la query.
		this.valIns = valIns;//Recogemos los valores a insertar
		this.dat = dat;//Recogemos el tipo de dato que queremos extraer
		this.conexion = conexion;//Recogemos la conexion con la base de datos.
		this.cant = cant;//Recogemos la cantidad de querys a concatenar.
		this.log = log;//Recogemos el log de eventos
	}
	
	/**
	 * Metodo que se encargará de ejecutar la query solicitada y devolver los datos.
	 * 
	 * @return Object[] - Array de objetos que retornará el Resultado de la Query realizada.<br>Solo se usará en el caso de los Select, ya que los Update, Insert y Delete no devuelven nada.
	 */
	public ArrayList<Object> execute() {
		//Declaramos y reinicializamos la query a construir
		String query = null;
		
		try {
			conexion.conect();//Conectamos
		} catch (ClassNotFoundException | SQLException e) {// En el caso de error desconectamos
			log.newReg("\n" + timeStamp.format(new Date()) + " - Error: No hay una conexion establecida a la base de datos");
			return null;
		}
		
		//Evaluamos el tipo de query solicitado
		switch (this.type) {
		case SELECT: //En el caso de haber solicitado un Select
			//Reinicializamos resultado
			if (resul != null) {
				resul.clear();
			}
			
			//Construimos la query tal y como se ha solicitado
			if(mod != null) {//si el modificador no es nulo
				//montamos la query con los modificadores
				query = this.type + " " + this.cols + " FROM " + this.tab + " " + this.mod + ";";
			} else {//sino
				//montamos la query sin los modificadores
				query = this.type + " " + this.cols + " FROM " + this.tab + ";";
			}
			
			try {
				//Preparamos y ejecutamos la query construida
				prep(query);
				
				/*
				 * Evaluamos que la recogida de los datos se produzca correctamente
				 */
				try {
					//Evaluamos la tabla sobre al que se ha hecho la Query
					switch (this.dat) {
					case CLIENTES://Si es la tabla clientes
						/*
						 * Bucle while por el que recorremos los registros obtenidos
						 */
						while(result.next() == true) {//Si hay datos en el registro siguiente 
							//Declaramos un objeto de tipo Cliente.
							Cliente cli = new Cliente();
							
							/*
							 * Recorremos las columnas del registro actual en busca de los datos 
							 * correespondientes para rellenar el objeto
							 */
							for (int p = 1;p <= resultmtdt.getColumnCount();p++) {
								String col = resultmtdt.getColumnName(p);
								//Evaluamos el nombre de la columna actual
								switch (col.toUpperCase()) {
									case "IDCLI"://Si la columna es 'idCli'
										//extraemos el valor en el campo 'idCli' del objeto Cliente
										cli.setIdCli(result.getInt(col));
										break;
									case "DNI"://Si la columna es 'DNI'
										//extraemos el valor en el campo 'Nif' del objeto Cliente
										cli.setNif(result.getString(col));
										break;
									case "NOM"://Si la columna es 'NOM'
										//extraemos el valor en el campo 'Nmbr' del objeto Cliente
										cli.setNmbr(result.getString(col));
										break;
									case "APEL"://Si la columna es 'APEL'
										//extraemos el valor en el campo 'Apllds' del objeto Cliente
										cli.setApllds(result.getString(col));
										break;
									case "TEL"://Si la columna es 'TEL'
										//extraemos el valor en el campo 'Tlfn' del objeto Cliente
										cli.setTlfn(result.getString(col));
										break;
									case "EMAIL"://Si la columna es 'EMAIL'
										//extraemos el valor en el campo 'Eml' del objeto Cliente
										cli.setEml(result.getString(col));
										break;
									case "DOM"://Si la columna es 'DOM'
										//extraemos el valor en el campo 'Dmcl' del objeto Cliente
										cli.setDmcl(result.getString(col));
										break;
									case "ACTIV"://Si la columna es 'DOM'
										//extraemos el valor en el campo 'Dmcl' del objeto Cliente
										cli.setStts(result.getBoolean(col));
										break;
								}
							}
							
							//Insertamos el Objeto con los datos extraidos del registro recibido
							resul.add(cli);
						}
						
						break;
					case CUENTA://si es la tabla cuenta
						/*
						 * Bucle while por el que recorremos los registros obtenidos
						 */
						while(result.next() == true) {//Si hay datos en el registro siguiente 
							//Declaramos un objeto de tipo Cuenta.
							Cuenta cu = new Cuenta();
							
							/*
							 * Recorremos las columnas del registro actual en busca de los datos 
							 * correespondientes para rellenar el objeto
							 */
							for (int p = 1;p <= resultmtdt.getColumnCount();p++) {
								String col = resultmtdt.getColumnName(p);
								//Evaluamos el nombre de la columna actual
								switch (col.toUpperCase()) {
									case "IDCU"://Si la columna es 'idCu'
										//extraemos el valor en el campo 'idCu' del objeto Cuenta
										cu.setIdCu((result.getInt(col)));
										break;
									case "NUMERO_CUENTA"://Si la columna es 'Numero_cuenta'
										//extraemos el valor en el campo 'IBAN' del objeto Cuenta
										cu.setIban(result.getString(col));
										break;
									case "NBANC"://Si la columna es 'NBanc'
										//extraemos el valor en el campo 'Nmbrbnc' del objeto Cuenta
										cu.setNmbrbnc(result.getString(col));
										break;
									case "FINI"://Si la columna es 'FINI'
										//extraemos el valor en el campo 'FINI' del objeto Cuenta
										cu.setFechaApertura(result.getDate(col));
										break;
									case "FFIN"://Si la columna es 'FFIN'
										//extraemos el valor en el campo 'FFIN' del objeto Cuenta
										cu.setFechaCierre(result.getDate(col));
										break;
								}
							}
							
							//Insertamos el Objeto con los datos extraidos del registro recibido
							resul.add(cu);
						}
						
						break;
					case TRANSFERENCIAS://en el caso de las transferencias
						/**
						 * Bucle while por el que recorremos los registros obtenidos
						 */
						while(result.next() == true) {//Si hay datos en el registro siguiente 
							//Declaramos un objeto de tipo Transferencia.
							Transferencia trnsfrnc = new Transferencia();
							
							/*
							 * Recorremos las columnas del registro actual en busca de los datos 
							 * correespondientes para rellenar el objeto
							 */
							for (int p = 1;p <= resultmtdt.getColumnCount();p++) {
								String col = resultmtdt.getColumnName(p);
								//Evaluamos el nombre de la columna actual
								switch (col.toUpperCase()) {
									case "CODTRAN"://Si la columna es 'CODTRAN'
										//extraemos el valor en el campo 'CODTRAN' del objeto Transferencia
										trnsfrnc.setRef(result.getInt(col));
										break;
									case "CORI"://Si la columna es 'CORI'
										//extraemos el valor en el campo 'CORI' del objeto Transferencia
										trnsfrnc.setOrigen(result.getInt(col));
										break;
									case "CDES"://Si la columna es 'CDES'
										//extraemos el valor en el campo 'CDES' del objeto Transferencia
										trnsfrnc.setDestino(result.getInt(col));
										break;
									case "FTRAN"://Si la columna es 'FTRAN'
										//extraemos el valor en el campo 'FTRAN' del objeto Transferencia
										trnsfrnc.setfTransf(result.getDate(col));
										break;
									case "IMP"://Si la columna es 'IMP'
										//extraemos el valor en el campo 'IMP' del objeto Transferencia
										trnsfrnc.setImp(result.getDouble(col));
										break;
									case "CNCPT"://Si la columna es 'CNCPT'
										//extraemos el valor en el campo 'CNCPT' del objeto Transferencia
										trnsfrnc.setCncpt(result.getString(col));
										break;
								}
							}
							
							//Insertamos el Objeto con los datos extraidos del registro recibido
							resul.add(trnsfrnc);
						}
						
						break;
					}
				} catch (SQLException e) {//en el caso de error
					log.newReg("\n" + timeStamp.format(new Date()) + " - Error: " + e);//Mostramos el error en consola
				}
			} catch (NullPointerException i) {
				//Si se sucede un error durante la ejecución
				log.newReg("\n" + timeStamp.format(new Date()) + " - Error: " + i);
			}
			
			//Retornamos el resultado de la Query en formato Object
			return resul;
			
		case INSERT://En el caso de haber solicitado un Insert
			/*
			 * Construimos la query tal y como se ha solicitado
			 */
			/*
			 * si se han solicitado mas de dos inserts, los valores vendran separados por ';'
			 */
			String[] valIns = this.valIns.split(";");//Separamos los grupos de valores a insertar
			String[] tab = this.tab.split(";");//Separamos los grupos de tablas a insertar
			String[] cols = this.cols.split(";");//Separamos los grupos de Columnas a insertar
			
			for (int i = 0;i < this.cant;i++) {//para cada Query solicitada
				if(mod != null) {//si el modificador no es nulo
					//Montamos con el modificador
					query = this.type + " INTO "  + tab[i] + "(" + cols[i] + ")" + " VALUES (" + valIns[i] + ")" + this.mod + ";";
				} else {//sino
					//Montamos sin el modificador
					query = this.type + " INTO "  + tab[i] + "(" + cols[i] + ")" + " VALUES (" + valIns[i] + ");";
				}
			
				try {
					//Preparamos y ejecutamos la query construida
					prep(query);
				} catch (NullPointerException f) {
					//Si se sucede un error durante la ejecución
					log.newReg("\n" + timeStamp.format(new Date()) + " - Error: " + f);
				}
			}
			
			return null;//los Update no recuperan ninguún valor por lo que devolvemos nulo.
		
		case UPDATE://En el caso de haber solicitado un Update
			/*
			 * Construimos la query tal y como se ha solicitado
			 */
			/*
			 * si se han solicitado mas de dos inserts, los valores vendran separados por ';'
			 */
			String[] valUpdt = this.valIns.split(";");//Separamos los grupos de valores a insertar
			String[] tabs = this.tab.split(";");//Separamos los grupos de tablas a insertar
			String[] colms = this.cols.split(";");//Separamos los grupos de Columnas a insertar
			String[] cond = this.mod.split(";");//Separamos los grupos de Columnas a insertar
			
			for (int i = 0;i < this.cant;i++) {//para cada Query solicitada
				if(mod != null) {//si el modificador no es nulo
					//Montamos con el modificador
					query = this.type + " " + tabs[i] + " SET " + colms[i]  + " = '" + valUpdt[i] + "'" + cond[i] + ";";
				} else {//si no
					//Montamos sin el modificador
					query = this.type + " " + tabs[i] + " SET " + colms[i]  + " = '" + valUpdt[i] + "'" + cond[i] + ";";
				}
				
				try {
					//Preparamos y ejecutamos la query construida
					prep(query);
				} catch (NullPointerException f) {
					//Si se sucede un error durante la ejecución
					log.newReg("\n" + timeStamp.format(new Date()) + " - Error: " + f);
				}
			}
			
			return null;//como los Update no devuelven ningún Dato, retornamos null
		
		case DELETE://En el caso de haber solicitado un Delete
			//montamos la query con los datos facilitados
			query = this.type + " FROM " + this.tab + " " + this.mod + ";";//
			
			try {
				//Preparamos y ejecutamos la query construida
				prep(query);
			} catch (NullPointerException f) {
				//Si se sucede un error durante la ejecución
				log.newReg("\n" + timeStamp.format(new Date()) + " - Error: " + f);
			}

			return null;//como los Delete no devuelven ningún Dato, retornamos null
		default://En el caso de que no exista la opcion, retornamos null
			return null;
		}
	}

	/**
	 * Metodo que prepara y ejecuta la Query.
	 * 
	 * @param query - Query a ejecutar.
	 */
	private void prep(String query) {
		try {
			/*
			 * Preparamos la query
			 */
			statment = conexion.getConexion().prepareStatement(query);
			
			/*
			 * Ejecutamos la query
			 */
			try {
				result = statment.executeQuery();//ejecutamos y recogemos los datos.
			} catch (SQLException e) {//si se sucede alguna excepción a la hora de recoger Datos
				statment.execute();//ejecutamos sin rellenar el result (Para Insert, Delete y Update)
			}
			//Obtenemos los metadatos de la query ejecutada
			resultmtdt = result.getMetaData();
		} catch (SQLException e) {//Si sucede error en la base de datos
			log.newReg("\n" + timeStamp.format(new Date()) + " - Error: " + e);//Mostramos el error en la consola
		} finally {
			//Desconectamos de la base de datos
			conexion.desconexion();
		}
	}
}
