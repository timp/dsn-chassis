/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.studymanagement.client;

import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;

import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class StudyManagementWidget {

	
	private StudyManagementWidgetModel model;
	private StudyManagementWidgetController controller;
	private StudyManagementWidgetDefaultRenderer renderer;

	public StudyManagementWidget(Panel menuCanvas, Panel displayCanvas, AtomService service) {
		
		model = new StudyManagementWidgetModel();
		
		controller = new StudyManagementWidgetController(model);
		
		renderer = new StudyManagementWidgetDefaultRenderer(menuCanvas, displayCanvas, controller, service);
		
		// register renderer as listener to model
		model.addListener(renderer);
	}
	
	
	
}
