package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;
import org.cggh.chassis.generic.widget.client.WidgetMemory;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 *  @author timp 
 *  @since 04/12/2009
 */
public class ReviewSubmissionWidget 
    extends DelegatingWidget<ReviewSubmissionWidgetModel, ReviewSubmissionWidgetRenderer> {


	private ReviewSubmissionWidgetController controller;
	

	
	@Override
	public void init() {
		super.init();  // instantiates model and renderer
		this.controller = new ReviewSubmissionWidgetController(this, this.model);
		this.renderer.setController(controller);
		this.memory = new Memory();
	}

	@Override
	protected ReviewSubmissionWidgetModel createModel() {
		return new ReviewSubmissionWidgetModel();
	}

	@Override
	protected ReviewSubmissionWidgetRenderer createRenderer() {
		return new ReviewSubmissionWidgetRenderer(this);
	}
	
	
    // TODO Not called yet - delete ??	
	public SubmissionEntry retrieveSubmissionEntry() {
		return this.model.getSubmissionEntry();
	} 
	
	public HandlerRegistration addCreateReviewSuccessHandler(CreateReviewSuccessHandler h) { 
		return this.addHandler(h, CreateReviewSuccessEvent.TYPE);
	}

	public class Memory extends WidgetMemory {

		@Override
		public String createMnemonic() {
			//TODO this can be null !!
			return model.getSubmissionEntry().getId();
		}

		@Override
		public Deferred<WidgetMemory> remember(String mnemonic) {
			Deferred<SubmissionEntry> deferredEntry = controller.retreiveSubmissionEntry(mnemonic);
			Deferred<WidgetMemory> deferredSelf = deferredEntry.adapt(new Function<SubmissionEntry, WidgetMemory>() {

				public WidgetMemory apply(SubmissionEntry in) {
					return Memory.this;
				} 
				
			});
			return deferredSelf;
		}

	}
}
