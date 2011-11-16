package org.cggh.chassis.rest.util;

import java.io.File;
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
  private static final String LOCAL_STUDIES_FEED_FILENAME = "downloaded/studies_feed.xml";
  private static final String SVN_PASSWORD = "dont_change_this";
  private static final String CHANGE_THIS_TO_RUN_THE_TEST = "change_this";

  public void testGetWwarnStudyFeed() throws Exception {
    if (CHANGE_THIS_TO_RUN_THE_TEST != SVN_PASSWORD)
    {
      runIt();
    }
  }

  private void runIt() throws NotFoundException, IOException {
    Date start = new Date();
    System.err.println("Start:" + start);
    CasProtectedResourceDownloaderFactory.downloaders.put(
            CasProtectedResourceDownloaderFactory.keyFromUrl("https://www.wwarn.org/"),
            new CasProtectedResourceDownloader("https://", "www.wwarn.org:443",
                    "timp", CHANGE_THIS_TO_RUN_THE_TEST, "/tmp"));

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
