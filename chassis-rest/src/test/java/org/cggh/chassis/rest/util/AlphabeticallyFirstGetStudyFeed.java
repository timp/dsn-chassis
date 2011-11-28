package org.cggh.chassis.rest.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Date;

import org.cggh.casutils.CasProtectedResourceDownloader;
import org.cggh.casutils.CasProtectedResourceDownloaderFactory;
import org.cggh.casutils.NotFoundException;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 2011-11-16
 */
public class AlphabeticallyFirstGetStudyFeed extends TestCase {

  protected static ChassisRestConfig config;
  
  
  private static final String LOCAL_STUDIES_FEED_FILENAME = "downloaded/studies_feed.xml";
  private static String wwarnLivePassord;
  
  public AlphabeticallyFirstGetStudyFeed() { 
    super();
  }
  public AlphabeticallyFirstGetStudyFeed(String name) { 
    super(name);
  }
  
  
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    config = new ChassisRestConfig();
    wwarnLivePassord = config.getConfiguration().get("wwarn-live-password");
  }

  
  public void testGetWwarnStudyFeed() throws Exception {
    if (wwarnLivePassord != null) {
      runIt();
    } else { 
      System.err.println("No password set in " + config.getConfiguration().getFileName());
    }
      
  }

  private void runIt() throws NotFoundException, IOException {
    Date start = new Date();
    System.err.println("Start:" + start);
    CasProtectedResourceDownloaderFactory.downloaders.put(
            CasProtectedResourceDownloaderFactory.keyFromUrl("https://www.wwarn.org/"),
            new CasProtectedResourceDownloader("https://", "www.wwarn.org:443",
                    "timp", wwarnLivePassord, "/tmp"));

    File studyOut = new File(LOCAL_STUDIES_FEED_FILENAME);
    studyOut.delete();
    String studyFeedUrl = "https://www.wwarn.org/repository/service/content/studies";

    CasProtectedResourceDownloader downloader =
            CasProtectedResourceDownloaderFactory.getCasProtectedResourceDownloader(studyFeedUrl);

    downloader.downloadUrlToFile(studyFeedUrl, studyOut);
    assertTrue(new File(LOCAL_STUDIES_FEED_FILENAME).exists());

    Date end = new Date();
    System.err.println("End:" + end);
    long diff = end.getTime() - start.getTime();
    System.err.println("Elapsed:" + diff / 1000);
    
    
    System.err.println(config.getConfiguration());
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
    String studyFileName = config.getConfiguration().get("STUDIES_DIR_NAME") 
            + "/" + config.getConfiguration().get("STUDY_ID") + ".xml";
    File studyEntry = new File(studyFileName );
    assertFalse("Study file " + studyFileName + "created", studyEntry.exists());
    String feedFileName = config.getConfiguration().get("FULL_FEED_FILENAME");
    System.err.println("ff"+feedFileName);
    StudyFeedSplitter.split(config.getConfiguration().get("FULL_FEED_FILENAME"));
    assertTrue("Study file " + studyFileName + " not created", studyEntry.exists());
    
  }

}
