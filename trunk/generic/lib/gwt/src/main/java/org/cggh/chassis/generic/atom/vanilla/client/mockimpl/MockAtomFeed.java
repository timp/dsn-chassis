/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.mockimpl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;

/**
 * @author aliman
 *
 */
public class MockAtomFeed implements AtomFeed {

	private String title;
	private String id;
	private String updated;

	/**
	 * @param title
	 */
	public MockAtomFeed(String title) {

		this.title = title;

		// generate new ID
		UUID id = UUID.randomUUID();
		this.id = id.toString();

		// set feed updated
		Date date = new Date();
		this.updated = date.toString(); // TODO check this is correct format, probably not
		
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed#getEntries()
	 */
	public List<AtomEntry> getEntries() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed#getId()
	 */
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed#getTitle()
	 */
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed#getUpdated()
	 */
	public String getUpdated() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param title
	 */
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param string
	 */
	public void setId(String string) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param string
	 */
	public void setUpdated(String string) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param mockEntry
	 */
	public void addEntry(MockAtomEntry mockEntry) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param entry
	 * @return
	 */
	public boolean contains(MockAtomEntry entry) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @param entry
	 */
	public void remove(MockAtomEntry entry) {
		// TODO Auto-generated method stub
		
	}

}
