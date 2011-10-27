package org.cggh.chassis.rest.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * Get yourself an entry and post it to the service like this:
 * $ curl -X POST -HContent-type:application/xml  --data @TBYKQ.xml \ 
 *     http://localhost:8080/chassis-rest/service/study
 * Fetch it again by
 * curl -HAccept:application/xml http://localhost:8080/chassis-rest/service/study/TBYKQ

 * 
 * @author timp
 * @since 25 Oct 2011 11:03:47
 *
 */
public class StudyPoster {

  
  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("Usage:");
      System.err.println("  java " + StudyPoster.class.getName()
          + " studyFileName restUrl");
      System.exit(1);
    }

    String studyFileName = args[0];
    String url = args[1];

    post(studyFileName, url);

  }

  public static int post(String studyFileName, String restUrl) throws IOException {
    if (studyFileName == null)
      throw new NullPointerException("File name required");
    if (restUrl == null)
      throw new NullPointerException("REST URL required");
    try {
      // Merely for validation of url format
      new URL(restUrl);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
    
    final HttpClient client = new HttpClient();

    final PostMethod post = new PostMethod(restUrl);

    InputStream data = new FileInputStream(studyFileName);
    post.setRequestEntity(new InputStreamRequestEntity(data));
    post.setRequestHeader("Content-Type", "application/xml");
    
    try {
      client.executeMethod(post);

      final String response = post.getResponseBodyAsString();
      System.out.println(response);

    } finally {
      post.releaseConnection();
    }
    
    return post.getStatusCode();
    
    
  
  }

}
