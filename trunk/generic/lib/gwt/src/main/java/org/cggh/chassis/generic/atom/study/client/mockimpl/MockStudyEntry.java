/**
 * 
 */
package org.cggh.chassis.generic.atom.study.client.mockimpl;

import java.util.List;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomEntry;

/**
 * @author aliman
 *
 */
public class MockStudyEntry extends MockAtomEntry implements StudyEntry {

	/**
	 * @param collectionURL
	 */
	protected MockStudyEntry(MockStudyFactory factory) {
		super(factory);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.StudyEntry#addModule(java.lang.String)
	 */
	public void addModule(String module) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.StudyEntry#getModules()
	 */
	public List<String> getModules() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.StudyEntry#removeModule(java.lang.String)
	 */
	public void removeModule(String module) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.StudyEntry#setModules(java.util.List)
	 */
	public void setModules(List<String> modules) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void put(AtomEntry entry) {
		super.put(entry);
		if (entry instanceof StudyEntry) {
			// TODO sync modules if instance of StudyEntry
		}
	}

}
