package dam.main;

import java.sql.Types;

public class Paciente extends DataClass {
	private static final String TABLA = "Paciente";	
	private int idPaciente;
	private String nombre;
	private int edad;
	private Types type;
	
	public Paciente() {super(TABLA);}
	
	public Paciente(int idPaciente, String nombre, int edad) {	
		super(TABLA);
		this.idPaciente = idPaciente;
		this.nombre = nombre; 
		this.edad = edad;
	} 
	
	@Override
	public String getTabla() {return this.TABLA;}

	
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
