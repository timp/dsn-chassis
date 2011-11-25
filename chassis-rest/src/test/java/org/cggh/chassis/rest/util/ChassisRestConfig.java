package org.cggh.chassis.rest.util;

import java.io.File;
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
    
    
    
    // Not happy, old feeds can hang around, but we cannot check 
    // the properties file until we have set up the defaults
    
    FULL_FEED_FILENAME = DATA_DIR_NAME + FEED_FILENAME; 
    if (!new File(FULL_FEED_FILENAME).exists()) {
      DATA_DIR_NAME = "data";
      FULL_FEED_FILENAME = "data" + FEED_FILENAME;
      System.err.println("Using dummy data");
      INVALID_STUDY_ID = "AJYER";
    }
    STUDIES_DIR_NAME = DATA_DIR_NAME + "/studies";
    
    defaults.put("DATA_DIR_NAME", "data");
    defaults.put("FULL_FEED_FILENAME", FULL_FEED_FILENAME); 
    defaults.put("STUDY_ID", STUDY_ID);
    defaults.put("INVALID_STUDY_ID", STUDY_ID);
    defaults.put("STUDIES_DIR_NAME", STUDIES_DIR_NAME);
    
    // Read in from file system - scrt pzzwd 
    c = new Configuration("chassis-rest", defaults);

    
    c.put("INVALID_STUDY_ID", INVALID_STUDY_ID); 
  }

  public Configuration getConfiguration() { 
    return c;
  }
}
