package org.cggh.chassis.rest.bean;

import java.util.List;
import java.util.Vector;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3._2005.atom.Entry;

@XmlRootElement(name="results")
@XmlAccessorType(XmlAccessType.FIELD)

public class UnmarshalResult {
	
	private Entry entry;
	@XmlElementWrapper(name="errorList")
	@XmlElement(name="error")
	private List<ValidationError> errors = new Vector<ValidationError>();
	
	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}
	
	public List<ValidationError> getErrors() {
		return errors;
	}
}
