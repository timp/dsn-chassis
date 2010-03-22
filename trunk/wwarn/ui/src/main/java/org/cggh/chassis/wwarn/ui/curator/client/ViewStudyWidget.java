/**
 * 
 */
package org.cggh.chassis.wwarn.ui.curator.client;

import java.util.HashMap;
import java.util.Map;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;
import org.cggh.chassis.generic.widget.client.MapMemory;
import org.cggh.chassis.generic.widget.client.WidgetMemory;


import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.xml.client.Element;


public class ViewStudyWidget 
	 	extends DelegatingWidget<ViewStudyWidgetModel, ViewStudyWidgetRenderer> {

	private static final Log log = LogFactory.getLog(ViewStudyWidget.class);
	
	private ViewStudyWidgetController controller;


	private StudySummaryWidget studySummaryWidget;
//	private ViewStudyMetadataWidget viewStudyMetadataWidget;
//	private ListSubmissionsWidget listSubmissionsWidget;
//	private ListCurationsWidget listCurationsWidget;

	@Override
	protected ViewStudyWidgetModel createModel() {
		return new ViewStudyWidgetModel();
	}

	public ViewStudyWidgetModel getModel() {
		return model;
	}

	@Override
	protected ViewStudyWidgetRenderer createRenderer() {
		return new ViewStudyWidgetRenderer(this);
	}

	public ViewStudyWidget() {
		super();
		this.controller = new ViewStudyWidgetController(this, this.model);
		this.renderer.setController(this.controller);
		this.memory = new Memory();
		this.studySummaryWidget = new StudySummaryWidget();
	}		
	
	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		log.enter("init");
		
		super.init();
		
		this.controller = new ViewStudyWidgetController(this, this.model);
		

		log.leave();
	}
	
	@Override
	public void refresh() {
		
		log.enter("refresh");
		
		studySummaryWidget.refresh();
		
		log.leave();		
		
	}
	
	/*
	 * TODO 
	public HandlerRegistration addListStudiesNavigationHandler(ListStudiesNavigationHandler h) {
		return this.addHandler(h, ListStudiesNavigationEvent.TYPE);
	}	
	*/
	public void setStudy(Element study) {
		
		log.debug("Setting study...." + study);
		
		model.setStudyEntryElement(study);
		studySummaryWidget.setStudyEntry(study);
	}
	
	public Deferred<Element> retrieveStudy() {
		
		log.enter("retrieveStudy");
		
		Deferred<Element> deferredElement = this.controller.retrieveStudy();
		
		log.leave();
		
		return deferredElement;
		
	}	
	
	@Override
	public Deferred<ChassisWidget> refreshAndCallback() {
		
		log.enter("refreshAndCallback");
		
		Deferred<ChassisWidget> deferredSelf = this.controller.refreshAndCallback();
		
		log.leave();
		return deferredSelf;
	}
	
	
	private class Memory extends MapMemory {

		private static final String KEY_STUDYID = "studyid";


		@Override
		public Map<String, String> createMnemonicMap() {
			log.enter("createMnemonicMap");
			
			Map<String, String> map = new HashMap<String, String>();
			
			Element study = model.studyEntryElement.get();
			
			String studyID = null;
			
			if (study != null) { 
				
				studyID = AtomHelper.getId(study);
			
				if (studyID != null) {
					
					map.put(KEY_STUDYID, studyID);
				}
				
			}
			
			log.leave();
			return map;
		}

		@Override
		public Deferred<WidgetMemory> remember(Map<String, String> mnemonic) {
			log.enter("remember");
			
			final Deferred<WidgetMemory> deferredMemory;
			
			model.status.set(AsyncWidgetModel.STATUS_INITIAL);
			
			String studyID = mnemonic.get(KEY_STUDYID);
			
			log.debug("found studyId: " + studyID);
			
			if (studyID != null) {
				
				log.debug("set selected study id to:" + studyID);
				
				setStudyID(studyID);
				
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

		private void setStudyID(String studyID) {
			
			model.setStudyID(studyID);
			
		}

	}


	public void setStudyID(String studyID) {
		model.setStudyID(studyID);
		
	}	



	
	

	

}
