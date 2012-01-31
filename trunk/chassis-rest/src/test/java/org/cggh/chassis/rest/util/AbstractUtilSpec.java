package org.cggh.chassis.rest.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.xml.parsers.DocumentBuilderFactory;

import org.cggh.casutils.CasProtectedResourceDownloader;
import org.cggh.casutils.CasProtectedResourceDownloaderFactory;
import org.cggh.casutils.NotFoundException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 2011-12-01
 */
public abstract class AbstractUtilSpec extends TestCase {
  public String DATA_STUDIES;
  public String STUDY_FEED_FILE_PATH;
  public String SERVICE_PROTOCOL_HOST_PORT;
  public String LINK_FEED_FILE_PATH;
  protected static ChassisRestConfig config;
  
  public AbstractUtilSpec() {
    super();
  }
  public AbstractUtilSpec(String name) {
    super(name);
  }
  
  protected void setUp() throws Exception {
    super.setUp();
    config =  new ChassisRestConfig();
    DATA_STUDIES = config.getConfiguration().get("STUDIES_DIR_NAME") + "/";
    STUDY_FEED_FILE_PATH = config.getConfiguration().get("STUDY_FEED_FILE_PATH");
    LINK_FEED_FILE_PATH = config.getConfiguration().get("DATA_DIR_NAME") + "/link_feed.xml";

    SERVICE_PROTOCOL_HOST_PORT = config.getConfiguration().get("SERVICE_PROTOCOL_HOST_PORT");
  }
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  protected String url(String url) {
    return SERVICE_PROTOCOL_HOST_PORT + "/chassis-rest/service" + url;
  }
  protected void downloadFeed(String outputFileName, String feedUrl, String user, String password) throws NotFoundException, IOException {
    Date start = new Date();
    System.err.println("Start:" + start);
    CasProtectedResourceDownloaderFactory.downloaders.put(
            CasProtectedResourceDownloaderFactory.keyFromUrl("https://www.wwarn.org/"),
            new CasProtectedResourceDownloader("https://", "www.wwarn.org:443",
                    user, password, System.getProperty("java.io.tmpdir")));
  
    File fileOut = new File(outputFileName);
    fileOut.delete();
  
    CasProtectedResourceDownloader downloader =
            CasProtectedResourceDownloaderFactory.getCasProtectedResourceDownloader(feedUrl);
  
    downloader.downloadUrlToFile(feedUrl, fileOut);
    assertTrue(new File(outputFileName).exists());
  
    Date end = new Date();
    System.err.println("End:" + end);
    long diff = end.getTime() - start.getTime();
    System.err.println("Elapsed:" + diff / 1000);
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
  
}


