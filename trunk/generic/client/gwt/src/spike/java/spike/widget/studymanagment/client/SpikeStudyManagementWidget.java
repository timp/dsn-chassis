/**
 * 
 */
package spike.widget.studymanagment.client;

import org.cggh.chassis.generic.client.gwt.widget.studymanagement.client.StudyManagementWidget;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

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
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(menuCanvas);
		verticalPanel.add(displayCanvas);
				
		@SuppressWarnings("unused")
		StudyManagementWidget studyManagementWidget = new StudyManagementWidget(menuCanvas, displayCanvas, "alice@example.com");
		
		RootPanel.get().add(verticalPanel);
		
		
	}

}
