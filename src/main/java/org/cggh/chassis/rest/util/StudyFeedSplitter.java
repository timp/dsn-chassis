package org.cggh.chassis.rest.util;

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
    XsltTransformer.transform(studyFeed, SPLIT_STUDY_FEED_XSL, null, true);
  }
  public static void split(String xmlFileName, String xsltFileName, String outFileName) {
    XsltTransformer.transform(xmlFileName, xsltFileName, outFileName, false);
  }
}
