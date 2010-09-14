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
public class ListStudiesWidget 
		extends DelegatingWidget<ListStudiesWidgetModel, ListStudiesWidgetRenderer> {

	private static final Log log = LogFactory.getLog(ListStudiesWidget.class);
	

	private ListStudiesWidgetController controller;

	public final ObservableProperty<Document> studyFeed = new ObservableProperty<Document>();
	public final ObservableProperty<Status> status = new ObservableProperty<Status>();

	public final ObservableProperty<String> message = new ObservableProperty<String>();

	public final WidgetEventChannel listStudiesViewStudyNavigationEventChannel = new WidgetEventChannel(this);




	@Override
	protected ListStudiesWidgetModel createModel() {
		return new ListStudiesWidgetModel();
	}
	public Document getStudyFeed() { 
		return model.studyFeed.get();
	} 
	public void setStudyFeed(Document studyFeed) {
		model.studyFeed.set(studyFeed);
	}
	

	@Override
	protected ListStudiesWidgetRenderer createRenderer() {
		return new ListStudiesWidgetRenderer(this);
	}

	// Using init() rather than constructor because reset() uses init().
	public void init() {

		super.init();

		this.controller = new ListStudiesWidgetController(this, this.model);

		this.memory = new Memory();

	}
	
	@Override
	public void refresh() {
		log.enter("refresh");

		this.controller.retrieveStudyFeed();

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

		@Override
		public Map<String, String> createMnemonicMap() {
			log.enter("createMnemonicMap");
			
			Map<String, String> map = new HashMap<String, String>();

			log.leave();
			return map;
		}

		@Override
		public Deferred<WidgetMemory> remember(Map<String, String> mnemonic) {
			log.enter("remember");
			
			log.debug("call back immediately");
			final Deferred<WidgetMemory> deferredMemory = new Deferred<WidgetMemory>();
			deferredMemory.callback(this);
		
			log.leave();
			return deferredMemory;
		}


	}

	
}
