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
	 * 
	 * @param table
	 * @return Nulo en caso de no encontrar registros o de error
	 */
	public ArrayList<Object> findAll(Queryable table){
		ArrayList<Object> result=null;
		Queryable datos;
		try {
			PreparedStatement ps = this.connection.prepareStatement("SELECT * FROM " + 
					table.getTabla());
			
			ResultSet rs = ps.executeQuery();					
			
			while(rs.next()) {
				if(table.getTabla().equals("Paciente")) {
					datos = new Paciente();
					((Paciente)datos).setNombre(rs.getString(1));
				}
			}			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<Object> findAll(Queryable table, String...fields){
		ArrayList<Object> result=null;
		Queryable datos;
		try {
			PreparedStatement ps = this.connection.prepareStatement("SELECT * FROM " + 
					table.getTabla());
			
			ResultSet rs = ps.executeQuery();					
			
			while(rs.next()) {
				if(table.getTabla().equals("Paciente")) {
					datos = new Paciente();
					((Paciente)datos).setNombre(rs.getString(1));
				}
			}			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;
	}
	
}
