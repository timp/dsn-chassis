/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.study.client.format;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomFormatException;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class TestStudyEntry extends GWTTestCase {

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.gwt.lib.study.Study";
	}
	
	
	
	public void testConstructor() {
		
		try {
			
			StudyEntry entry = new StudyEntry();

			String title = "this is a title";
			entry.setTitle(title);
			assertEquals(title, entry.getTitle());

			Integer sampleSize = new Integer(42);
			entry.setSampleSize(sampleSize);
			assertEquals(sampleSize, entry.getSampleSize());
			
		} catch (AtomFormatException ex) {
			ex.printStackTrace();
			fail(ex.getLocalizedMessage());
		}
	}

	public void testConstructor_entryDocXML() {
		
		try {
			
			String xml = 
				"<entry xmlns=\"http://www.w3.org/2005/Atom\">\n" +
				"  <content type=\"application/xml\">\n" +
				"    <study xmlns=\"http://www.cggh.org/chassis/atom/xmlns\"></study>\n" +
				"  </content>\n" +
				"</entry>";

			StudyEntry entry = new StudyEntry(xml);

			String title = "this is a title";
			entry.setTitle(title);
			assertEquals(title, entry.getTitle());

			Integer sampleSize = new Integer(42);
			entry.setSampleSize(sampleSize);
			assertEquals(sampleSize, entry.getSampleSize());

		} catch (AtomFormatException ex) {
			ex.printStackTrace();
			fail(ex.getLocalizedMessage());
		}
	}

	
	@SuppressWarnings("unused")
	public void testConstructor_badFormat() {
		try {
		
			String badXML = "foo bar";
			StudyEntry entry = new StudyEntry(badXML);
			fail("expected exception");
			
		} catch (AtomFormatException ex) {
			
			System.out.println("testConstructor_badFormat :: caught expected exception: "+ex.getLocalizedMessage());
			
			assertFalse((ex instanceof StudyFormatException));
		}		
	}

	@SuppressWarnings("unused")
	public void testConstructor_badRootElement() {
		try {
		
			String badXML = "<foo><bar>baz</bar></foo>";
			StudyEntry entry = new StudyEntry(badXML);
			fail("expected exception");
			
		} catch (AtomFormatException ex) {
			System.out.println("testConstructor_badRootElement :: caught expected exception: "+ex.getLocalizedMessage());
			assertFalse((ex instanceof StudyFormatException));
		}		
	}

	@SuppressWarnings("unused")
	public void testConstructor_badRootElement2() {
		try {
		
			String badXML = "<entry><bar>baz</bar></entry>";
			StudyEntry entry = new StudyEntry(badXML);
			fail("expected exception");
			
		} catch (AtomFormatException ex) {
			System.out.println("testConstructor_badRootElement2 :: caught expected exception: "+ex.getLocalizedMessage());
			assertFalse((ex instanceof StudyFormatException));
		}		
	}

	@SuppressWarnings("unused")
	public void testConstructor_noStudyElement() {
		try {
		
			String badXML = "<entry xmlns=\"http://www.w3.org/2005/Atom\"></entry>";
			StudyEntry entry = new StudyEntry(badXML);
			fail("expected exception");
			
		} catch (AtomFormatException ex) {
			System.out.println("testConstructor_noStudyElement :: caught expected exception: "+ex.getLocalizedMessage());
			assertTrue((ex instanceof StudyFormatException));
		}		
	}

}
