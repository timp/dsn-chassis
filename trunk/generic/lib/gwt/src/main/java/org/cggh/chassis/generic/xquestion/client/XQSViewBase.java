/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
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
	
	
	
	private Log log = LogFactory.getLog(this.getClass());

	
	
	protected void render(Element e) {
		log.enter("render");

		if (e.getTagName().equals(XQS.ELEMENT_LABEL)) {
			this.canvas.add(new Label(XML.firstChildNodeValueOrNullIfNoChildren(e)));
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
			this.canvas.add(new HTML(html));
		}

		log.leave();
	}
	
	
	
}
