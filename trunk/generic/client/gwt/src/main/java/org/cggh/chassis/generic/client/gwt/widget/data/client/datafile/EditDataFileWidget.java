/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileFeed;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileQuery;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidget;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetMemory;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetModel;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author aliman
 *
 */
public class EditDataFileWidget 
	extends AtomCrudWidget<DataFileEntry, DataFileFeed, DataFileQuery, AtomCrudWidgetModel<DataFileEntry>, EditDataFileWidgetRenderer, EditDataFileWidgetController>
{
	
	
	
	
	
	private Log log = LogFactory.getLog(EditDataFileWidget.class);


	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected AtomCrudWidgetModel<DataFileEntry> createModel() {
		return new AtomCrudWidgetModel<DataFileEntry>();
	}



	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected EditDataFileWidgetRenderer createRenderer() {
		return new EditDataFileWidgetRenderer(this);
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.client.ui.AtomCrudWidget#createController()
	 */
	@Override
	protected EditDataFileWidgetController createController() {
		return new EditDataFileWidgetController(this, this.model);
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

		this.memory = new AtomCrudWidgetMemory<DataFileEntry, DataFileFeed>(this.model, this.controller);

		log.leave();
	}
	
	
	

	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(EditDataFileWidget.class);
	}





	/**
	 * @param id
	 */
	public void editEntry(DataFileEntry entry) {
		
		// make sure we do a plain retrieve before editing so we don't persist any expanded links
		controller.retrieveEntry(entry.getEditLink().getHref());
		
	}




}
