/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import java.util.Date;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class XInputDate extends XFormControl {

	
	
	private Log log = LogFactory.getLog(this.getClass());
	private DateBox dateBox;
	private DateTimeFormat xsDateFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
	private InlineLabel readOnlyLabel;
	
	
	
	
	
	public static final String STYLENAME = "xinput-date";

	
	protected XInputDate(Element definition, XQuestionModel model, boolean readOnly) {
		super(definition, model, readOnly);
		construct();
	}

	
	
	
	protected void construct() {
		log.enter("construct");
		
		log.debug("check guard conditions");
		checkDefinitionTagName();

		log.debug("construct canvas");
		constructCanvas();
		
		log.debug("add custom style");
		addCustomStyle();
		
		log.debug("look for label");
		constructLabel();
		
		log.debug("instantiate date box");
		constructDateBox();
		
		log.debug("add value change handler");
		addValueChangeHandler();
		
		log.debug("look for hint");
		constructHint();

		log.debug("complete construction");
		initWidget(this.canvas);
		
		log.leave();
	}

	
	
	
	protected void checkDefinitionTagName() {
		checkDefinitionTagName(XQS.ELEMENT_INPUT);
	}
	
	

	protected void constructCanvas() {

		this.canvas = new FlowPanel();
		this.canvas.addStyleName(STYLENAME);
		
	}
	
	
	
	protected void constructDateBox() {

		if (readOnly) {

			readOnlyLabel = new InlineLabel();
			readOnlyLabel.addStyleName(XFormControl.STYLENAME_ANSWER);
			this.canvas.add(readOnlyLabel);
			
		}
		else {
			
			dateBox = new DateBox();
			
			dateBox.setFormat(new DateBox.Format() {
				
				public void reset(DateBox dateBox, boolean abandon) {
					// TODO Auto-generated method stub
					
				}
				
				public Date parse(DateBox dateBox, String text, boolean reportError) {
					if (text != null && !text.equals("")) {
						return xsDateFormat.parse(text);
					}
					return null;
				}
				
				public String format(DateBox dateBox, Date date) {
					if (date != null) {
						return xsDateFormat.format(date);
					}
					return null;
				}
			});
			
			this.canvas.add(dateBox);		

		}

	}




	/**
	 * 
	 */
	protected void addValueChangeHandler() {
		
		if (!readOnly) {
			
			dateBox.addValueChangeHandler(new ValueChangeHandler<Date>() {
				
				private Log log = LogFactory.getLog(this.getClass());
				
				/* (non-Javadoc)
				 * @see com.google.gwt.event.logical.shared.ValueChangeHandler#onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent)
				 */
				public void onValueChange(ValueChangeEvent<Date> event) {
					log.enter("onValueChange");
					log.debug("event value: "+event.getValue());
					String dateString = xsDateFormat.format(event.getValue());
					model.setValue(dateString);
					log.leave();
				}

			});

		}
		
	}

	

	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.xquestion.client.XFormControl#setValue(java.lang.String, boolean)
	 */
	@Override
	public void setValue(String value, boolean fireEvents) {
		if (readOnly) {
			this.readOnlyLabel.setText(value);
		}
		else if (value != null && !value.equals("")) {
			
			// TODO handle parse errors
			
			this.setValue(xsDateFormat.parse(value), fireEvents);
			
		}
		else {
			
			log.debug("value was null or empty string, do nothing; value: "+value);
			
		}
	}

	
	
	public void setValue(Date value, boolean fireEvents) {
		if (readOnly) {
			this.readOnlyLabel.setText(xsDateFormat.format(value));
		}
		else {
			this.dateBox.setValue(value, fireEvents);
		}
	}
	
}
