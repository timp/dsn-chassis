package org.cggh.chassis.rest.bean;

import java.util.List;
import java.util.Vector;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3._2005.atom.Entry;

@XmlRootElement(name="results")
public class UnmarshalResult {
	
	private Entry entry;
	private List<ValidationError> errors = new Vector<ValidationError>();
	
	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}

	@XmlElement(name="error")
	public List<ValidationError> getErrors() {
		return errors;
	}
}
