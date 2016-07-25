package com.syml.generator.domain.annotation;


public class FieldInfo {
	
	public String fieldName;
	public String fieldTypeInERP;
	public String getFieldMethod;
	public String setFieldMethod;
	public Object fieldValue;
	public Class<?> classType;
	
	public FieldInfo(){}

	public FieldInfo(String fieldName, String fieldType,
			String getFieldMethod, String setFieldMethod, Object fieldValue) {
		super();
		this.fieldName = fieldName;
		this.fieldTypeInERP = fieldType;
		this.getFieldMethod = getFieldMethod;
		this.setFieldMethod = setFieldMethod;
		this.fieldValue = fieldValue;
	}

	@Override
	public String toString() {
		return "FieldInfo [fieldName=" + fieldName + ", fieldType=" + fieldTypeInERP
				+ ", getFieldMethod=" + getFieldMethod + ", setFieldMethod="
				+ setFieldMethod + ", fieldValue=" + fieldValue
				+ ", classType=" + classType + "]";
	}

}
