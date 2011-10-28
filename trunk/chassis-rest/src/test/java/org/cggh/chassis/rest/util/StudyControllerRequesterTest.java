package org.cggh.chassis.rest.util;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 25 Oct 2011 11:36:41
 *
 */
public class StudyControllerRequesterTest extends TestCase {

  public StudyControllerRequesterTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.cggh.chassis.rest.util.StudyControllerRequester#post(java.lang.String, java.lang.String)}.
   */
  public void testPostStringString() throws Exception {
    //StudyControllerRequester.post("TBYKQ.xml", "http://localhost:8080/chassis-rest/service/study");
    // invalid
    //StudyControllerRequester.post("studies/ACXFX.xml", "http://localhost:8080/chassis-rest/service/study");
    //StudyControllerRequester.post("studies/AJYER.xml", "http://localhost:8080/chassis-rest/service/study");
    //StudyControllerRequester.post("studies/AKUQT.xml", "http://localhost:8080/chassis-rest/service/study");
    //StudyControllerRequester.post("studies/EUKMD.xml", "http://localhost:8080/chassis-rest/service/study");
    
    // Badly formed
    //assertEquals(200, StudyControllerRequester.post("studies/ZZSPY.xml", "http://localhost:8888/chassis-rest/service/study"));
    
    
    assertEquals(200, StudyControllerRequester.post("studies/YGTAH.xml", "http://localhost:8888/chassis-rest/service/study"));
    
    
  }
  

  public void testPostAllStudies() throws Exception { 
    File studiesDir = new File("studies");
    @SuppressWarnings("unchecked")
    Iterator<File> it = FileUtils.iterateFiles(studiesDir, new String[] {"xml"}, false);
    int fileCount = 0;
    while(it.hasNext()){
      fileCount++;
      String studyFileName = "studies/" + it.next().getName(); 
      System.out.print(studyFileName);
      System.out.print(" - ");
      System.out.println(StudyControllerRequester.post(studyFileName, "http://localhost:8080/chassis-rest/service/study"));
    }
    System.out.println(fileCount);
    
  }


}
