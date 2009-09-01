/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.mockimpl;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomCategory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFormatException;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomPersonConstruct;

import com.google.gwt.xml.client.Element;




/**
 * @author aliman
 *
 */
public class MockAtomFactory implements AtomFactory {


	
	
	protected MockAtomFeed createMockFeed(String title) {
		return new MockAtomFeed(title, this);
	}




	protected MockAtomEntry createMockEntry() {
		return new MockAtomEntry(this);
	}




	/**
	 * @param authors
	 * @return
	 */
	protected List<AtomPersonConstruct> copyPersons(List<AtomPersonConstruct> persons) {
		List<AtomPersonConstruct> mockPersons = new ArrayList<AtomPersonConstruct>();
		for (AtomPersonConstruct person : persons) {
			MockAtomPersonConstruct mockPerson = this.copy(person);
			mockPersons.add(mockPerson);
		}
		return mockPersons;
	}

	

	
	protected MockAtomPersonConstruct copy(AtomPersonConstruct person) {
		MockAtomPersonConstruct mockPerson = new MockAtomPersonConstruct(this);
		mockPerson.put(person);
		return mockPerson;
	}
	
	
	
	
	protected MockAtomEntry copy(AtomEntry entry) {
		MockAtomEntry mockEntry = new MockAtomEntry(this);
		mockEntry.put(entry);
		return mockEntry;
	}




	/**
	 * @param categories
	 * @return
	 */
	protected List<AtomCategory> copyCategories(List<AtomCategory> categories) {
		// TODO Auto-generated method stub
		return null;
	}




	/**
	 * @param links
	 * @return
	 */
	protected List<AtomLink> copyLinks(List<AtomLink> links) {
		List<AtomLink> mockLinks = new ArrayList<AtomLink>();
		for (AtomLink link : links) {
			MockAtomLink mockLink = new MockAtomLink(this);
			mockLink.put(link);
			mockLinks.add(mockLink);
		}
		return mockLinks;
	}




	/**
	 * @return
	 */
	protected MockAtomLink createMockAtomLink() {
		return new MockAtomLink(this);
	}
	
	
	
	
	




	/**
	 * @param mockAtomFeed
	 * @return
	 */
	protected MockAtomFeed copy(AtomFeed feed) {
		MockAtomFeed copy = new MockAtomFeed(feed.getTitle(), this);
		copy.put(feed);
		return copy;
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createEntry()
	 */
	public AtomEntry createEntry() {
		return createMockEntry();
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createEntry(java.lang.String)
	 */
	public AtomEntry createEntry(String entryDocument) throws AtomFormatException {
		// not needed
		return null;
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createEntry(com.google.gwt.xml.client.Element)
	 */
	public AtomEntry createEntry(Element entryElement) throws AtomFormatException {
		// not needed
		return null;
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createFeed(java.lang.String)
	 */
	public AtomFeed createFeed(String feedDocument) throws AtomFormatException {
		// not needed
		return null;
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createPersonConstruct()
	 */
	public AtomPersonConstruct createPersonConstruct() {
		return new MockAtomPersonConstruct(this);
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createPersonConstruct(com.google.gwt.xml.client.Element)
	 */
	public AtomPersonConstruct createPersonConstruct(Element personElement)	throws AtomFormatException {
		// not needed
		return null;
	}




	
	
	
}
