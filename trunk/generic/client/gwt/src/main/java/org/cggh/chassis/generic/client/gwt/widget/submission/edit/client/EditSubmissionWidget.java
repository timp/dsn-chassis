/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.edit.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.rewrite.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.ui.Composite;

/**
 * @author raok
 *
 */
public class EditSubmissionWidget extends Composite {
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private EditSubmissionWidgetModel model;
	private EditSubmissionWidgetController controller;
	private EditSubmissionWidgetDefaultRenderer renderer;
	private Set<EditSubmissionWidgetPubSubAPI> listeners = new HashSet<EditSubmissionWidgetPubSubAPI>(); 

	
	
	
	
	
	/**
	 * 
	 */
	public EditSubmissionWidget() {
		log.enter("<constructor>");
		
		this.model = new EditSubmissionWidgetModel();
		
		this.controller = new EditSubmissionWidgetController(this, this.model);
						
		this.renderer = new EditSubmissionWidgetDefaultRenderer(this.controller);
		
		this.model.addListener(this.renderer);
		
		this.initWidget(this.renderer.getCanvas());
		
		log.leave();
		
	}


	
	
	public void addEditSubmissionWidgetListener(EditSubmissionWidgetPubSubAPI listener) {
		listeners.add(listener);				
	}

	
	
	
	public void setSubmissionEntry(SubmissionEntry submissionEntryToEdit) {
		log.enter("setSubmissionEntry");
		
		log.debug("setting model on submission form with entry: "+submissionEntryToEdit.getTitle());

		this.renderer.getForm().setModel(submissionEntryToEdit);
		this.controller.ready();

		log.leave();
	}

	
	
	
	public void fireOnUserActionEditSubmissionCancelled() {
		for (EditSubmissionWidgetPubSubAPI listener : listeners) {
			listener.onUserActionCreateSubmissionCancelled();
		}
	}
	
	
	

	public void fireOnSubmissionUpdateSuccess(SubmissionEntry submissionEntry) {
		for (EditSubmissionWidgetPubSubAPI listener : listeners) {
			listener.onCreateSubmissionSuccess(submissionEntry);
		}
	}

	
	
	
	/**
	 * @param error
	 */
	public void fireOnSubmissionUpdateError(Throwable error) {
		for (EditSubmissionWidgetPubSubAPI listener : listeners) {
			listener.onCreateSubmissionError(error);
		}
	}




	/**
	 * 
	 */
	public void refreshStudies() {
		this.renderer.getForm().refreshStudies();
	}




	/**
	 * 
	 */
	public void render() {
		this.renderer.render();
	}





	
}
