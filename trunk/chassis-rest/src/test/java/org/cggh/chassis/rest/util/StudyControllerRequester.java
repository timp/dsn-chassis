package org.cggh.chassis.rest.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;

/**
 * This is meant to be the equivalent of 
 * 
 * Get yourself an entry and post it to the service like this:
 * $ curl -X POST -HContent-type:application/xml  -HAccept:application/xml --data @TBYKQ.xml \ 
 *     http://localhost:8080/chassis-rest/service/study
 * Fetch it again by
 * curl -HAccept:application/xml http://localhost:8080/chassis-rest/service/study/TBYKQ

 * 
 * @author timp
 * @since 25 Oct 2011 11:03:47
 *
 */
public class StudyControllerRequester {

  
  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("Usage:");
      System.err.println("  java " + StudyControllerRequester.class.getName()
          + " studyFileName restUrl");
      System.exit(1);
    }

    String studyFileName = args[0];
    String url = args[1];

    post(studyFileName, url);

  }

  public static HttpResponse post(String studyFileName, String restUrl) throws IOException {
    return update(studyFileName, restUrl, new PostMethod(restUrl));
  }
  public static HttpResponse put(String studyFileName, String restUrl) throws IOException {
    return update(studyFileName, restUrl, new PutMethod(restUrl));
  }
  public static HttpResponse delete(String restUrl) throws IOException {
    return request( restUrl, new DeleteMethod(restUrl));
  }
  
  private static HttpResponse request(String restUrl, DeleteMethod method) throws IOException {
    if (restUrl == null)
      throw new NullPointerException("REST URL required");
    try {
      // Merely for validation of url format
      new URL(restUrl);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
    
    final HttpClient client = new HttpClient();

    method.setRequestHeader("Accept", "application/xml");

    HttpResponse response;
    try {
      client.executeMethod(method);
      response = new HttpResponse(method.getStatusCode(), method.getResponseBodyAsString());
    } finally {
      method.releaseConnection();
    }
    return response;
    
  }

  private static HttpResponse update(String studyFileName, String restUrl, final EntityEnclosingMethod method) 
      throws FileNotFoundException, IOException, HttpException {
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

    InputStream data = new FileInputStream(studyFileName);
    method.setRequestEntity(new InputStreamRequestEntity(data));  
    method.setRequestHeader("Content-Type", "application/xml");
    
    // Note: curl adds a default of 
    // Accept: */*
    // httpclient does not.
    // for */* spring returns xml, for nothing it throws and error. 
    method.setRequestHeader("Accept", "application/xml");
    
    HttpResponse response;
    try {
      client.executeMethod(method);
      response = new HttpResponse(method.getStatusCode(), method.getResponseBodyAsString());
    } finally {
      method.releaseConnection();
    }
    return response;
  }
  
  

}
