package org.cggh.chassis.wwarn.ui.curator.client;

import java.util.HashMap;
import java.util.Map;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;

import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;
import org.cggh.chassis.generic.widget.client.MapMemory;
import org.cggh.chassis.generic.widget.client.ObservableProperty;
import org.cggh.chassis.generic.widget.client.WidgetMemory;



import org.cggh.chassis.generic.widget.client.WidgetEvent;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;
import org.cggh.chassis.generic.widget.client.WidgetEventChannel;



import com.google.gwt.xml.client.Document;

import com.google.gwt.xml.client.Element;

import com.google.gwt.http.client.URL;

/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 * @author timp
 *
 */
public class ListStudyRevisionsWidget 
		extends DelegatingWidget<ListStudyRevisionsWidgetModel, ListStudyRevisionsWidgetRenderer> {

	private static final Log log = LogFactory.getLog(ListStudyRevisionsWidget.class);
	

	private ListStudyRevisionsWidgetController controller;

	public final ObservableProperty<String> studyUrl = new ObservableProperty<String>();

	public final ObservableProperty<Element> studyEntry = new ObservableProperty<Element>();
	public final ObservableProperty<Status> status = new ObservableProperty<Status>();

	public final ObservableProperty<String> message = new ObservableProperty<String>();

	public final WidgetEventChannel studyActionsListStudiesNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsViewStudyNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsViewStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsListStudyRevisionsNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsEditStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsUploadDataFilesWizardNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel currentStudyRevisionViewCurrentStudyNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel priorStudyRevisionsListViewRevisionNavigationEventChannel = new WidgetEventChannel(this);




	@Override
	protected ListStudyRevisionsWidgetModel createModel() {
		return new ListStudyRevisionsWidgetModel();
	}
	public String getStudyUrl() { 
		return model.studyUrl.get();
	} 
	public void setStudyUrl(String studyUrl) {
		model.studyUrl.set(studyUrl);
	}
	
	public Element getStudyEntry() { 
		return model.studyEntry.get();
	} 
	public void setStudyEntry(Element studyEntry) {
		model.studyEntry.set(studyEntry);
	}
	

	@Override
	protected ListStudyRevisionsWidgetRenderer createRenderer() {
		return new ListStudyRevisionsWidgetRenderer(this);
	}

	// Using init() rather than constructor because reset() uses init().
	public void init() {

		super.init();

		this.controller = new ListStudyRevisionsWidgetController(this, this.model);

		this.memory = new Memory();

	}
	
	@Override
	public void refresh() {
		log.enter("refresh");

		this.controller.retrieveStudyEntry();

		renderer.studySummaryWidgetUiField.refresh();


		renderer.studyRevisionListWidgetUiField.refresh();


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

		private static final String KEY = "studyUrl";


		@Override
		public Map<String, String> createMnemonicMap() {
			log.enter("createMnemonicMap");
			
			Map<String, String> map = new HashMap<String, String>();

			Element studyEntry = model.studyEntry.get();
			
			String url = null;
			
			if (studyEntry != null) { 
				
				url = AtomHelper.getEditLinkHrefAttr(studyEntry);
			
				if (url != null) {					
					map.put(KEY, URL.encodeComponent(url));
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
			
			String url = URL.decodeComponent(mnemonic.get(KEY));
			log.debug("found url: " + url);
			
			if (url != null) {
				
				log.debug("set url to:" + url);

				model.studyUrl.set(url);
				
				deferredMemory = refreshAndCallback().adapt(new Function<ChassisWidget, WidgetMemory>() {

					public WidgetMemory apply(ChassisWidget in) {
						log.debug("call back returning this memory");
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
