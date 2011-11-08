package org.cggh.chassis.rest.controller;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.httpclient.HttpStatus;
import org.cggh.chassis.rest.bean.UnmarshalResult;
import org.cggh.chassis.rest.bean.ValidationError;
import org.cggh.chassis.rest.dao.NotFoundException;
import org.cggh.chassis.rest.dao.StudyDAO;
import org.cggh.chassis.rest.jaxb.EntryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import org.w3._2005.atom.Entry;
import org.w3._2005.atom.Feed;
import org.xml.sax.SAXException;
@Controller
public class StudyController {

  @Autowired
  Jaxb2Marshaller validatingMarshaller;
  
  @Autowired
  StudyDAO studyDAO;
  
  // if the request accept header is xml, or */* then the xml bean is used
  // if the request accept header is empty or text/html then .jsp is used
  
  private static final String STUDY_OBJECT_VIEW_NAME = "study";
  private static final String STUDY_COLLECTION_VIEW_NAME = "studies";
  private static final String ERROR_LIST_VIEW_NAME = "errors";
    
  @RequestMapping(method = RequestMethod.GET, value = "/study/{id}")
  public ModelAndView getStudy(@PathVariable String id, HttpServletResponse response)  {
    Entry entry = studyDAO.getEntry(id);
    if (entry == null) { 
      return notFound(id, response);          
    } else {
      return new ModelAndView(STUDY_OBJECT_VIEW_NAME, "entry", entry);
    }
  }

  private ModelAndView notFound(String id, HttpServletResponse response) {
    response.setStatus(HttpStatus.SC_NOT_FOUND);
    ModelAndView mav = new ModelAndView(ERROR_LIST_VIEW_NAME, "errors", new ArrayList<ValidationError>());
    mav.addObject("id", id);
    return mav;
  }
  
  @RequestMapping(method=RequestMethod.PUT, value="/study/{id}")
  public ModelAndView updateStudy(@RequestBody String body, @PathVariable String id, HttpServletResponse response) throws JAXBException, SAXException {
    Source source = new StreamSource(new StringReader(body));
      UnmarshalResult unmarshalledResult = EntryUtil.validate(validatingMarshaller, source);
      //s = m_studyDAO.unmarshal(source);
      //s= (Entry) jaxb2Mashaller.unmarshal(source);
      if (unmarshalledResult.getErrors().isEmpty()) {
        try { 
          studyDAO.updateEntry(id, unmarshalledResult.getEntry());
        } catch (Exception e) { 
          response.setStatus(HttpStatus.SC_BAD_REQUEST);
          ModelAndView mav = new ModelAndView(ERROR_LIST_VIEW_NAME, "errors", unmarshalledResult.getErrors());
          mav.addObject("id", id);

          return mav.addObject("exception", e.getMessage());          
        }
        response.setStatus(HttpStatus.SC_CREATED);
        return new ModelAndView(STUDY_OBJECT_VIEW_NAME, "entry", unmarshalledResult.getEntry());
      } else {
        //unmarshalledResult.setEntry(null);
        return new ModelAndView(ERROR_LIST_VIEW_NAME, "errors", unmarshalledResult.getErrors());
      }
  }
  
  @RequestMapping(method=RequestMethod.POST, value="/study")
  public ModelAndView addStudy(@RequestBody String body, HttpServletResponse response) 
        throws JAXBException, SAXException {
    Source source = new StreamSource(new StringReader(body));
    UnmarshalResult unmarshalledResult = EntryUtil.validate(validatingMarshaller, source);
      
    if (unmarshalledResult.getErrors().isEmpty()) {
      try { 
        studyDAO.saveEntry(unmarshalledResult.getEntry());
      } catch (Exception e) { 
          response.setStatus(HttpStatus.SC_BAD_REQUEST);
          Entry entry = unmarshalledResult.getEntry();
          ModelAndView mav = new ModelAndView(ERROR_LIST_VIEW_NAME, "errors", unmarshalledResult.getErrors());
          String studyId = entry == null ? "dummy" : entry.getStudyID();
          mav.addObject("id", studyId);
          return mav.addObject("exception", e.getMessage());          
        }
        response.setStatus(HttpStatus.SC_CREATED);        
        return new ModelAndView(STUDY_OBJECT_VIEW_NAME, "entry", unmarshalledResult.getEntry());
      } else {
        response.setStatus(HttpStatus.SC_BAD_REQUEST);
        Entry entry = unmarshalledResult.getEntry();
        ModelAndView mav = new ModelAndView(ERROR_LIST_VIEW_NAME, "errors", unmarshalledResult.getErrors());
        String studyId = entry == null ? "dummy" : entry.getStudyID();
        mav.addObject("id", studyId);

        return mav;
      }
  }

  
  @RequestMapping(method=RequestMethod.DELETE, value="/study/{id}")
  public ModelAndView removeStudy(@PathVariable String id, HttpServletResponse response) throws NotFoundException {
    if (!studyDAO.remove(id)) {
      return notFound(id, response); 
    }
    return getStudies();
  }
  
  @RequestMapping(method=RequestMethod.GET, value="/studies")
  public ModelAndView getStudies() {
    Feed list = new Feed();
    // Wouldn't it be nice it this was setEntrys or similar
    list.setEntry((List<Entry>) studyDAO.getAll());
    return new ModelAndView(STUDY_COLLECTION_VIEW_NAME, "studies", list);
  }
  
}
