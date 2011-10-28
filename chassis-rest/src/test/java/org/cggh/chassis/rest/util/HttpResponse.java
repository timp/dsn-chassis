/**
 * 
 */
package org.cggh.chassis.rest.util;

/**
 * @author timp
 * @since 28 Oct 2011 16:18:19
 *
 */
public class HttpResponse {
  private int status;
  private String body;
  public HttpResponse(int status, String body) {
    super();
    this.status = status;
    this.body = body;
  }
  public int getStatus() {
    return status;
  }
  public String getBody() {
    return body;
  }
}
