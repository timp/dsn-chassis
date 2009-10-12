/**
 * 
 */
package spike.xqs.lib.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Text;

/**
 * @author aliman
 *
 */
public class Questionnaire extends Component {

	
	
	private String defaultPrefix;
	private String defaultNamespaceUri;
	private Questionnaire parent;
	private VerticalPanel canvas;
	private Element definition;
	private Log log = LogFactory.getLog(this.getClass());

	
	
	public Questionnaire(Document definition) {
		this(definition.getDocumentElement(), null, null, null);
	}
	
	
	
	/**
	 * @param definition
	 */
	public Questionnaire(Element definition, String defaultPrefix, String defaultNamespaceUri, Questionnaire parent) {
		
		this.parent = parent;
		this.definition = definition;
		
		// guard conditions
		
		String elementName = XML.getLocalName(definition);
		if (!XQS.ELEMENT_QUESTIONNAIRE.equals(elementName)) {
			throw new XQSFormatException("bad element name, expected "+XQS.ELEMENT_QUESTIONNAIRE+", found "+elementName);
		}
			
		// default prefix and namespace uri
		
		this.defaultPrefix = definition.getAttribute(XQS.ATTR_DEFAULTPREFIX);
		if (this.defaultPrefix == null) {
			this.defaultPrefix = defaultPrefix; // not overridden
		}
		
		this.defaultNamespaceUri = definition.getAttribute(XQS.ATTR_DEFAULTNAMESPACEURI);
		if (this.defaultNamespaceUri == null) {
			this.defaultNamespaceUri = defaultNamespaceUri; // not overridden
		}
		
		// instantiate binding and child components

		List<Element> childElements = XML.elements(definition.getChildNodes());
		for (Element e : childElements) {
			
			if (XML.getLocalName(e).equals(XQS.ELEMENT_BINDING)) {
				this.binding = new Binding(e, this.defaultPrefix, this.defaultNamespaceUri, this.parent);
			}
			
		}

		if (this.binding == null) {
			throw new XQSFormatException("no binding specified");
		}
		
		this.render();
		this.initWidget(canvas);

	}

	
	
	public void render() {
		log.enter("render");
		
		this.canvas = new VerticalPanel();
		this.canvas.setBorderWidth(2); // temp to see what's happening
		this.canvas.setSpacing(5); // temp to see what's happening

		List<Element> childElements = XML.elements(this.definition.getChildNodes());
		for (Element e : childElements) {
			
			if (XML.getLocalName(e).equals(XQS.ELEMENT_QUESTION)) {
				Question q = new Question(e, this.defaultPrefix, this.defaultNamespaceUri, this);
				this.canvas.add(q);
			}

			if (XML.getLocalName(e).equals(XQS.ELEMENT_QUESTIONNAIRE)) {
				Questionnaire qa = new Questionnaire(e, this.defaultPrefix, this.defaultNamespaceUri, this); 
				this.canvas.add(qa);
			}
			
			if (XML.getLocalName(e).equals(XQS.ELEMENT_LABEL)) {
				this.canvas.add(new Label(XML.firstChildNodeValueOrNullIfNoChildren(e)));
			}

			if (XML.getLocalName(e).equals(XQS.ELEMENT_HTML)) {
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

			


		}
		
		log.leave();
	}
	

	
}
