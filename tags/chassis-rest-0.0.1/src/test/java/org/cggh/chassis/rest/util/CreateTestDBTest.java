package org.cggh.chassis.rest.util;

/**
 * @author timp
 * @since 2011-11-16
 */
public class CreateTestDBTest extends AbstractUtilSpec {

  
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
    LINK_FEED_FILE_PATH = downloadedDir + "links.xml";
    PRUNED_STUDY_FEED_FILE_PATH = downloadedDir + "pruned-studies.xml";
  }

  

}
