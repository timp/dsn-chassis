package org.cggh.chassis.rest.util;

import java.io.File;
import java.io.FilenameFilter;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 24 Oct 2011 15:29:26
 *
 */
public class StudyFeedSplitterTest extends TestCase {

  // change this manually to use wwarn.org data
  private static final String DATA = "data";
  //private static final String DATA = "downloaded";
  private static final String DATA_STUDIES = DATA + "/studies";
  private static final String STUDY_ID = "XGQRX";

  public StudyFeedSplitterTest(String name) {
    super(name);
  }

  public void testMain() {
  }

  public void testSplitString() throws Exception {
    File[] files = new File(DATA_STUDIES).listFiles(new FilenameFilter(){
      public boolean accept(File dir, String name) {
        return name.indexOf(".xml") > 0 ;
      } 
    });
    for (File child : files) {
      if (!child.delete()) 
        throw new RuntimeException("Could not delete " + child.getCanonicalPath());
    }
    File studyEntry = new File(DATA_STUDIES + "/" + STUDY_ID + ".xml");
    assertFalse("Study file not created", studyEntry.exists());
    StudyFeedSplitter.split(DATA + "/studies_feed.xml");
    assertTrue("Study file created", studyEntry.exists());
  }

  public void testSplitStringStringString() throws Exception {
  }

}
