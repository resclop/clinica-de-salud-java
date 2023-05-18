package dam.main;

import java.util.ArrayList;

public class TestMain {
	private DataBaseManager dbManager;
	public static void main(String[] args) {
		TestMain tm = new TestMain();
		//DataBaseManager databaseManager = new DataBaseManager ();
		DataBaseConnection databaseConnection = new DataBaseConnection("jdbc:mysql://localhost/clinica?user=root&password=root");
		tm.dbManager = new DataBaseManager(databaseConnection.getConnection());				
		tm.obtenerPacientes();
			
		
	}
	

	
	
	

	public void obtenerPacientes() {
		if(this.dbManager!=null) {
			ArrayList<DataClass> data = this.dbManager.findAll(new DataClass("Paciente"));
			for(DataClass paciente: data) {				
				System.out.println(((Paciente)paciente).getNombre());
			}
		}
	}
}
