package org.cggh.chassis.wwarn.ui.curator.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.MapMemory;
import org.cggh.chassis.generic.widget.client.MultiWidget;
import org.cggh.chassis.generic.widget.client.WidgetMemory;
import org.cggh.chassis.generic.widget.client.ObservableProperty;
import org.cggh.chassis.generic.widget.client.WidgetEventChannel;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;

/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 * @author timp
 *
 */
public class UploadDataFilesWizardWidget 
		extends MultiWidget {

	private static final Log log = LogFactory.getLog(UploadDataFilesWizardWidget.class);

	private UploadCuratedDataFilesWidget uploadCuratedDataFilesWidget;
	private SelectDerivationFilesWidget selectDerivationFilesWidget;
	private CurationSummaryWidget curationSummaryWidget;


	public final ObservableProperty<Status> status = new ObservableProperty<Status>();

	public final ObservableProperty<String> studyUrl = new ObservableProperty<String>();

	public final ObservableProperty<Element> studyEntry = new ObservableProperty<Element>();


	// UploadCuratedDataFiles
	public final WidgetEventChannel uploadCuratedDataFilesUploadCuratedDataFilesProceedFromStep1EventChannel = new WidgetEventChannel(this);

	// SelectDerivationFiles
	public final WidgetEventChannel selectDerivationFilesSelectDerivationFilesProceedFromStep2EventChannel = new WidgetEventChannel(this);

	// CurationSummary



	@Override
	public void refresh() {
		log.enter("refresh");
		this.uploadCuratedDataFilesWidget.refresh();
		log.leave();
	}

	@Override
	public void init() {
		super.init();
		this.memory = new Memory();
	}

	
	@Override
	public void renderMainChildren() {
		log.enter("renderMainChildren");

		this.uploadCuratedDataFilesWidget = new UploadCuratedDataFilesWidget();
		this.mainChildren.add(this.uploadCuratedDataFilesWidget);

		// Set default child
		this.defaultChild = this.uploadCuratedDataFilesWidget;

		this.selectDerivationFilesWidget = new SelectDerivationFilesWidget();
		this.mainChildren.add(this.selectDerivationFilesWidget);

		this.curationSummaryWidget = new CurationSummaryWidget();
		this.mainChildren.add(this.curationSummaryWidget);

		log.leave();

	}


	@Override
	public void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");
		super.registerHandlersForChildWidgetEvents();
		

		// UploadCuratedDataFiles events 
		log.debug("Adding UploadCuratedDataFiles>UploadCuratedDataFiles>ProceedFromStep1 event handler");
		this.childWidgetEventHandlerRegistrations.add(
				uploadCuratedDataFilesWidget.uploadCuratedDataFilesProceedFromStep1EventChannel.addHandler(
						new WidgetEventHandler<ProceedFromStep1Event>() {
			@Override
			public void onEvent(ProceedFromStep1Event e) {
				
				log.enter("onEvent(ProceedFromStep1Event)");

				setActiveChild(selectDerivationFilesWidget);
				
				log.leave();
			}
		}));



		// SelectDerivationFiles events 
		log.debug("Adding SelectDerivationFiles>SelectDerivationFiles>ProceedFromStep2 event handler");
		this.childWidgetEventHandlerRegistrations.add(
				selectDerivationFilesWidget.selectDerivationFilesProceedFromStep2EventChannel.addHandler(
						new WidgetEventHandler<ProceedFromStep2Event>() {
			@Override
			public void onEvent(ProceedFromStep2Event e) {
				
				log.enter("onEvent(ProceedFromStep2Event)");
				
				Element studyEntry =  e.getStudyEntry();
				log.debug("Setting studyEntry to " + studyEntry);

				curationSummaryWidget.studyEntry.set(studyEntry);
				

				setActiveChild(curationSummaryWidget);
				
				log.leave();
			}
		}));



		// CurationSummary events 
		log.leave();
	}



	@Override
	public void setActiveChild(Widget child, boolean memorise) {
		log.enter("setActiveChild");
		
		super.setActiveChild(child, memorise);
		
		// FIXME 
		// Only Delegating widgets refresh themselves
		if (child == this.uploadCuratedDataFilesWidget) {
			((ChassisWidget)child).refresh();
		}
		if (child == this.selectDerivationFilesWidget) {
			((ChassisWidget)child).refresh();
		}
		if (child == this.curationSummaryWidget) {
			((ChassisWidget)child).refresh();
		}
		log.leave();
	}

	
	
	private class Memory extends MapMemory {

		private static final String KEY = "studyUrl";


		@Override
		public Map<String, String> createMnemonicMap() {
			log.enter("createMnemonicMap");
			
			Map<String, String> map = new HashMap<String, String>();

			Element studyEntryElement = studyEntry.get();
			
			String url = null;
			
			if (studyEntryElement != null) { 
				
				url = AtomHelper.getEditLinkHrefAttr(studyEntryElement);
			
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
			
			status.set(AsyncWidgetModel.STATUS_INITIAL);
			
			String url = URL.decodeComponent(mnemonic.get(KEY));
			log.debug("found url: " + url);
			
			if (url != null) {
				
				log.debug("set url to:" + url);

				studyUrl.set(url);
				
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
