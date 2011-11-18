package org.cggh.chassis.rest.util;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

import junit.framework.TestCase;

public abstract class StudyControllerRequestSpec extends TestCase {

  protected String DATA_STUDIES; 
  protected String FEED_FILE_NAME;

  
  
  public abstract String getDataDir(); 
  
  public StudyControllerRequestSpec() {
    super();
  }

  public StudyControllerRequestSpec(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
    DATA_STUDIES = getDataDir() + "/studies/";
    FEED_FILE_NAME = getDataDir() +"/studies_feed.xml";
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  protected String url(String url) {
    return "http://localhost:8080/chassis-rest/service" + url;
  }

  public void testValidate() throws Exception { 
    String fileName = DATA_STUDIES + "AJYER.xml";
    HttpResponse r = StudyControllerRequester.create(fileName, url("/study"));
    assertTrue(r.getBody(), r.getBody().contains("The value 'Yesterday' of element 'study-is-published' is not valid"));    
    assertEquals(400, r.getStatus());
  
    assertEquals(404,StudyControllerRequester.delete(url("/study/AJYER")).getStatus());
  }

  /**
   * Test method for {@link org.cggh.chassis.rest.util.StudyControllerRequester#create(java.lang.String, java.lang.String)}.
   */
  public void testPostStringString() throws Exception {
    HttpResponse postResponse = StudyControllerRequester.create(DATA_STUDIES + "XGQRX.xml", url("/study"));
    assertEquals(postResponse.getBody(), 201, postResponse.getStatus());
  
    HttpResponse deleteEntryResponse = StudyControllerRequester.delete(url("/study/XGQRX"));
    assertEquals(deleteEntryResponse.getBody(), 200, deleteEntryResponse.getStatus());
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
      HttpResponse r = StudyControllerRequester.create(studyFileName, url("/study"));
      System.out.println(studyFileName);
      //System.out.print(" - ");
      //System.out.println(r.getBody());
      if (r.getStatus() != 201) {
        System.out.print(studyFileName);
        System.out.print(" - ");
        System.out.println(r.getBody());
        if (r.getBody().indexOf("errorList") > 0) { 
          System.err.println(r.getBody());
        }
        failCount ++;
      } else {
        StudyControllerRequester.delete(url("/study/" + f.getName())).getStatus();
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
    HttpResponse response = StudyControllerRequester.create(FEED_FILE_NAME, url("/study"));
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