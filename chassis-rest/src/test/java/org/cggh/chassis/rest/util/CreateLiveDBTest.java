package org.cggh.chassis.rest.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

/**
 * @author timp
 * @since 2011-11-16
 */
public class CreateLiveDBTest extends AbstractUtilSpec {

  // private static String PRUNED_STUDY_FEED_FILE_PATH;
  static String wwarnLivePassword;
  static String wwarnLiveUser;
  
  public CreateLiveDBTest() { 
    super();
  }
  public CreateLiveDBTest(String name) { 
    super(name);
  }
  
  
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    //PRUNED_STUDY_FEED_FILE_PATH = config.getConfiguration().get("DATA_DIR_NAME") + "/chassis-rest-studies.xml";;
  }

  
  public void testGetWwarnChassis() throws Exception {
      setupXmlFiles();
      testPostsFromDirectory(DATA_STUDIES);
      //postFeeds();
      HttpResponse response = StudyControllerRequester.readAcceptingHtml(url("/studyCount"));
      assertEquals(url("/studyCount"), 200, response.getStatus());
      if (response.getBody().indexOf("" + (countEntries(STUDY_FEED_FILE_PATH, "atom:entry") 
              + countEntries(STUDY_FEED_FILE_PATH, "atom:entry"))) > -1)
        System.out.println("Looks like there no are validation errors");
      else
        System.out.println("Looks like there are validation errors");
        
  }
  
  private void testPostsFromDirectory(String directory) throws Exception { 
    String url = url("/uncache");
    HttpResponse response = StudyControllerRequester.uncache(url);
    assertEquals(url, 200, response.getStatus());
    
    url = url("/studies/");
    response = StudyControllerRequester.read(url);   
    assertEquals(url, 200, response.getStatus());
    
    
    assertTrue(response.getBody(), response.getBody().indexOf('\n') == -1); // empty feed

    
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
        System.out.println(studyFileName);
        System.out.print(" - ");
        if (r.getBody().indexOf("errors") > 0) { 
          System.err.println(r.getPrettyBody());
        }
        failCount ++;
      } else {
        StudyControllerRequester.delete(url(entryUrl)).getStatus();
      }
    }
  
    System.out.println("Files: " + fileCount + " fail count: " + failCount);

    
  }
  private void postFeeds() throws Exception { 
    String url = url("/uncache");
    HttpResponse response = StudyControllerRequester.uncache(url);
    assertEquals(url, 200, response.getStatus());
    url = url("/studies");
    response = StudyControllerRequester.create(STUDY_FEED_FILE_PATH, url );
    // We know there are currently failing entries
    assertEquals(response.getBody(), 400, response.getStatus());
    url = url("/links");
    response = StudyControllerRequester.create(LINK_FEED_FILE_PATH, url("/links"));
    assertEquals(url, 201, response.getStatus());
  }

  
  public void testPrune() { 
  //  XsltTransformer.transform(STUDY_FEED_FILE_PATH, "prune.xsl", PRUNED_STUDY_FEED_FILE_PATH, true);    
  }

  private void setupXmlFiles() throws IOException {
    
    deleteExistingFiles();
    
    //XsltTransformer.transform(STUDY_FEED_FILE_PATH, "prune.xsl", PRUNED_STUDY_FEED_FILE_PATH, true);
    
    String studyFileName = config.getConfiguration().get("STUDIES_DIR_NAME") 
            + "/" + config.getConfiguration().get("STUDY_ID") + ".xml";
    File studyEntry = new File(studyFileName );

    assertFalse("Study file " + studyFileName + "created", studyEntry.exists());
    // StudyFeedSplitter.split(PRUNED_STUDY_FEED_FILE_PATH);
    StudyFeedSplitter.split(STUDY_FEED_FILE_PATH);
    assertTrue("Study file " + studyFileName + " not created", studyEntry.exists());
  }

  private void deleteExistingFiles() throws IOException {
    String studiesDirName = config.getConfiguration().get("STUDIES_DIR_NAME");
    File[] files = new File(studiesDirName)
        .listFiles(new FilenameFilter(){
      public boolean accept(File dir, String name) {
        return name.indexOf(".xml") > 0 ;
      } 
    });
    for (File child : files) {
      if (!child.delete()) 
        throw new RuntimeException("Could not delete " + child.getCanonicalPath());
    }
  }
}
