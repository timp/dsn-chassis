/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import org.cggh.chassis.generic.atom.client.AtomFactory;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyFactory;
import org.cggh.chassis.generic.atomext.client.study.StudyFeed;
import org.cggh.chassis.generic.client.gwt.forms.client.BaseForm;

/**
 * @author aliman
 *
 */
public class StudyForm extends
		BaseForm<StudyEntry, StudyFeed, StudyFormRenderer> {

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.forms.client.BaseForm#createAtomFactory()
	 */
	@Override
	protected AtomFactory<StudyEntry, StudyFeed> createAtomFactory() {
		return new StudyFactory();
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected StudyFormRenderer createRenderer() {
		return new StudyFormRenderer(this);
	}

	
	
	public static class Resources extends org.cggh.chassis.generic.client.gwt.forms.client.BaseForm.Resources {
		
		public Resources() {
			super(StudyForm.class.getName());
		}
		
	}





	
}
