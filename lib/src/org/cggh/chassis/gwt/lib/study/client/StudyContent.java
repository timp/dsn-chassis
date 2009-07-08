/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.study.client;

import org.cggh.chassis.gwt.lib.atom.client.AtomContent;
import org.cggh.chassis.gwt.lib.common.client.ChassisNS;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class StudyContent extends AtomContent {

	private StudyEntry study;

	public StudyContent(StudyEntry study) {
		this.study = study;
	}
	
	@Override
	public String getType() {
		return "application/atom+xml";
	}
	
	@Override
	public String getInlineContent() {
		String xml = 
			"<study xmlns=\""+ChassisNS.NS+"\">";
		
		xml +=
			"</study>";
		
		return xml;
	}
}
