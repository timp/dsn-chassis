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
import com.google.gwt.user.client.ui.HorizontalPanel;
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
	
	
	
	
	
	public static final String STYLENAME = "xinput-date";

	
	protected XInputDate(Element definition, XQuestionModel model) {
		super(definition, model);
		construct();
	}

	
	
	
	protected void construct() {
		log.enter("construct");
		
		log.trace("check guard conditions");
		checkDefinitionTagName();

		log.trace("construct canvas");
		constructCanvas();
		
		log.trace("add custom style");
		addCustomStyle();
		
		log.trace("look for label");
		constructLabel();
		
		log.trace("instantiate date box");
		constructDateBox();
		
		log.trace("add value change handler");
		addValueChangeHandler();
		
		log.trace("look for hint");
		constructHint();

		log.trace("complete construction");
		initWidget(this.canvas);
		
		log.leave();
	}

	
	
	
	protected void checkDefinitionTagName() {
		checkDefinitionTagName(XQS.ELEMENT_INPUT);
	}
	
	

	protected void constructCanvas() {

		this.canvas = new HorizontalPanel();
		this.canvas.addStyleName(STYLENAME);
		
	}
	
	
	
	protected void constructDateBox() {

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




	/**
	 * 
	 */
	protected void addValueChangeHandler() {
		
		dateBox.addValueChangeHandler(new ValueChangeHandler<Date>() {
			
			private Log log = LogFactory.getLog(this.getClass());
			
			/* (non-Javadoc)
			 * @see com.google.gwt.event.logical.shared.ValueChangeHandler#onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent)
			 */
			public void onValueChange(ValueChangeEvent<Date> event) {
				log.enter("onValueChange");
				log.trace("event value: "+event.getValue());
				String dateString = xsDateFormat.format(event.getValue());
				model.setValue(dateString);
				log.leave();
			}

		});
		
	}

	

	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.xquestion.client.XFormControl#setValue(java.lang.String, boolean)
	 */
	@Override
	public void setValue(String value, boolean fireEvents) {
		// TODO Auto-generated method stub

	}

	
	
	public void setValue(Date value, boolean fireEvents) {
		this.dateBox.setValue(value, fireEvents);
	}
	
}
