/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.view.client;

import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;

import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class ViewStudyWidget {

	final private ViewStudyWidgetModel model;
	final private ViewStudyWidgetController controller;
	final private ViewStudyWidgetDefaultRenderer renderer;
	
	public ViewStudyWidget(Panel canvas, AtomService service) {
		
		model = new ViewStudyWidgetModel();
		
		controller = new ViewStudyWidgetController(model, service);
		
		renderer = new ViewStudyWidgetDefaultRenderer(canvas);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
	}
		
	public void loadStudyByEntryURL(String entryURL) {
		controller.loadStudyEntry(entryURL);
	}
	
	
}
