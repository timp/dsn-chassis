/**
 * 
 */
package spike.widget.application.client;

import org.cggh.chassis.generic.client.gwt.widget.application.client.perspective.PerspectiveWidget;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author raok
 *
 */
public class SpikeApplicationWidget implements EntryPoint {

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		
		
		PerspectiveWidget widget = new PerspectiveWidget(RootPanel.get(), "bob@example.com");
				
		
	}

}
