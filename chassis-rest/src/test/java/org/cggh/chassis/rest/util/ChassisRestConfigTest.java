/**
 * 
 */
package org.cggh.chassis.rest.util;

import junit.framework.TestCase;

/**
 * @author timp
 *
 */
public class ChassisRestConfigTest extends TestCase {

  public void testGetConfiguration() {
    ChassisRestConfig c = new ChassisRestConfig();
    assertEquals("Beauty", c.getConfiguration().get("truth"));
  }

  public void testIt() { 
    StringBuffer sb = new StringBuffer(); 
    System.err.println(sb.toString());
  }
}
