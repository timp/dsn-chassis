/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public abstract class XSelectBase extends XFormControl {

	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	protected Label readOnlyLabel;
	protected Map<String,String> items = new HashMap<String,String>();
	
	
	
	/**
	 * @param definition
	 * @param model
	 * @param readOnly 
	 */
	XSelectBase(Element definition, XQuestionModel model, boolean readOnly) {
		super(definition, model, readOnly);
	}

	
	
	
	

	
	protected void constructItemMap() {
		List<Element> itemElements = XML.getElementsByTagName(definition, XQS.ELEMENT_ITEM);
		for (int index=0; index < itemElements.size() ; index++) {
			
			Element itemElement = itemElements.get(index);
			String itemLabel = XML.getElementSimpleContentByTagName(itemElement, XQS.ELEMENT_LABEL);
			String itemValue = XML.getElementSimpleContentByTagName(itemElement, XQS.ELEMENT_VALUE);

			if (itemLabel == null) {
				throw new XQuestionFormatException("bad select1 definition, found no label for item ["+index+"]");
			}
			
			if (itemValue == null) {
				throw new XQuestionFormatException("bad select1 definition, found no value for item ["+index+"]");
			}

			items.put(itemValue, itemLabel);
			
		}
	}




	
	


}
