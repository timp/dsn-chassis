package org.cggh.chassis.rest.util;

import java.util.Properties;

import org.cggh.chassis.rest.configuration.Configuration;

public class ChassisRestConfig {
  private Configuration configuraton;
  
  public ChassisRestConfig() { 
    Properties defaults = new Properties();
    defaults.put("truth", "Beauty");
    String CHASSIS_DOWNLOAD_DIR = "data";
    String STUDY_STUDY_FEED_FILENAME = "/studies.xml";
    String STUDY_FEED_FILE_PATH;
    String STUDY_ID = "XGQRX";
    String STUDIES_DIR_NAME;
    // String INVALID_STUDY_ID = "TFCBZ"; // live
    String INVALID_STUDY_ID = "AJYER"; // test
    
    String SERVICE_PROTOCOL_HOST_PORT = "http://localhost:8080";
    
    // Read in from file system - scrt pzzwd
    
    // This is just a dummy run to see if file exists
    // Not happy at duplication but we cannot check 
    // the properties file until we have set up the defaults
    configuraton = new Configuration("chassis-rest", defaults);
    if (!configuraton.configFileExists()) { 
      CHASSIS_DOWNLOAD_DIR = "data";
      INVALID_STUDY_ID = "AJYER";      
      System.err.println("Using dummy data");
    }    
    STUDY_FEED_FILE_PATH = CHASSIS_DOWNLOAD_DIR + STUDY_STUDY_FEED_FILENAME; 
    STUDIES_DIR_NAME = CHASSIS_DOWNLOAD_DIR + "/studies";
    
    defaults.put("DATA_DIR_NAME", "data");
    defaults.put("STUDY_FEED_FILE_PATH", STUDY_FEED_FILE_PATH); 
    defaults.put("STUDY_ID", STUDY_ID);
    defaults.put("INVALID_STUDY_ID", INVALID_STUDY_ID);
    defaults.put("STUDIES_DIR_NAME", STUDIES_DIR_NAME);
    defaults.put("SERVICE_PROTOCOL_HOST_PORT", SERVICE_PROTOCOL_HOST_PORT);
    
    // Create it again with new defaults
    configuraton = new Configuration("chassis-rest", defaults);
  }

  public Configuration getConfiguration() { 
    return configuraton;
  }
}
