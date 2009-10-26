/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.create.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionWidgetModel;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.ui.Composite;

/**
 * @author raok
 *
 */
public class CreateSubmissionWidget extends Composite {
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	
	
	
	
	private CreateSubmissionWidgetModel model;
	private CreateSubmissionWidgetController controller; 
	final private CreateSubmissionWidgetDefaultRenderer renderer;
	private Set<CreateSubmissionWidgetPubSubAPI> listeners = new HashSet<CreateSubmissionWidgetPubSubAPI>();

	
	
	
	/**
	 * Construct a widget.
	 * 
	 * @param canvas
	 * @param authorEmail
	 */
	public CreateSubmissionWidget() {
		log.enter("<constructor>");
		
		this.model = new CreateSubmissionWidgetModel();
		
		this.controller = new CreateSubmissionWidgetController(this, this.model);
						
		this.renderer = new CreateSubmissionWidgetDefaultRenderer(this.controller);
		
		this.model.addListener(this.renderer);
		
		this.initWidget(this.renderer.getCanvas());
		
		this.controller.ready();
		
		log.leave();
	}
	
	
	
	
	
	public void fireOnUserActionCreateNewSubmissionCancelled() {
		for (CreateSubmissionWidgetPubSubAPI listener : listeners) {
			listener.onUserActionCreateNewSubmissionCancelled();
		}
	}
	
	
	

	public void fireOnNewSubmissionSaveSuccess(SubmissionEntry submissionEntry) {
		for (CreateSubmissionWidgetPubSubAPI listener : listeners) {
			listener.onNewSubmissionSaveSuccess(submissionEntry);
		}
	}

	
	
	
	public void addCreateSubmissionWidgetListener(CreateSubmissionWidgetPubSubAPI listener) {
		listeners.add(listener);				
	}





	/**
	 * @param error
	 */
	public void fireOnNewSubmissionSaveError(Throwable error) {
		for (CreateSubmissionWidgetPubSubAPI listener : listeners) {
			listener.onNewSubmissionSaveError(error);
		}
	}





}
