/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.mockimpl;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomCategory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomPersonConstruct;




/**
 * @author aliman
 *
 */
public class MockAtomFactory {


	
	
	public MockAtomFeed createMockFeed(String title) {
		return new MockAtomFeed(title);
	}




	public MockAtomEntry createMockEntry() {
		return new MockAtomEntry(this);
	}




	/**
	 * @param authors
	 * @return
	 */
	List<AtomPersonConstruct> mockPersons(List<AtomPersonConstruct> persons) {
		List<AtomPersonConstruct> mockPersons = new ArrayList<AtomPersonConstruct>();
		for (AtomPersonConstruct person : persons) {
			MockAtomPersonConstruct mockPerson = this.mockPerson(person);
			mockPersons.add(mockPerson);
		}
		return mockPersons;
	}

	

	
	MockAtomPersonConstruct mockPerson(AtomPersonConstruct person) {
		MockAtomPersonConstruct mockPerson = new MockAtomPersonConstruct(this);
		mockPerson.put(person);
		return mockPerson;
	}
	
	
	
	
	MockAtomEntry mockEntry(AtomEntry entry) {
		MockAtomEntry mockEntry = new MockAtomEntry(this);
		mockEntry.put(entry);
		return mockEntry;
	}




	/**
	 * @param categories
	 * @return
	 */
	List<AtomCategory> mockCategories(List<AtomCategory> categories) {
		// TODO Auto-generated method stub
		return null;
	}




	/**
	 * @param links
	 * @return
	 */
	List<AtomLink> mockLinks(List<AtomLink> links) {
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




	
	
	
}
