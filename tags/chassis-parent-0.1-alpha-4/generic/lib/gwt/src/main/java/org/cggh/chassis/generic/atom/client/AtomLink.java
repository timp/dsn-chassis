/**
 * 
 */
package org.cggh.chassis.generic.atom.client;

import org.cggh.chassis.generic.xml.client.ElementWrapper;

/**
 * @author aliman
 *
 */
public interface AtomLink extends ElementWrapper {

	public String getHref();
	public void setHref(String href);
	public String getRel();
	public void setRel(String rel);
	public String getType();
	public void setType(String type);
	public String getHrefLang();
	public void setHrefLang(String hrefLang);
	public String getTitle();
	public void setTitle(String title);
	public String getLength();
	public void setLength(String length);

}
