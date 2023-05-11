package dam.main;

import java.sql.Types;
import java.util.ArrayList;

public class Paciente implements Queryable {
	private final String TABLA="Paciente"; 
	private int idPaciente;
	private String nombre;
	private int edad;
	private ArrayList<DataField> dataFields;
	private Types type;
	
	public Paciente() {}
	public Paciente(int idPaciente, String nombre, int edad) {		
		this.idPaciente = idPaciente;
		this.nombre = nombre; 
		this.edad = edad;
		this.dataFields = new ArrayList<DataField>();
		// TO DO: Añadir el tipo al constructor de dataFields
		this.dataFields.add(new DataField("id_paciente","idPaciente", this.idPaciente, null, true));
		this.dataFields.add(new DataField("nombre","nombre", this.nombre, null));
		this.dataFields.add(new DataField("edad","edad", this.edad, null));
	} 
	
	@Override
	public String getTabla() {return this.TABLA;}
	

	public ArrayList<DataField> getDataField() {
		return this.dataFields;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getIdPaciente() {
		return idPaciente;
	}
	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
}
