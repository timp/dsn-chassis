package org.cggh.chassis.rest.util;

/**
 * @author timp
 * @since 2011-11-16
 */
public class CreateTestDBTest extends AbstractUtilSpec {

  // private static String PRUNED_STUDY_FEED_FILE_PATH;
  
  public CreateTestDBTest() { 
    super();
  }
  public CreateTestDBTest(String name) { 
    super(name);
  }
  
  
  @Override
  protected void setUp() throws Exception {
    super.setUp();    
    String downloadedDir =  "data/";
    STUDY_FEED_FILE_PATH = downloadedDir + "studies.xml";
    STUDY_ENTRY_DIR_NAME = downloadedDir + "studies";
    //PRUNED_STUDY_FEED_FILE_PATH = downloadedDir + "/www.wwarn.org/chassis-rest-studies.xml";
  }

  

}
