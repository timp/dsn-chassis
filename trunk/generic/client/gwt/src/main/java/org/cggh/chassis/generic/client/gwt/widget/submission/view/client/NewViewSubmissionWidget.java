/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.format.impl.SubmissionFactoryImpl;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.AtomServiceImpl;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;
import org.cggh.chassis.generic.widget.client.ChassisWidget;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class NewViewSubmissionWidget extends ChassisWidget {

	
	
	
	private Log log;
	private NewViewSubmissionWidgetModel model;
	private NewViewSubmissionWidgetDefaultRenderer renderer;
	private SubmissionFactoryImpl submissionFactory;
	private AtomService service;

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#getName()
	 */
	@Override
	protected String getName() {
		return "viewSubmissionWidget";
	}
	
	
	


	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		log = LogFactory.getLog(NewViewSubmissionWidget.class); // instantiate here because called from superclass constructor
		log.enter("init");
		
		log.debug("instantiate helpers");
		this.submissionFactory = new SubmissionFactoryImpl();
		this.service = new AtomServiceImpl(this.submissionFactory);
		
		log.debug("instantiate a model");
		this.model = new NewViewSubmissionWidgetModel(this);
		
		log.debug("instantiate a renderer");
		this.renderer = new NewViewSubmissionWidgetDefaultRenderer(this);
		
		log.debug("set renderer canvas");
		this.renderer.setCanvas(this.contentBox);
		
		log.leave();
		
	}

	
	
	

	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#renderUI()
	 */
	@Override
	protected void renderUI() {
		log.enter("renderUI");
		
		// delegate this to renderer
		this.renderer.renderUI();
		
		log.leave();
		
	}
	
	
	


	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#bindUI()
	 */
	@Override
	protected void bindUI() {
		log.enter("bindUI");
		
		// delegate to renderer
		this.renderer.bindUI(this.model);
		
		log.leave();
		
	}
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#syncUI()
	 */
	@Override
	protected void syncUI() {
		log.enter("syncUI");
		
		// delegate to renderer
		this.renderer.syncUI();
		
		log.leave();
		
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#destroy()
	 */
	@Override
	public void destroy() {
		log.enter("destroy");
		
		// unbind
		this.unbindUI();

		// TODO anything else?
		
		log.leave();
		
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#unbindUI()
	 */
	@Override
	protected void unbindUI() {
		log.enter("unbindUI");
		
		// delegate to renderer
		if (this.renderer != null) this.renderer.unbindUI();
		
		
		log.leave();
		
	}

	
	
	
	/**
	 * Set the submission entry to display properties for.
	 * 
	 * @param entry
	 */
	public void setSubmissionEntry(SubmissionEntry entry) {
		log.enter("setSubmissionEntry");

		this.model.setSubmissionEntry(entry);
		this.model.setStatus(NewViewSubmissionWidgetModel.STATUS_READY);

		// should not need to do anything else, renderer will automatically
		// update UI on submission entry change
		
		log.leave();
	}





	public void retrieveSubmissionEntry(String submissionEntryUrl) {
		log.enter("retrieveSubmissionEntry");
		
		log.debug("retrieving entry: " + submissionEntryUrl);

		model.setStatus(NewViewSubmissionWidgetModel.STATUS_RETRIEVE_PENDING);
		
		//request submissionEntry
		Deferred<AtomEntry> deferred = service.getEntry(ConfigurationBean.getSubmissionFeedURL() + submissionEntryUrl);
		
		//add callbacks
		deferred.addCallback(new RetrieveSubmissionEntryCallback());
		deferred.addErrback(new RetrieveSubmissionEntryErrback());
		
		log.leave();
	}

	
	
	
	private class RetrieveSubmissionEntryCallback implements Function<SubmissionEntry,SubmissionEntry> {

		public SubmissionEntry apply(SubmissionEntry submissionEntry) {
			
			model.setSubmissionEntry(submissionEntry);
			model.setStatus(NewViewSubmissionWidgetModel.STATUS_READY);

			return submissionEntry;
		}
		
	}
	
	
	
	
	private class RetrieveSubmissionEntryErrback implements Function<Throwable,Throwable> {

		public Throwable apply(Throwable error) {
			model.setStatus(NewViewSubmissionWidgetModel.STATUS_ERROR);
			return error;
		}
		
	}
	
	
	
	/**
	 * Register interest in edit submission action events.
	 * 
	 * @param h handler to receive events
	 * @return a handler registration to remove the handler if needed
	 */
	public HandlerRegistration addEditSubmissionActionHandler(EditSubmissionActionHandler h) {
		return this.addHandler(h, EditSubmissionActionEvent.TYPE);
	}
	
	

	
	/**
	 * Register interest in upload data file action events.
	 * 
	 * @param h handler to receive events
	 * @return a handler registration to remove the handler if needed
	 */
	public HandlerRegistration addUploadDataFileActionHandler(UploadDataFileActionHandler h) {
		return this.addHandler(h, UploadDataFileActionEvent.TYPE);
	}
	
	

	
	
	
}
