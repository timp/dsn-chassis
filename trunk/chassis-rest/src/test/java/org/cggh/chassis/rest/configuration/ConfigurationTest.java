package org.cggh.chassis.rest.configuration;

import java.util.Properties;

import junit.framework.TestCase;

public class ConfigurationTest extends TestCase {

  public void testConfigurationString() {
    Configuration c = new Configuration("notFound");
    assertNull(c.get("nothing"));
    assertNull(c.put("key", "value"));
    assertEquals("value", c.put("key", "value"));
    assertEquals("value", c.get("key"));
  }

  public void testConfigurationStringProperties() {
   Properties defaults = new Properties();
   defaults.put("truth", "Beauty");
   Configuration c = new Configuration("notFound", defaults);
   assertNull(c.get("nothing"));
   assertNull(c.put("key", "value"));
   assertEquals("value", c.put("key", "value"));
   assertEquals("value", c.get("key"));
   
   assertEquals("Beauty", c.get("truth"));
   
  }



}
