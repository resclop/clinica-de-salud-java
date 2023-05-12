package dam.main;

import java.util.ArrayList;

public class DataClass implements Queryable{
	private ArrayList<DataField> dataFields;
	private DataClass dataClass;
	private String tableName;

	public DataClass(String tableName) {
		this.tableName = tableName;
		this.setDataFields(this);
	}

	/**
	 * Este método se encarga de establecer la relacion a través de la clase DataField el nombre de las columnas 
	 * de cada clase con el nombre de las propiedades
	 * @param dataClass
	 */
	// TODO: falta solventar el problema con el tipo de dato
	private void setDataFields(DataClass dataClass) {
		this.dataFields = new ArrayList<DataField>();
		if(dataClass instanceof Paciente) {		
			Paciente paciente = (Paciente) dataClass;
			this.dataFields.add(new DataField("id_paciente","idPaciente", paciente.getIdPaciente(), null, true));
			this.dataFields.add(new DataField("nombre","nombre", paciente.getNombre(), null));
			this.dataFields.add(new DataField("edad","edad", paciente.getEdad(), null));
			this.dataClass = paciente;
		}else if(dataClass instanceof Fisioterapeuta) {	
			Fisioterapeuta fisioterapeuta = (Fisioterapeuta) dataClass;
			this.dataFields.add(new DataField("id_fisio","idFisio", fisioterapeuta.getIdFisio(),null,true));
			this.dataFields.add(new DataField("nombre","nombre", fisioterapeuta.getNombre(),null,false));
			this.dataFields.add(new DataField("especialidad","especialidad",fisioterapeuta.getEspecialidad(),null,false));
			this.dataFields.add(new DataField("localidad","localidad", fisioterapeuta.getLocalidad(),null,false));
			this.dataClass = fisioterapeuta;
		}else if(dataClass instanceof Diagnostico) {
			Diagnostico diagnostico = (Diagnostico) dataClass;
			this.dataFields.add(new DataField("fecha", "fecha", diagnostico.getFecha(),null, true));
			this.dataFields.add(new DataField("id_Digisio", "idDiagFisio", diagnostico.getIdDiagFisio(),null, false));
			this.dataFields.add(new DataField("id_digPacient", "idDiagPac", diagnostico.getIdDiagPac(),null, false));
			this.dataFields.add(new DataField("descripcion", "descripcion", diagnostico.getDescripcion(),null, false));
			this.dataClass = diagnostico;
		}

	}
	@Override
	public String getTabla() {	
		return this.tableName;
	}

	public ArrayList<DataField> getDataField() {
		return this.dataFields;
	}
	public DataClass getDataClass() {
		return this.dataClass;
	}

	/**
	 * Método encargado de establecer el valor a la propiedad introducida por parámetro.
	 * @param property es la referencia "llave" a la que se quiere modificar
	 * @param value es el valor que se quiere establecer
	 */
	public void setPropertyValue(String property, Object value) {
		if(this.dataClass instanceof Paciente) {		
			Paciente paciente = (Paciente)this.dataClass;
			switch(property) {
			case "nombre":
				paciente.setNombre(value.toString());
				break;
			case "edad":
				paciente.setEdad((int) value);
				break;
			}
			this.dataClass = paciente;
		} else if(this.dataClass instanceof Fisioterapeuta) {
			Fisioterapeuta fisioterapeuta = (Fisioterapeuta)this.dataClass;
			switch(property) {
			case "nombre":
				fisioterapeuta.setNombre(value.toString());
				break;
			case "especialidad":
				fisioterapeuta.setEspecialidad(value.toString());
				break;
			case "localidad":
				fisioterapeuta.setLocalidad(value.toString());
				break;
			}
			this.dataClass = fisioterapeuta;
		} else if(this.dataClass instanceof Diagnostico) {
			Diagnostico diagnostico = (Diagnostico)this.dataClass;
			switch(property) {
			case "descripcion":
				diagnostico.setDescripcion(value.toString());
				break;
			}
		}
	}
}