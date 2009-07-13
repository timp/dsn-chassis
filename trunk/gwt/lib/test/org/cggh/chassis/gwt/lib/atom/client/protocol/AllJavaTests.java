/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client.protocol;

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
	TestGetFeedResponseHandler.class , 
	TestGetEntryResponseHandler.class ,
	TestPostEntryResponseHandler.class ,
	TestPutEntryResponseHandler.class
 	})

public class AllJavaTests {}
