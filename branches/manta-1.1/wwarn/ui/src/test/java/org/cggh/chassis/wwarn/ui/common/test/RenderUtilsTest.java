/**
 * 
 */
package org.cggh.chassis.wwarn.ui.common.test;

import org.cggh.chassis.wwarn.ui.common.client.RenderUtils;

import junit.framework.TestCase;

/**
 * @author timp
 *
 */
public class RenderUtilsTest extends TestCase {

	/**
	 * @param name
	 */
	public RenderUtilsTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link org.cggh.chassis.wwarn.ui.common.client.RenderUtils#extractEmails(java.lang.String)}.
	 */
	public void testExtractEmails() {
		String s1 = " \n , tim@paneris.net,     \n";
		assertEquals(1, RenderUtils.extractEmails(s1).length);
		assertEquals("tim@paneris.net", RenderUtils.extractEmails(s1)[0]);

		String s2 = "tim@paneris.net, timp@paneris.net ,, tpp@paneris.net";
		assertEquals(3, RenderUtils.extractEmails(s2).length);
		assertEquals("tim@paneris.net", RenderUtils.extractEmails(s2)[0]);

		String s3 = "tim@paneris.net, timp@paneris.net tpp@paneris.net";
		assertEquals(3, RenderUtils.extractEmails(s3).length);
		assertEquals("tim@paneris.net", RenderUtils.extractEmails(s3)[0]);
	}

}
