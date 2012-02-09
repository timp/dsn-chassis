/**
 * 
 */
package org.cggh.chassis.rest.util;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 2011-11-25
 *
 */
public class ChassisRestConfigTest extends TestCase {

  public void testGetConfiguration() {
    ChassisRestConfig c = new ChassisRestConfig();
    assertEquals("Beauty", c.getConfiguration().get("truth"));
  }

}
