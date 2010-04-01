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





public class SubmitWidget extends DelegatingWidget<SubmitWidgetModel, SubmitWidgetRenderer> {

	
	
	
	private Log log = LogFactory.getLog(SubmitWidget.class);
	private SubmitWidgetController controller;
	
	
	
	
	@Override
	protected SubmitWidgetModel createModel() {
		return new SubmitWidgetModel();
	}




	@Override
	protected SubmitWidgetRenderer createRenderer() {
		return new SubmitWidgetRenderer();
	}
	
	
	
	
	public SubmitWidget() {
		super();
		this.controller = new SubmitWidgetController(this, this.model);
		this.renderer.setController(this.controller);
		this.memory = new Memory();
	}
	
	
	
	
	public void setSelectedStudy(String id) {
		this.model.setSelectedStudyId(id);
	}
	
	
	
	public String getSelectedStudyId() {
		return this.model.getSelectedStudyId();
	}
	
	
	

	
	@Override
	public void refresh() {
		refreshAndCallback();
	}
	
	
	
	@Override
	public Deferred<ChassisWidget> refreshAndCallback() {
		log.enter("refreshAndCallback");
		
		// delegate to controller
		Deferred<ChassisWidget> deferredSelf = this.controller.refreshAndCallback();
		
		return deferredSelf;
	}




	public HandlerRegistration addProceedActionHandler(ProceedActionHandler h) {
		return this.addHandler(h, ProceedActionEvent.TYPE);
	}
	
	
	
	
	public HandlerRegistration addStepBackNavigationHandler(StepBackNavigationHandler h) {
		return this.addHandler(h, StepBackNavigationEvent.TYPE);
	}
	
	
	

	public HandlerRegistration addBackToStartNavigationHandler(BackToStartNavigationHandler h) {
		return this.addHandler(h, BackToStartNavigationEvent.TYPE);
	}
	
	
	

	
	private class Memory extends MapMemory {

		
		
		private static final String KEY = "study";
		
		
		
		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.MapMemory#createMnemonicMap()
		 */
		@Override
		public Map<String, String> createMnemonicMap() {
			log.enter("createMnemonicMap");
			
			Map<String, String> map = new HashMap<String, String>();
			
			String selectedStudyId = model.getSelectedStudyId();
			
			if (selectedStudyId != null) {
				map.put(KEY, URL.encodeComponent(selectedStudyId));
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
			
			model.setStatus(AsyncWidgetModel.STATUS_INITIAL);
			
			String studyId = mnemonic.get(KEY);
			
			log.debug("found studyId: "+studyId);
			
			if (studyId != null) {
				
				log.debug("set selected study id");
				setSelectedStudy(URL.decodeComponent(studyId));
				
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





	/**
	 * @return
	 */
	public String getSubmissionId() {
		log.enter("getSubmissionId");
		
		String submissionId = model.getSubmissionId();
		
		log.leave();
		return submissionId;
	}




	public SubmitWidgetModel getModel() {
		return model;
	}





}
