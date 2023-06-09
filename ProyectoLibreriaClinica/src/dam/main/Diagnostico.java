package dam.main;

import java.sql.Types;
import java.time.LocalDate;

public class Diagnostico extends DataClass{
	private static final String TABLA= "Diagnostico";
	private LocalDate fecha;
	private int idDiagFisio; 
	private int idDiagPac;
	private String descripcion; 
	private Types type;
	//TODO pasar al constructor de la clase base el nombre de la tabla correspondiente 
	public Diagnostico() {super(TABLA);}  

	public Diagnostico(LocalDate fecha, int idDiagFisio, int idDiagPac, String descripcion) {
		//TODO pasar al constructor de la clase base el nombre de la tabla correspondiente
		super(TABLA);
		this.fecha = fecha;
		this.idDiagFisio = idDiagFisio;
		this.idDiagPac = idDiagPac;
		this.descripcion = descripcion;
	}

	@Override
	public String getTabla() {return this.TABLA;}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}


	public int getIdDiagFisio() {
		return idDiagFisio;
	}

	public void setIdDiagFisio(int idDiagFisio) {
		this.idDiagFisio = idDiagFisio;
	}

	public int getIdDiagPac() {
		return idDiagPac;
	}

	public void setIdDiagPac(int idDiagPac) {
		this.idDiagPac = idDiagPac;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
