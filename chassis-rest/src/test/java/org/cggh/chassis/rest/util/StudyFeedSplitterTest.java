package org.cggh.chassis.rest.util;

import java.io.File;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 24 Oct 2011 15:29:26
 *
 */
public class StudyFeedSplitterTest extends TestCase {

  private static final String DATA_STUDIES = "data/studies";
  private static final String DATA = "data";

  public StudyFeedSplitterTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.cggh.chassis.rest.util.StudyFeedSplitter#main(java.lang.String[])}.
   */
  public void testMain() {
  }

  /**
   * Test method for {@link org.cggh.chassis.rest.util.StudyFeedSplitter#split(java.lang.String)}.
   */
  public void testSplitString() throws Exception {
    for (File child : new File(DATA_STUDIES).listFiles()) {
      if (!child.delete()) 
        throw new RuntimeException("Could not delete " + child.getCanonicalPath());
    }
    File studyEntry = new File(DATA_STUDIES + "/XGQRX.xml");
    assertFalse("Study file not created", studyEntry.exists());
    StudyFeedSplitter.split(DATA + "/studies_feed.xml");
    assertTrue("Study file created", studyEntry.exists());
  }

  /**
   * Test method for {@link org.cggh.chassis.rest.util.StudyFeedSplitter#split(java.lang.String, java.lang.String, java.lang.String)}.
   */
  public void testSplitStringStringString() throws Exception {
  }

}
