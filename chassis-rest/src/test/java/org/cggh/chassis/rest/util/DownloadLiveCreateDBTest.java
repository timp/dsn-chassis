package org.cggh.chassis.rest.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.cggh.casutils.CasProtectedResourceDownloader;
import org.cggh.casutils.CasProtectedResourceDownloaderFactory;
import org.cggh.casutils.NotFoundException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * @author timp
 * @since 2011-11-16
 */
public class DownloadLiveCreateDBTest extends AbstractUtilSpec {

  private static String PRUNED_FEED_FILE_PATH;
  private static String wwarnLivePassword;
  private static String wwarnLiveUser;
  
  public DownloadLiveCreateDBTest() { 
    super();
  }
  public DownloadLiveCreateDBTest(String name) { 
    super(name);
  }
  
  
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    wwarnLivePassword = config.getConfiguration().get("wwarn-live-password");
    wwarnLiveUser = config.getConfiguration().get("wwarn-live-user");
    PRUNED_FEED_FILE_PATH = config.getConfiguration().get("DATA_DIR_NAME") + "/chassis-rest-studies.xml";;
  }

  
  public void testGetWwarnChassis() throws Exception {
      setupXmlFiles();
      testPostsFromDirectory(DATA_STUDIES);
      HttpResponse response = StudyControllerRequester.readAcceptingHtml(url("/studyCount"));
      assertEquals(url("/studyCount"), 200, response.getStatus());
      System.err.println(response.getBody());
      if (response.getBody().indexOf("" + countEntries(FEED_FILE_PATH, "atom:entry")) > -1)
        System.err.println("Looks like there no are validation errors");
      else
        System.err.println("Looks like there are validation errors");
        
  }

  private void testPostsFromDirectory(String directory) throws Exception { 
    String url = url("/uncache");
    HttpResponse response = StudyControllerRequester.uncache(url);
    System.out.println(response.getBody());
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
  
  public void testPrune() { 
  //  XsltTransformer.transform(FEED_FILE_PATH, "prune.xsl", PRUNED_FEED_FILE_PATH, true);    
  }

  private void setupXmlFiles() throws NotFoundException, IOException {
    if (wwarnLivePassword != null && ! (new File(FEED_FILE_PATH).exists())) { 
      System.err.println("Downloading feed: " + FEED_FILE_PATH);
      downloadFeed();
    } else 
      System.err.println("Using existing feed");
    System.err.println("Config:" + config.getConfiguration());
    
    deleteExistingFiles();
    
    XsltTransformer.transform(FEED_FILE_PATH, "prune.xsl", PRUNED_FEED_FILE_PATH, true);
    
    String studyFileName = config.getConfiguration().get("STUDIES_DIR_NAME") 
            + "/" + config.getConfiguration().get("STUDY_ID") + ".xml";
    File studyEntry = new File(studyFileName );

    assertFalse("Study file " + studyFileName + "created", studyEntry.exists());
    StudyFeedSplitter.split(PRUNED_FEED_FILE_PATH);
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
  private void downloadFeed() throws NotFoundException, IOException {
    Date start = new Date();
    System.err.println("Start:" + start);
    CasProtectedResourceDownloaderFactory.downloaders.put(
            CasProtectedResourceDownloaderFactory.keyFromUrl("https://www.wwarn.org/"),
            new CasProtectedResourceDownloader("https://", "www.wwarn.org:443",
                    wwarnLiveUser, wwarnLivePassword, System.getProperty("java.io.tmpdir")));

    File studyOut = new File(FEED_FILE_PATH);
    studyOut.delete();
    String studyFeedUrl = "https://www.wwarn.org/repository/service/content/studies";

    CasProtectedResourceDownloader downloader =
            CasProtectedResourceDownloaderFactory.getCasProtectedResourceDownloader(studyFeedUrl);

    downloader.downloadUrlToFile(studyFeedUrl, studyOut);
    assertTrue(new File(FEED_FILE_PATH).exists());

    Date end = new Date();
    System.err.println("End:" + end);
    long diff = end.getTime() - start.getTime();
    System.err.println("Elapsed:" + diff / 1000);
  }

  private int countEntries(String xmlFile, String elementName) throws Exception { 
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
    System.out.println("Document contains " + nodes.getLength() + " " + elementName + " elements.");
    return nodes.getLength();
  }
}
