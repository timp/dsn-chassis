package org.cggh.chassis.rest.util;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

import junit.framework.TestCase;

public class StudyControllerRequesterTest extends TestCase {


  protected String DATA_STUDIES; 
  protected String FULL_FEED_FILENAME;

  protected static ChassisRestConfig config;
  
  public StudyControllerRequesterTest() {
    super();
  }

  public StudyControllerRequesterTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
    config =  new ChassisRestConfig();
    DATA_STUDIES = config.getConfiguration().get("STUDIES_DIR_NAME") + "/";
    FULL_FEED_FILENAME = config.getConfiguration().get("FULL_FEED_FILENAME");
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  protected String url(String url) {
    return "http://localhost:8080/chassis-rest/service" + url;
  }

  public void testValidate() throws Exception {
    String invalidStudyId = config.getConfiguration().get("INVALID_STUDY_ID");
    String studyFileName = DATA_STUDIES + "/" + invalidStudyId + ".xml";
    System.out.println("Posting " + studyFileName + " to " + url("/study"));
    HttpResponse r = StudyControllerRequester.create(studyFileName, url("/study"));
    assertTrue(r.getBody(), r.getBody().contains(" is not valid"));    
    assertEquals(400, r.getStatus());
  
    assertEquals(404,StudyControllerRequester.delete(url("/study/" + invalidStudyId)).getStatus());
  }

  /**
   * Test method for {@link org.cggh.chassis.rest.util.StudyControllerRequester#create(java.lang.String, java.lang.String)}.
   */
  public void testPostStringString() throws Exception {
    String studyId = config.getConfiguration().get("STUDY_ID");
    String studyFileName = DATA_STUDIES + "/" + studyId + ".xml";
    System.out.println("Posting " + studyFileName + " to " + url("/study"));
    HttpResponse postResponse = StudyControllerRequester.create(studyFileName, url("/study"));
    assertEquals(postResponse.getBody() + "\nDelete:" + StudyControllerRequester.delete(url("/study/" + studyId)), 201, postResponse.getStatus());
  
  }

  public void testPostAllStudies() throws Exception {
    testPostsFromDirectory(DATA_STUDIES);
  }

  private void testPostsFromDirectory(String directory) throws Exception { 
    File studiesDir = new File(directory);
    @SuppressWarnings("unchecked")
    Iterator<File> it = FileUtils.iterateFiles(studiesDir, new String[] { "xml" }, false);
    int fileCount = 0;
    int failCount = 0;
    while (it.hasNext()) {
      fileCount++;
      File f = it.next();
      String studyFileName = directory + f.getName();
      String entryUrl = url("/study/" + f.getName());
      if (StudyControllerRequester.read(url("/study/" + f.getName())).getStatus() == 200) {
        int deleteStatus = StudyControllerRequester.delete(entryUrl).getStatus();
        System.err.println("Deleted existing " + entryUrl + " : " + deleteStatus);
      }
      HttpResponse r = StudyControllerRequester.create(studyFileName, url("/study"));
      //System.out.println(studyFileName);
      //System.out.print(" - ");
      //System.out.println(r.getBody());
      if (r.getStatus() != 201) {
        System.out.print(studyFileName);
        System.out.print(" - ");
        System.out.println(r.getBody());
        if (r.getBody().indexOf("errors") > 0) { 
          System.err.println(r.getBody());
        }
        failCount ++;
      } else {
        //StudyControllerRequester.delete(url(entryUrl)).getStatus();
      }
    }
  
    System.out.println("Files: " + fileCount + " fail count: " + failCount);
    
  }

  public void testRead() throws Exception {
    HttpResponse response = StudyControllerRequester.read(url("/study/"));
    assertEquals(405, response.getStatus());
    response = StudyControllerRequester.read(url("/studies/"));   
    assertEquals(200, response.getStatus());
    //System.out.println(response.getBody());
  }

  public void testAddStudies() throws Exception { 
    System.err.println(FULL_FEED_FILENAME);
    HttpResponse response = StudyControllerRequester.create(FULL_FEED_FILENAME, url("/study"));
    System.out.println(response.getBody());
  }

  public void testReadNotFound() throws Exception { 
    // FIXME We are not marshalling if not found
    assertEquals(url("/study/notThere.xml"),  500, StudyControllerRequester.read(url("/study/notThere.xml")).getStatus());
    assertEquals(url("/study/notThere.html"), 404, StudyControllerRequester.read(url("/study/notThere.html")).getStatus());
    assertEquals(url("/study/notThere"),      404, StudyControllerRequester.read(url("/study/notThere")).getStatus());
  }

  public void testDelete() throws Exception { 
    HttpResponse deleteCollectionResponse = StudyControllerRequester.delete(url("/study/"));
    assertEquals(deleteCollectionResponse.getBody(), 405, deleteCollectionResponse.getStatus());
  
    // FIXME We are not marshalling if not found
    HttpResponse deleteNonExistent = StudyControllerRequester.delete(url("/study/notThere.xml"));
    assertEquals(deleteNonExistent.getBody(), 500, deleteNonExistent.getStatus());
  
    HttpResponse deleteNonExistentHtml = StudyControllerRequester.delete(url("/study/notThere.html"));
    assertEquals(deleteNonExistentHtml.getBody(), 404, deleteNonExistentHtml.getStatus());
    
  }

}