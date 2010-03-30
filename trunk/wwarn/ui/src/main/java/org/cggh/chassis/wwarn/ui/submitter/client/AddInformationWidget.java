package org.cggh.chassis.wwarn.ui.submitter.client;

import java.util.HashMap;
import java.util.Map;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;
import org.cggh.chassis.generic.widget.client.MapMemory;
import org.cggh.chassis.generic.widget.client.WidgetMemory;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.http.client.URL;





public class AddInformationWidget extends DelegatingWidget<AddInformationWidgetModel, AddInformationWidgetRenderer> {

	
	
	private AddInformationWidgetController controller;
	private AddInformationWidgetView view;




	@Override
	protected AddInformationWidgetModel createModel() {
		return new AddInformationWidgetModel();
	}


	
	
	@Override
	protected AddInformationWidgetRenderer createRenderer() {
		return new AddInformationWidgetRenderer();
	}
	
	
	
	@Override
	public void init() {
		super.init();
		
		controller = new AddInformationWidgetController(this, model);
		view = new AddInformationWidgetView(controller);
		renderer.setView(view);
		memory = new Memory();
		
	}
	
	
	
	
	public HandlerRegistration addHomeNavigationEventHandler(HomeNavigationHandler h) {
		return this.addHandler(h, HomeNavigationEvent.TYPE);
	}

	
	
	public void setSubmission(String id) {
		model.submissionId.set(id);
	}
	
	
	
	@Override
	public void refresh() {
		refreshAndCallback();
	}
	
	
	
	
	@Override
	public Deferred<ChassisWidget> refreshAndCallback() {
		return controller.refreshAndCallback();
	}
	
	
	

	private class Memory extends MapMemory {

		
		private Log log = LogFactory.getLog(Memory.class);
		
		private static final String KEY_SUBMISSION = "submission";
		
		
		
		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.MapMemory#createMnemonicMap()
		 */
		@Override
		public Map<String, String> createMnemonicMap() {
			log.enter("createMnemonicMap");
			
			Map<String, String> map = new HashMap<String, String>();
			
			String submissionId = model.submissionId.get();
			
			if (submissionId != null) {
				map.put(KEY_SUBMISSION, URL.encodeComponent(submissionId));
			}
			
			log.leave();
			return map;
		}

		
		
		
		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.MapMemory#remember(java.util.Map)
		 */
		@Override
		public Deferred<WidgetMemory> remember(Map<String, String> mnemonic) {
			log.enter("remember");
			
			Deferred<WidgetMemory> deferredMemory;
			
			model.status.set(AsyncWidgetModel.STATUS_INITIAL);
			
			String submissionId = mnemonic.get(KEY_SUBMISSION);
			
			log.debug("found submissionId: "+submissionId);
			
			if (submissionId != null) {
				
				log.debug("set selected submission id");
				setSubmission(URL.decodeComponent(submissionId));
				
				log.debug("refresh and call back");
				deferredMemory = refreshAndCallback().adapt(new Function<ChassisWidget, WidgetMemory>() {

					public WidgetMemory apply(ChassisWidget in) {
						return Memory.this;
					}
					
				});
			}
			
			else {
				
				log.debug("call back immediately");
				deferredMemory = new Deferred<WidgetMemory>();
				deferredMemory.callback(this);
				
			}
			
			log.leave();
			return deferredMemory;
		}
		
		
		
		
	}





}
