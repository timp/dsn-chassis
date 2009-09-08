/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.viewall.client;

import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;

import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class ViewAllStudiesWidget {

	final private ViewAllStudiesWidgetModel model;
	final private ViewAllStudiesWidgetController controller;
	final private ViewAllStudiesWidgetDefaultRenderer renderer;
	
	public ViewAllStudiesWidget(Panel canvas, AtomService service) {
		
		model = new ViewAllStudiesWidgetModel();
		
		controller = new ViewAllStudiesWidgetController(model, service);
		
		renderer = new ViewAllStudiesWidgetDefaultRenderer(canvas);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
	}
	
	public void loadStudiesByFeedURL(String feedURL) {
		controller.loadStudiesByFeedURL(feedURL);
	}
	
	
}
