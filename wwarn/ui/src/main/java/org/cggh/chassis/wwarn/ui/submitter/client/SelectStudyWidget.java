package org.cggh.chassis.wwarn.ui.submitter.client;

import java.util.Map;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;
import org.cggh.chassis.generic.widget.client.WidgetMemory;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * When told to refresh itself, the SelectStudyWidget makes a GET request to the Studies Query Service URL, 
 * to retrieve a feed of all study entries owned by the user. 
 * 
 * Depending upon whether the feed is empty (no studies created yet) or not the widget is rendered differently.
 * 
 * @author timp
 * @since 13/01/10
 *
 */
public class SelectStudyWidget 
		extends DelegatingWidget<SelectStudyWidgetModel, SelectStudyWidgetRenderer> {



	private Log log = LogFactory.getLog(SelectStudyWidget.class);
	
	
	
	private Map<String,String> studyLinksToTitles;



	private SelectStudyWidgetController controller;

	
	@Override
	public void init() { 
		super.init(); // instantiates model and renederer
		this.controller = new SelectStudyWidgetController(this, this.model);
		this.memory = new Memory();		 
	}
	
	@Override
	protected SelectStudyWidgetModel createModel() {
		return new SelectStudyWidgetModel();
	}



	@Override
	protected SelectStudyWidgetRenderer createRenderer() {
		return new SelectStudyWidgetRenderer(this);
	}

	public String getSelectedStudyId() { 
		return model.getSelectedStudyId();
	}
/*
	@Override
	public void bindUI() {
		
		HandlerRegistration a = this.proceedButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				ProceedActionEvent e = new ProceedActionEvent();
				fireEvent(e);
			}
		});
		
		this.childWidgetEventHandlerRegistrations.add(a);

	}
	*/
	
	
	
	@Override
	public void refresh() {
		this.controller.retrieveStudies();
	}

	
	
	public HandlerRegistration addProceedActionHandler(ProceedActionHandler h) {
		return this.addHandler(h, ProceedActionEvent.TYPE);
	}






	







	private class Memory extends WidgetMemory {

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.WidgetMemory#createMnemonic()
		 */
		@Override
		public String createMnemonic() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.WidgetMemory#remember(java.lang.String)
		 */
		@Override
		public Deferred<WidgetMemory> remember(String mnemonic) {
			// TODO Auto-generated method stub
			return null;
		}

	}




	public  SelectStudyWidgetRenderer getRenderer() {
		return renderer;
	}
	
}
