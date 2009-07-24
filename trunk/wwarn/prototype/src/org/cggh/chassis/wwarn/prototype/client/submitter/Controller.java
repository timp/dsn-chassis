/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter;

import com.google.gwt.core.client.GWT;

/**
 * @author aliman
 *
 */
class Controller {

	private Model model;
	private SubmitterPerspective owner;

	private void log(String message, String context) {
		String output = Controller.class.getName() + " :: " + context + " :: " + message;
		GWT.log(output, null);
	}

	Controller(Model model, SubmitterPerspective owner) {
		this.model = model;
		this.owner = owner;
	}

	void init() {
		// TODO Auto-generated method stub
		
	}

	void setStateToken(String stateToken) {
		String _ = "setStateToken("+stateToken+")";
		log("begin",_);

		log("set field on model",_);
		this.model.setStateToken(stateToken);
		
		log("check if state token applies to this perspective",_);
		if (stateToken.startsWith(SubmitterPerspective.TOKEN_BASE)) {
			
			log("was perspective switched?",_);
			if (!this.model.getIsCurrentPerspective()) {
				log("switched, set perspective is current: true",_);
				this.model.setIsCurrentPerspective(true);
			}
			
			// TODO inspect rest of token & do something about it
		}
		else {

			log("was perspective switched?",_);
			if (this.model.getIsCurrentPerspective()) {
				log("switched, set perspective is current: false",_);
				this.model.setIsCurrentPerspective(false);
			}			
		}
		
		log("end",_);
	}

}
