package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;
import org.cggh.chassis.generic.widget.client.WidgetEventChannel;
import org.cggh.chassis.generic.widget.client.WidgetMemory;

/**
 * When told to refresh itself, the SelectStudyWidget makes a GET request to the Studies Query Service URL, 
 * to retrieve a feed of all study entries owned by the user. 
 * 
 * If the feed is empty (no studies created yet) 
 *   only the create option is displayed
 * else 
 *   the option to select a previously created study is displayed
 * 
 * 
 * @author timp
 * @since 13/01/10
 *
 */
public class SelectStudyWidget 
		extends DelegatingWidget<SelectStudyWidgetModel, SelectStudyWidgetRenderer> {



	private Log log = LogFactory.getLog(SelectStudyWidget.class);
	
	
	
	private SelectStudyWidgetController controller;

	
	@Override
	public void init() { 
		super.init(); // instantiates model and renederer
		this.controller = new SelectStudyWidgetController(this, this.model);
		this.renderer.setController(this.controller);
		this.memory = new Memory();		 
	}
	
	@Override
	protected SelectStudyWidgetModel createModel() {
		return new SelectStudyWidgetModel();
	}

	public SelectStudyWidgetModel getModel() {
		return model;
	}

	
	@Override
	protected SelectStudyWidgetRenderer createRenderer() {
		return new SelectStudyWidgetRenderer(this);
	}

	public  SelectStudyWidgetRenderer getRenderer() {
		return renderer;
	}


	
	
	
	
	public String getSelectedStudyId() { 
		return model.getSelectedStudyId();
	}
	public void setSelectedStudy(String selectedStudyId) {
		model.setSelectedStudy(selectedStudyId);
	}


	
	
	@Override
	public void refresh() {
		this.controller.retrieveStudies();
	}

	public final WidgetEventChannel proceed = new WidgetEventChannel(this);
	
	





	







	private class Memory extends WidgetMemory {

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.WidgetMemory#createMnemonic()
		 */
		@Override
		public String createMnemonic() {
			return model.getSelectedStudyId();
		}

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.WidgetMemory#remember(java.lang.String)
		 */
		@Override
		public Deferred<WidgetMemory> remember(String mnemonic) {
			log.enter("remember");
			
			Deferred<WidgetMemory> deferredMemory;
			
			model.setStatus(AsyncWidgetModel.STATUS_INITIAL);
			
			String studyId = mnemonic;
			
			log.debug("found studyId: "+studyId);
			
			if (studyId != null) {
				
				log.debug("set selected study id");
				setSelectedStudy(studyId);
				
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
