package org.cggh.chassis.rest.jaxb;

import java.util.List;
import java.util.Vector;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.transform.Source;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;


/**
 * A Java object is UNMARSHALLED _from_ XML eg an atom entry or feed.
 * This process may not be error free.
 *  
 *  It can be also, confusingly, be rendered as XML. 
 *  
 * @author timp
 * @since 13 Dec 2011
 *
 */

@XmlRootElement(name="results")
@XmlAccessorType(XmlAccessType.FIELD)
public class UnmarshalledObject<T> {
  // we do not want to serialise these
  @XmlTransient
  Jaxb2Marshaller marshaller;
  @XmlTransient
  Source source;
  
  @XmlTransient
  protected T it;
  
  // FIXME remove this it is only referred to in the errorMarshaller
  public UnmarshalledObject() {
    super();
  }
  public UnmarshalledObject(Jaxb2Marshaller marshaller, Source source) {
    super();
    this.marshaller = marshaller;
    this.source = source;
  }

  public T getIt() {
    if (it == null)
      it = unmarshall();
    return it;
  }

  public void setIt(T it) {
    this.it = it;
  }
  @SuppressWarnings("unchecked")
  private T unmarshall() {
    
    marshaller.setValidationEventHandler(new ValidationEventHandler() {
      public boolean handleEvent(ValidationEvent event) {
        // Hmm, this pretty much ignores most errors
        //if (event.getMessage().indexOf("cvc-complex-type.2.4.") == -1)
          addError(new ValidationError(event.getMessage()));
        return true; // Keep going
      }
    });
  
    return (T) marshaller.unmarshal(source);
  }

  private String id;
  private String exceptionMessage;
  
  @XmlElementWrapper(name = "errors")
  @XmlElement(name = "error")
  private List<ValidationError> errors = new Vector<ValidationError>();

  public List<ValidationError> getErrors() {
  	return errors;
  }

  public void addError(ValidationError e) { 
    errors.add(e);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getExceptionMessage() {
    return exceptionMessage;
  }

  public void setException(String exceptionMessage) {
    this.exceptionMessage = exceptionMessage;
  }
}