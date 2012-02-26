package org.cggh.chassis.rest.util;


/**
 * @author timp
 * @since 2011-11-16
 */
public class CreatePrunedDBTest extends AbstractUtilSpec {

  
  public CreatePrunedDBTest() { 
    super();
  }
  public CreatePrunedDBTest(String name) { 
    super(name);    
  }
  
  
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    String downloadedDir = config.getConfiguration().get("DATA_DIR_NAME") + "/www.wwarn.org/";
    STUDY_FEED_FILE_PATH = downloadedDir + "studies.xml";
    STUDY_ENTRY_DIR_NAME = downloadedDir + "studies";
    LINK_FEED_FILE_PATH = downloadedDir + "links.xml";

    PRUNED_STUDY_FEED_FILE_PATH = downloadedDir + "pruned-studies.xml";
  }

  public void testPostStudies() throws Exception { 
    if(!postStudies())
      fail("There are validation errors");
  }
  
}
