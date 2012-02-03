package org.cggh.chassis.rest.util;

import java.io.File;

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
    STUDY_ENTRY_DIR_NAME = config.getConfiguration().get("STUDY_ENTRY_DIR_NAME") + "/";
    LINK_FEED_FILE_PATH = config.getConfiguration().get("DATA_DIR_NAME") + "/link_feed.xml";

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
  
}


