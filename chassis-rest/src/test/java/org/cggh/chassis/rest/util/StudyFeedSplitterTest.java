package org.cggh.chassis.rest.util;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 24 Oct 2011 15:29:26
 *
 */
public class StudyFeedSplitterTest extends TestCase {

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
  public void testSplitString() {
    StudyFeedSplitter.split("studies_feed.xml");
  }

  /**
   * Test method for {@link org.cggh.chassis.rest.util.StudyFeedSplitter#split(java.lang.String, java.lang.String, java.lang.String)}.
   */
  public void testSplitStringStringString() throws Exception {
  }

}
