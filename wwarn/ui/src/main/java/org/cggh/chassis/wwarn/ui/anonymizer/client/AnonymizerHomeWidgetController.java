/**
 * 
 */
package org.cggh.chassis.wwarn.ui.anonymizer.client;


/**
 * @author lee
 *
 */
public class AnonymizerHomeWidgetController {

	// Give this controller its undefined owner and model.
	private AnonymizerHomeWidgetModel model;

	
	// Allow this controller to construct itself, defining its owner and model.
	/**
	 * @param owner
	 * @param model
	 */
	public AnonymizerHomeWidgetController(
			AnonymizerHomeWidget owner,
			AnonymizerHomeWidgetModel model) {
		
		this.setModel(model);

	}


	public void setModel(AnonymizerHomeWidgetModel model) {
		this.model = model;
	}


	public AnonymizerHomeWidgetModel getModel() {
		return model;
	}




	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


}
