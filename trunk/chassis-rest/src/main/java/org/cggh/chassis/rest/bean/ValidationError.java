package org.cggh.chassis.rest.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="error")
public class ValidationError {
	private String error;

	public ValidationError() {
	
	}
	public ValidationError(String message) {
		setError(message);
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
