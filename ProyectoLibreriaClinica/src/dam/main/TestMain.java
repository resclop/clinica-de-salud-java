package dam.main;

import java.util.ArrayList;

public class TestMain {
	private DataBaseManager dbManager;
	public static void main(String[] args) {
		TestMain tm = new TestMain();
		//DataBaseManager databaseManager = new DataBaseManager ();
		DataBaseConnection databaseConnection = 
				new DataBaseConnection("jdbc:mysql://localhost/Clinica?user=root&password=root");
		
		tm.dbManager = new DataBaseManager(databaseConnection);				
		//tm.obtenerPacientes();
		//System.out.println(tm.dbManager.findAll(new DataClass("Paciente")));

		DataBaseManager dbm = new DataBaseManager(databaseConnection);
		
		dbm.findAll(new DataClass("Paciente")).forEach(System.out::println);
		
		dbm.exportXml();
		
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
