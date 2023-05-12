package dam.main;

import java.sql.Types;

public class Fisioterapeuta extends DataClass{
	private static final String TABLA="Fisioterapeuta";
	private int idFisio;
	private String nombre;
	private String especialidad;
	private String localidad;
	private Types type;
	
	public Fisioterapeuta() {super(TABLA);} 

	public Fisioterapeuta(int idFisio, String nombre, String especialidad, String localidad) {
		super(TABLA);
		this.idFisio = idFisio; 
		this.nombre = nombre;
		this.especialidad = especialidad;
		this.localidad = localidad;
	}

	public int getIdFisio() {
		return idFisio;
	}

	public void setIdFisio(int idFisio) {
		this.idFisio = idFisio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	@Override
	public String getTabla() {return this.TABLA;}
	
}
