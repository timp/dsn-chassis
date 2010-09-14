/**
 * @author timp
 * @since 16 Dec 2009
 */
package org.cggh.chassis.generic.atomext.client.submission;

import org.cggh.chassis.generic.atom.client.AtomEntryImpl;
import org.cggh.chassis.generic.atom.client.AtomLinkImpl;

/**
 * Thrown when a link that should be present is not. 
 *
 */
public class MissingLinkIntegrityException extends RuntimeException {

	private static final long serialVersionUID = -3471067196755925392L;
	
	private AtomEntryImpl entryImpl;
	private Class<AtomLinkImpl> atomLink;

	/**
	 * @param submissionEntryImpl
	 * @param class1
	 */
	//TODO FIXME 
	@SuppressWarnings("unchecked")
	public MissingLinkIntegrityException(AtomEntryImpl entryImpl, Class atomLink) {
		this.entryImpl = entryImpl;
		this.atomLink = atomLink;
	}



	@Override
	public String getMessage() {
		return entryImpl.toString() + " is missing an expected link of type " + atomLink.getName();
	}

}
