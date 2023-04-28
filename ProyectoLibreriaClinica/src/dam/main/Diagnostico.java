package dam.main;

import java.time.LocalDate;

public class Diagnostico implements Queryable{
	private final String TABLA= "Diagnostico";
	private LocalDate fecha;
	private int id_digfisio;
	private int id_digPacient;
	private String descripcion; 
	 
	public Diagnostico() {} 

	public Diagnostico(LocalDate fecha, int id_digfisio, int id_digPacient, String descripcion) {
		super();
		this.fecha = fecha;
		this.id_digfisio = id_digfisio;
		this.id_digPacient = id_digPacient;
		this.descripcion = descripcion;
	}

	@Override
	public String getTabla() {return this.TABLA;}
	
	
}
