package org.cggh.chassis.rest.jaxb;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.transform.Source;

import org.cggh.chassis.rest.bean.UnmarshalResult;
import org.cggh.chassis.rest.bean.ValidationError;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.w3._2005.atom.Entry;
import org.xml.sax.SAXException;

public class EntryUtil {

	public static UnmarshalResult validate(Jaxb2Marshaller marshaller, Source source) throws JAXBException, SAXException {
		final UnmarshalResult ret = new UnmarshalResult();
		// Now all you need to do to validate on marshal is to provide the
		// created schema to the marshaller:

		// To receive notifications of validation events, register an
		// appropriate event handler:
		/*
		ClassPathResource accountLookUpSchema =
			new ClassPathResource("atom.xsd");
		marshaller.setSchema(accountLookUpSchema);
		*/
		final List<ValidationEvent> events = new LinkedList<ValidationEvent>();
		marshaller.setValidationEventHandler(new ValidationEventHandler() {
			public boolean handleEvent(ValidationEvent event) {
				events.add(event);
				ret.getErrors().add(new ValidationError(event.getMessage()));
				// Keep going
				return true;
			}
		});
		

		Object o = marshaller.unmarshal(source);
		//assertFalse("List of validation events must not be empty.",
		//		events.isEmpty());
		if (!events.isEmpty()) {
			System.out.println(events);
		}
		ret.setEntry((Entry) o);
		return ret;
	}
}
