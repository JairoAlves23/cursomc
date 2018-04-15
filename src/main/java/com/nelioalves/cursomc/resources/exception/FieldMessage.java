package com.nelioalves.cursomc.resources.exception;

import java.io.Serializable;

public class FieldMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fieldName;
	private String Messege;

	public FieldMessage() {

	}

	public FieldMessage(String fieldName, String messege) {
		super();
		this.fieldName = fieldName;
		Messege = messege;
	}

	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMessege() {
		return Messege;
	}

	public void setMessege(String messege) {
		Messege = messege;
	}

}
