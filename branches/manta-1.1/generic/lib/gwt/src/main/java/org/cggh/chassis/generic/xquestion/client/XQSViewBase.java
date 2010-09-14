/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Text;

/**
 * @author aliman
 *
 */
public abstract class XQSViewBase {

	
	
	protected Panel canvas;
	protected Element definition;
	protected XQSModelBase model;
	protected List<Widget> widgets = new ArrayList<Widget>();
	
	
	
	private Log log = LogFactory.getLog(this.getClass());

	
	
	protected void render(Element e, boolean readOnly) {
		log.enter("render");

		Widget w = null;
		
		String readOnlyAttribute = e.getAttribute(XQS.ATTR_READONLY);
		
		if (readOnly && readOnlyAttribute != null && readOnlyAttribute.equals(XQS.READONLY_HIDE)) {
			// do not render
		}
		else {
			
			if (e.getTagName().equals(XQS.ELEMENT_LABEL)) {
				w = new Label(XML.firstChildNodeValueOrNullIfNoChildren(e));
//				this.canvas.add(w);
			}

			if (e.getTagName().equals(XQS.ELEMENT_HTML)) {
				String html = getHTMLContent(e);
//				NodeList nodes = e.getChildNodes();
//				for (int i=0; i<nodes.getLength(); i++) {
//					Node n = nodes.item(i);
//					if (n instanceof Element) {
//						html += n.toString();
//					}
//					else if (n instanceof Text) {
//						html += n.toString();
//					}
//				}
				log.debug("rendering html: "+html);
				w = new HTML(html);
//				this.canvas.add(w);
			}

			if (e.getTagName().equals(XQS.ELEMENT_INLINEHTML)) {
				String html = getHTMLContent(e);
//				NodeList nodes = e.getChildNodes();
//				for (int i=0; i<nodes.getLength(); i++) {
//					Node n = nodes.item(i);
//					if (n instanceof Element) {
//						html += n.toString();
//					}
//					else if (n instanceof Text) {
//						html += n.toString();
//					}
//				}
				log.debug("rendering html: "+html);
				w = new InlineHTML(html);
//				this.canvas.add(w);
			}

			if (w != null) {
				this.widgets.add(w);
			}
			
			log.leave();

		} 
		
	}
	
	
	
	protected static String getHTMLContent(Element e) {
		String html = "";
		NodeList nodes = e.getChildNodes();
		for (int i=0; i<nodes.getLength(); i++) {
			Node n = nodes.item(i);
			if (n instanceof Element) {
				html += n.toString();
			}
			else if (n instanceof Text) {
				html += n.toString();
			}
		}
		return html;
	}
	
	
	
	/**
	 * 
	 */
	protected void refresh() {
		canvas.clear();
		if (model.isRelevant()) {
			canvas.setVisible(true);
			for (Widget w : widgets) {
				canvas.add(w);
			}
		}
		else {
			canvas.setVisible(false);
		}
	}





	
	
}
