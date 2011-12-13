/**
 * 
 */
package org.cggh.chassis.rest.bean;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.transform.Source;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.w3._2005.atom.Feed;
import org.xml.sax.SAXException;

/**
 * @author timp
 * @since 2011-12-13
 */
public class UnmarshalledFeed extends UnmarshalledObject {

  protected Feed feed;
  
  public Feed getFeed() {
    return feed;
  }

  public void setFeed(Feed feed) {
    this.feed = feed;
  }

  @Override
  public void setIt(Object it) {
    setFeed((Feed)it);
  }

  @Override
  public Object getIt() {
    return getFeed();
  }

  public static UnmarshalledFeed create(Jaxb2Marshaller marshaller, Source source)
          throws JAXBException, SAXException {

    final UnmarshalledFeed unmarshalResult = new UnmarshalledFeed();
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
    unmarshalResult.setFeed((Feed) marshaller.unmarshal(source));
    
    return unmarshalResult;
  }
}
