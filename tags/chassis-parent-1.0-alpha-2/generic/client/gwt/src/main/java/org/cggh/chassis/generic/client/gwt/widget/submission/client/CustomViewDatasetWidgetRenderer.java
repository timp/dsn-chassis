/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;


import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.ViewDatasetWidget;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.ViewDatasetWidgetRenderer;
import static org.cggh.chassis.generic.widget.client.HtmlElements.*;




/**
 * @author aliman
 *
 */
public class CustomViewDatasetWidgetRenderer extends ViewDatasetWidgetRenderer {


	
	
	/**
	 * @param owner
	 */
	public CustomViewDatasetWidgetRenderer(ViewDatasetWidget owner) {
		super(owner);
	}

	
	
	
	@Override
	protected void renderMainHeading() {
		// do nothing
	}
	
	
	
	@Override
	protected void renderActionsWidget() {
		// do nothing
	}
	
	
	
	
	@Override
	protected void setMainPanelStyle() {
		// do nothing
	}





	
	
	@Override
	protected void renderStudiesSubHeading() {
		this.contentPanel.add(h4Widget("Studies")); // TODO i18n
	}
	
	
	
	@Override
	protected void renderDataFilesSubHeading() {
		this.contentPanel.add(h4Widget("Data Files")); // TODO i18n
	}



	
	@Override
	protected void renderDataSharingSection() {

		// do nothing
		
	}








	
}
