/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.client.gwt.widget.data.client.DataManagementWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;
import org.cggh.chassis.generic.widget.client.WidgetMemory;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class ViewDataFileWidget 
	extends DelegatingWidget<ViewDataFileWidgetModel, ViewDataFileWidgetRenderer> {
	private Log log = LogFactory.getLog(ViewDataFileWidget.class);
	private ViewDataFileWidgetController controller;
	private String currentEntryId;


	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		this.model = new ViewDataFileWidgetModel(this);
		this.controller = new ViewDataFileWidgetController(this, this.model);
		this.renderer = new ViewDataFileWidgetRenderer(this);
		this.renderer.setCanvas(this.contentBox);
		this.memory = new Memory();

		log.leave();
	}
	
	
	

	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(DataManagementWidget.class);
	}




	/**
	 * @param dataFileEntry
	 */
	public Deferred<DataFileEntry> setEntry(String id) {
		log.enter("setEntry");

		// store to use as mnemonic
		this.currentEntryId = id;
		
		// delegate to controller
		Deferred<DataFileEntry> def = this.controller.getEntry(id);;
		
		log.leave();
		return def;
	}




	public HandlerRegistration addEditDataFileActionHandler(DataFileActionHandler h) {
		return this.addHandler(h, EditDataFileActionEvent.TYPE);
	}





	public HandlerRegistration addUploadRevisionActionHandler(DataFileActionHandler h) {
		return this.addHandler(h, UploadDataFileRevisionActionEvent.TYPE);
	}




	/**
	 * @author aliman
	 *
	 */
	public class Memory extends WidgetMemory {
		private Log log = LogFactory.getLog(Memory.class);

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.WidgetMemory#createMnemonic()
		 */
		@Override
		public String createMnemonic() {
			log.enter("createMnemonic");

			String mnemonic = currentEntryId;
			
			log.debug("mnemonic: "+mnemonic);

			log.leave();
			return mnemonic;
		}
		
		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.WidgetMemory#remember(java.lang.String)
		 */
		@Override
		public Deferred<WidgetMemory> remember(String mnemonic) {
			log.enter("remember");

			Deferred<DataFileEntry> deferredEntry = setEntry(mnemonic);
			
			final WidgetMemory self = this;
			
			Deferred<WidgetMemory> deferredSelf = deferredEntry.adapt(new Function<DataFileEntry, WidgetMemory>() {

				public WidgetMemory apply(DataFileEntry in) {
					return self;
				}
			});

			log.leave();
			return deferredSelf;
		}

	}



}
