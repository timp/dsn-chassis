/**
 * 
 */
package org.cggh.chassis.generic.atom.study.client.format;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public interface StudyFactory extends AtomFactory {

	public StudyEntry createStudyEntry();

	/**
	 * @param studyElement
	 * @return
	 */
	public Study createStudy(Element studyElement);

	/**
	 * @param studyFeedDoc
	 * @return
	 */
	public StudyFeed createStudyFeed(String studyFeedDoc);
	
}
