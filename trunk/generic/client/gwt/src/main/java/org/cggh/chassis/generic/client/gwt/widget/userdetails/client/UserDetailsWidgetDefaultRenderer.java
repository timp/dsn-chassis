/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import java.util.Set;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author raok
 *
 */
class UserDetailsWidgetDefaultRenderer implements UserDetailsWidgetModelListener {

	private UserDetailsWidgetController controller;

	private Panel canvas;
	
	//Expose view elements for testing purposes.
	Label userNameLabel = new Label();
	ListBox userRolesListBox = new ListBox();
	Label currentRoleLabel = new Label();
	Widget loadingUIObject = new SimplePanel();
	Widget userDetailsUIObject = new SimplePanel();

	UserDetailsWidgetDefaultRenderer(Panel canvas, UserDetailsWidgetController controller) {
		this.canvas = canvas;
		this.controller = controller;
		
		initCanvas();
	}

	
	private void initCanvas() {
	
		// TODO implement this method
		canvas.add(loadingUIObject);
	}
	
	
	
	public void onCurrentRoleChanged(String before, String after) {
		currentRoleLabel.setText(after);
	}

	public void onRolesChanged(Set<String> before, Set<String> after) {
		
		// clear ListBox
		userRolesListBox.clear();
		
		for (String role : after) {
			// TODO create friendly translator for roles?
			userRolesListBox.addItem(role, role);
		}
	}

	public void onStatusChanged(Integer before, Integer after) {
		
		if ( (after == UserDetailsWidgetModel.STATUS_INITIAL)
			  || (after == UserDetailsWidgetModel.STATUS_LOADING) )	
		{
			canvas.clear();
			canvas.add(loadingUIObject);
		}
		else if (after == UserDetailsWidgetModel.STATUS_FOUND) 
		{
			canvas.clear();
			canvas.add(userDetailsUIObject);
		}
		else
		{
			// TODO handle error case (could use extra panel or pass error to parent)
		}
	}

	public void onUserNameChanged(String before, String after) {
		userNameLabel.setText(after);
	}


	public void setController(UserDetailsWidgetController controller) {
		this.controller = controller;
	}

}
