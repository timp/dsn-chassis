package org.cggh.chassis.rest.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.cggh.casutils.CasProtectedResourceDownloader;
import org.cggh.casutils.CasProtectedResourceDownloaderFactory;
import org.cggh.casutils.NotFoundException;
import org.cggh.chassis.rest.configuration.Configuration;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 2011-11-16
 */
public class AlphabeticallyFirstGetStudyFeed extends TestCase {
  
  static Configuration configuration = new Configuration("chassis-rest");
  
  private static final String LOCAL_STUDIES_FEED_FILENAME = "downloaded/studies_feed.xml";
  private static final String wwarnLivePassord = configuration.get("wwarn-live-password");
  
  public void testGetWwarnStudyFeed() throws Exception {
    if (wwarnLivePassord != null) {
      runIt();
    } else { 
      System.err.println("No password set in " + configuration.getFileName());
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
  }

}
