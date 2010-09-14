/**
 * 
 */
package org.cggh.chassis.generic.atom.client;

import org.cggh.chassis.generic.xml.client.ElementWrapper;

/**
 * @author aliman
 *
 */
public interface AtomCategory extends ElementWrapper {

	public String getTerm();
	public void setTerm(String term);
	public String getScheme();
	public void setScheme(String scheme);
	public String getLabel();
	public void setLabel(String label);
	
}
