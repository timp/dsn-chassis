package org.cggh.chassis.rest.util;

/**
 * This test ensures that we do not lose the ability to detect if 
 * a feed has spontaneously lost nodes. 
 * 
 * @author timp
 * @since 2012-02-24
 */
public class CatchCorruptedFeedTest extends AbstractUtilSpec {

  
  public CatchCorruptedFeedTest() { 
    super();
  }
  public CatchCorruptedFeedTest(String name) { 
    super(name);
  }
  
  
  @Override
  protected void setUp() throws Exception {
    super.setUp();    
    String downloadedDir =  "data/";
    STUDY_FEED_FILE_PATH = downloadedDir + "studies_missing_nodes.xml";
    STUDY_ENTRY_DIR_NAME = downloadedDir + "studies";
    LINK_FEED_FILE_PATH  = downloadedDir + "links.xml";
    PRUNED_STUDY_FEED_FILE_PATH = downloadedDir + "pruned-studies.xml";
  }

  public void testPostStudies() throws Exception { 
    if(postStudies())
      fail("There should have been validation errors");
  }
  

}
