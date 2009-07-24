/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.curator;

import org.cggh.chassis.wwarn.prototype.client.shared.Perspective;

import com.google.gwt.core.client.GWT;

/**
 * @author aliman
 *
 */
public class CuratorPerspective implements Perspective {

	private void log(String message, String context) {
		String output = CuratorPerspective.class.getName() + " :: " + context + " :: " + message;
		GWT.log(output, null);
	}

	private static final String TOKEN_HOME = "home";
	private static final String TOKEN_SEPARATOR = "/";
	private static final String TOKEN_BASE = "curator";
	private static final String NAME = "curator";

	public String getHomeToken() {
		return TOKEN_BASE + TOKEN_SEPARATOR + TOKEN_HOME;
	}

	public String getName() {
		return NAME;
	}

	public String getTokenBase() {
		return TOKEN_BASE;
	}

	public void setStateToken(String stateToken) {
		String _ = "setStateToken("+stateToken+")";
		log("begin",_);
		
		// TODO Auto-generated method stub
		
		log("end",_);
	}


}
