/**
 * 
 */
package org.cggh.chassis.generic.atom.client.format;

/**
 * @author aliman
 *
 */
public interface AtomLink {

	public String getHref();
	public void setHref(String href);
	public String getRel();
	public void setRel(String rel);
	public String getType();
	public void setType(String type);
	public String getHreflang();
	public void setHrefLang(String hrefLang);
	public String getTitle();
	public void setTitle(String title);
	public String getLength();
	public void setLength(String length);

}
