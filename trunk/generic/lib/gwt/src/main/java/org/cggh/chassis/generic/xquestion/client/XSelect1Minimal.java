/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import java.util.Iterator;
import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class XSelect1Minimal extends XSelectBase {


	
	
	private Log log = LogFactory.getLog(this.getClass());
	private ListBox box;
	private Label readOnlyLabel;
	
	
	
	
	public static final String STYLENAME = "xselect1-minimal";

	
	
	
	/**
	 * @param definition
	 * @param model
	 * @param readOnly 
	 */
	public XSelect1Minimal(Element definition, XQuestionModel model, boolean readOnly) {
		super(definition, model, readOnly);
		construct();
	}

	
	
	
	private void construct() {
		log.enter("construct");
		
		log.trace("check guard conditions");
		checkDefinition();

		log.trace("construct canvas");
		constructCanvas();
		
		log.trace("add custom style");
		addCustomStyle();
		
		log.trace("look for label");
		constructLabel();

		log.trace("map values to items");
		Deferred<List<Element>> deferredItems = constructItemMap();
		deferredItems.addCallback(new Function<List<Element>, List<Element>>() {

			public List<Element> apply(List<Element> in) {
				addItems(in);
				constructListBox();
				return in;
			}
			
		});
		
		log.trace("look for hint");
		constructHint();

		log.trace("complete construction");
		initWidget(this.canvas);
		
		log.leave();
	}
	
	
	
	
	private void checkDefinition() {
		checkDefinitionTagName(XQS.ELEMENT_SELECT1);
		checkDefinitionAppearance(XQS.APPEARANCE_MINIMAL);
	}
	
	
	
	
	private void constructCanvas() {
		this.canvas = new HorizontalPanel();
		this.canvas.addStyleName(STYLENAME);
	}
	
	
	
	
	private void constructListBox() {

		if (readOnly) {
			
			readOnlyLabel = new Label();
			readOnlyLabel.addStyleName(XFormControl.STYLENAME_ANSWER);
			this.canvas.add(readOnlyLabel);

		}
		else {

			box = new ListBox();
			this.canvas.add(box);
			
			Iterator<String> it = values.iterator();
			
			for (int index=0; it.hasNext(); index++) {
				
				String itemValue = it.next();
				String itemLabel = labels.get(itemValue);
				
				log.trace("adding list item for item label: "+itemLabel+"; value: "+itemValue);
				
				box.addItem(itemLabel, itemValue);
				
				if (index == 0) {
					if (model.getValue() == null) {
						model.setValue(itemValue); // set initial value
					}
				}
								
			}

			box.addClickHandler(new ListBoxClickHandler());

		}
		
	}
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.xquestion.client.XFormControl#setValue(java.lang.String, boolean)
	 */
	@Override
	public void setValue(String value, boolean fireEvents) {
		log.enter("setValue");
		
		if (readOnly) {
			
			String text = (value.equals("")) ? value : labels.get(value);
			this.readOnlyLabel.setText(text);
			
		}
		else {

			for (int index=0; index<box.getItemCount(); index++) {
				String itemValue = box.getValue(index);
				log.trace("comparing item value ["+itemValue+"] with value to set ["+value+"]");
				if (value.equals(itemValue)) {
					log.trace("found match, setting selected index: "+index);
					box.setSelectedIndex(index);
					return;
				}
			}

		}
		
		log.leave();
	}
	
	
	
	
	protected class ListBoxClickHandler implements ClickHandler {

		private Log log = LogFactory.getLog(this.getClass());
		
		public void onClick(ClickEvent event) {
			log.enter("onClick");
			int index = box.getSelectedIndex();
			String value = box.getValue(index);
			if (model.getValue() == null || !model.getValue().equals(value)) {
				model.setValue(value);	
				log.trace("setting model with value: "+value);
			}
			else {
				log.trace("no change to selection");
			}
			log.leave();
		}

	}
	

	
	
	
}
