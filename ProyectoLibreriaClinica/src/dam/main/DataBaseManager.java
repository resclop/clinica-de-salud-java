package dam.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Gestor de conexión a la bbdd, contiene métodos para realizar el filtrado, la selección de columnas, 
 * la modificación, borrado o añadido de datos... además de la exportación e importación XML
 * @author Raul  
 * @version 1.0
 */
public class DataBaseManager {

	private Connection connection = null;
	private Statement statement;
	private PreparedStatement pStatement;
	private DataBaseConnection dataBaseConnection;
	
	public DataBaseManager(DataBaseConnection connection) {
		this.dataBaseConnection = connection;
	}
	
	public DataBaseManager(Connection connection) {		
		this.connection = connection;
		try {
			this.statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método para obtener los datos de una tabla
	 * @param table de la clase DataClass, se encarga de verificar la tabla de la que se extraen los datos
	 * y al mismo tiempo en ese mismo objeto se irán almacenando los datos 
	 * @return devuelve un arraylist de tipo DataClass con los resultados obtenidos del parámetro table introducido
	 */
	public ArrayList<DataClass> findAll(DataClass table){
		Connection connection = null;
		ArrayList<DataClass> result=new ArrayList<DataClass>();
		try {
			connection=DriverManager.getConnection(this.dataBaseConnection.getConnectionString());
			this.dataBaseConnection.connect(connection);
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + 
					table.getTabla());
			ResultSet rs = ps.executeQuery();					
			while(rs.next()) {
				ArrayList<DataField> fields = table.getDataField();
				int i=1;
				for(DataField field:fields) {
					table.setPropertyValue(field.getPropertyName(), rs.getObject(i));
					i++;
				}
				result.add(table);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		} finally {
			this.dataBaseConnection.disconnect();
		}
		return result;
	}

	/**
	 * Método para obtener un filtrado de las columnas de cualquier tabla por al menos dos campos
	 * @param table indica la tabla de la bbdd de donde se extraen los datos
	 * @param fields es una array que indica el numero de columnas de las que se quieren extraer datos 
	 * @return devuelve una ArrayList con los datos extraidos de la bbdd
	 */
	public ArrayList<Queryable> findAll(Queryable table, String...fields){
		Connection connection = null;
		ArrayList<Queryable> result=null;
		Queryable datos=null;
		String peticion = "SELECT ";
		if (fields.length == 0) {
			peticion += "* ";
		} else {
			for(String field : fields) {
				peticion += field + ",";
			}
		}
		peticion.substring(0, peticion.length()-1);
		try {
			connection=DriverManager.getConnection(this.dataBaseConnection.getConnectionString());
			this.dataBaseConnection.connect(connection);
			PreparedStatement ps = this.connection.prepareStatement(peticion + " FROM " + 
					table.getTabla());
			ResultSet rs = ps.executeQuery();					
			while(rs.next()) {
				if(table.getTabla().equals("Paciente")) {
					datos = new Paciente();
					for(int i=0;i<fields.length;i++) {
						if(fields[i].toLowerCase().equals("idpaciente")) {
							((Paciente)datos).setIdPaciente(rs.getInt(i+1));
						}else if(fields[i].toLowerCase().equals("nombre")) {
							((Paciente)datos).setNombre(rs.getString(i+1));
						}else if(fields[i].toLowerCase().equals("edad")) {
							((Paciente)datos).setEdad(rs.getInt(i+1));
						}							
					}
					//validación de campo clave
					if(((Paciente)datos).getIdPaciente()==0) continue;
				} else if (table.getTabla().equals("Fisioterapeuta")) {
					datos = new Fisioterapeuta();
					for(int i=0;i<fields.length;i++) {
						if(fields[i].toLowerCase().equals("idfisio")) {
							((Fisioterapeuta)datos).setIdFisio(rs.getInt(i+1));
						} else if (fields[i].toLowerCase().equals("nombre")){
							((Fisioterapeuta)datos).setNombre(rs.getString(i+1));
						} else if (fields[i].toLowerCase().equals("especialidad")){
							((Fisioterapeuta)datos).setEspecialidad(rs.getString(i+1));
						}else if (fields[i].toLowerCase().equals("localidad")){
							((Fisioterapeuta)datos).setLocalidad(rs.getString(i+1));
						}
					}
					//validación de campo clave
					if(((Fisioterapeuta)datos).getIdFisio()==0) continue;
				} else if (table.getTabla().equals("Diagnostico")) {
					datos = new Diagnostico();
					for(int i=0;i<fields.length;i++) {
						if(fields[i].toLowerCase().equals("fecha")) {
							((Diagnostico)datos).setFecha(rs.getDate(i+1).toLocalDate());
						} else if (fields[i].toLowerCase().equals("iddiagfisio")) {
							((Diagnostico)datos).setIdDiagFisio(rs.getInt(i+1));
						} else if (fields[i].toLowerCase().equals("iddiagpac")) {
							((Diagnostico)datos).setIdDiagPac(rs.getInt(i+1));
						} else if (fields[i].toLowerCase().equals("descripcion")) {
							((Diagnostico)datos).setDescripcion(rs.getString(i+1));
						}
					}
					//validación de campo clave
					if(((Diagnostico)datos).getFecha()==null) continue;
				}
				result.add(datos);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}finally {
			this.dataBaseConnection.disconnect();
		}
		return result;
	}

	/**
	 * Método para obtener un filtro de las columnas de cualquier tabla
	 * @param table indica la tabla de la bbdd de donde se extraen los datos
	 * @param fields es una array que indica el numero de columnas de las que se quieren extraer datos 
	 * @filters filters indica el valor por el que se va a comparar
	 * @return devuelve una ArrayList con los datos extraidos de la bbdd
	 */
	public ArrayList<Queryable> findAllBy(Queryable table, HashMap<String, Object> filters, String...fields){
		Connection connection = null;
		ArrayList<Queryable> result=null;
		Queryable datos=null;
		int type, j=0;
		String peticion = "SELECT ";
		String whereData="";
		if (fields.length == 0) {
			peticion += "* ";
		} else {
			for(String field : fields) {
				peticion += field + ",";
			}
		}
		peticion.substring(0, peticion.length()-1);
		try {
			for	(String key : filters.keySet()) {
				whereData+= key+"=? AND ";
			}
			whereData = whereData.substring(0, whereData.length()-5);
			connection=DriverManager.getConnection(this.dataBaseConnection.getConnectionString());
			this.dataBaseConnection.connect(connection);
			PreparedStatement ps = this.connection.prepareStatement(peticion + " FROM " + 
					table.getTabla() + " where " + whereData);

			for(Object filter: filters.values()) {
				if (filter instanceof Integer) {
					type = Types.INTEGER;
				} else if(filter instanceof String) {
					type = Types.VARCHAR;
				} else if (filter instanceof Double) {
					type = Types.DOUBLE;
				}
			}	
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				if(table.getTabla().equals("Paciente")) {
					datos = new Paciente();
					for(int i=0;i<fields.length;i++) {
						if(fields[i].toLowerCase().equals("idpaciente")) {
							((Paciente)datos).setIdPaciente(rs.getInt(i+1));
						}else if(fields[i].toLowerCase().equals("nombre")) {
							((Paciente)datos).setNombre(rs.getString(i+1));
						}else if(fields[i].toLowerCase().equals("edad")) {
							((Paciente)datos).setEdad(rs.getInt(i+1));
						}							
					}
					//validación de campo clave
					if(((Paciente)datos).getIdPaciente()==0) continue;
				} else if (table.getTabla().equals("Fisioterapeuta")) {
					datos = new Fisioterapeuta();
					for(int i=0;i<fields.length;i++) {
						if(fields[i].toLowerCase().equals("idfisio")) {
							((Fisioterapeuta)datos).setIdFisio(rs.getInt(i+1));
						} else if (fields[i].toLowerCase().equals("nombre")){
							((Fisioterapeuta)datos).setNombre(rs.getString(i+1));
						} else if (fields[i].toLowerCase().equals("especialidad")){
							((Fisioterapeuta)datos).setEspecialidad(rs.getString(i+1));
						}else if (fields[i].toLowerCase().equals("localidad")){
							((Fisioterapeuta)datos).setLocalidad(rs.getString(i+1));
						}
					}
					//validación de campo clave
					if(((Fisioterapeuta)datos).getIdFisio()==0) continue;
				} else if (table.getTabla().equals("Diagnostico")) {
					datos = new Diagnostico();
					for(int i=0;i<fields.length;i++) {
						if(fields[i].toLowerCase().equals("fecha")) {
							((Diagnostico)datos).setFecha(rs.getDate(i+1).toLocalDate());
						} else if (fields[i].toLowerCase().equals("iddiagfisio")) {
							((Diagnostico)datos).setIdDiagFisio(rs.getInt(i+1));
						} else if (fields[i].toLowerCase().equals("iddiagpac")) {
							((Diagnostico)datos).setIdDiagPac(rs.getInt(i+1));
						} else if (fields[i].toLowerCase().equals("descripcion")) {
							((Diagnostico)datos).setDescripcion(rs.getString(i+1));
						}
					}
					//validación de campo clave
					if(((Diagnostico)datos).getFecha()==null) continue;
				}
				result.add(datos);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}finally {
			this.dataBaseConnection.disconnect();
		}
		return result;
	}

	/**
	 * Metodo para obtener los datos de una consulta de manera ordenada por alguno de los campos seleccionados
	 */
	public ArrayList<Queryable> findOrderBy (DataClass table,ArrayList<String> fields,ColumnOrder... columnOrder){
		Connection connection = null;
		ArrayList<Queryable> result=new ArrayList<Queryable>();
		String peticion = "SELECT ";
		String orderBy = " ORDER BY ";
		if (fields.size() == 0) {
			peticion += "* ";
		} else {
			for(String field : fields) {
				peticion += field + ",";
			}
		}
		peticion.substring(0, peticion.length()-1);

		try {
			for (ColumnOrder column : columnOrder) {
				orderBy+= column.getIndex() +" " +column.getOrder();
			}
			if (orderBy.length()>10) {
				orderBy = orderBy.substring(0, orderBy.length()-1);
			}

			connection=DriverManager.getConnection(this.dataBaseConnection.getConnectionString());
			this.dataBaseConnection.connect(connection);
			PreparedStatement ps = this.connection.prepareStatement(peticion + " FROM " + 
					table.getTabla() + orderBy );
			ResultSet rs = ps.executeQuery();					
			while(rs.next()) {
				for(int i=0;i<fields.size();i++) {
					((DataClass)table).setPropertyValue(fields.get(i), rs.getObject(i));
				}
				result.add(table);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}finally {
			this.dataBaseConnection.disconnect();
		}
		return result;
	}
	/**
	 * Se debe poder modificar cualquiera de los registros de una tabla
	 */
	public boolean updateValue (DataClass table){
		Connection connection = null;
		boolean updated= false;
		String where=" ";
		String set=" SET ";

		//se construye el WHERE
		ArrayList<DataField> fields = (ArrayList<DataField>) table.getDataField().stream().filter(p->p.isPrimaryKey()).toList();
		for(DataField field:fields) {
			String comillas = field.getFieldType().equals(Types.VARCHAR)?"'":"";										
			where += field.getFieldName() + "=" + comillas + field.getPropertyValue() +
					comillas + " AND ";
		}
		where=where.substring(0,where.length()-5);
		// Indicar el SET, se obtienen todos los campos que no son claves
		// y que se van a usar en la cláusula SET
		fields = (ArrayList<DataField>) table.getDataField().stream().filter(p->!p.isPrimaryKey()).toList();
		for(DataField field:fields) {
			String comillas = field.getFieldType().equals(Types.VARCHAR)?"'":"";
			set+=field.getFieldName() + "=" + comillas +
					field.getPropertyValue() + comillas + ",";
		}
		set=set.substring(0,set.length()-1);

		try {
			this.pStatement = this.connection.prepareStatement("UPDATE " +  table.getTabla() +
					set + where);
			updated = this.pStatement.executeUpdate()>0;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.dataBaseConnection.disconnect();
		}
		return updated;
	}

	/**
	 * Método para añadir nuevos registros a una tabla, a través de la clase DataClass se obtiene cual
	 * es la tabla y a través de la clase DataField el nombre de los campos y las propiedades
	 * @param table 
	 * @return added 
	 */
	public boolean add (DataClass table){
		Connection connection = null;
		boolean added = false;
		String query = "";
		ArrayList<DataField> fields= (ArrayList<DataField>) table.getDataField().stream().filter(p->!p.isPrimaryKey()).toList();
		for(DataField field: fields) {
			String comillas = field.getFieldType().equals(Types.VARCHAR)?"'":"";
			query+=field.getFieldName() + " values " + comillas + field.getPropertyValue() + comillas;
		}
		try {
			this.pStatement = this.connection.prepareStatement("INSERT INTO " +  table.getTabla() + " "+query);
			added = this.pStatement.executeUpdate()>0;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.dataBaseConnection.disconnect();
		}
		return added;
	}
	/**
	 * Método para eliminar registros de una tabla, una vez finalizado, se cierra la conexion
	 * @param table
	 * @return
	 */
	public boolean delete (DataClass table){
		Connection connection = null;
		boolean deleted = false;
		String where=" ";
		ArrayList<DataField> fields = (ArrayList<DataField>) table.getDataField().stream().filter(p->p.isPrimaryKey()).toList();
		for(DataField field:fields) {
			String comillas = field.getFieldType().equals(Types.VARCHAR)?"'":"";										
			where += field.getFieldName() + "=" + comillas + field.getPropertyValue() +
					comillas + " AND ";
		}
		where=where.substring(0,where.length()-5);
		try {
			connection=DriverManager.getConnection(this.dataBaseConnection.getConnectionString());
			this.dataBaseConnection.connect(connection);
			this.pStatement = this.connection.prepareStatement("DELETE FROM " +  table.getTabla() +
					 where);
			deleted = this.pStatement.executeUpdate()>0;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.dataBaseConnection.disconnect();
		}
		return deleted;
	}
	
	/**
	 * Lógica del método para exportar el XML, sin terminar
	 * @return
	 */
	public boolean exportXml() {
		try {
			Document document = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder().newDocument();
			// Debe de ir el nombre de la tabla 
			Element raiz = document.createElement("");
			Element children = document.createElement("");
			Element nombre = document.createElement("");
			nombre.appendChild(document.createTextNode("Texto"));
			children.appendChild(nombre);
			raiz.appendChild(children);
			document.appendChild(raiz);
			Transformer transformer = TransformerFactory.newDefaultInstance().newTransformer();
			transformer.transform(new DOMSource(document), new StreamResult(new FileWriter("resultado.xml")));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * lógica del método para importar el XML sin terminar
	 * @return
	 */
	public boolean importXml() {
		try {
			Document document = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder()
					.parse(new FileInputStream(""));
			Element raiz = document.getDocumentElement();
			for (int i = 0; i < raiz.getChildNodes().getLength(); i++) {
				Node node = raiz.getChildNodes().item(i);
				if (node.getNodeType()== Node.ELEMENT_NODE) {
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
