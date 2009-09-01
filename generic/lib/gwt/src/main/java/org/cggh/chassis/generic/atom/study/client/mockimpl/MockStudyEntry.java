/**
 * 
 */
package org.cggh.chassis.generic.atom.study.client.mockimpl;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomEntry;

/**
 * @author aliman
 *
 */
public class MockStudyEntry extends MockAtomEntry implements StudyEntry {

	private List<String> modules = new ArrayList<String>();

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
		this.modules.add(module);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.StudyEntry#getModules()
	 */
	public List<String> getModules() {
		return new ArrayList<String>(this.modules);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.StudyEntry#removeModule(java.lang.String)
	 */
	public void removeModule(String module) {
		this.modules.remove(module);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.StudyEntry#setModules(java.util.List)
	 */
	public void setModules(List<String> modules) {
		this.modules = modules;
	}
	
	@Override
	public void put(AtomEntry entry) {
		super.put(entry);
		if (entry instanceof StudyEntry) {
			StudyEntry study = (StudyEntry) entry;
			this.modules = new ArrayList<String>(study.getModules());
		}
	}

}
