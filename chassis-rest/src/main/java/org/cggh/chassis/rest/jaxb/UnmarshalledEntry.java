package org.cggh.chassis.rest.jaxb;

import javax.xml.bind.JAXBException;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.transform.Source;

import org.cggh.chassis.rest.bean.ValidationError;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.w3._2005.atom.Entry;
import org.xml.sax.SAXException;

/**
 * @author timp
 * @since 2011-12-13
 */
public class UnmarshalledEntry extends UnmarshalledObject {
	
  protected Entry entry;
  
  private UnmarshalledEntry() {
    super();
  }

  public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}

  public static UnmarshalledEntry from(Jaxb2Marshaller marshaller, Source source)
          throws JAXBException, SAXException {
    
    final UnmarshalledEntry unmarshalled = new UnmarshalledEntry();
    
    marshaller.setValidationEventHandler(new ValidationEventHandler() {
      public boolean handleEvent(ValidationEvent event) {
        unmarshalled.addError(new ValidationError(event.getMessage()));
        return true; // Keep going
      }
    });
  
    unmarshalled.setEntry((Entry) marshaller.unmarshal(source));
    
    return unmarshalled;
  }
}
