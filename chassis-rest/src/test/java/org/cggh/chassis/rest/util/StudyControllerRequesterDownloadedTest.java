package org.cggh.chassis.rest.util;

/**
 * @author timp
 * @since 25 Oct 2011 11:36:41
 *
 */
public class StudyControllerRequesterDownloadedTest extends StudyControllerRequestSpec {

  public StudyControllerRequesterDownloadedTest(String name) {
    super(name);
  }
  @Override
  public String getDataDir() {
    return "downloaded";
  }
  
  public void testValidate() throws Exception { 
    String invalidStudyId = "HUNKC";
    String fileName = DATA_STUDIES + invalidStudyId +".xml";
    HttpResponse r = StudyControllerRequester.create(fileName, url("/study"));
    //assertTrue(r.getBody(), r.getBody().contains("The value 'Yesterday' of element 'study-is-published' is not valid"));    
    assertEquals(400, r.getStatus());
  
    assertEquals(404,StudyControllerRequester.delete(url("/study/" + invalidStudyId)).getStatus());
  }


}
