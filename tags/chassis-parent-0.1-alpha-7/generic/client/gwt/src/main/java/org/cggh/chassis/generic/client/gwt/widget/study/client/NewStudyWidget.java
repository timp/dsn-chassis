/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;


import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyFeed;
import org.cggh.chassis.generic.atomext.client.study.StudyQuery;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidget;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetModel;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;




/**
 * @author aliman
 *
 */
public class NewStudyWidget 
	extends AtomCrudWidget<StudyEntry, StudyFeed, StudyQuery, AtomCrudWidgetModel<StudyEntry>, NewStudyWidgetRenderer, NewStudyWidgetController>

{

	
	
	
	private Log log;

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomui.client.AtomCrudWidget#createController()
	 */
	@Override
	protected NewStudyWidgetController createController() {
		return new NewStudyWidgetController(this, this.model);
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected AtomCrudWidgetModel<StudyEntry> createModel() {
		return new AtomCrudWidgetModel<StudyEntry>();
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected NewStudyWidgetRenderer createRenderer() {
		return new NewStudyWidgetRenderer(this);
	}

	
	
	
	@Override
	public void init() {
		ensureLog();
		log.enter("init");
		
		super.init(); // will instantiate model, renderer and controller
		
		this.renderer.setController(this.controller);
		
		log.leave();
	}
	
	
	
	
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(NewStudyWidget.class);
	}





	
	
}
