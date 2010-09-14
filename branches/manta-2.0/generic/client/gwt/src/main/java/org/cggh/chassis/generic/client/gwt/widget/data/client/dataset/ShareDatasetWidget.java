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
public class ShareDatasetWidget 
	extends DelegatingWidget<ShareDatasetWidgetModel, ShareDatasetWidgetRenderer> {

	
	
	
	Log log = LogFactory.getLog(ShareDatasetWidget.class);
	private ShareDatasetWidgetController controller;
	
	

	
	@Override
	public void init() {
		super.init(); // will instantiate model and renderer
		
		this.controller = new ShareDatasetWidgetController(this, this.model);
		
		this.renderer.setController(controller);
		
		this.memory = new Memory();
	}
	
	
	

	/**
	 * @param id
	 */
	public void retrieveEntry(String id) {
		log.enter("retrieveEntry");
		
		this.controller.retrieveEntry(id);
		
		log.leave();
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected ShareDatasetWidgetModel createModel() {
		return new ShareDatasetWidgetModel();
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected ShareDatasetWidgetRenderer createRenderer() {
		return new ShareDatasetWidgetRenderer(this);
	}
	
	
	
	
	class Memory extends WidgetMemory {

		
		
		
		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.WidgetMemory#createMnemonic()
		 */
		@Override
		public String createMnemonic() {
			return model.getDatasetEntryId();
		}

		
		
		
		
		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.WidgetMemory#remember(java.lang.String)
		 */
		@Override
		public Deferred<WidgetMemory> remember(String mnemonic) {
			
			Deferred<DatasetEntry> deferredEntry = controller.retrieveEntry(mnemonic);
			
			Deferred<WidgetMemory> deferredSelf = deferredEntry.adapt(new Function<DatasetEntry, WidgetMemory>() {

				public WidgetMemory apply(DatasetEntry in) {
					return Memory.this;
				}
				
			});
			
			return deferredSelf;
		}
		
		
		
		
	}
	
	
	
	
	public HandlerRegistration addViewDatasetActionHandler(DatasetActionHandler h) {
		return this.addHandler(h, ViewDatasetActionEvent.TYPE);
	}




	public void fireViewDatasetActionEvent() {
		ViewDatasetActionEvent e = new ViewDatasetActionEvent();
		e.setEntry(model.getDatasetEntry());
		this.fireEvent(e);
	}


	
	
}
