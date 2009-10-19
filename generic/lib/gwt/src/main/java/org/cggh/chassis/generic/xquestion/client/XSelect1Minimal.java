/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class XSelect1Minimal extends XFormControl {


	
	
	private Log log = LogFactory.getLog(this.getClass());
	private ListBox box;
	
	
	
	
	public static final String STYLENAME = "xselect1-minimal";

	
	
	
	/**
	 * @param definition
	 * @param model
	 */
	public XSelect1Minimal(Element definition, XQuestionModel model) {
		super(definition, model);
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
		
		log.trace("instantiate list box");
		constructListBox();
		
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

		box = new ListBox();
		this.canvas.add(box);
		
		List<Element> itemElements = XML.getElementsByTagName(definition, XQS.ELEMENT_ITEM);
		
		for (int i=0; i < itemElements.size() ; i++) {
			
			Element ie = itemElements.get(i);
			String itemLabel = XML.getElementSimpleContentByTagName(ie, XQS.ELEMENT_LABEL);
			String itemValue = XML.getElementSimpleContentByTagName(ie, XQS.ELEMENT_VALUE);
			
			if (itemLabel == null) {
				throw new XQuestionFormatException("bad select1 definition, found no label for item ["+i+"]");
			}
			
			if (itemValue == null) {
				throw new XQuestionFormatException("bad select1 definition, found no value for item ["+i+"]");
			}
			
			log.trace("adding list item for item label: "+itemLabel+"; value: "+itemValue);
			
			box.addItem(itemLabel, itemValue);
			
			if (i == 0) {
				if (model.getValue() == null) {
					model.setValue(itemValue); // set initial value
				}
			}
							
		}

		box.addClickHandler(new ListBoxClickHandler());
		
	}
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.xquestion.client.XFormControl#setValue(java.lang.String, boolean)
	 */
	@Override
	public void setValue(String value, boolean fireEvents) {
		log.enter("setValue");
		
		for (int index=0; index<box.getItemCount(); index++) {
			String itemValue = box.getValue(index);
			log.trace("comparing item value ["+itemValue+"] with value to set ["+value+"]");
			if (value.equals(itemValue)) {
				log.trace("found match, setting selected index: "+index);
				box.setSelectedIndex(index);
				return;
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
