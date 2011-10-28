package org.cggh.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;


/**
 * JUnit tests for XMLUtilities
 * 
 * @since
 */
public class XMLUtilitiesTest extends BaseTestCase {

	/**
	 * Constructor
	 * @param name Name of the test
	 */
	public XMLUtilitiesTest(String name) {
		super(name);
	}

	/**
	 * Run the tests from the command line
	 * 
	 * @param args	No parameters required or expected
	 */
	public static void main(String args[]) {
		TestRunner.run(suite());
	}

	/**
	 * Return the test suite
	 * 
	 * @return the test suite
	 */
	public static Test suite() {
		return new TestSuite(XMLUtilitiesTest.class);
	}

	/**
	 * Check that the elementToString utility works by building a 
	 * stringbuffer, loading it into a Document, then extracting it from
	 * the Document as an element and then comparing the output from
	 * elementToString with the original string
	 */
	public void testElementToString() {

		try {
			
			String testData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + buildGoodXML();
			// Get a Document factory
			DocumentBuilderFactory dfactory = 
									DocumentBuilderFactory.newInstance();

			dfactory.setNamespaceAware(true);
			dfactory.setValidating(true);

			DocumentBuilder documentBuilderA = dfactory.newDocumentBuilder();

			// Parse the input stream into the 1st document object
			byte inputBytesA[] = testData.toString().getBytes();
			ByteArrayInputStream bais = new ByteArrayInputStream(inputBytesA);
			Document docA = documentBuilderA.parse(bais);

			// Compare the Element from the document with the original string
			// from the original stringbuffer
			Element docElem = docA.getDocumentElement();
			String elemString = removeWhiteSpace(
									XMLUtilities.elementToString(docElem));
			Assert.assertTrue(
				elemString.equals(removeWhiteSpace(testData.toString())));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Check that the elementToStringLog utility works by building a
	 * stringbuffer, loading it into a Document, then extracting it from
	 * the Document as an element and then comparing the output from
	 * elementToStringLog with the original string
	 */
	public void testElementToStringLog() {

		try {
			String testData = buildGoodXML();
			// Get a Document factory
			DocumentBuilderFactory dfactory = 
									DocumentBuilderFactory.newInstance();

			dfactory.setNamespaceAware(true);
			dfactory.setValidating(true);

			DocumentBuilder documentBuilderA = dfactory.newDocumentBuilder();

			// Parse the input stream into the 1st document object
			byte inputBytesA[] = testData.toString().getBytes();
			ByteArrayInputStream bais = new ByteArrayInputStream(inputBytesA);
			Document docA = documentBuilderA.parse(bais);

			String elemString = removeWhiteSpace(
					XMLUtilities.elementToStringLog(docA.getDocumentElement()));
			Assert.assertTrue(
				elemString.equals(removeWhiteSpace(testData.toString())));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Check that the getDocumentBuilder utility works by
	 * asking it to build a DocumentBuilder and then confirming that one
	 * is returned
	 */
	public void testGetDocumentBuilderNoErrorHandler() {
		try {
			DocumentBuilder db = XMLUtilities.getDocumentBuilder();

			Assert.assertTrue(
				"getDocumentBuilder() didn't return a valid object.",
				(db instanceof DocumentBuilder));
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	/**
	 * Check that the getDocumentBuilder utility works - when 
	 * passed an errorhandler
	 * works by asking getDocumentBuilder to build a DocumentBuilder 
	 * and then confirming that one is returned
	 */
	public void testGetDocumentBuilder() {
		try {
			ErrorHandler eh = new DefaultSAXErrorHandler();
			DocumentBuilder db = XMLUtilities.getDocumentBuilder(eh);

			Assert.assertTrue(
				"getDocumentBuilder(eh) didn't return a valid object.",
				(db instanceof DocumentBuilder));
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	/**
	 * Check that the getDocument utility works when passed no XML 
	 * (should return an empty DOM)
	 */
	public void testGetDocumentNoXML() {
		try {
			Document doc = XMLUtilities.getDocument();

			Assert.assertTrue(
				"getDocument() didn't return a valid Document object.",
				(doc instanceof Document));
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	/**
	 * Check that the getDocument utility works when passed no XML 
	 * (should return an empty DOM) - when passed an ErrorHandler
	 */
	public void testGetDocumentNoXMLWithErrorHandler() {
		try {
			ErrorHandler eh = new DefaultSAXErrorHandler();
			Document doc = XMLUtilities.getDocument(eh);

			Assert.assertTrue(
				"getDocument(eh) didn't return a valid Document object.",
				(doc instanceof Document));
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	/**
	 * Check that the getDocument utility works when passed XML
	 * No Error Handler passed so default will be used 
	 * No validating boolean passed so it will be defaulted to false
	 */
	public void testGetDocumentWithGoodXMLString() {
		try {
			String xmlString = "<dummy/>";
			Document doc = XMLUtilities.getDocument(xmlString);

			Assert.assertTrue(
				"getDocument(xmlString) didn't return a valid Document object.",
				(doc instanceof Document));
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	/**
	 * Check that the getDocument utility works when passed XML 
	 * No validating boolean passed so it will be defaulted to false
	 */
	public void testGetDocumentWithGoodXMLStringWithErrorHandler() {
		String xmlString = "";
		try {
			ErrorHandler eh = new DefaultSAXErrorHandler();
			xmlString = buildGoodXML();
			Document doc = XMLUtilities.getDocument(xmlString, eh);
		} catch (Exception e) {
			Assert.assertTrue("Error Occurred while validating" 
			+	" the following XML\n" + xmlString, false);
		}

	}

	/**
	 * Check that the getDocument utility works when passed XML 
	 * Validating boolean of true passed
	 */
	/*
	public void 
		testGetDocumentWithGoodXMLStringWithErrorHandlerValidatingTrue() {
		String xmlString = "";
		try {
			ErrorHandler eh = new DefaultSAXErrorHandler();
			xmlString = buildGoodXML();
			Document doc = XMLUtilities.getDocument(xmlString, eh, true);
		} catch (Exception e) {
			java.io.File f = new java.io.File(".");
			try {
				System.out.println(f.getCanonicalPath());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Assert.assertTrue("Error Occurred while validating" 
			+	" the following XML\n" + xmlString, false);
		}

	}
*/
	/**
	 * Check that the getDocument utility works when passed bad XML 
	 * The XML passed refers to a schema missing an essential element
	 * Validating boolean of true passed
	 */
	public void 
		testGetDocumentWithBadXMLSchemaWithErrorHandlerValidatingTrue() {
		try {
			ErrorHandler eh = new DefaultSAXErrorHandler();
			String xmlString = buildBadXML();
			Document doc = XMLUtilities.getDocument(xmlString, eh, true);

			Assert.assertTrue("Expected a validation failure as " 
			+ " getDocument was passed the following incorrect XML\n" 
			+ xmlString, false);

		} catch (XMLUtilitiesException x) {
			// Expect an exception - not a failure
		} catch (Exception e) {
			// Unexpected exception type
			fail(e.getMessage());
		}

	}

	/**
	 * Check that the getDocument utility fails correctly 
	 * when passed nulls 1
	 */
	public void 
		testGetDocumentWithValidXMLStringNullErrorHandlerValidatingTrue() {
		try {
			ErrorHandler eh = null;
			String xmlString = buildGoodXML();
			Document doc = XMLUtilities.getDocument(xmlString, eh, true);

			Assert.assertTrue("Expected a failure as a null " 
			+ " ErrorHandler was passed", false);
		} catch (XMLUtilitiesException x) {
			// Expect an exception - not a failure
		} catch (Exception e) {
			// Unexpected exception type
			fail(e.getMessage());
		}
	}

	/**
	 * Check that the getDocument utility fails correctly 
	 * when passed nulls 2
	 */
	public void 
		testGetDocumentWithNullXMLStringValidErrorHandlerValidatingTrue() {
		try {
			ErrorHandler eh = new DefaultSAXErrorHandler();
			String xmlString = null;
			Document doc = XMLUtilities.getDocument(xmlString, eh, true);

			Assert.assertTrue("Expected a failure as a null " 
			+ " XMLString was passed", false);
		} catch (XMLUtilitiesException n) {
			// Expect an exception - not a failure
		} catch (Exception e) {
			// Unexpected exception type
			fail(e.getMessage());
		}
	}

	/**
	 * Check that the getDocument utility fails correctly 
	 * when passed nulls 3
	 */
	public void 
		testGetDocumentWithNullInputSourceValidErrorHandlerValidatingTrue() {
		try {
			ErrorHandler eh = new DefaultSAXErrorHandler();
			String xmlString =  buildGoodXML();
			InputSource iSource = null;
			Document doc = XMLUtilities.getDocument(iSource, eh, true);

			Assert.assertTrue("Expected a failure as a null " 
			+ " iSource was passed", false);
		} catch (NullPointerException n) {
			// Expect an exception - not a failure
		} catch (Exception e) {
			// Unexpected exception type
			fail(e.getMessage());
		}
	}

	/**
	 * Check that the getDocument utility fails correctly 
	 * when passed nulls 4
	 */
	public void 
		testGetDocumentWithNullInputStreamValidErrorHandlerValidatingTrue() {
		try {
			ErrorHandler eh = new DefaultSAXErrorHandler();
			String xmlString =  buildGoodXML();
			InputStream iStream = null;
			Document doc = XMLUtilities.getDocument(iStream, eh, true);

			Assert.assertTrue("Expected a failure as a null " 
			+ " iStream was passed", false);
		} catch (XMLUtilitiesException x) {
			// Expect an exception - not a failure
		} catch (Exception e) {
			// Unexpected exception type
			fail(e.getMessage());
		}
	}

	/**
	 * Check that the getDocument utility fails correctly when 
	 * passed nulls 5
	 */
	public void 
		testGetDocumentWithNullFileValidErrorHandlerValidatingTrue() {
		try {
			ErrorHandler eh = new DefaultSAXErrorHandler();
			String xmlString =  buildGoodXML();
			File iFile = null;
			Document doc = XMLUtilities.getDocument(iFile, eh, true);

			Assert.assertTrue("Expected a failure as a null " 
			+ " iFile was passed", false);
		} catch (XMLUtilitiesException x) {
			// Expect an exception - not a failure
		} catch (Exception e) {
			// Unexpected exception type
			fail(e.getMessage());
		}
	}

	/**
	 * Check that the getDocument utility works correctly 
	 * when passed nulls 6
	 */
	/*
	public void 
		testGetDocumentWithValidIStreamNullSysIdValidEHValidating() {
		try {
			ErrorHandler eh = new DefaultSAXErrorHandler();
			String systemId =  null;

			InputStream iStream = new BufferedInputStream(
	            new ByteArrayInputStream(buildGoodXML().getBytes()));
			
			Document doc = XMLUtilities.getDocument(iStream, 
													systemId, 
													eh, 
													true);
			Assert.assertTrue(
				"getDocument(iStream, systemId, eh, true) "
				+ "didn't return a valid Document object.",
				(doc instanceof Document));
		} catch (Exception e) {
			// Unexpected exception type
			fail(e.getMessage());
		}
	}
*/
	/**
	 * Check that the elementToString utility works with valid input
	 */
	public void 
		testElementToStringWithValidElement() {
		try {
			String xmlString = buildGoodXML();
			Document doc = XMLUtilities.getDocument(xmlString);
			doc.normalize();
			
			String myString = XMLUtilities.
							  elementToString(doc.
							  				  getDocumentElement());			
		} catch (Exception e) {
			// Unexpected exception type
			fail(e.getMessage());
		}
	}

	/**
	 * Check that the elementToString utility fails correctly 
	 * when passed a null element (expect XMLUtilitiesException)
	 */
	public void 
		testElementToStringWithNullElement() {
		try {
			Element elem = null;	
								
			String myString = XMLUtilities.
							  elementToString(elem);			

			Assert.assertTrue("Expected a failure as a null " 
			+ " Element was passed", false);

		} catch (XMLUtilitiesException x) {
			// Expect an exception - not a failure
		} catch (Exception e) {
			e.printStackTrace();
			// Unexpected exception type
			fail(e.getMessage());
		}
	}

	/**
	 * Utility function to strip out whitespace from a passed in string
	 * 
	 * @param inStr	String to remove whitespace from
	 * @return String from which all the whitespace has been removed
	 */
	private String removeWhiteSpace(String inStr) {
		StringBuffer outStr = new StringBuffer();
		for (int ii = 0; ii < inStr.length(); ii++) {
			if (inStr.substring(ii, ii + 1).equals("\n")) {
				// do nothing				
			} else if (inStr.substring(ii, ii + 1).equals(" ")) {
				// do nothing				
			} else if (inStr.substring(ii, ii + 1).equals("\t")) {
				// do nothing				
			} else {
				outStr.append(inStr.substring(ii, ii + 1));
			}
		}
		return outStr.toString();
	}

	/**
	 * Build the good XML
	 * @return String containing the test xml
	 * @throws IOException 
	 */
	private String buildGoodXML() throws IOException {
		URL url = FileUtilities.findResource("good.xml", getClass());
		if (url == null) {
			fail("couldn't find file resource - null");
		}
		File f = new File(url.getFile());
		if (!f.exists()) {
			fail("couldn't find file resource");
		}
		String ret = FileUtils.readFileToString(f, "UTF-8");
		
		return ret;
	}

	/**
	 * Build the bad XML
	 * Will point to a schema file missing essential elements
	 * @return String containing the test xml
	 * @throws IOException 
	 */
	private String buildBadXML() throws IOException {
		URL url = FileUtilities.findResource("bad.xml", getClass());
		if (url == null) {
			fail("couldn't find file resource - null");
		}
		File f = new File(url.getFile());
		if (!f.exists()) {
			fail("couldn't find file resource");
		}
		String ret = FileUtils.readFileToString(f, "UTF-8");
		
		return ret;
	}

}