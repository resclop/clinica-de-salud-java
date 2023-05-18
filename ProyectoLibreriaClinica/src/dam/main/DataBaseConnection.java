package dam.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

	// Para poder gestionar la conexion
	private Connection connection;
	// Para guardar la cadena de conexion que va a usar la conexion
	private String connectionString;
	
	public DataBaseConnection(String connectionString) {
		this.connectionString = connectionString;
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public boolean connect(Connection connection) {
		this.connection=connection;
		return connection == null?false:true;
	}
	
	public boolean disconnect() {
		try {
			this.connection.close();
			this.connectionString="";
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public Connection getConnection () {
		return this.connection; 
	}
	
	public boolean isConnected() {
		try { 
			return !this.connection.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
			return false; 
		}
	}
	
	public String getConnectionString() {
		return this.connectionString;
	}
	
}
