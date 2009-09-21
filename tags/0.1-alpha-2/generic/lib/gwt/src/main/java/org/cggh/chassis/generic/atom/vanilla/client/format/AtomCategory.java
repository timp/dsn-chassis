/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.format;

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
