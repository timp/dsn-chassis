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

  private String url(String url) {
    String fullUrl =  "http://localhost:8888/chassis-rest/service" + url;
    //System.err.println(fullUrl);
    return fullUrl;
  }

  public void testValidate() throws Exception { 
    HttpResponse r = StudyControllerRequester.create("studies/AJYER.xml", url("/study"));
    if (r.getBody().indexOf("errors") > 0)
      System.err.println(r.getBody());
    
  }
  
  /**
   * Test method for {@link org.cggh.chassis.rest.util.StudyControllerRequester#create(java.lang.String, java.lang.String)}.
   */
  public void testPostStringString() throws Exception {
    //StudyControllerRequester.create("TBYKQ.xml", url("/study"));
    // invalid
    //StudyControllerRequester.create("studies/ACXFX.xml", url("/study"));
    //StudyControllerRequester.create("studies/AJYER.xml", url("/study"));
    //StudyControllerRequester.create("studies/AKUQT.xml", url("/study"));
    //StudyControllerRequester.create("studies/EUKMD.xml", url("/study"));

    /*
    HttpResponse r = StudyControllerRequester.post("studies/ZZSPY.xml", url("/study"));
    assertEquals(200, r.getStatus());
    System.err.println(r.getBody());
    */

    String studiesDir = DATA_STUDIES;
    HttpResponse postResponse = StudyControllerRequester.create(studiesDir + "XGQRX.xml", url("/study"));
    // should be 201?
    assertEquals(postResponse.getBody(), 200, postResponse.getStatus());

    HttpResponse deleteCollectionResponse = StudyControllerRequester.delete(url("/study/"));
    assertEquals(deleteCollectionResponse.getBody(), 405, deleteCollectionResponse.getStatus());

    HttpResponse deleteEntryResponse = StudyControllerRequester.delete(url("/study/XGQRX"));
    assertEquals(deleteEntryResponse.getBody(), 200, deleteEntryResponse.getStatus());

  }

  public void testPostAllStudies() throws Exception {
    File studiesDir = new File(DATA_STUDIES);
    //File studiesDir = new File("studies");
    @SuppressWarnings("unchecked")
    Iterator<File> it = FileUtils.iterateFiles(studiesDir, new String[] { "xml" }, false);
    int fileCount = 0;
    while (it.hasNext()) {
      fileCount++;
      File f = it.next();
      String studyFileName = DATA_STUDIES + f.getName();
      //String studyFileName = "studies/"  + it.next().getName();
      HttpResponse r = StudyControllerRequester.create(studyFileName, url("/study"));
      System.out.print(studyFileName);
      System.out.print(" - ");
      System.out.println(r.getBody());
      if (r.getStatus() == 500) {
        System.out.print(studyFileName);
        System.out.print(" - ");
        System.out.println(r.getBody());
      } else {
        if (r.getBody().indexOf("errors") > 0)
          System.err.println(r.getBody());
      }
      // Think you need to delete to rollback
      StudyControllerRequester.delete(url("/study/" + f.getName())).getStatus();
    }

    System.out.println("Files: " + fileCount);
    assertEquals(2, fileCount);
  }

  public void testRead() throws Exception {
    HttpResponse response = StudyControllerRequester.read(url("/study/"));
    assertEquals(405, response.getStatus());
    
    response = StudyControllerRequester.read(url("/studies/"));   
    assertEquals(200, response.getStatus());
    System.out.println(response.getBody());
  }

}
