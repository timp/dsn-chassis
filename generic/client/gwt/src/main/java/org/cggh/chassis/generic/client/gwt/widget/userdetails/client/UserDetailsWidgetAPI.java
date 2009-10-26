package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;

public interface UserDetailsWidgetAPI {

	public Deferred<UserDetailsTO> refreshUserDetails();
	
	public void addListener(UserDetailsPubSubAPI listener);

}