package org.cggh.chassis.rest.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "errorText"
})
@XmlRootElement(name = "error")
public class ValidationError {
	private String errorText;

	public ValidationError() {
	
	}
	public ValidationError(String message) {
		setError(message);
	}

	public String getError() {
		return errorText;
	}

	public void setError(String error) {
		this.errorText = error;
	}
}
