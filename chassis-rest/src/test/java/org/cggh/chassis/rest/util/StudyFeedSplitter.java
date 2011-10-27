package org.cggh.chassis.rest.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


/**
 * @author timp
 * @since 24 Oct 2011 14:36:59
 *
 */
public class StudyFeedSplitter {

  private static final String SPLIT_STUDY_FEED_XSL = "split-study-feed.xsl";

  /**
   * Accept two command line arguments: the name of an XML file, and
   * the name of an XSLT stylesheet. The result of the transformation
   * is written to stdout.
   */
  public static void main(String[] args) {
    if (args.length < 2 || args.length > 3) {
      System.err.println("Usage:");
      System.err.println("  java " + StudyFeedSplitter.class.getName()
          + " xmlFileName xsltFileName [output filename]");
      System.exit(1);
    }

    String xmlFileName = args[0];
    String xsltFileName = args[1];
    String outFileName = (args.length > 2) ? args[2] : null;
    
    split(xmlFileName, xsltFileName, outFileName);

  }
  
  public static void split(String studyFeed)  {
    split(studyFeed,null, null);    
  }

  public static void split(String studyFeed, String xsltFileName, String outputFilename)  {
    
    TransformerFactory tFactory = TransformerFactory.newInstance();

    StreamSource xslt;
    if (xsltFileName == null)
      xslt = new StreamSource(
          new InputStreamReader(
              StudyFeedSplitter.class.getClassLoader().getResourceAsStream(SPLIT_STUDY_FEED_XSL)),
              SPLIT_STUDY_FEED_XSL);
    else
     xslt = new StreamSource(xsltFileName);
    Transformer transformer;
    try {
      transformer = tFactory.newTransformer(xslt);
    } catch (Exception e1) {
      throw new RuntimeException(e1);
    }
    if (transformer == null) { 
      throw new NullPointerException("Transformer is null");
    }
    
    OutputStream os;
    if (outputFilename == null)
      os = System.out;
    else
      try {
        os = new FileOutputStream(outputFilename);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    
    StreamSource studies = new StreamSource(studyFeed);
    StreamResult output = new StreamResult (os);
    
    try {
      transformer.transform(studies, output);
    } catch (TransformerException e) {
      throw new RuntimeException(e);
    }
  }
}
