package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

public interface UserDetailsWidgetAPI {

	public void refreshUserDetails();
	
	public void addListener(UserDetailsPubSubAPI listener);

}