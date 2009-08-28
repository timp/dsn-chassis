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


	
	
	public MockAtomFeed createMockFeed(String title) {
		return new MockAtomFeed(title, this);
	}




	public MockAtomEntry createMockEntry() {
		return new MockAtomEntry(this);
	}




	/**
	 * @param authors
	 * @return
	 */
	List<AtomPersonConstruct> copyPersons(List<AtomPersonConstruct> persons) {
		List<AtomPersonConstruct> mockPersons = new ArrayList<AtomPersonConstruct>();
		for (AtomPersonConstruct person : persons) {
			MockAtomPersonConstruct mockPerson = this.copy(person);
			mockPersons.add(mockPerson);
		}
		return mockPersons;
	}

	

	
	MockAtomPersonConstruct copy(AtomPersonConstruct person) {
		MockAtomPersonConstruct mockPerson = new MockAtomPersonConstruct(this);
		mockPerson.put(person);
		return mockPerson;
	}
	
	
	
	
	MockAtomEntry copy(AtomEntry entry) {
		MockAtomEntry mockEntry = new MockAtomEntry(this);
		mockEntry.put(entry);
		return mockEntry;
	}




	/**
	 * @param categories
	 * @return
	 */
	List<AtomCategory> copyCategories(List<AtomCategory> categories) {
		// TODO Auto-generated method stub
		return null;
	}




	/**
	 * @param links
	 * @return
	 */
	List<AtomLink> copyLinks(List<AtomLink> links) {
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
	public MockAtomLink createMockAtomLink() {
		return new MockAtomLink(this);
	}




	/**
	 * @param mockAtomFeed
	 * @return
	 */
	MockAtomFeed copy(MockAtomFeed feed) {
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




	
	
	
}
