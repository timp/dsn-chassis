/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyFeed;
import org.cggh.chassis.generic.atomext.client.study.StudyQuery;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidget;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetMemory;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetModel;


/**
 * @author raok
 *
 */
public class EditStudyWidget 
	extends AtomCrudWidget<StudyEntry, StudyFeed, StudyQuery, AtomCrudWidgetModel<StudyEntry>, EditStudyWidgetRenderer, EditStudyWidgetController>
	
{

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomui.client.AtomCrudWidget#createController()
	 */
	@Override
	protected EditStudyWidgetController createController() {
		return new EditStudyWidgetController(this, model);
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
	protected EditStudyWidgetRenderer createRenderer() {
		return new EditStudyWidgetRenderer(this);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {

		super.init(); // this will instantiate model, renderer and controller
		
		this.renderer.setController(this.controller);

		this.memory = new AtomCrudWidgetMemory<StudyEntry, StudyFeed>(this.model, this.controller);

	}

	/**
	 * @param entry
	 */
	public void editEntry(String url) {

		// make sure we do a plain retrieve before editing so we don't persist any expanded links
		this.controller.retrieveEntry(url);
		
	}
	
}
