package org.cggh.chassis.wwarn.ui.curator.client;

import java.util.HashMap;
import java.util.Map;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;
import org.cggh.chassis.generic.widget.client.MapMemory;
import org.cggh.chassis.generic.widget.client.ObservableProperty;
import org.cggh.chassis.generic.widget.client.WidgetMemory;


import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import com.google.gwt.xml.client.Element;

import org.cggh.chassis.generic.widget.client.WidgetEventChannel;


public class ViewStudyWidget 
	 	extends DelegatingWidget<ViewStudyWidgetModel, ViewStudyWidgetRenderer> {

	private static final Log log = LogFactory.getLog(ViewStudyWidget.class);
	

	public final WidgetEventChannel studySummaryViewStudyNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studySummaryEditStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studySummaryListStudyRevisionsNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studySummaryViewStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studySummaryListStudiesNavigationEventChannel = new WidgetEventChannel(this);
	
	private ViewStudyWidgetController controller;

	public final WidgetEventChannel viewStudyViewStudyNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel viewStudyEditStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel viewStudyListStudyRevisionsNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel viewStudyViewStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel viewStudyListStudiesNavigationEventChannel = new WidgetEventChannel(this);




	// public so as to be available to renderer 
	public StudySummaryWidget studySummaryWidget;


	// public so as to be available to renderer 
	public ViewStudyMetadataWidget viewStudyMetadataWidget;


	// public so as to be available to renderer 
	public ListSubmissionsWidget listSubmissionsWidget;


	// public so as to be available to renderer 
	public ListCurationsWidget listCurationsWidget;

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
	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		log.enter("init");
		
		super.init();
		

		this.controller = new ViewStudyWidgetController(this, this.model);
		this.renderer.setController(controller);
		this.memory = new Memory();

		log.leave();
	}
	
	@Override
	public void refresh() {
		log.enter("refresh");
		
		// TODO refresh this
		renderer.studySummaryWidgetUiField.refresh();
		
		log.leave();	
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
			
			Element study = model.studyEntry.get();
			
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
				
				model.studyID.set(studyID);
				
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
