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
	protected List<Widget> widgets = new ArrayList<Widget>();
	
	
	
	private Log log = LogFactory.getLog(this.getClass());

	
	
	protected void render(Element e) {
		log.enter("render");

		Widget w = null;
		
		if (e.getTagName().equals(XQS.ELEMENT_LABEL)) {
			w = new Label(XML.firstChildNodeValueOrNullIfNoChildren(e));
//			this.canvas.add(w);
		}

		if (e.getTagName().equals(XQS.ELEMENT_HTML)) {
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
			log.trace("rendering html: "+html);
			w = new HTML(html);
//			this.canvas.add(w);
		}

		if (w != null) {
			this.widgets.add(w);
		}
		
		log.leave();
	}
	
	
	
	/**
	 * 
	 */
	protected void refresh() {
		this.canvas.clear();
		for (Widget w : widgets) {
			this.canvas.add(w);
		}
	}





	
	
}
