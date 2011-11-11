/**
 * 
 */
package org.cggh.chassis.manta.util;

import java.text.SimpleDateFormat;

import junit.framework.TestCase;

/**
 * @author timp
 *
 */
public class SimpleDateFormatTest extends TestCase {

  /**
   * @param name
   */
  public SimpleDateFormatTest(String name) {
    super(name);
  }

  public void testSimpleDateFormat() throws Exception { 
    SimpleDateFormat it = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    assertTrue(it.parse("2011-06-10T14:48:23.733Z").after(it.parse("2011-06-10T14:48:16.204Z")));
  }
}
