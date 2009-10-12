/**
 * 
 */
package spike.xqs.lib.client;

import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Text;

/**
 * @author aliman
 *
 */
public class Question extends Component {

	private Element definition;
	private String defaultPrefix;
	private String defaultNamespaceUri;
	private Questionnaire parent;
	private VerticalPanel canvas;
	private Log log = LogFactory.getLog(this.getClass());
	private boolean repeatable;
	private boolean required;

	/**
	 * @param qe
	 * @param defaultNamespaceUri 
	 * @param defaultPrefix 
	 * @param parent 
	 */
	public Question(Element definition, String defaultPrefix, String defaultNamespaceUri, Questionnaire parent) {
		this.definition = definition;
		this.defaultPrefix = defaultPrefix;
		this.defaultNamespaceUri = defaultNamespaceUri;
		this.parent = parent;
		
		// guard conditions
		
		String elementName = XML.getLocalName(definition);
		if (!XQS.ELEMENT_QUESTION.equals(elementName)) {
			throw new XQSFormatException("bad element name, expected "+XQS.ELEMENT_QUESTION+", found "+elementName);
		}
			
		// attributes
		
		String repeatable = definition.getAttribute(XQS.ATTR_REPEATABLE);
		if (XQS.YES.equals(repeatable)) {
			this.repeatable = true;
		}
		
		String required = definition.getAttribute(XQS.ATTR_REQUIRED);
		if (XQS.YES.equals(required)) {
			this.required = true;
		}		
		
		// instantiate binding 

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

	
	
	/**
	 * 
	 * 
	 * 
	 */
	private void render() {
		log.enter("render");
		
		this.canvas = new VerticalPanel();
		this.canvas.setBorderWidth(1); // temp to see what's happening
		this.canvas.setSpacing(5); // temp to see what's happening

		for (Element e : XML.elements(definition.getChildNodes())) {

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

			if (XML.getLocalName(e).equals(XQS.ELEMENT_INPUT)) {
				
				String type = e.getAttribute(XQS.ATTR_TYPE);
				if (XQS.INPUTTYPE_TEXT.equals(type)) {
					TextBox t = new TextBox();
					this.canvas.add(t);
					t.addValueChangeHandler(new ValueChangeHandler<String>() {

						public void onValueChange(ValueChangeEvent<String> event) {
							binding.setValue(event.getValue());
						}
						
					});
				}
			}
		}

		if (this.repeatable) {
			Button repeatButton = new Button();
			repeatButton.setText("+");
			this.canvas.add(repeatButton);
			repeatButton.addClickHandler(new ClickHandler() {

				public void onClick(ClickEvent event) {
					Question clone = new Question(definition, defaultPrefix, defaultNamespaceUri, parent);
					parent.getElement().insertBefore(clone.getElement(), getElement().getNextSibling());
				}
				
			});
		}
		
		log.leave();
	}

}
