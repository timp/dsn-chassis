/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;
import org.cggh.chassis.generic.widget.client.WidgetMemory;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class SelectCuratorWidget 
	extends DelegatingWidget<SelectCuratorWidgetModel, SelectCuratorWidgetRenderer> {

	
	
	private Log log = LogFactory.getLog(SelectCuratorWidget.class);
	private SelectCuratorWidgetController controller;
	
	
	
	@Override
	protected SelectCuratorWidgetModel createModel() {
		return new SelectCuratorWidgetModel();
	}

	
	
	
	@Override
	protected SelectCuratorWidgetRenderer createRenderer() {
		return new SelectCuratorWidgetRenderer();
	}

	
	
	
	@Override
	public void init() {
		
		super.init(); // will instantiate the model and renderer
		
		this.controller = new SelectCuratorWidgetController(this, this.model);
		this.renderer.setController(this.controller);
		
		this.memory = new Memory();
	}



	
	public void retrieveSubmissionEntry(String entryUrl) {
		log.enter("retrieveSubmissionEntry");
		
		this.controller.retrieveSubmissionEntry(entryUrl);

		log.leave();
	}
	
	
	public HandlerRegistration addUpdateSubmissionSuccessHandler(UpdateSubmissionSuccessHandler h) {
		return this.addHandler(h, UpdateSubmissionSuccessEvent.TYPE);
	}

	
	
	
	class Memory extends WidgetMemory {

		
		
		
		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.WidgetMemory#createMnemonic()
		 */
		@Override
		public String createMnemonic() {
			return model.getSubmissionEntryUrl();
		}

		
		
		
		
		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.WidgetMemory#remember(java.lang.String)
		 */
		@Override
		public Deferred<WidgetMemory> remember(String mnemonic) {
			
			Deferred<SubmissionEntry> deferredEntry = controller.retrieveSubmissionEntry(mnemonic);
			
			Deferred<WidgetMemory> deferredSelf = deferredEntry.adapt(new Function<SubmissionEntry, WidgetMemory>() {

				public WidgetMemory apply(SubmissionEntry in) {
					return Memory.this;
				}
				
			});
			
			return deferredSelf;
		}
		
		
		
		
	}
	

}
