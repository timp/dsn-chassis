package org.cggh.chassis.rest.util;

import java.util.Properties;

import org.cggh.chassis.rest.configuration.Configuration;

public class ChassisRestConfig {
  private Configuration c;
  
  public ChassisRestConfig() { 
    Properties defaults = new Properties();
    defaults.put("truth", "Beauty");
    String FEED_FILENAME = "/studies_feed.xml";
    String DATA_DIR_NAME = "downloaded";
    String STUDY_ID = "XGQRX";
    String STUDIES_DIR_NAME;
    String FULL_FEED_FILENAME;
    String INVALID_STUDY_ID = "TFCBZ"; // live
    
    String SERVICE_PROTOCOL_HOST_PORT = "http://localhost:8080";
    
    // Read in from file system - scrt pzzwd
    
    // This is just a dummy run to see if file exists
    // Not happy, at duplication but we cannot check 
    // the properties file until we have set up the defaults
    c = new Configuration("chassis-rest", defaults);
    if (!c.configFileExists()) { 
      DATA_DIR_NAME = "data";
      INVALID_STUDY_ID = "AJYER";      
      System.err.println("Using dummy data");
    }    
    FULL_FEED_FILENAME = DATA_DIR_NAME + FEED_FILENAME; 
    STUDIES_DIR_NAME = DATA_DIR_NAME + "/studies";
    
    defaults.put("DATA_DIR_NAME", "data");
    defaults.put("FULL_FEED_FILENAME", FULL_FEED_FILENAME); 
    defaults.put("STUDY_ID", STUDY_ID);
    defaults.put("INVALID_STUDY_ID", INVALID_STUDY_ID);
    defaults.put("STUDIES_DIR_NAME", STUDIES_DIR_NAME);
    defaults.put("SERVICE_PROTOCOL_HOST_PORT", SERVICE_PROTOCOL_HOST_PORT);
    
    // Create it again with new defaults
    c = new Configuration("chassis-rest", defaults);
  }

  public Configuration getConfiguration() { 
    return c;
  }
}
