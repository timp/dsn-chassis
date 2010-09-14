/**
 * 
 */
package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;
import static org.cggh.chassis.generic.widget.client.HtmlElements.*;
import org.cggh.chassis.wwarn.ui.submitter.client.ViewStudyWidgetModel.EntryChangeEvent;
import org.cggh.chassis.wwarn.ui.submitter.client.ViewStudyWidgetModel.EntryChangeHandler;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class ViewStudyWidgetRenderer extends AsyncWidgetRenderer<ViewStudyWidgetModel> {

	
	
	
	private static final Log log = LogFactory.getLog(ViewStudyWidgetRenderer.class);
	
	
	
	@Override
	protected void renderMainPanel() {
		log.enter("renderMainPanel");
		
		// nothing to do

		log.leave();
	}
	
	
	
	@Override
	public void registerHandlersForModelChanges() {
		super.registerHandlersForModelChanges();
		
		this.modelChangeHandlerRegistrations.add(
				this.model.addEntryChangeHandler(new EntryChangeHandler() {
			
			public void onChange(EntryChangeEvent e) {
				log.enter("onchange(EntryChangeEvent)");
				syncUIWithEntry(e.getAfter());
				log.leave();
			}
			
		}));
		
	}



	/**
	 * @param after
	 */
	protected void syncUIWithEntry(Element entryElement) {
		log.enter("syncUIWithEntry");
		
		this.mainPanel.clear();

		if (entryElement != null) {
			
			String title = AtomHelper.getTitle(entryElement);
			this.mainPanel.add(strongWidget(title));
			
		}
		
		log.leave();
	}
	
	
	
	
	@Override
	public void syncUI() {
		super.syncUI();

		this.syncUIWithEntry(this.model.getEntry());
		
	}

}
