package dam.main;

import java.sql.Types;

public class DataField {
	private String fieldName;
	private String propertyName;
	private Object propertyValue;
	private boolean primaryKey;
	private Types fieldType;
	public DataField(String fieldName, String propertyName, Object propertyValue, Types fieldType) {	
		this.fieldName = fieldName;
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		this.primaryKey = false;
		this.fieldType = fieldType;
	}
	public DataField(String fieldName, String propertyName, Object propertyValue, Types fieldType, boolean primaryKey) {	
		this(fieldName,propertyName,propertyValue,fieldType);
		this.primaryKey = primaryKey;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Object getPropertyValue() {
		if(this.fieldType.equals(Types.INTEGER))
			return Integer.valueOf(this.propertyValue.toString());
		if(this.fieldType.equals(Types.VARCHAR))
			return this.propertyValue.toString();
		return null;
	}
	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}
	public boolean isPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}
	public Types getFieldType() {
		return fieldType;
	}
	public void setFieldType(Types fieldType) {
		this.fieldType = fieldType;
	}	
	
}
