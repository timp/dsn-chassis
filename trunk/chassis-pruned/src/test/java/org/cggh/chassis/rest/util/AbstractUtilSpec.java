package org.cggh.chassis.rest.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.cggh.chassis.rest.configuration.Configuration;

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
    return SERVICE_PROTOCOL_HOST_PORT + "/chassis-pruned/service" + url;
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
    
    XsltTransformer.transform(STUDY_FEED_FILE_PATH, "prune.xsl", PRUNED_STUDY_FEED_FILE_PATH, true);
    
    String studyFileName = dirName 
            + "/" + config.getConfiguration().get("STUDY_ID") + ".xml";
    File studyEntry = new File(studyFileName );
    System.err.println(studyFileName);

    assertFalse("Study file " + studyFileName + " created", studyEntry.exists());
    StudyFeedSplitter.split(PRUNED_STUDY_FEED_FILE_PATH);
    //StudyFeedSplitter.split(STUDY_FEED_FILE_PATH);
    assertTrue("Study file " + studyFileName + " not created", studyEntry.exists());
  }

  protected void testPostsFromDirectory(String directory) throws Exception { 
    String url = url("/uncache");
    HttpResponse response = StudyControllerRequester.uncache(url);
    assertEquals(url, 200, response.getStatus());
    
    
    System.err.println("Checking that /studies is empty");
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
      System.err.println("Creating from " +studyFileName + " to " + url("/study"));
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
        System.err.println("Deleting " + url(entryUrl));
        StudyControllerRequester.delete(url(entryUrl)).getStatus();
      }
    }
  
    System.out.println("Files: " + fileCount + " fail count: " + failCount);
    
    System.err.println("\nCreating views");
    executeEach(getConnection("chassisPruned"), slurpFile("src/main/sql/views.sql"));
  }

  
  public boolean postStudies() throws Exception {
    setupXmlFiles(STUDY_ENTRY_DIR_NAME);
    testPostsFromDirectory(STUDY_ENTRY_DIR_NAME);
    HttpResponse response = StudyControllerRequester.readAcceptingHtml(url("/studyCount"));
    assertEquals(url("/studyCount"), 200, response.getStatus());
    int entriesCount = countEntries(STUDY_FEED_FILE_PATH, "atom:entry"); 

    if (response.getBody().indexOf("Count:" + entriesCount) > -1)
      return true;
    else
      return false;
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
  
  public static String slurpFile(String path) throws IOException {
    FileInputStream stream = new FileInputStream(new File(path));
    try {
      FileChannel fc = stream.getChannel();
      MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
      /* Instead of using default, pass in a decoder. */
      return Charset.defaultCharset().decode(bb).toString();
    }
    finally {
      stream.close();
    }
  }  

  public static  int[] executeEach(Connection conn, String statements) throws SQLException{ 
    String[] statementArray = statements.split(";"); 
    ArrayList<Integer> r = new ArrayList<Integer>();
    for (String sql : statementArray){
      sql = sql.trim();
      if(!sql.equals("")){
        sql=sql+";";
        r.add(new Integer(execute(conn,sql)));
      }
    }
    int[] result = new int[r.size()];
    for (int i = 0; i < result.length; i++) {
      Integer   b = r.get(i);
      result[i] = (b == null ? 0 : b.intValue());
    }

    return result;
  }
  public static int execute(Connection conn, String statement) throws SQLException  { 
    
    Statement s = conn.createStatement();
    boolean returnSwitch; 
    try { 
      returnSwitch = s.execute(statement);
    } catch (SQLException e) { 
      throw new RuntimeException ("Error in SQL: \n" + statement, e);
    }
    int result;
    if (returnSwitch)
      result = 0;
    else 
      result = s.getUpdateCount();
    s.close();
    return result;
  }

  public static Connection getConnection(String dbName) {
    Configuration config = new Configuration("chassis-pruned", dbName);
    String dbBaseUrl = config.getSetProperty("dbBaseUrl"); // "jdbc:mysql://charlie.well.ox.ac.uk:3306/"
    String user = config.getSetProperty("user"); // "root";
    String password = config.get("password"); // optional
    Connection conn = null;
    String driver = "com.mysql.jdbc.Driver";
    try {
      Class.forName(driver).newInstance();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    try {
      conn = DriverManager.getConnection(dbBaseUrl + dbName, user, password);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return conn;
  }
}


