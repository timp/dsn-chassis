package org.cggh.chassis.rest.jaxb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * A Java object is UNMARSHALLED _from_ XML eg an atom entry or feed. This process may not be error free.
 * 
 * It can be also, confusingly, be rendered as XML.
 * 
 * @author timp
 * @since 13 Dec 2011
 * 
 */

@XmlRootElement(name = "results")
@XmlAccessorType(XmlAccessType.FIELD)
public class UnmarshalledObject<T> {
  // we do not want to serialise these
  @XmlTransient
  Jaxb2Marshaller marshaller;

  @XmlTransient
  protected T it;

  private String id;
  private String exceptionMessage;
  
  // FIXME - this is required in the autowiring of the validating marshaller
  public UnmarshalledObject() {
    super();
  }

  public UnmarshalledObject(Jaxb2Marshaller marshaller) {
    super();
    this.marshaller = marshaller;
  }

  public T previousResult() {

    return it;
  }

  @SuppressWarnings("unchecked")
  public T unmarshall(Source source, boolean transform, Transformer transformer, boolean validate) {

    if (transform) {
      if (transformer == null) {
        throw new NullPointerException("Transformer is null");
      }
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      StreamResult output = new StreamResult (os);
      
      try {
        transformer.transform(source, output);
      } catch (TransformerException e) {
        throw new RuntimeException(e);
      }
      source = new StreamSource(new ByteArrayInputStream(os.toByteArray()));
    }
    if (validate) {
    marshaller.setValidationEventHandler(new ValidationEventHandler() {
      public boolean handleEvent(ValidationEvent event) {
        // Hmm, this pretty much ignores most errors
        // if (event.getMessage().indexOf("cvc-complex-type.2.4.") == -1)
        addError(new ValidationError(event.getMessage()));
        return true; // Keep going
      }
    });
    } 

    it = (T) marshaller.unmarshal(source);

    return it;
  }

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