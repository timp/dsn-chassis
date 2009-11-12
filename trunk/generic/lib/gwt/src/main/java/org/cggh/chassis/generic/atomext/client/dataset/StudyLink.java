/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.dataset;

import org.cggh.chassis.generic.atom.client.AtomLink;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;

/**
 * @author aliman
 *
 */
public interface StudyLink extends AtomLink {

	public StudyEntry getEntry();
	
}
