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
 * @since 21 Dec 2011
 *
 */
public class XsltTransformer {
  
  public static void transform(String xmlFileName, String xsltFileName, String outputFileName)  {
    transform(xmlFileName, xsltFileName, outputFileName, false);
  }
  public static void transform(String studyFeed, String xsltFileName, String outputFilename, boolean useClasspath)  {
    System.err.println("Transforming:" + studyFeed + ", " + xsltFileName + ", " + outputFilename + ",");
    StreamSource xslt;
    if (useClasspath)
      xslt = new StreamSource(
          new InputStreamReader(
              StudyFeedSplitter.class.getClassLoader().getResourceAsStream(xsltFileName)),
              xsltFileName);
    else
     xslt = new StreamSource(xsltFileName);
    XsltTransformer.transform(studyFeed, xslt, outputFilename);

  }  
  public static void transform(String studyFeed, StreamSource xslt, String outputFilename)  {
    TransformerFactory tFactory = TransformerFactory.newInstance();
    Transformer transformer;
    try {
      transformer = tFactory.newTransformer(xslt);
    } catch (Exception e) {
      throw new RuntimeException(e);
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
