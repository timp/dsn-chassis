/**
 * 
 */
package legacy.org.cggh.chassis.generic.atom.vanilla.client.mockimpl;

import java.util.ArrayList;
import java.util.List;

import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomCategory;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomFormatException;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink;


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
	protected List<AtomAuthor> copyPersons(List<AtomAuthor> persons) {
		List<AtomAuthor> mockPersons = new ArrayList<AtomAuthor>();
		for (AtomAuthor person : persons) {
			MockAtomAuthor mockPerson = this.copy(person);
			mockPersons.add(mockPerson);
		}
		return mockPersons;
	}

	

	
	protected MockAtomAuthor copy(AtomAuthor person) {
		MockAtomAuthor mockPerson = new MockAtomAuthor(this);
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
	
	public AtomFeed createFeed() {
		// not needed
		return null;
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createAuthor()
	 */
	public AtomAuthor createAuthor() {
		return new MockAtomAuthor(this);
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createAuthor(com.google.gwt.xml.client.Element)
	 */
	public AtomAuthor createAuthor(Element personElement)	throws AtomFormatException {
		// not needed
		return null;
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createLink()
	 */
	public AtomLink createLink() {
		return createMockAtomLink();
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createLink(com.google.gwt.xml.client.Element)
	 */
	public AtomLink createLink(Element linkElement) throws AtomFormatException {
		// not needed
		return null;
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createCategory()
	 */
	public AtomCategory createCategory() {
		return createMockCategory();
	}




	/**
	 * @return
	 */
	private MockAtomCategory createMockCategory() {
		return new MockAtomCategory();
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createCategory(com.google.gwt.xml.client.Element)
	 */
	public AtomCategory createCategory(Element categoryElement)
			throws AtomFormatException {
		// TODO Auto-generated method stub
		return null;
	}




	
	
	
}
