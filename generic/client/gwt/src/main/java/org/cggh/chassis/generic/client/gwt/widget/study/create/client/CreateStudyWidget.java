/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.create.client;


import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;

import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class CreateStudyWidget {

	final private CreateStudyWidgetModel model;
	final private CreateStudyWidgetController controller;
	final private CreateStudyWidgetDefaultRenderer renderer;
	
	public CreateStudyWidget(Panel canvas, AtomService service, String feedURL) {
		
		model = new CreateStudyWidgetModel();
		
		controller = new CreateStudyWidgetController(model, service, feedURL);
		
		renderer = new CreateStudyWidgetDefaultRenderer(canvas, controller);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
	}
	
	public CreateStudyWidget(AtomService service, String feedURL, CreateStudyWidgetDefaultRenderer customRenderer) {
		
		model = new CreateStudyWidgetModel();
		
		controller = new CreateStudyWidgetController(model, service, feedURL);
		
		renderer = customRenderer;
		
		// inject controller into renderer
		renderer.setController(controller);

		// register renderer as listener to model
		model.addListener(renderer);
	}
	
	public void setUpNewStudy() {
		controller.setUpNewStudy();
	}
	
	public void addCreateStudyWidgetControllerListener(CreateStudyWidgetControllerListener listener) {
		controller.addListener(listener);
	}
	
}
