package dam.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

	private Connection connection;
	
	public boolean connect(String connectionString, String user, String password) {
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			this.connection = DriverManager.getConnection(connectionString, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return connection == null?false:true;
	}
	
	public boolean disconnect() {
		try {
			this.connection.close();
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
	
	
}
