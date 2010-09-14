/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client.sq;



import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyFeed;
import org.cggh.chassis.generic.atomext.client.study.StudyQuery;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidget;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetMemory;
import org.cggh.chassis.generic.client.gwt.widget.study.client.EditStudyActionEvent;
import org.cggh.chassis.generic.client.gwt.widget.study.client.EditStudyQuestionnaireActionEvent;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.study.client.ViewStudyActionEvent;
import org.cggh.chassis.generic.client.gwt.widget.study.client.ViewStudyQuestionnaireActionEvent;
import org.cggh.chassis.generic.xquestion.client.XQuestionnaire;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author raok
 *
 */
public class ViewStudyQuestionnaireWidget 
	extends AtomCrudWidget<StudyEntry, StudyFeed, StudyQuery, StudyQuestionnaireWidgetModel, ViewStudyQuestionnaireWidgetRenderer, StudyQuestionnaireWidgetController>
	
{

	
	
	
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {

		super.init(); // this will instantiate controller, model and renderer
		
		// memory
		this.memory = new AtomCrudWidgetMemory<StudyEntry, StudyFeed>(this.model, this.controller);
		
	}

	
	
	
	
	@Override
	public void render() {
		super.render();
		
		// TODO is it right to do this here?
		
		Deferred<XQuestionnaire> dq = this.controller.loadQuestionnaire();

		dq.addCallback(new Function<XQuestionnaire, XQuestionnaire>() {

			public XQuestionnaire apply(XQuestionnaire in) {
				renderer.setQuestionnaire(in);
				return in;
			}

		});
		
	}
	
	
	
	

	/**
	 * @param id
	 */
	public Deferred<StudyEntry> viewEntry(String url) {

		// delegate to controller
		return this.controller.retrieveEntry(url);
		
	}



	
	public HandlerRegistration addViewStudyActionHandler(StudyActionHandler h) {
		return this.addHandler(h, ViewStudyActionEvent.TYPE);
	}




	
	public HandlerRegistration addEditStudyActionHandler(StudyActionHandler h) {
		return this.addHandler(h, EditStudyActionEvent.TYPE);
	}




	
	public HandlerRegistration addEditStudyQuestionnaireActionHandler(StudyActionHandler h) {
		return this.addHandler(h, EditStudyQuestionnaireActionEvent.TYPE);
	}




	

	public HandlerRegistration addViewStudyQuestionnaireActionHandler(StudyActionHandler h) {
		return this.addHandler(h, ViewStudyQuestionnaireActionEvent.TYPE);
	}






	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomui.client.AtomCrudWidget#createController()
	 */
	@Override
	protected StudyQuestionnaireWidgetController createController() {
		return new StudyQuestionnaireWidgetController(this, this.model);
	}






	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected StudyQuestionnaireWidgetModel createModel() {
		return new StudyQuestionnaireWidgetModel();
	}






	@Override
	protected ViewStudyQuestionnaireWidgetRenderer createRenderer() {
		return new ViewStudyQuestionnaireWidgetRenderer(this);
	}




	
}
