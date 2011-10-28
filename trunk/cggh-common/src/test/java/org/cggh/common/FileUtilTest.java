package org.cggh.common;

import java.io.File;
import java.net.URL;


/**
 * Test the file utilities
 */
public class FileUtilTest extends BaseTestCase {

	/**
	 * Constructor for FileUtilTest
	 * @param name - passed to super
	 */
	public FileUtilTest(String name) {
		super(name);
	}

	/**
	 * Main method
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		junit.textui.TestRunner.run(FileUtilTest.class);
	}

	/**
	 * Test all three ways of using fileFileResourceAsURL
	 * This also implicitly tests findResource
	 */
	public void testFileAsURL() {
		try {
			URL url = FileUtilities.findFileAsURL("XSLTtest.xsl", 
															getClass());
			if (url == null) {
				fail("couldn't find file resource - null");
			}
			File f = new File(url.getFile());
			if (!f.exists()) {
				fail("couldn't find file resource");
			}
			url = FileUtilities.findFileAsURL(f.getAbsolutePath(), getClass());
			f = new File(url.getFile());
			if (!f.exists()) {
				fail("couldn't find fully qualified path");
			}
			StringBuffer sb = new StringBuffer("file:///");
			sb.append(f.getAbsolutePath());
			url = FileUtilities.findFileAsURL(sb.toString(), getClass());
			f = new File(url.getFile());
			if (!f.exists()) {
				fail("couldn't find url");
			}

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}