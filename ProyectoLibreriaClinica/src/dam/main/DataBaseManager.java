package dam.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
/**
 * Gestor de conexión a la bbdd
 * @author Raul  
 * @version 1.0
 */
public class DataBaseManager {

	private Connection connection = null;
	private Statement statement;

	public DataBaseManager(Connection connection) {		
		this.connection = connection;
		try {
			this.statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método para consultar datos de cualquiera de las tablas de la bbdd
	 * @param table se encarga de gestionar que la tabla pasada por parámetro tenga implementada 
	 * la interfaz Queryable
	 * @return Nulo en caso de no encontrar registros o de error
	 */
	public ArrayList<Queryable> findAll(Queryable table){
		ArrayList<Queryable> result=null;
		Queryable datos=null;
		try {
			PreparedStatement ps = this.connection.prepareStatement("SELECT * FROM " + 
					table.getTabla());
			ResultSet rs = ps.executeQuery();					
			while(rs.next()) {
				if(table.getTabla().equals("Paciente")) {
					datos = new Paciente();
					((Paciente)datos).setIdPaciente(rs.getInt(1));
					((Paciente)datos).setNombre(rs.getString(2));
					((Paciente)datos).setEdad(rs.getInt(3));
				} else if (table.getTabla().equals("Fisioterapeuta")) {
					datos = new Fisioterapeuta();
					((Fisioterapeuta)datos).setIdFisio(rs.getInt(1));
					((Fisioterapeuta)datos).setNombre(rs.getString(2));
					((Fisioterapeuta)datos).setEspecialidad(rs.getString(3));
					((Fisioterapeuta)datos).setLocalidad(rs.getString(4));
				} else if (table.getTabla().equals("Diagnostico")) {
					datos = new Diagnostico();
					((Diagnostico)datos).setFecha(rs.getDate(1).toLocalDate());
					((Diagnostico)datos).setIdDiagFisio(rs.getInt(2));
					((Diagnostico)datos).setIdDiagPac(rs.getInt(3));
					((Diagnostico)datos).setDescripcion(rs.getString(4));
				}
				result.add(datos);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
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
		}
		return result;
	}

	/**
	 * Metodo para obtener los datos de una consulta de manera ordenada por alguno de los campos seleccionados
	 */
	public ArrayList<Queryable> findOrderBy (Queryable table){
		ArrayList<Queryable> result=null;
		Queryable datos=null;

		return null;
	}
	/**
	 * Se debe poder modificar cualquiera de los registros de una tabla
	 */
	public ArrayList<Queryable> modify (Queryable table){
		ArrayList<Queryable> result=null;
		Queryable datos=null;
		
		return null;
	}


	/**
	 * Método para añadir nuevos registros a una tabla, de uno en uno o varios
	 * @param table
	 * @return 
	 */
	public ArrayList<Queryable> add (Queryable table){
		ArrayList<Queryable> result=null;
		Queryable datos=null;

		return null;
	}
	/**
	 * método para eliminar registros de una tabla, de uno en uno o varios
	 * @param table
	 * @return
	 */
	public ArrayList<Queryable> delete (Queryable table){
		ArrayList<Queryable> result=null;
		Queryable datos=null;

		return null;
	}
}
