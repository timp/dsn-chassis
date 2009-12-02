/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyFeed;
import org.cggh.chassis.generic.atomext.client.study.StudyQuery;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidget;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetModel;

/**
 * @author aliman
 *
 */
public class NewStudyWidgetController extends StudyCrudWidgetController {

	/**
	 * @param owner
	 * @param model
	 */
	public NewStudyWidgetController(
			AtomCrudWidget<StudyEntry, StudyFeed, StudyQuery, ?, ?, ?> owner,
			AtomCrudWidgetModel<StudyEntry> model) {
		super(owner, model);
	}

}
