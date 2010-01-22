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
public class ListSubmissionsWidget 
	extends DelegatingWidget<ListSubmissionsWidgetModel, ListSubmissionsWidgetRenderer> {

	
	
	
	
	Log log = LogFactory.getLog(ListSubmissionsWidget.class);
	private ListSubmissionsWidgetController controller;
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected ListSubmissionsWidgetModel createModel() {
		return new ListSubmissionsWidgetModel();
	}
	
	
	protected ListSubmissionsWidgetModel getModel() {
		return model;
	}

	
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected ListSubmissionsWidgetRenderer createRenderer() {
		return new ListSubmissionsWidgetRenderer(this);
	}
	
	
	
	
	@Override
	public void init() {
		
		super.init(); // this should instantiate model and renderer
		
		this.controller = new ListSubmissionsWidgetController(this, this.model);

		this.memory = new Memory();
		
	}

	
	
	
	
	@Override
	public void refresh() {
		// delegate to controller
		this.controller.refreshSubmissions();
		
	}
	
	
	
	
	public HandlerRegistration addViewSubmissionActionHandler(SubmissionActionHandler h) {
		return this.addHandler(h, ViewSubmissionActionEvent.TYPE);
	}

	
	public class Memory extends WidgetMemory {

		@Override
		public String createMnemonic() {
			if (model.getFilterByReviewExistance() == null) {
				return "all";
			} else if (model.getFilterByReviewExistance().booleanValue()) { 
				return "accepted";
			} else { 
				return "pending";
			}
		}

		@Override
		public Deferred<WidgetMemory> remember(String mnemonic) {
			log.enter("remember");
			log.debug("Mnemonic:" + mnemonic);
			if (mnemonic.equals("all"))
				model.setFilterByReviewExistance(null); // noop
			else if (mnemonic.equals("accepted"))
				model.setFilterByReviewExistance(Boolean.TRUE);
			else if (mnemonic.equals("pending"))
				model.setFilterByReviewExistance(Boolean.FALSE);
			else { 
				model.setFilterByReviewExistance(null);
				log.debug("Defaulting to all");
			}
			

            // The work has been done but we cannot return null
			Deferred<SubmissionEntry> unusedDeferredEntry = new Deferred<SubmissionEntry>();
			
			Deferred<WidgetMemory> deferredSelf = unusedDeferredEntry.adapt(new Function<SubmissionEntry, WidgetMemory>() {

				public WidgetMemory apply(SubmissionEntry in) {
					return Memory.this;
				}
				
			});
			
			log.leave();
			return deferredSelf;
		}
	}
	
}
