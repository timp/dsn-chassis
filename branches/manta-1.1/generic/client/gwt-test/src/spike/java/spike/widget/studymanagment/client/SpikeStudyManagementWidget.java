/**
 * 
 */
package spike.widget.studymanagment.client;

import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyManagementWidget;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author raok
 *
 */
public class SpikeStudyManagementWidget implements EntryPoint {

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		
		
		SimplePanel menuCanvas = new SimplePanel();
		SimplePanel displayCanvas = new SimplePanel();
		
		FlowPanel mainPanel = new FlowPanel();
		mainPanel.add(menuCanvas);
		mainPanel.add(displayCanvas);
				
		UserDetailsTO user = new UserDetailsTO();
		user.setId("alice@example.com");
		ChassisUser.setCurrentUser(user);
		
		StudyManagementWidget studyManagementWidget = new StudyManagementWidget();
		displayCanvas.add(studyManagementWidget);
		
		MenuBar mb = new MenuBar();
		mb.addItem("studies", studyManagementWidget.getMenu());
		menuCanvas.add(mb);
		
		RootPanel.get("holdall").add(mainPanel);
	}

}
