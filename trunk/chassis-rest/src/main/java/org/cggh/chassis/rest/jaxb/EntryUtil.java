package org.cggh.chassis.rest.jaxb;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.transform.Source;

import org.cggh.chassis.rest.bean.UnmarshalledEntry;
import org.cggh.chassis.rest.bean.ValidationError;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.w3._2005.atom.Entry;
import org.xml.sax.SAXException;

public class EntryUtil {

  public static UnmarshalledEntry validate(Jaxb2Marshaller marshaller, Source source)
          throws JAXBException, SAXException {

    final UnmarshalledEntry unmarshalResult = new UnmarshalledEntry();
    final List<ValidationEvent> events = new LinkedList<ValidationEvent>();

    marshaller.setValidationEventHandler(new ValidationEventHandler() {
      public boolean handleEvent(ValidationEvent event) {
        events.add(event);
        unmarshalResult.addError(new ValidationError(event.getMessage()));
        // Keep going
        return true;
      }
    });

    if (!events.isEmpty()) {
      System.err.println(events);
    } 
    unmarshalResult.setEntry((Entry) marshaller.unmarshal(source));
    
    return unmarshalResult;
  }

}
