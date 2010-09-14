package org.cggh.chassis.wwarn.ui.anonymizer.client;

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
import org.cggh.chassis.wwarn.ui.anonymizer.client.BackToStartNavigationEvent;
import org.cggh.chassis.wwarn.ui.anonymizer.client.BackToStartNavigationHandler;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.http.client.URL;
import com.google.gwt.xml.client.Element;


/**
 * @author lee
 *
 */
public class ReviewFileWidget 
	extends DelegatingWidget<ReviewFileWidgetModel, ReviewFileWidgetRenderer> {

	private static final Log log = LogFactory.getLog(ReviewFileWidget.class);
	
	private ReviewFileWidgetController controller;
	
	@Override
	protected ReviewFileWidgetModel createModel() {
		return new ReviewFileWidgetModel();
	}

	public ReviewFileWidgetModel getModel() {
		return model;
	}
	

	@Override
	protected ReviewFileWidgetRenderer createRenderer() {
		return new ReviewFileWidgetRenderer(this);
	}

	public ReviewFileWidget() {
		super();
		this.controller = new ReviewFileWidgetController(this, this.model);
		this.renderer.setController(this.controller);
		this.memory = new Memory();
	}	
	
	public void init() {

		log.enter("init");		
		
		super.init();

		log.leave();
	}
	
	@Override
	public void refresh() {
		
		log.enter("refresh");
		
		
		log.leave();
	}
	
	public HandlerRegistration addBackToStartNavigationHandler(BackToStartNavigationHandler h) {
		return this.addHandler(h, BackToStartNavigationEvent.TYPE);
	}

	public void setFileToBeReviewedEntryElement(Element fileToBeReviewedEntryElement) {
		
		log.debug("Setting file to be reviewed....");
		
		model.setFileToBeReviewedEntryElement(fileToBeReviewedEntryElement);
	}

	public Deferred<Element> retrieveFileToBeReviewedEntryElement() {
		
		log.enter("retrieveFileToBeReviewed");
		
		Deferred<Element> deferredElement = this.controller.retrieveFileToBeReviewedEntryElement();
		
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

		private static final String KEY_MEDIA = "media";


		@Override
		public Map<String, String> createMnemonicMap() {
			log.enter("createMnemonicMap");
			
			Map<String, String> map = new HashMap<String, String>();
			
			Element fileToBeReviewed = model.fileToBeReviewedEntryElement.get();
			
			String fileToBeReviewedID = null;
			
			if (fileToBeReviewed != null) { 
				
				fileToBeReviewedID = AtomHelper.getId(fileToBeReviewed);
			
				if (fileToBeReviewedID != null) {
					
					map.put(KEY_MEDIA, URL.encodeComponent(fileToBeReviewedID));
				}
				
			}
			
			log.leave();
			return map;
		}

		@Override
		public Deferred<WidgetMemory> remember(Map<String, String> mnemonic) {
			log.enter("remember");
			
			final Deferred<WidgetMemory> deferredMemory;
			
			model.setStatus(AsyncWidgetModel.STATUS_INITIAL);
			
			String fileToBeReviewedID = mnemonic.get(KEY_MEDIA);
			
			log.debug("found mediaId: " + fileToBeReviewedID);
			
			if (fileToBeReviewedID != null) {
				
				log.debug("set selected media id to:" + fileToBeReviewedID);
				
				setFileToBeReviewedID(URL.decodeComponent(fileToBeReviewedID));
				
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

		private void setFileToBeReviewedID(String fileToBeReviewedID) {
			
			model.setFileToBeReviewedID(fileToBeReviewedID);
			
		}

	}	

}
