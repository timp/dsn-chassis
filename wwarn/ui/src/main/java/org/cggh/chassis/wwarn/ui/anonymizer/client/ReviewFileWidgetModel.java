/**
 * 
 */
package org.cggh.chassis.wwarn.ui.anonymizer.client;

import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;


/**
 * @author lee
 *
 */
public class ReviewFileWidgetModel extends AsyncWidgetModel {

	
	private String errorMessage;

	public ReviewFileWidgetModel() {

	}

	@Override
	public void init() {
		super.init();
	}	
	
	
	public void setErrorMessage(String message) {
		this.errorMessage = message;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

}
