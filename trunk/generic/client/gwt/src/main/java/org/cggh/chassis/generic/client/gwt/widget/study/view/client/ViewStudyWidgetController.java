/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.view.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;

/**
 * @author raok
 *
 */
public class ViewStudyWidgetController {

	private ViewStudyWidgetModel model;
	private AtomService service;

	public ViewStudyWidgetController(ViewStudyWidgetModel model, AtomService service) {
		this.model = model;
		this.service = service;
	}


	public void loadStudyEntryByEntryURL(String entryURL) {
		getStudyEntry(entryURL).addCallback(new GetStudyEntryCallback());
	}
	
	Deferred<AtomEntry> getStudyEntry(String entryURL) {
		
		return service.getEntry(entryURL);
	}
	
	class GetStudyEntryCallback implements Function<StudyEntry,StudyEntry> {

		public StudyEntry apply(StudyEntry studyEntry) {
			
			model.setStudyEntry(studyEntry);
			
			model.setStatus(ViewStudyWidgetModel.STATUS_LOADED);
			
			return studyEntry;
		}
		
	}

	public void loadStudyEntry(StudyEntry newStudyEntry) {
		model.setStudyEntry(newStudyEntry);
		model.setStatus(ViewStudyWidgetModel.STATUS_LOADED);
	}
	

}
