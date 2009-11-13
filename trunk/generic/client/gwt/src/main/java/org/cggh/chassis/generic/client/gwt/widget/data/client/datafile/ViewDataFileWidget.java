/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
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
	
	
	
	
	
	
	private Log log;
	private ViewDataFileWidgetController controller;


	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected ViewDataFileWidgetModel createModel() {
		return new ViewDataFileWidgetModel();
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected ViewDataFileWidgetRenderer createRenderer() {
		return new ViewDataFileWidgetRenderer(this);
	}


	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		super.init(); // this will instantiate model and renderer
		
		this.controller = new ViewDataFileWidgetController(this, this.model);
		this.memory = new Memory();

		log.leave();
	}
	
	
	

	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(ViewDataFileWidget.class);
	}




	/**
	 * @param dataFileEntry
	 */
	public Deferred<DataFileEntry> viewEntry(String id) {
		log.enter("viewEntry");

		// delegate to controller
		Deferred<DataFileEntry> def = this.controller.viewEntry(id);
		
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

			// use current entry id as mnemonic
			String mnemonic = model.getCurrentEntryId();
			
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

			Deferred<DataFileEntry> deferredEntry = viewEntry(mnemonic);
			
			final WidgetMemory self = this;
			
			Deferred<WidgetMemory> deferredSelf = deferredEntry.adapt(new Function<DataFileEntry, WidgetMemory>() {

				public WidgetMemory apply(DataFileEntry in) {
					// when async operation is complete, return self
					return self;
				}
			});
			
			// actually, async operation here doesn't have any impact on 
			// memory child, so could callback with self immediately - 
			// however, will leave as is for now
			
			log.leave();
			return deferredSelf;
		}

	}




}
