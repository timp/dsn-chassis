/**
 * 
 */
package org.cggh.chassis.generic.atom.study.client.format;

import java.util.List;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;

/**
 * @author aliman
 *
 */
public interface StudyFeed extends AtomFeed {

	public List<StudyEntry> getStudyEntries();
	
}
