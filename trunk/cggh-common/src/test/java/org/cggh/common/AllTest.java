
package org.cggh.common;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test class to run all the tests in this package
 */
public class AllTest extends TestCase {
	/**
	 * Constructor
	 * @param name Name of the test
	 */
	public AllTest(String name) {
		super(name);
	}

	/**
	 * Run the tests from the command line
	 * 
	 * @param args	No parameters required or expected
	 */	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	/**
	 * Return the test suite
	 * 
	 * @return the test suite
	 */
	public static Test suite() {
		TestSuite ts = new TestSuite();
		
		ts.addTestSuite(FileUtilTest.class);
		ts.addTestSuite(XMLUtilitiesTest.class);
		
		return (ts);
	}
}
