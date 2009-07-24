/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.shared;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;

/**
 * @author aliman
 *
 */
public class HistoryCommand implements Command {

	private String state;

	public HistoryCommand(String state) {
		this.state = state;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.Command#execute()
	 */
	public void execute() {
		History.newItem(state);
	}

}
