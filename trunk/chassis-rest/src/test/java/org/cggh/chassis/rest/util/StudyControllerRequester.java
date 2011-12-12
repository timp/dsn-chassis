package org.cggh.chassis.rest.util;

import java.io.BufferedInputStream;
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
 * Get yourself an entry and post it to the service like this: $ curl -X POST -HContent-type:application/xml -HAccept:application/xml --data @TBYKQ.xml \ http://localhost:8080/chassis-rest/service/study Fetch it again by curl -HAccept:application/xml
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
    method.setRequestHeader("Accept", "text/html");
    return method;
  }

  static HttpMethod acceptXmlHttpMethod(HttpMethod method) {
    method.setRequestHeader("Accept", "text/html");
    // FIXME
    // method.setRequestHeader("Accept", "application/xml");
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

  public static HttpResponse uncache(String restUrl) throws IOException {
    return executeMethod(acceptHtmlHttpMethod(new PostMethod(valid(restUrl))));
  }


  private static HttpResponse update(String xmlFileName, final EntityEnclosingMethod method)
          throws FileNotFoundException, IOException, HttpException {
    InputStream data = new FileInputStream(xmlFileName);
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
      InputStream is = method.getResponseBodyAsStream();
      BufferedInputStream bis = new BufferedInputStream(is);
      StringBuffer sb = new StringBuffer();
      byte[] bytes = new byte[8192];
      int count = bis.read(bytes);
      while (count != -1 && count <= 8192) {
        sb.append(trimEnd(new String(bytes)));
        count = bis.read(bytes);
      }
      if (count != -1) {
        sb.append(trimEnd(new String(bytes)));
      }
      bis.close();
      // System.out.println( "\nDone" );
      String body = sb.toString();
      response = new HttpResponse(method.getStatusCode(), body);
    } finally {
      method.releaseConnection();
    }
    return response;
  }

  public static String trimEnd(String in) {
    int len = in.length();
    int st = 0;
    char[] val = in.toCharArray();
    while ((st < len) && (val[len - 1] <= ' ')) {
      len--;
    }
    return  (((st > 0) || (len < in.length())) ? in.substring(st, len) : in);
  }

}
