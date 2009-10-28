/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla;

import org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.TestDeleteEntryCallback;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.TestGetEntryCallback;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.TestGetFeedCallback;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.TestPostEntryCallback;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.TestPutEntryCallback;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author aliman
 *
 */
public class AllJavaTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("org.cggh.chassis.generic.atom.vanilla.Atom - All Plain Java Tests");
		//$JUnit-BEGIN$
		
		suite.addTestSuite(TestGetEntryCallback.class);
		suite.addTestSuite(TestGetFeedCallback.class);
		suite.addTestSuite(TestPostEntryCallback.class);
		suite.addTestSuite(TestPutEntryCallback.class);
		suite.addTestSuite(TestDeleteEntryCallback.class);
		
		suite.addTest(new JUnit4TestAdapter(org.cggh.chassis.generic.atom.vanilla.client.mockimpl.TestMockAtomStore.class));

		//$JUnit-END$
		return suite;
	}

}
