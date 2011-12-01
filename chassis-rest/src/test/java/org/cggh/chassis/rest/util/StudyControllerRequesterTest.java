package org.cggh.chassis.rest.util;


public class StudyControllerRequesterTest extends AbstractUtilSpec {


  public StudyControllerRequesterTest() {
    super();
  }

  public StudyControllerRequesterTest(String name) {
    super(name);
  }


  protected String url(String url) {
    return SERVICE_PROTOCOL_HOST_PORT + "/chassis-rest/service" + url;
  }

  public void testValidate() throws Exception {
    String invalidStudyId = config.getConfiguration().get("INVALID_STUDY_ID");
    String studyFileName = DATA_STUDIES + "/" + invalidStudyId + ".xml";
    System.out.println("Posting " + studyFileName + " to " + url("/study"));
    HttpResponse r = StudyControllerRequester.create(studyFileName, url("/study"));
    assertTrue(r.getBody(), r.getBody().contains(" is not valid"));    
    assertEquals(400, r.getStatus());
  
    assertEquals(404,StudyControllerRequester.delete(url("/study/" + invalidStudyId + ".html")).getStatus());
  }

  /**
   * Test method for {@link org.cggh.chassis.rest.util.StudyControllerRequester#create(java.lang.String, java.lang.String)}.
   */
  public void testPostStringString() throws Exception {
    String studyId = config.getConfiguration().get("STUDY_ID");
    String studyFileName = DATA_STUDIES + "/" + studyId + ".xml";
    System.out.println("Posting " + studyFileName + " to " + url("/study"));
    // It may have been created already
    HttpResponse delResponse = StudyControllerRequester.delete(url("/study/" + studyId));
    System.err.println("Del reponse=" + delResponse.getStatus());
    HttpResponse postResponse = StudyControllerRequester.create(studyFileName, url("/study"));
    assertEquals(postResponse.getBody() , 201, postResponse.getStatus());
  
  }


  public void testRead() throws Exception {
    HttpResponse response = StudyControllerRequester.read(url("/study/"));
    assertEquals(405, response.getStatus());
    
    response = StudyControllerRequester.read(url("/studies/"));   
    assertEquals(200, response.getStatus());    
    System.out.println(response.getBody());
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
    // This is due to not inferring the correct template when no extension
    assertEquals(url("/study/notThere"),      500, StudyControllerRequester.read(url("/study/notThere")).getStatus());
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