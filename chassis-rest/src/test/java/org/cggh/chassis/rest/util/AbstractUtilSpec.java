package org.cggh.chassis.rest.util;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 2011-12-01
 */
public abstract class AbstractUtilSpec extends TestCase {
  public String DATA_STUDIES;
  public String FULL_FEED_FILENAME;
  public String SERVICE_PROTOCOL_HOST_PORT;
  protected static ChassisRestConfig config;

  public AbstractUtilSpec() {
    super();
  }
  public AbstractUtilSpec(String name) {
    super(name);
  }
  
  protected void setUp() throws Exception {
    super.setUp();
    config =  new ChassisRestConfig();
    DATA_STUDIES = config.getConfiguration().get("STUDIES_DIR_NAME") + "/";
    FULL_FEED_FILENAME = config.getConfiguration().get("FULL_FEED_FILENAME");
    SERVICE_PROTOCOL_HOST_PORT = config.getConfiguration().get("SERVICE_PROTOCOL_HOST_PORT");
  }
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  protected String url(String url) {
    return SERVICE_PROTOCOL_HOST_PORT + "/chassis-rest/service" + url;
  }
  
  
}