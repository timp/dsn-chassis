/**
 * 
 */
package legacy.org.cggh.chassis.generic.atom.vanilla;


import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.TestDeleteEntryCallback;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.TestGetEntryCallback;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.TestGetFeedCallback;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.TestPostEntryCallback;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.TestPutEntryCallback;

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
		
		suite.addTest(new JUnit4TestAdapter(legacy.org.cggh.chassis.generic.atom.vanilla.client.mockimpl.TestMockAtomStore.class));

		//$JUnit-END$
		return suite;
	}

}
