/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;
import org.cggh.chassis.generic.widget.client.WidgetMemory;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class ViewDatasetWidget 
	extends DelegatingWidget<ViewDatasetWidgetModel, ViewDatasetWidgetRenderer> {
	private Log log = LogFactory.getLog(ViewDatasetWidget.class);
	private ViewDatasetWidgetController controller;


	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected ViewDatasetWidgetModel createModel() {
		return new ViewDatasetWidgetModel();
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected ViewDatasetWidgetRenderer createRenderer() {
		return new ViewDatasetWidgetRenderer(this);
	}


	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		super.init(); // this will instantiate model and renderer
		
		this.controller = new ViewDatasetWidgetController(this, this.model);
		this.memory = new Memory();
		
		log.leave();
	}
	
	
	
	
	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(ViewDatasetWidget.class);
	}




	


	/**
	 * @param id
	 */
	public Deferred<DatasetEntry> viewEntry(String id) {

		// delegate to controller
		return this.controller.viewEntry(id);
		
	}



	
	public HandlerRegistration addEditDatasetActionHandler(DatasetActionHandler h) {
		return this.addHandler(h, EditDatasetActionEvent.TYPE);
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

			Deferred<DatasetEntry> deferredEntry = viewEntry(mnemonic);
			
			final WidgetMemory self = this;
			
			Deferred<WidgetMemory> deferredSelf = deferredEntry.adapt(new Function<DatasetEntry, WidgetMemory>() {

				public WidgetMemory apply(DatasetEntry in) {
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
