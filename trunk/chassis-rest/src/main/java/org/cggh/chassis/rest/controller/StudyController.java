package org.cggh.chassis.rest.controller;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.httpclient.HttpStatus;
import org.cggh.chassis.rest.dao.ChassisDAO;
import org.cggh.chassis.rest.dao.NotFoundException;
import org.cggh.chassis.rest.dao.StudyDAO;
import org.cggh.chassis.rest.jaxb.UnmarshalledObject;
import org.cggh.chassis.rest.jaxb.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import org.w3._2005.atom.Entry;
import org.w3._2005.atom.Feed;
import org.xml.sax.SAXException;

@Controller
public class StudyController {

  @Autowired
  @Resource(name = "validatingMarshaller")
  Jaxb2Marshaller validatingMarshaller;

  @Autowired
  @Resource(name = "validatingPrunedMarshaller")
  Jaxb2Marshaller validatingPrunedMarshaller;

  @Autowired
  @Resource(name = "nonValidatingMarshaller")
  Jaxb2Marshaller marshaller;

  @Autowired
  StudyDAO studyDAO;

  @Autowired
  ChassisDAO chassisDAO;

  private Transformer transformer;

  // if the request accept header is xml, or */* then the xml bean is used
  // if the request accept header is empty or text/html then .jsp is used

  private static final String STUDY_OBJECT_VIEW_NAME = "study";
  private static final String STUDY_COLLECTION_VIEW_NAME = "studies";
  private static final String ERROR_LIST_VIEW_NAME = "errors";

  public StudyController(InputStream pruner) throws TransformerConfigurationException {
    super();
    if (pruner != null) {

      StreamSource xslt = new StreamSource(pruner);
      TransformerFactory tFactory = TransformerFactory.newInstance();

      transformer = tFactory.newTransformer(xslt);
    }
  }

    
  @RequestMapping(method = RequestMethod.GET, value = "/study/{id}")
  public ModelAndView getStudy(@PathVariable String id, HttpServletResponse response) {
    Entry entry = studyDAO.getEntry(id);
    if (entry == null) {
      return notFound(id, response);
    } else {
      return new ModelAndView(STUDY_OBJECT_VIEW_NAME, "entry", entry);
    }
  }

  private ModelAndView notFound(String id, HttpServletResponse response) {
    response.setStatus(HttpStatus.SC_NOT_FOUND);
    UnmarshalledObject<Entry> unmo = new UnmarshalledObject<Entry>(validatingMarshaller);
    unmo.addError(new ValidationError("No study found with id " + id));

    ModelAndView mav = new ModelAndView(ERROR_LIST_VIEW_NAME, "empty", unmo);
    mav.addObject("id", id);
    mav.addObject("errors", unmo.getErrors());
    return mav;
  }

  /**
   * There is no point in including the study id as this is held in the data, in fact it is confusing and potentially dangerous to do so.
   * 
   * @param body
   * @param validate
   * @param prune
   * @param response
   * @return
   * @throws JAXBException
   * @throws SAXException
   */
  @RequestMapping(method = RequestMethod.PUT, value = "/study")
  public ModelAndView updateStudy(@RequestBody String body, @RequestParam(value = "validate", defaultValue = "true", required = false) boolean validate, @RequestParam(value = "prune", defaultValue = "true", required = false) boolean prune, HttpServletResponse response)
          throws JAXBException,
          SAXException {
    Source source = new StreamSource(new StringReader(body));
    UnmarshalledObject<Entry> unmarshalledResult = new UnmarshalledObject<Entry>(getStudyMarshaller(validate, prune));
    Entry entry = unmarshalledResult.unmarshall(source, prune, transformer, validate);
    if (entry != null)
      unmarshalledResult.setId(entry.getId());
    if (unmarshalledResult.getErrors().isEmpty()) {
      try {
        entry = studyDAO.updateEntry(entry);
      } catch (Exception e) {
        ModelAndView mav = marshallingError(response, unmarshalledResult);
        mav.addObject("exception", e.getMessage());
        return mav;
      }
      response.setStatus(HttpStatus.SC_CREATED);
      return new ModelAndView(STUDY_OBJECT_VIEW_NAME, "entry", entry);
    } else {
      return new ModelAndView(ERROR_LIST_VIEW_NAME, "errors", unmarshalledResult);
    }
  }

  private Jaxb2Marshaller getStudyMarshaller(boolean validate, boolean prune) {
    Jaxb2Marshaller marsh;
    if (validate) {
      if (prune) {
        marsh = validatingPrunedMarshaller;
      } else {
        marsh = validatingMarshaller;
      }
    } else {
      marsh = marshaller;
    }
    return marsh;
  }

  @RequestMapping(method = RequestMethod.POST, value = "/study")
  public ModelAndView addStudy(@RequestBody String body, @RequestParam(value = "validate", defaultValue = "true", required = false) boolean validate, @RequestParam(value = "prune", defaultValue = "true", required = false) boolean prune, HttpServletResponse response)
          throws JAXBException, SAXException {
    Source source = new StreamSource(new StringReader(body));
    UnmarshalledObject<Entry> unmarshalledResult = new UnmarshalledObject<Entry>(getStudyMarshaller(validate, prune));

    Entry entry = unmarshalledResult.unmarshall(source, prune, transformer, validate);
    if (entry != null)
      unmarshalledResult.setId(entry.getId());
    if (unmarshalledResult.getErrors().isEmpty()) {
      try {
        studyDAO.saveEntry(entry);
      } catch (Exception e) {
        ModelAndView mav = marshallingError(response, unmarshalledResult);
        mav.addObject("exception", e.getMessage());
        return mav;
      }
      response.setStatus(HttpStatus.SC_CREATED);
      ModelAndView mav = new ModelAndView(STUDY_OBJECT_VIEW_NAME, "entry", entry);
      return mav.addObject("id", entry.getId());
    } else {
      return marshallingError(response, unmarshalledResult);
    }
  }

  @RequestMapping(method = RequestMethod.POST, value = "/validateStudy")
  public ModelAndView validateStudy(@RequestBody String body, @RequestParam(value = "validate", defaultValue = "true", required = false) boolean validate, @RequestParam(value = "prune", defaultValue = "false", required = false) boolean prune, HttpServletResponse response)
          throws JAXBException, SAXException {
    Source source = new StreamSource(new StringReader(body));
    UnmarshalledObject<Entry> unmarshalledResult = new UnmarshalledObject<Entry>(getStudyMarshaller(validate, prune));

    Entry entry = unmarshalledResult.unmarshall(source, prune, transformer, validate);
    if (entry != null)
      unmarshalledResult.setId(entry.getId());
    if (unmarshalledResult.getErrors().isEmpty()) {
      response.setStatus(HttpStatus.SC_OK);
      ModelAndView mav = new ModelAndView(STUDY_OBJECT_VIEW_NAME, "entry", entry);
      return mav.addObject("id", entry.getId());
    } else {
      response.setStatus(HttpStatus.SC_NOT_ACCEPTABLE);
      return marshallingError(response, unmarshalledResult);
    }
  }

  private ModelAndView marshallingError(HttpServletResponse response, UnmarshalledObject<?> unmarshalledResult) {
    response.setStatus(HttpStatus.SC_BAD_REQUEST);
    ModelAndView mav = new ModelAndView(ERROR_LIST_VIEW_NAME, "errors", unmarshalledResult);
    String id;
    if (unmarshalledResult != null && unmarshalledResult.previousResult() != null && unmarshalledResult.getId() != null)
      id = unmarshalledResult.getId();
    else
      id = "error";
    mav.addObject("id", id);

    return mav;
  }

  @RequestMapping(method = RequestMethod.POST, value = "/links")
  public ModelAndView addLinks(@RequestBody String body, HttpServletResponse response) throws JAXBException, SAXException {
    Source source = new StreamSource(new StringReader(body));
    UnmarshalledObject<Feed> unmarshalledResult = new UnmarshalledObject<Feed>(marshaller);
    boolean prune = false;
    boolean validate = false;
    Feed feed = unmarshalledResult.unmarshall(source, prune, transformer, validate);
    if (feed != null)
      unmarshalledResult.setId(feed.getId());
    if (unmarshalledResult.getErrors().isEmpty()) {
      for (Entry entry : feed.getEntry()) {
        try {
          studyDAO.saveEntry(entry);
        } catch (Exception e) {
          ModelAndView mav = marshallingError(response, unmarshalledResult);
          mav.addObject("exception", e.getMessage());
          return mav;
        }
      }
      response.setStatus(HttpStatus.SC_CREATED);
      Feed list = new Feed();
      list.setEntry((List<Entry>) studyDAO.getEntries());
      return new ModelAndView(STUDY_COLLECTION_VIEW_NAME, "studies", list);
    } else {
      return marshallingError(response, unmarshalledResult);
    }
  }

  @RequestMapping(method = RequestMethod.POST, value = "/studies")
  public ModelAndView addStudies(@RequestBody String body, @RequestParam(value = "validate", defaultValue = "true", required = false) boolean validate, @RequestParam(value = "prune", defaultValue = "true", required = false) boolean prune, HttpServletResponse response)
          throws JAXBException, SAXException {
    Source source = new StreamSource(new StringReader(body));
    UnmarshalledObject<Feed> unmarshalledResult = new UnmarshalledObject<Feed>(getStudyMarshaller(validate, prune));
    Feed feed = unmarshalledResult.unmarshall(source, prune, transformer, validate);

    if (feed != null)
      unmarshalledResult.setId(feed.getId());
    if (unmarshalledResult.getErrors().isEmpty()) {
      for (Entry entry : feed.getEntry()) {
        try {
          studyDAO.saveEntry(entry);
        } catch (Exception e) {
          ModelAndView mav = marshallingError(response, unmarshalledResult);
          mav.addObject("exception", e.getMessage());
          return mav;
        }
      }
      response.setStatus(HttpStatus.SC_CREATED);
      Feed list = new Feed();
      list.setEntry((List<Entry>) studyDAO.getEntries());
      return new ModelAndView(STUDY_COLLECTION_VIEW_NAME, "studies", list);
    } else {
      return marshallingError(response, unmarshalledResult);
    }
  }

  /**
   * Imports studies
   * 
   * @param body
   * @param validate
   * @param prune
   * @param response
   * @return
   * @throws JAXBException
   * @throws SAXException
   */
  @RequestMapping(method = RequestMethod.GET, value = "/import")
  public ModelAndView importStudies(@RequestParam(value = "validate", defaultValue = "true", required = false) boolean validate, @RequestParam(value = "prune", defaultValue = "true", required = false) boolean prune, HttpServletResponse response)
          throws JAXBException, SAXException {
    File studies = null;
    File links = null;
    Feed feed = null;
    UnmarshalledObject<Feed> unmarshalledResult = null;
    try {
      studies = chassisDAO.getStudies();
      links = chassisDAO.getLinks();
      
      Source source = new StreamSource(studies);
      unmarshalledResult = new UnmarshalledObject<Feed>(getStudyMarshaller(validate, prune));
      feed = unmarshalledResult.unmarshall(source, prune, transformer, validate);
      if (feed != null) {
        unmarshalledResult.setId(feed.getId());
      }
      if (unmarshalledResult.getErrors().isEmpty()) {
        //Do as individual entries because run into problems with ids if doing as a feed
        for (Entry entry : feed.getEntry()) {
          try {
            studyDAO.updateEntry(entry);
          } catch (Exception e) {
            ModelAndView mav = marshallingError(response, unmarshalledResult);
            mav.addObject("exception", e.getMessage());
            return mav;
          }
        }

        //Now do the links

        source = new StreamSource(links);
        UnmarshalledObject<Feed> linksResult = new UnmarshalledObject<Feed>(getStudyMarshaller(validate, prune));
        //Never prune links feed or it becomes invalid - not necessary anyway
        feed = linksResult.unmarshall(source, false, transformer, validate);
        if (feed != null) {
          linksResult.setId(feed.getId());
        }
        if (linksResult.getErrors().isEmpty()) {
          //Do as individual entries because run into problems with ids if doing as a feed
          for (Entry entry : feed.getEntry()) {
            try {
              String linkId = entry.getId();
              String studyId = "l-" + linkId.substring(linkId.length()-5);
              entry.setStudyID(studyId);
              studyDAO.updateEntry(entry);
            } catch (Exception e) {
              ModelAndView mav = marshallingError(response, linksResult);
              mav.addObject("exception", e.getMessage());
              return mav;
            }
          }
        } else {
          return marshallingError(response, linksResult);
        }   
        
        response.setStatus(HttpStatus.SC_CREATED);
        Feed list = new Feed();
        list.setEntry((List<Entry>) studyDAO.getEntries());
        return new ModelAndView(STUDY_COLLECTION_VIEW_NAME, "studies", list);
      } else {
        return marshallingError(response, unmarshalledResult);
      }
    } catch (Exception e) {
      ModelAndView mav = marshallingError(response, unmarshalledResult);
      mav.addObject("exception", e.getMessage());
      return mav;
    } finally {
      if (studies != null) {
        studies.delete();
      }
      if (links != null) {
        links.delete();
      }
    }

  }

  @RequestMapping(method = RequestMethod.POST, value = "/validateStudies")
  public ModelAndView validateStudies(@RequestBody String body, @RequestParam(value = "validate", defaultValue = "true", required = false) boolean validate, @RequestParam(value = "prune", defaultValue = "false", required = false) boolean prune, HttpServletResponse response)
          throws JAXBException, SAXException {
    Source source = new StreamSource(new StringReader(body));
    UnmarshalledObject<Feed> unmarshalledResult = new UnmarshalledObject<Feed>(getStudyMarshaller(validate, prune));
    Feed feed = unmarshalledResult.unmarshall(source, prune, transformer, validate);

    if (feed != null)
      unmarshalledResult.setId(feed.getId());
    if (unmarshalledResult.getErrors().isEmpty()) {
      response.setStatus(HttpStatus.SC_OK);
      return new ModelAndView(STUDY_COLLECTION_VIEW_NAME, "studies", feed);
    } else {
      response.setStatus(HttpStatus.SC_NOT_ACCEPTABLE);
      return marshallingError(response, unmarshalledResult);
    }
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/study/{id}")
  public ModelAndView removeStudy(@PathVariable String id, HttpServletResponse response) throws NotFoundException {
    if (!studyDAO.removeEntry(id)) {
      return notFound(id, response);
    }
    return getStudiesModelAndView();
  }

  @RequestMapping(method = RequestMethod.GET, value = "/studies")
  public ModelAndView getStudies() {
    return getStudiesModelAndView();
  }

  @RequestMapping(method = RequestMethod.GET, value = "/studyCount")
  public ModelAndView getCount() {
    Long count = studyDAO.entryCount();
    return new ModelAndView("message", "message", "Count:" + count).
            addObject("body", count + " study entries found.");
  }

  private ModelAndView getStudiesModelAndView() {
    Feed list = new Feed();
    // Wouldn't it be nice it this was setEntrys or similar
    list.setEntry((List<Entry>) studyDAO.getEntries());
    return new ModelAndView(STUDY_COLLECTION_VIEW_NAME, "studies", list);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/uncache")
  public ModelAndView uncache() {
    studyDAO.evict();
    return new ModelAndView("message", "message", "Done").
            addObject("body", "No, really. <b>I think</b> the cache has been cleared; "
                    + "<em>pace</em> this is one of the three hard problems in computer science.");
  }
}
