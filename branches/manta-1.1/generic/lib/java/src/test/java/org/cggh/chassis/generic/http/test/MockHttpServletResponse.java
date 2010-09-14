/**
 * 
 */
package org.cggh.chassis.generic.http.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author timp
 * @since 2006/12/05
 */
public class MockHttpServletResponse implements HttpServletResponse {

    public void addCookie(Cookie arg0) {
    }

    public boolean containsHeader(String arg0) {
        return false;
    }

    public String encodeURL(String arg0) {
        return null;
    }

    public String encodeRedirectURL(String arg0) {
        return null;
    }

    public String encodeUrl(String arg0) {
        return null;
    }

    public String encodeRedirectUrl(String arg0) {
        return null;
    }

    int status = 0;
    String statusMessage = null;
    public void sendError(int status, String statusMessage) throws IOException {
    	this.status = status;
    	this.statusMessage = statusMessage;
    	committed = true;
    }

    public int getStatus() { 
    	return status;
    }
    public void sendError(int status) throws IOException {
    	this.status = status;
    	committed = true;
    }

    public void sendRedirect(String arg0) throws IOException {
    }

    public void setDateHeader(String arg0, long arg1) {
    }

    public void addDateHeader(String arg0, long arg1) {
    }

    public void setHeader(String arg0, String arg1) {
    }

    public void addHeader(String arg0, String arg1) {
    }

    public void setIntHeader(String arg0, int arg1) {
    }

    public void addIntHeader(String arg0, int arg1) {
    }

    public void setStatus(int status) {
    	this.status = status;
    }

    public void setStatus(int status, String statusMessage) {
    	this.status = status;
    	this.statusMessage = statusMessage;
    }

    public String getCharacterEncoding() {
      return "ISO-8859-1";
    }
    ByteArrayOutputStream bout = new ByteArrayOutputStream(); 
    public ServletOutputStream getOutputStream() throws IOException {
        return new ServletOutputStream() {
        
            public void println(String arg0) throws IOException {
                super.println(arg0);
            }

            public void write(int b) throws IOException {
              bout.write(b);
            }
        };
    }
    /**
     * @return what was written
     */
    public String getWritten() {
      return bout.toString();
    }
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(getOutputStream());
    }

    public void setContentLength(int arg0) {
    }

    String contentType;
    public void setContentType(String type) {
      contentType = type;
    }

    public String getContentType() {
        return contentType;
    }

    public void setBufferSize(int arg0) {
    }

    public int getBufferSize() {
        return 0;
    }

    public void flushBuffer() throws IOException {
    }

    public void resetBuffer() {
    }

    boolean committed = false;
    public boolean isCommitted() {
        return committed;
    }

    public void reset() {
    }

    public void setLocale(Locale arg0) {
    }

    public Locale getLocale() {
        return null;
    }

    public void setCharacterEncoding(String charset) {
      throw new RuntimeException("TODO No one else has ever called this method." +
                                 " Do you really want to start now? " + charset);
      
    }
    
}