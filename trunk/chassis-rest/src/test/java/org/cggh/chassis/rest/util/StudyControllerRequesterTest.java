package org.cggh.chassis.rest.util;

import junit.framework.TestCase;


public class StudyControllerRequesterTest extends TestCase {

  private String STUDY_ENTRY_DIR_NAME;
  protected static ChassisRestConfig config;
  public String SERVICE_PROTOCOL_HOST_PORT;
  private String STUDY_FEED_FILE_PATH;

  public StudyControllerRequesterTest() {
    super();
  }
  public StudyControllerRequesterTest(String name) {
    super(name);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    STUDY_ENTRY_DIR_NAME = "data/studies";
    config =  new ChassisRestConfig();
    SERVICE_PROTOCOL_HOST_PORT = config.getConfiguration().get("SERVICE_PROTOCOL_HOST_PORT");
    String dataDir =  "data/";
    STUDY_FEED_FILE_PATH = dataDir + "studies.xml";
  }
  
  protected String url(String url) {
    return SERVICE_PROTOCOL_HOST_PORT + "/chassis-rest/service" + url;
  }

  
  public void testValidate() throws Exception {
    String invalidStudyId = config.getConfiguration().get("INVALID_STUDY_ID");
    String studyFileName = "data/bad_" + invalidStudyId + ".xml";
    String url = url("/study/" + invalidStudyId + ".html");
    if (StudyControllerRequester.read(url).getStatus() == 200) 
      StudyControllerRequester.delete(url);
    System.out.println("Posting " + studyFileName + " to " + url("/study"));
    HttpResponse r = StudyControllerRequester.create(studyFileName, url("/study"));
    System.err.println("Create status:" + r.getStatus());
  }

  /**
   * Test method for {@link org.cggh.chassis.rest.util.StudyControllerRequester#create(java.lang.String, java.lang.String)}.
   */
  public void testPostStringString() throws Exception {
    String studyId = config.getConfiguration().get("STUDY_ID");
    String studyFileName = "data/good_" + studyId + ".xml";
    System.err.println("Posting " + studyFileName + " to " + url("/study"));
    // It may have been created already
    HttpResponse delResponse = StudyControllerRequester.delete(url("/study/" + studyId));
    System.err.println("Del reponse=" + delResponse.getStatus());
    HttpResponse postResponse = StudyControllerRequester.create(studyFileName, url("/study"));
    assertEquals(postResponse.getBody() , 201, postResponse.getStatus());
    assertTrue(postResponse.getBody().trim().endsWith("entry>"));
    HttpResponse putResponse = StudyControllerRequester.update(studyFileName, url("/study/" + studyId));
    assertEquals(putResponse.getBody(), 201, putResponse.getStatus());
    assertTrue(putResponse.getBody().trim().endsWith("entry>"));
    //System.err.println(putResponse.getPrettyBody());
  }

  public void testRead() throws Exception {
    HttpResponse response = StudyControllerRequester.read(url("/study/"));
    assertEquals(405, response.getStatus());
    
    response = StudyControllerRequester.read(url("/studies/"));   
    assertEquals(200, response.getStatus());    
  }


  public void testAddStudies() throws Exception { 
    System.err.println(STUDY_FEED_FILE_PATH);
    HttpResponse response = StudyControllerRequester.create(STUDY_FEED_FILE_PATH, url("/studies"));
    // Currently both live and test contain validation errors 
    assertEquals(response.getBody(), 400, response.getStatus());
  }

  public void testReadNotFound() throws Exception { 
    assertEquals(url("/study/notThere.xml"),  404, StudyControllerRequester.read(url("/study/notThere.xml")).getStatus());
    assertEquals(url("/study/notThere.html"), 404, StudyControllerRequester.read(url("/study/notThere.html")).getStatus());

    HttpResponse r = StudyControllerRequester.read(url("/study/notThere"));
    assertEquals(url("/study/notThere"),      404, r.getStatus());
    System.err.println(r.getPrettyBody());
    
    r = StudyControllerRequester.readAcceptingHtml(url("/study/notThere"));
    assertEquals(url("/study/notThere"),      404, r.getStatus());
  }

  public void testDelete() throws Exception { 
    HttpResponse deleteCollectionResponse = StudyControllerRequester.delete(url("/study/"));
    assertEquals(deleteCollectionResponse.getBody(), 405, deleteCollectionResponse.getStatus());
  
    // FIXME We are not marshalling if not found
    HttpResponse deleteNonExistent = StudyControllerRequester.delete(url("/study/notThere.xml"));
    assertEquals(deleteNonExistent.getBody(), 404, deleteNonExistent.getStatus());
  
    HttpResponse deleteNonExistentHtml = StudyControllerRequester.delete(url("/study/notThere.html"));
    assertEquals(deleteNonExistentHtml.getBody(), 404, deleteNonExistentHtml.getStatus());
    assertTrue(deleteNonExistentHtml.getBody(), deleteNonExistentHtml.getBody().indexOf("No study found with id notThere") > -1);
  }
  
  public void testCount() throws Exception { 
    HttpResponse response = StudyControllerRequester.readAcceptingHtml(url("/studyCount"));
    
    assertEquals(url("/studyCount"), 200, response.getStatus());

    //System.err.println(response.getBody());
  }

}