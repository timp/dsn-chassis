/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.MenuEvent;
import org.cggh.chassis.generic.widget.client.MultiWidget;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class SubmissionManagementWidget 
	extends MultiWidget {

	
	
	
	
	// utility fields
	private Log log = LogFactory.getLog(SubmissionManagementWidget.class);
	
	
	
	
	// UI fields
	ViewSubmissionsPendingReviewWidget vsprwWidget;
	
	
	
	public SubmissionManagementWidget() {
		super(false, true);
	}
	
	
	
	

	@Override
	protected void renderMainChildren() {
		log.enter("renderMainChildren");
		
		this.vsprwWidget = new ViewSubmissionsPendingReviewWidget();
		
		this.mainChildren.add(this.vsprwWidget);
		
		log.leave();
	}

	
	
	
	
	@Override
	protected void registerHandlersForChildWidgetEvents() {

		// TODO
		
	}

	
	
	
	
	@Override
	protected void renderMenuBar() {
		log.enter("renderMenuBar");
		
		Command viewSubmissionsPendingReviewCommand = new Command() { 
			public void execute() { 

				setActiveChild(vsprwWidget);
				fireEvent(new MenuEvent());
				
			} 
		};

		MenuItem viewSubmissionsPendingReviewMenuItem = new MenuItem("submissions pending review", viewSubmissionsPendingReviewCommand );
		this.menu.addItem(viewSubmissionsPendingReviewMenuItem);
		
		log.leave();
	}


	
	
	@Override
	protected void setActiveChild(Widget child, boolean memorise) {
		super.setActiveChild(child, memorise);
		
		if (child instanceof ViewSubmissionsPendingReviewWidget) {
			ViewSubmissionsPendingReviewWidget w = (ViewSubmissionsPendingReviewWidget) child;
			w.refresh();
		}
		
	}


	
	
}
