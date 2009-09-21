/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.mockimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;

/**
 * @author aliman
 *
 */
public class MockAtomFeed implements AtomFeed {

	protected String title;
	protected String id;
	protected String updated;
	protected List<AtomEntry> entries = new ArrayList<AtomEntry>();
	protected MockAtomFactory factory;

	/**
	 * @param title
	 * @param factory 
	 */
	protected MockAtomFeed(String title, MockAtomFactory factory) {

		this.title = title;
		this.factory = factory;

		// generate new ID
//		UUID id = UUID.randomUUID();
		this.id = Double.toString(Math.random());

		// set feed updated
		Date date = new Date();
		this.updated = date.toString(); // TODO check this is correct format, probably not
		
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed#getEntries()
	 */
	public List<AtomEntry> getEntries() {
		return new ArrayList<AtomEntry>(this.entries);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed#getId()
	 */
	public String getId() {
		return this.id;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed#getTitle()
	 */
	public String getTitle() {
		return this.title;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed#getUpdated()
	 */
	public String getUpdated() {
		return this.updated;
	}

	/**
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param mockEntry
	 */
	public void add(MockAtomEntry mockEntry) {
		this.entries.add(mockEntry);
	}

	/**
	 * @param entry
	 * @return
	 */
	public boolean contains(MockAtomEntry entry) {
		return this.entries.contains(entry);
	}

	/**
	 * @param entry
	 */
	public void remove(MockAtomEntry entry) {
		this.entries.remove(entry);
	}

	/**
	 * @param feed
	 */
	public void put(AtomFeed feed) {
		this.entries = new ArrayList<AtomEntry>();
		for (AtomEntry entry : feed.getEntries()) {
			this.entries.add(factory.copy(entry));
		}
	}

}
