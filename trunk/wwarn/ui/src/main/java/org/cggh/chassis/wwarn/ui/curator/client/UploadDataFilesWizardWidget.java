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
import org.cggh.chassis.generic.widget.client.ObservableProperty;
import org.cggh.chassis.generic.widget.client.PropertyChangeEvent;
import org.cggh.chassis.generic.widget.client.PropertyChangeHandler;
import org.cggh.chassis.generic.widget.client.WidgetEventChannel;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;
import org.cggh.chassis.generic.widget.client.WidgetMemory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;

/**
 * Manual refresh.
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



	
	
	@Override
	public void bindUI() {
		super.bindUI();
		studyUrl.addChangeHandler(new PropertyChangeHandler<String>() {
			public void onChange(PropertyChangeEvent<String> e) {
				log.enter("onchange(studyUrl)");
				log.leave();
			}
		});
		studyEntry.addChangeHandler(new PropertyChangeHandler<Element>() {
			public void onChange(PropertyChangeEvent<Element> e) {
				log.enter("onchange(studyEntry)");
				uploadCuratedDataFilesWidget.studyEntry.set(e.getAfter());
				// TODO Does it need one?
				//selectDerivationFilesWidget.studyEntry.set(e.getAfter());
				curationSummaryWidget.studyEntry.set(e.getAfter());
				log.leave();
			}
		});
	}


	@Override
	public void refresh() {
		this.uploadCuratedDataFilesWidget.refresh();
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
		
		//NoUploadCuratedDataFiles events 
		//NoSelectDerivationFiles events 
		//NoCurationSummary events 
		log.leave();
	}


	@Override
	public void setActiveChild(Widget child, boolean memorise) {
		log.enter("setActiveChild");
		
		super.setActiveChild(child, memorise);
		
		// Most widgets will refresh themselves, but the uploadCuratedDataFilesWidget will not 
		if (child == this.uploadCuratedDataFilesWidget) {
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

			Element entry = studyEntry.get();
			
			String url = null;
			
			if (entry != null) { 
				
				url = AtomHelper.getEditLinkHrefAttr(entry);
			
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
