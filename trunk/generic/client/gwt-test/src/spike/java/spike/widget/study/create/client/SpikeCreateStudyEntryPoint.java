/**
 * 
 */
package spike.widget.study.create.client;

import org.cggh.chassis.generic.client.gwt.widget.study.create.client.CreateStudyWidget;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author raok
 *
 */
public class SpikeCreateStudyEntryPoint implements EntryPoint {

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
				
		CreateStudyWidget widget = new CreateStudyWidget();
		RootPanel.get().add(widget);
		
		widget.setUpNewStudy("alice@example.com");
		
	}

}
