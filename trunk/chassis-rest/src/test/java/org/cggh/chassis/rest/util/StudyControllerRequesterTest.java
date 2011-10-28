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

  private static final String DATA_STUDIES = "data/studies/";

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
    //StudyControllerRequester.post("TBYKQ.xml", url("/study"));
    // invalid
    //StudyControllerRequester.post("studies/ACXFX.xml", url("/study"));
    //StudyControllerRequester.post("studies/AJYER.xml", url("/study"));
    //StudyControllerRequester.post("studies/AKUQT.xml", url("/study"));
    //StudyControllerRequester.post("studies/EUKMD.xml", url("/study"));
    
    // Badly formed
    //assertEquals(200, StudyControllerRequester.post("studies/ZZSPY.xml", url("/study"));
    
    HttpResponse postResponse = StudyControllerRequester.post("studies/YGTAH.xml", url("/study")); 
    // should be 201?
    assertEquals(postResponse.getBody(), 200, postResponse.getStatus());

    HttpResponse deleteCollectionResponse = StudyControllerRequester.delete(url("/study/")); 
    assertEquals(deleteCollectionResponse.getBody(), 405, deleteCollectionResponse.getStatus());
  
    HttpResponse deleteEntryResponse = StudyControllerRequester.delete(url("/study/YGTAH")); 
    
    assertEquals(deleteEntryResponse.getBody(), 200, deleteEntryResponse.getStatus());
  }
   private String url(String url) { 
     return "http://localhost:8888/chassis-rest/service" + url;
   }

  public void testPostAllStudies() throws Exception { 
    File studiesDir = new File(DATA_STUDIES );
    @SuppressWarnings("unchecked")
    Iterator<File> it = FileUtils.iterateFiles(studiesDir, new String[] {"xml"}, false);
    int fileCount = 0;
    while(it.hasNext()){
      fileCount++;
      String studyFileName = DATA_STUDIES  + it.next().getName(); 
      System.out.print(studyFileName);
      System.out.print(" - ");
      System.out.println(StudyControllerRequester.post(studyFileName, url("/study")));
    }
    assertEquals(1, fileCount);
    
  }


}
