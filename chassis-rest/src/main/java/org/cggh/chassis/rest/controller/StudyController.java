package org.cggh.chassis.rest.controller;

import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.cggh.chassis.rest.bean.UnmarshalResult;
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
	StudyDAO studyDS;
	
	private static final String XML_VIEW_NAME = "studies";
	
	@RequestMapping(method=RequestMethod.GET, value="/study/{id}")
	public ModelAndView getStudy(@PathVariable String id) {
		Entry e = null;
		try {
			e = studyDS.getEntry(id);
		} catch (NumberFormatException e1) {
      throw new RuntimeException(e1);
		} catch (JAXBException e1) {
      throw new RuntimeException(e1);
		}
		return new ModelAndView(XML_VIEW_NAME, "object", e);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/study/{id}")
	public ModelAndView updateStudy(@RequestBody String body,@PathVariable String id) {
		Source source = new StreamSource(new StringReader(body));
		ModelAndView ret = null;
		Object s = null;
		try {
			UnmarshalResult res = EntryUtil.validate(validatingMarshaller, source);
			//s = m_studyDAO.unmarshal(source);
			//s= (Entry) jaxb2Mashaller.unmarshal(source);
			if (res.getErrors().isEmpty()) {
				studyDS.updateEntry(id, res.getEntry());
				s = res.getEntry();
				ret = new ModelAndView(XML_VIEW_NAME, "object", s);
			} else {
				res.setEntry(null);
				s = res;
				ret = new ModelAndView("errors", "object", s);
			}
		} catch (JAXBException e) {
      throw new RuntimeException(e);
		} catch (SAXException e) {
      throw new RuntimeException(e);
		}
				
		return ret;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/study")
	public ModelAndView addStudy(@RequestBody String body) {
		Source source = new StreamSource(new StringReader(body));
		ModelAndView ret = null;
		Object s = null;
		try {
			UnmarshalResult res = EntryUtil.validate(validatingMarshaller, source);
			//s = m_studyDAO.unmarshal(source);
			//s= (Entry) jaxb2Mashaller.unmarshal(source);
			if (res.getErrors().isEmpty()) {
				studyDS.saveEntry(res.getEntry());
				s = res.getEntry();
				ret = new ModelAndView(XML_VIEW_NAME, "object", s);
			} else {
				res.setEntry(null);
				s = res;
				ret = new ModelAndView("errors", "object", s);
			}
		} catch (JAXBException e) {
      throw new RuntimeException(e);
		} catch (SAXException e) {
      throw new RuntimeException(e);
		}
				
		return ret;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/study/{id}")
	public ModelAndView removeStudy(@PathVariable String id) {
		studyDS.remove(id);
		return getStudies();
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/studies")
	public ModelAndView getStudies() {
		Feed list = new Feed();
		list.setEntry((List<Entry>) studyDS.getAll());
		return new ModelAndView(XML_VIEW_NAME, "studies", list);
	}
	
}
