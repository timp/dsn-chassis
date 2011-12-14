package org.cggh.chassis.rest.jaxb;

import javax.xml.bind.JAXBException;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.transform.Source;

import org.cggh.chassis.rest.bean.ValidationError;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.w3._2005.atom.Feed;
import org.xml.sax.SAXException;

/**
 * @author timp
 * @since 2011-12-13
 */
public class UnmarshalledFeed extends UnmarshalledObject {

  protected Feed feed;
  
  private UnmarshalledFeed() {
    super();
  }

  public Feed getFeed() {
    return feed;
  }

  public void setFeed(Feed feed) {
    this.feed = feed;
  }

  public static UnmarshalledFeed from(Jaxb2Marshaller marshaller, Source source)
          throws JAXBException, SAXException {

    final UnmarshalledFeed unmarshalled = new UnmarshalledFeed();

    marshaller.setValidationEventHandler(new ValidationEventHandler() {
      public boolean handleEvent(ValidationEvent event) {
        unmarshalled.addError(new ValidationError(event.getMessage()));
        return true; // Keep going
      }
    });

    unmarshalled.setFeed((Feed) marshaller.unmarshal(source));
    
    return unmarshalled;
  }
}
