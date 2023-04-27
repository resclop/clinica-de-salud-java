package dam.main;

public class Paciente implements Queryable {
	private final String TABLA="Paciente";
	private String nombre;
	public Paciente() {}
	public Paciente(String nombre) {
		super();
		this.nombre = nombre;
	}
	public String getTabla() {return this.TABLA;}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
}
