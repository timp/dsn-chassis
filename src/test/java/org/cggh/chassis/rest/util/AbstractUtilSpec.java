package org.cggh.chassis.rest.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 2011-12-01
 */
public abstract class AbstractUtilSpec extends TestCase {
  public String STUDY_ENTRY_DIR_NAME;
  public String STUDY_FEED_FILE_PATH;
  public String LINK_FEED_FILE_PATH;
  public String PRUNED_STUDY_FEED_FILE_PATH;
  public String SERVICE_PROTOCOL_HOST_PORT;
  protected static ChassisRestConfig config;
  protected String studiesDirName;
  
  public AbstractUtilSpec() {
    super();
  }
  public AbstractUtilSpec(String name) {
    super(name);
  }
  
  protected void setUp() throws Exception {
    super.setUp();
    config =  new ChassisRestConfig();

    SERVICE_PROTOCOL_HOST_PORT = config.getConfiguration().get("SERVICE_PROTOCOL_HOST_PORT");
  }
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  protected String url(String url) {
    return SERVICE_PROTOCOL_HOST_PORT + "/chassis-rest/service" + url;
  }
  
  public static int countEntries(String xmlFile, String elementName) throws Exception { 
    File file = new File(xmlFile);
    if (!file.exists())
      throw new IllegalArgumentException("File (" + file.getCanonicalPath() + ") not found");
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
     // Create the builder and parse the file
    Document doc;
    try {
      doc = factory.newDocumentBuilder().parse(xmlFile);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    NodeList nodes = doc.getElementsByTagName(elementName);
    return nodes.getLength();
  }

  protected void setupXmlFiles(String dirName) throws IOException {
    
    deleteExistingFiles(dirName);
    
    //XsltTransformer.transform(STUDY_FEED_FILE_PATH, "prune.xsl", PRUNED_STUDY_FEED_FILE_PATH, true);
    
    String studyFileName = dirName 
            + "/" + config.getConfiguration().get("STUDY_ID") + ".xml";
    File studyEntry = new File(studyFileName );
    System.err.println(studyFileName);

    assertFalse("Study file " + studyFileName + " created", studyEntry.exists());
    // StudyFeedSplitter.split(PRUNED_STUDY_FEED_FILE_PATH);
    System.err.println(STUDY_FEED_FILE_PATH);
    StudyFeedSplitter.split(STUDY_FEED_FILE_PATH);
    assertTrue("Study file " + studyFileName + " not created", studyEntry.exists());
  }

  protected void testPostsFromDirectory(String directory) throws Exception { 
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
      String studyFileName = directory + "/" + f.getName();
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
  
  public void testPostStudies() throws Exception {
    setupXmlFiles(STUDY_ENTRY_DIR_NAME);
    testPostsFromDirectory(STUDY_ENTRY_DIR_NAME);
    HttpResponse response = StudyControllerRequester.readAcceptingHtml(url("/studyCount"));
    assertEquals(url("/studyCount"), 200, response.getStatus());
    if (response.getBody().indexOf("" + (countEntries(STUDY_FEED_FILE_PATH, "atom:entry") 
              + countEntries(STUDY_FEED_FILE_PATH, "atom:entry"))) > -1)
      System.out.println("Looks like there no are validation errors");
    else
      System.out.println("Looks like there are validation errors");
  }
  
  public void testPostLinkedStudies() throws Exception { 
    String url = url("/links");
    HttpResponse response = StudyControllerRequester.create(LINK_FEED_FILE_PATH, url);
    assertEquals(LINK_FEED_FILE_PATH + "=>" + url, 201, response.getStatus());    
  }

  
  public void testPrune() { 
    XsltTransformer.transform(STUDY_FEED_FILE_PATH, "prune.xsl", PRUNED_STUDY_FEED_FILE_PATH, true);    
  }


  protected void deleteExistingFiles(String directoryName) throws IOException {
    File[] files = new File(directoryName)
        .listFiles(new FilenameFilter(){
      public boolean accept(File dir, String name) {
        return name.indexOf(".xml") > 0 ;
      } 
    });
    if (files != null)
      for (File child : files) {
        if (!child.delete()) 
          throw new RuntimeException("Could not delete " + child.getCanonicalPath());
      }
  }
  
}


