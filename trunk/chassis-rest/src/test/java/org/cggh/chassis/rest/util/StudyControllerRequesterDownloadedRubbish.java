package org.cggh.chassis.rest.util;

/**
 * @author timp
 * @since 25 Oct 2011 11:36:41
 *
 */
public class StudyControllerRequesterDownloadedRubbish  {

  public String getDataDir() {
    return "downloaded";
  }
  
  public void testValidate() throws Exception { 
    /**
    String invalidStudyId = "TFCBZ";  
    String fileName = DATA_STUDIES + invalidStudyId +".xml";
    HttpResponse r = StudyControllerRequester.create(fileName, url("/study"));
    System.err.println(r.getBody());
    assertTrue(r.getBody(), r.getBody().contains("is not a valid value for"));    
    assertEquals(400, r.getStatus());
  
    assertEquals(404,StudyControllerRequester.delete(url("/study/" + invalidStudyId)).getStatus());
    */
  }


}
