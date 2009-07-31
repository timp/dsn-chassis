/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */



@RunWith(Suite.class)
@Suite.SuiteClasses( { 
	org.cggh.chassis.gwt.lib.atom.client.protocol.AllJavaTests.class,
	org.cggh.chassis.gwt.lib.twisted.client.TestDeferred.class
 	})

public class AllJavaTests {

}
