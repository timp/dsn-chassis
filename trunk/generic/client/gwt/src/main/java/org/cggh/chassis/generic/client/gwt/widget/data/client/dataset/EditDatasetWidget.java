/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetFeed;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetQuery;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidget;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetMemory;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetModel;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author aliman
 *
 */
public class EditDatasetWidget 
	extends AtomCrudWidget<DatasetEntry, DatasetFeed, DatasetQuery, AtomCrudWidgetModel<DatasetEntry>, EditDatasetWidgetRenderer, EditDatasetWidgetController>


{
	
	
	
	
	
	
	private Log log = LogFactory.getLog(EditDatasetWidget.class);





	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(EditDatasetWidget.class);
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected AtomCrudWidgetModel<DatasetEntry> createModel() {
		return new AtomCrudWidgetModel<DatasetEntry>();
	}






	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected EditDatasetWidgetRenderer createRenderer() {
		return new EditDatasetWidgetRenderer(this);
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.client.ui.AtomCrudWidget#createController()
	 */
	@Override
	protected EditDatasetWidgetController createController() {
		return new EditDatasetWidgetController(this, this.model);
	}







	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		super.init(); // this will instantiate model, renderer and controller
		
		this.renderer.setController(this.controller);
		
		this.memory = new AtomCrudWidgetMemory<DatasetEntry, DatasetFeed>(this.model, this.controller);

		log.leave();
	}





	/**
	 * @param entry
	 */
	public void editEntry(DatasetEntry entry) {

		// make sure we do a plain retrieve before editing so we don't persist any expanded links
		this.controller.retrieveEntry(entry.getEditLink().getHref());
		
	}
	
	
	


}
