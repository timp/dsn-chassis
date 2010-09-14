/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

/**
 * @author aliman
 *
 */
public class GWTTestXQuestionnaire extends GWTTestCase {



	
	private Log log = LogFactory.getLog(this.getClass());

	
	
	
	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.xquestion.XQuestion";
	}
	
	
	

	public void testQuestionnaireWithTwoQuestions() {
		log.enter("testQuestionnaireWithTwoQuestions");
		
		String definition = 
			"<questionnaire>" +
				"<model>" +
					"<element name='foo'/>" +
				"</model>" +
				"<view>" +
					"<question id='bar'>" +
						"<model>" +
							"<element name='bar'/>" +
						"</model>" +
						"<view>" +
							"<input type='string'/>" +
						"</view>" +
					"</question>" +
					"<question id='baz'>" +
						"<model>" +
							"<element name='baz'/>" +
						"</model>" +
						"<view>" +
							"<input type='string'/>" +
						"</view>" +
					"</question>" +
				"</view>" +
			"</questionnaire>";
		
		Document d = XMLParser.parse(definition);
		XQuestionnaire q = new XQuestionnaire(d.getDocumentElement());
		q.init();
		
		List<XQuestion> questions = q.getView().getQuestions();
		
		assertEquals(2, questions.size());
		
		String[] names = { "bar", "baz" };
		String[] values = { "quux", "spong" };
		
		for (int i=0; i<2; i++) {
			Widget w = questions.get(i).getView().getFormControl();
			assertNotNull(w);
			assertTrue((w instanceof XInputString));
			XInputString t = (XInputString) w;
			t.setValue(values[i], true); // fire events
		}
		
		Element e = q.getModel().getElement();
		
		log.debug("model element: "+e.toString());
		
		assertEquals("foo", e.getTagName());
		
		List<Element> children = XML.elements(e.getChildNodes());
		assertEquals(2, children.size());
		
		for (int i=0; i<2; i++) {
			Element child = children.get(i);
			assertEquals(names[i], child.getTagName());
			assertEquals(values[i], XML.firstChildNodeValueOrNullIfNoChildren(child));
		}

		log.leave();
	}




	public void testQuestionnaireWithTwoQuestionsNS() {
		log.enter("testQuestionnaireWithTwoQuestions");
		
		String definition = 
			"<questionnaire>" +
				"<model>" +
					"<element name='foo' prefix='x' namespaceUri='http://example.org/x'/>" +
				"</model>" +
				"<view>" +
					"<question id='bar'>" +
						"<model>" +
							"<element name='bar' prefix='y' namespaceUri='http://example.org/y'/>" +
						"</model>" +
						"<view>" +
							"<input type='string'/>" +
						"</view>" +
					"</question>" +
					"<question id='baz'>" +
						"<model>" +
							"<element name='baz' prefix='z' namespaceUri='http://example.org/z'/>" +
						"</model>" +
						"<view>" +
							"<input type='string'/>" +
						"</view>" +
					"</question>" +
				"</view>" +
			"</questionnaire>";
		
		Document d = XMLParser.parse(definition);
		XQuestionnaire q = new XQuestionnaire(d.getDocumentElement());
		q.init();
		
		List<XQuestion> questions = q.getView().getQuestions();
		
		assertEquals(2, questions.size());
		
		String[] prefixes = { "y", "z" };
		String[] namespaceUris = { "http://example.org/y", "http://example.org/z" };
		String[] names = { "bar", "baz" };
		String[] values = { "quux", "spong" };
		
		for (int i=0; i<2; i++) {
			Widget w = questions.get(i).getView().getFormControl();
			assertNotNull(w);
			assertTrue((w instanceof XInputString));
			XInputString t = (XInputString) w;
			t.setValue(values[i], true); // fire events
		}
		
		Element e = q.getModel().getElement();
		
		log.debug("model element: "+e.toString());
		
		assertEquals("x:foo", e.getTagName());
		assertEquals("foo", XML.getLocalName(e));
		assertEquals("x", e.getPrefix());
		assertEquals("http://example.org/x", e.getNamespaceURI());
		
		List<Element> children = XML.elements(e.getChildNodes());
		assertEquals(2, children.size());
		
		for (int i=0; i<2; i++) {
			Element child = children.get(i);
			assertEquals(prefixes[i]+":"+names[i], child.getTagName());
			assertEquals(names[i], XML.getLocalName(child));
			assertEquals(prefixes[i], child.getPrefix());
			assertEquals(namespaceUris[i], child.getNamespaceURI());
			assertEquals(values[i], XML.firstChildNodeValueOrNullIfNoChildren(child));
		}

		log.leave();
	}




	public void testQuestionnaireWithNestedQuestionnaire() {
		log.enter("testQuestionnaireWithNestedQuestionnaire");
		
		String definition = 
			"<questionnaire>" +
				"<model>" +
					"<element name='foo'/>" +
				"</model>" +
				"<view>" +
					"<question id='bar'>" +
						"<model>" +
							"<element name='bar'/>" +
						"</model>" +
						"<view>" +
							"<input type='string'/>" +
						"</view>" +
					"</question>" +
					"<questionnaire>" +
						"<model>" +
							"<element name='baz'/>" +
						"</model>" +
						"<view>" +
							"<question id='quux'>" +
								"<model>" +
									"<element name='quux'/>" +
								"</model>" +
								"<view>" +
									"<input type='string'/>" +
								"</view>" +
							"</question>" +
						"</view>" +
					"</questionnaire>" +
				"</view>" +
			"</questionnaire>";
		
		Document d = XMLParser.parse(definition);
		XQuestionnaire questionnaire = new XQuestionnaire(d.getDocumentElement());
		questionnaire.init();
		
		List<XQuestion> questions = questionnaire.getView().getQuestions();		
		assertEquals(1, questions.size());
		
		List<XQuestionnaire> nestedQuestionnaires = questionnaire.getView().getNestedQuestionnaires();
		assertEquals(1, nestedQuestionnaires.size());
		
		List<XQuestion> nestedQuestions = nestedQuestionnaires.get(0).getView().getQuestions();
		assertEquals(1, nestedQuestions.size());
		
		Widget w0 = questions.get(0).getView().getFormControl();
		assertNotNull(w0);
		assertTrue((w0 instanceof XInputString));
		XInputString t0 = (XInputString) w0;
		t0.setValue("spong", true); // fire events

		Widget w1 = nestedQuestions.get(0).getView().getFormControl();
		assertNotNull(w1);
		assertTrue((w1 instanceof XInputString));
		XInputString t1 = (XInputString) w1;
		t1.setValue("ding", true); // fire events

		Element e = questionnaire.getModel().getElement();
		
		log.debug("model element: "+e.toString());
		
		assertEquals("foo", e.getTagName());
		
		List<Element> children = XML.elements(e.getChildNodes());
		assertEquals(2, children.size());

		Element child0 = children.get(0);
		assertEquals("bar", child0.getTagName());
		assertEquals("spong", XML.firstChildNodeValueOrNullIfNoChildren(child0));
		assertEquals(0, XML.elements(child0.getChildNodes()).size());
		
		Element child1 = children.get(1);
		assertEquals("baz", child1.getTagName());
		assertEquals(1, XML.elements(child1.getChildNodes()).size());
		
		Element nestedChild = XML.elements(child1.getChildNodes()).get(0);
		assertEquals("quux", nestedChild.getTagName());
		assertEquals("ding", XML.firstChildNodeValueOrNullIfNoChildren(nestedChild));
		
		log.leave();
	}





	public void testQuestionnaireWithNestedQuestionnaireNS() {
		log.enter("testQuestionnaireWithNestedQuestionnaireNS");
		
		String definition = 
			"<questionnaire>" +
				"<model>" +
					"<element name='foo' prefix='a' namespaceUri='http://example.org/a'/>" +
				"</model>" +
				"<view>" +
					"<question id='bar'>" +
						"<model>" +
							"<element name='bar' prefix='b' namespaceUri='http://example.org/b'/>" +
						"</model>" +
						"<view>" +
							"<input type='string'/>" +
						"</view>" +
					"</question>" +
					"<questionnaire>" +
						"<model>" +
							"<element name='baz' prefix='c' namespaceUri='http://example.org/c'/>" +
						"</model>" +
						"<view>" +
							"<question id='quux'>" +
								"<model>" +
									"<element name='quux' prefix='d' namespaceUri='http://example.org/d'/>" +
								"</model>" +
								"<view>" +
									"<input type='string'/>" +
								"</view>" +
							"</question>" +
						"</view>" +
					"</questionnaire>" +
				"</view>" +
			"</questionnaire>";
		
		Document d = XMLParser.parse(definition);
		XQuestionnaire questionnaire = new XQuestionnaire(d.getDocumentElement());
		questionnaire.init();
		
		List<XQuestion> questions = questionnaire.getView().getQuestions();		
		assertEquals(1, questions.size());
		
		List<XQuestionnaire> nestedQuestionnaires = questionnaire.getView().getNestedQuestionnaires();
		assertEquals(1, nestedQuestionnaires.size());
		
		List<XQuestion> nestedQuestions = nestedQuestionnaires.get(0).getView().getQuestions();
		assertEquals(1, nestedQuestions.size());
		
		Widget w0 = questions.get(0).getView().getFormControl();
		assertNotNull(w0);
		assertTrue((w0 instanceof XInputString));
		XInputString t0 = (XInputString) w0;
		t0.setValue("spong", true); // fire events

		Widget w1 = nestedQuestions.get(0).getView().getFormControl();
		assertNotNull(w1);
		assertTrue((w1 instanceof XInputString));
		XInputString t1 = (XInputString) w1;
		t1.setValue("ding", true); // fire events

		Element e = questionnaire.getModel().getElement();
		
		log.debug("model element: "+e.toString());
		
		assertEquals("a:foo", e.getTagName());
		assertEquals("foo", XML.getLocalName(e));
		assertEquals("a", e.getPrefix());
		assertEquals("http://example.org/a", e.getNamespaceURI());
		
		List<Element> children = XML.elements(e.getChildNodes());
		assertEquals(2, children.size());

		Element child0 = children.get(0);
		assertEquals("b:bar", child0.getTagName());
		assertEquals("bar", XML.getLocalName(child0));
		assertEquals("b", child0.getPrefix());
		assertEquals("http://example.org/b", child0.getNamespaceURI());
		assertEquals("spong", XML.firstChildNodeValueOrNullIfNoChildren(child0));
		assertEquals(0, XML.elements(child0.getChildNodes()).size());
		
		Element child1 = children.get(1);
		assertEquals("c:baz", child1.getTagName());
		assertEquals("baz", XML.getLocalName(child1));
		assertEquals("c", child1.getPrefix());
		assertEquals("http://example.org/c", child1.getNamespaceURI());
		assertEquals(1, XML.elements(child1.getChildNodes()).size());
		
		Element nestedChild = XML.elements(child1.getChildNodes()).get(0);
		assertEquals("d:quux", nestedChild.getTagName());
		assertEquals("quux", XML.getLocalName(nestedChild));
		assertEquals("d", nestedChild.getPrefix());
		assertEquals("http://example.org/d", nestedChild.getNamespaceURI());
		assertEquals("ding", XML.firstChildNodeValueOrNullIfNoChildren(nestedChild));
		
		log.leave();
	}




	public void testQuestionnaireWithNestedQuestionnaireDefaultNS() {
		log.enter("testQuestionnaireWithNestedQuestionnaireDefaultNS");
		
		String definition = 
			"<questionnaire defaultPrefix='x' defaultNamespaceUri='http://example.org/x'>" +
				"<model>" +
					"<element name='foo'/>" +
				"</model>" +
				"<view>" +
					"<question id='bar'>" +
						"<model>" +
							"<element name='bar'/>" +
						"</model>" +
						"<view>" +
							"<input type='string'/>" +
						"</view>" +
					"</question>" +
					"<questionnaire>" +
						"<model>" +
							"<element name='baz'/>" +
						"</model>" +
						"<view>" +
							"<question id='quux'>" +
								"<model>" +
									"<element name='quux'/>" +
								"</model>" +
								"<view>" +
									"<input type='string'/>" +
								"</view>" +
							"</question>" +
						"</view>" +
					"</questionnaire>" +
				"</view>" +
			"</questionnaire>";
		
		Document d = XMLParser.parse(definition);
		XQuestionnaire questionnaire = new XQuestionnaire(d.getDocumentElement());
		questionnaire.init();
		
		List<XQuestion> questions = questionnaire.getView().getQuestions();		
		assertEquals(1, questions.size());
		
		List<XQuestionnaire> nestedQuestionnaires = questionnaire.getView().getNestedQuestionnaires();
		assertEquals(1, nestedQuestionnaires.size());
		
		List<XQuestion> nestedQuestions = nestedQuestionnaires.get(0).getView().getQuestions();
		assertEquals(1, nestedQuestions.size());
		
		Widget w0 = questions.get(0).getView().getFormControl();
		assertNotNull(w0);
		assertTrue((w0 instanceof XInputString));
		XInputString t0 = (XInputString) w0;
		t0.setValue("spong", true); // fire events

		Widget w1 = nestedQuestions.get(0).getView().getFormControl();
		assertNotNull(w1);
		assertTrue((w1 instanceof XInputString));
		XInputString t1 = (XInputString) w1;
		t1.setValue("ding", true); // fire events

		Element e = questionnaire.getModel().getElement();
		
		log.debug("model element: "+e.toString());
		
		assertEquals("x:foo", e.getTagName());
		assertEquals("foo", XML.getLocalName(e));
		assertEquals("x", e.getPrefix());
		assertEquals("http://example.org/x", e.getNamespaceURI());
		
		List<Element> children = XML.elements(e.getChildNodes());
		assertEquals(2, children.size());

		Element child0 = children.get(0);
		assertEquals("x:bar", child0.getTagName());
		assertEquals("bar", XML.getLocalName(child0));
		assertEquals("x", child0.getPrefix());
		assertEquals("http://example.org/x", child0.getNamespaceURI());
		assertEquals("spong", XML.firstChildNodeValueOrNullIfNoChildren(child0));
		assertEquals(0, XML.elements(child0.getChildNodes()).size());
		
		Element child1 = children.get(1);
		assertEquals("x:baz", child1.getTagName());
		assertEquals("baz", XML.getLocalName(child1));
		assertEquals("x", child1.getPrefix());
		assertEquals("http://example.org/x", child1.getNamespaceURI());
		assertEquals(1, XML.elements(child1.getChildNodes()).size());
		
		Element nestedChild = XML.elements(child1.getChildNodes()).get(0);
		assertEquals("x:quux", nestedChild.getTagName());
		assertEquals("quux", XML.getLocalName(nestedChild));
		assertEquals("x", nestedChild.getPrefix());
		assertEquals("http://example.org/x", nestedChild.getNamespaceURI());
		assertEquals("ding", XML.firstChildNodeValueOrNullIfNoChildren(nestedChild));
		
		log.leave();
	}



	public void testQuestionnaireWithRepeatableQuestion() {
		log.enter("testQuestionnaireWithRepeatableQuestion");
		
		String definition = 
			"<questionnaire>" +
				"<model>" +
					"<element name='foo'/>" +
				"</model>" +
				"<view>" +
					"<question id='bar' repeatable='yes'>" +
						"<model>" +
							"<element name='bar'/>" +
						"</model>" +
						"<view>" +
							"<input type='string'/>" +
						"</view>" +
					"</question>" +
				"</view>" +
			"</questionnaire>";
		
		Document d = XMLParser.parse(definition);
		XQuestionnaire q = new XQuestionnaire(d.getDocumentElement());
		q.init();

		// test before repeat
		
		List<XQuestion> questions = q.getView().getQuestions();
		
		assertEquals(1, questions.size());

		Widget w = questions.get(0).getView().getFormControl();
		XInputString t = (XInputString) w;
		t.setValue("baz", true); // fire events

		Element e = q.getModel().getElement();
		
		log.debug("model element, before repeat: "+e.toString());
		
		assertEquals("foo", e.getTagName());
		
		List<Element> children = XML.elements(e.getChildNodes());
		assertEquals(1, children.size());
		
		Element child = children.get(0);
		assertEquals("bar", child.getTagName());
		assertEquals("baz", XML.firstChildNodeValueOrNullIfNoChildren(child));

		// now call repeat
		
		questions.get(0).repeat();
		
		questions = q.getView().getQuestions();
		
		assertEquals(2, questions.size()); // should now be two questions	
		
		String[] values = { "baz", "quux" };
		
		for (int i=0; i<2; i++) {
			w = questions.get(i).getView().getFormControl();
			assertNotNull(w);
			assertTrue((w instanceof XInputString));
			t = (XInputString) w;
			t.setValue(values[i], true); // fire events
		}
		
		e = q.getModel().getElement();
		
		log.debug("model element, after repeat: "+e.toString());
		
		assertEquals("foo", e.getTagName());
		
		children = XML.elements(e.getChildNodes());
		assertEquals(2, children.size()); // should now be two children
		
		for (int i=0; i<2; i++) {
			child = children.get(i);
			assertEquals("bar", child.getTagName());
			assertEquals(values[i], XML.firstChildNodeValueOrNullIfNoChildren(child));
		}

		log.leave();
	}


}

