package org.cggh.chassis.rest.util;


/**
 * @author timp
 * @since 2011-11-16
 */
public class CreateLiveDBTest extends AbstractUtilSpec {


  // private static String PRUNED_STUDY_FEED_FILE_PATH;
  
  public CreateLiveDBTest() { 
    super();
  }
  public CreateLiveDBTest(String name) { 
    super(name);    
  }
  
  
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    String downloadedDir = config.getConfiguration().get("DATA_DIR_NAME") + "/www.wwarn.org/";
    STUDY_FEED_FILE_PATH = downloadedDir + "studies.xml";
    STUDY_ENTRY_DIR_NAME = downloadedDir + "studies";
    LINK_FEED_FILE_PATH = downloadedDir + "links.xml";

    //PRUNED_STUDY_FEED_FILE_PATH = downloadedDir + "/www.wwarn.org/chassis-rest-studies.xml";
  }

  
  
}
