package org.cggh.chassis.rest.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;

/**
 * This is meant to be the equivalent of
 * 
 * Get yourself an entry and post it to the service like this: $ curl -X POST
 * -HContent-type:application/xml -HAccept:application/xml --data @TBYKQ.xml \
 * http://localhost:8080/chassis-rest/service/study Fetch it again by curl
 * -HAccept:application/xml
 * http://localhost:8080/chassis-rest/service/study/TBYKQ
 * 
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

    create(studyFileName, url);

  }

  static HttpMethod acceptHtmlHttpMethod(HttpMethod method) {
    // Note: curl adds a default of
    // Accept: */*
    // httpclient does not.
    // for */* spring returns xml, for nothing it throws and error.
    method.setRequestHeader("Accept", "application/xml");
    return method;
  }

  static EntityEnclosingMethod acceptHtmlHttpMethod(EntityEnclosingMethod method) {
    // Note: curl adds a default of
    // Accept: */*
    // httpclient does not.
    // for */* spring returns xml, for nothing it throws and error.
    method.setRequestHeader("Accept", "application/xml");
    return method;
  }

  static HttpMethod acceptXmlHttpMethod(HttpMethod method) {
    method.setRequestHeader("Accept", "text/html");
    return method;
  }

  static EntityEnclosingMethod acceptXmlHttpMethod(EntityEnclosingMethod method) {
    method.setRequestHeader("Accept", "text/html");
    return method;
  }
  private static String valid(String url) throws MalformedURLException { 
    if (url == null)
      throw new NullPointerException("URL required");
    new URL(url);
    return url;
  }


  /** C */
  public static HttpResponse create(String fileName, String restUrl)
      throws IOException {
    return update(fileName, acceptXmlHttpMethod(new PostMethod(
        valid(restUrl))));
  }
  public static HttpResponse createAcceptingHtml(String studyFileName, String restUrl)
      throws IOException {
    return update(studyFileName, acceptHtmlHttpMethod(new PostMethod(
        valid(restUrl))));
  }

  /** R */
  public static HttpResponse read(String restUrl) throws IOException {
    return executeMethod(acceptXmlHttpMethod(new GetMethod(valid(restUrl))));
  }
  public static HttpResponse readAcceptingHtml(String restUrl) throws IOException {
    return executeMethod(acceptHtmlHttpMethod(new GetMethod(valid(restUrl))));
  }

  /** U */
  // Beware replaces entry in study, but everything else is an insert not an
  // update
  public static HttpResponse update(String studyFileName, String restUrl)
      throws IOException {
    return update(studyFileName, acceptXmlHttpMethod(new PutMethod(valid(restUrl))));
  }
  public static HttpResponse updateAcceptingHtml(String studyFileName, String restUrl)
      throws IOException {
    return update(studyFileName, acceptHtmlHttpMethod(new PutMethod(valid(restUrl))));
  }

  /** D */
  public static HttpResponse delete(String restUrl) throws IOException {
    return executeMethod(acceptXmlHttpMethod(new DeleteMethod(valid(restUrl))));
  }
  public static HttpResponse deleteAcceptingHtml(String restUrl) throws IOException {
    return executeMethod(acceptHtmlHttpMethod(new DeleteMethod(valid(restUrl))));
  }
  
  
  private static HttpResponse update(String studyFileName, final EntityEnclosingMethod method) 
      throws FileNotFoundException, IOException, HttpException {
    InputStream data = new FileInputStream(studyFileName);
    method.setRequestEntity(new InputStreamRequestEntity(data));
    method.setRequestHeader("Content-Type", "application/xml");

    return executeMethod(method);
  }

  private static HttpResponse executeMethod(final HttpMethod method)
      throws IOException, HttpException {
    HttpResponse response;
    final HttpClient client = new HttpClient();
    try {
      client.executeMethod(method);
      response = new HttpResponse(method.getStatusCode(),
          method.getResponseBodyAsString());
    } finally {
      method.releaseConnection();
    }
    return response;
  }

}
