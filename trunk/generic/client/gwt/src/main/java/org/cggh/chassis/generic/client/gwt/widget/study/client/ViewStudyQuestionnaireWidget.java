/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xquestion.client.XQuestionnaire;

import com.google.gwt.user.client.ui.Composite;


/**
 * @author aliman
 *
 */
public class ViewStudyQuestionnaireWidget extends Composite {

	
	
	
	private Log log = LogFactory.getLog(this.getClass());

	XQuestionnaire questionnaire;

	StudyQuestionnaireWidgetModel model;
	private StudyQuestionnaireWidgetController controller;
	private ViewStudyQuestionnaireWidgetDefaultRenderer renderer;
	private Set<PubSubAPI> listeners = new HashSet<PubSubAPI>();


	




	public ViewStudyQuestionnaireWidget() {
		log.enter("<constructor>");
		
		this.model = new StudyQuestionnaireWidgetModel();
		this.controller = new StudyQuestionnaireWidgetController(this.model);
		this.renderer = new ViewStudyQuestionnaireWidgetDefaultRenderer(this);
		this.model.addListener(this.renderer);
		
		this.initWidget(this.renderer.getCanvas());
	
		Deferred<XQuestionnaire> d = this.controller.loadQuestionnaire();
		d.addCallback(new Function<XQuestionnaire, XQuestionnaire>() {

			public XQuestionnaire apply(XQuestionnaire in) {
				questionnaire = in;
				model.setStatus(StudyQuestionnaireWidgetModel.STATUS_READY);
				return in;
			}

		});
		
		log.leave();
	}
	
	
	
	
	
	public void addListener(PubSubAPI listener) {
		this.listeners.add(listener);
	}

	
	
	
	public void setEntry(StudyEntry entry) {
		this.controller.setEntry(entry, true);
	}
	
	
	
	
	public static interface PubSubAPI {
	
		public void onUserActionEditStudyQuestionnaire(StudyEntry entry);
		public void onUserActionViewStudy(StudyEntry entry);
		
	}
	
	
	
	
	void fireOnUserActionEditStudyQuestionnaire() {
		for (PubSubAPI listener : listeners) {
			listener.onUserActionEditStudyQuestionnaire(this.model.getEntry());
		}
	}
	
	
	
	void fireOnUserActionViewStudy() {
		for (PubSubAPI listener : listeners) {
			listener.onUserActionViewStudy(this.model.getEntry());
		}
	}
	
	
}
