/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

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
public class GWTTestXQuestion extends GWTTestCase {
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	
	
	

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.xquestion.XQuestion";
	}
	
	
	
	public void testQuestionWithTextInput() {
		log.enter("testQuestionWithTextInput");
		
		String definition = 
			"<question id=\"foo\">" +
				"<model>" +
					"<element name='foo'/>" +
				"</model>" +
				"<view>" +
					"<input type='string'/>" +
				"</view>" +
			"</question>";
		
		Document d = XMLParser.parse(definition);
		XQuestion q = new XQuestion(d.getDocumentElement());
		q.init();
		
		Widget w = q.getView().getFormControl();
		
		assertNotNull(w);
		assertTrue((w instanceof XInputString));
		
		XInputString input = (XInputString) w;
		
		assertNotNull(input);
		
		input.setValue("bar", true); // fire events
		
		Element e = q.getModel().getElement();
		
		log.debug("model element: "+e.toString());
		
		assertEquals("foo", e.getTagName());
		assertEquals(1, e.getChildNodes().getLength()); // 1 text node
		assertEquals("bar", XML.firstChildNodeValueOrNullIfNoChildren(e));

		log.leave();
	}




	public void testQuestionWithTextInputNS() {
		log.enter("testQuestionWithTextInput");
		
		String definition = 
			"<question id=\"foo\">" +
				"<model>" +
					"<element name='foo' prefix='x' namespaceUri='http://example.org/bar'/>" +
				"</model>" +
				"<view>" +
					"<input type='string'/>" +
				"</view>" +
			"</question>";
		
		Document d = XMLParser.parse(definition);
		XQuestion q = new XQuestion(d.getDocumentElement());
		q.init();
		
		Widget w = q.getView().getFormControl();
		
		assertNotNull(w);
		assertTrue((w instanceof XInputString));

		XInputString input = (XInputString) w;

		input.setValue("bar", true); // fire events
		
		Element e = q.getModel().getElement();
		
		log.debug("model element: "+e.toString());
		
		assertEquals("x:foo", e.getTagName());
		assertEquals("foo", XML.getLocalName(e));
		assertEquals("x", e.getPrefix());
		assertEquals("http://example.org/bar", e.getNamespaceURI());
		assertEquals(1, e.getChildNodes().getLength()); // 1 text node
		assertEquals("bar", XML.firstChildNodeValueOrNullIfNoChildren(e));

		log.leave();
	}
}
