package org.cggh.chassis.rest.bean;

import org.w3._2005.atom.Entry;

public class UnmarshalledEntry extends UnmarshalledObject {
	
  protected Entry entry;
  
  public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}

  @Override
  public void setIt(Object it) {
    setEntry((Entry)it);
  }

  @Override
  public Object getIt() {
    return getEntry();
  }
}
