/**
 * 
 */
package org.cggh.chassis.generic.http.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author timp
 * @since 2006/12/05
 *
 */
public class MockHttpServletRequest implements HttpServletRequest {

    Map<String,String[]> parameters = new HashMap<String,String[]>();
    
    /**
     * @param map the parameters
     */
    public void setParameters(Map<String,String[]> map) {
        parameters = map;
    }
    public void resetParameters() { 
    	parameters = new HashMap<String,String[]>(); 
    }
    
    public String getAuthType() {
        return null;
    }

    public Cookie[] getCookies() {
        return null;
    }

    public long getDateHeader(String arg0) {
        return 0;
    }

    // Note this is not correct, should be a MultiMap
    Hashtable<String,String> headers = new Hashtable<String,String>();
    public String getHeader(String arg0) {
        return (String)headers.get(arg0);
    }
    /**
     * @param key the header key
     * @param value the value to set it to 
     */
    public void setHeader(String key, String value) {
      headers.put(key, value);
    }
    public Enumeration<String> getHeaders(String arg0) {
        return headers.elements();
    }

    public Enumeration<String> getHeaderNames() {
        return headers.keys();
    }

    public int getIntHeader(String arg0) {
        return -1;
    }

    String method = null;
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    String pathInfo;
    public String getPathInfo() {
        return pathInfo;
    }
    /**
     * @param info the info to set
     */
    public void setPathInfo(String info) {
      pathInfo = info;
    }

    public String getPathTranslated() {
        return null;
    }


    String contextPath = "";
    public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
    public String getContextPath() {
        return contextPath; 
    }

    public String getQueryString() {
        return null;
    }

    public String getRemoteUser() {
        return null;
    }

    public boolean isUserInRole(String arg0) {
        return false;
    }

    public Principal getUserPrincipal() {
        return null;
    }

    public String getRequestedSessionId() {
        return null;
    }

    String requestURI = null;
    public String getRequestURI() {
        return requestURI;
    }
    /**
     * @param uri the uri to set
     */
    public void setRequestURI(String uri) {
      requestURI = uri;
    }

    public StringBuffer getRequestURL() {
    	StringBuffer sb = new StringBuffer();
    	sb.append(getScheme());
    	sb.append("://");
    	sb.append(getServerName());
    	if (getServerPort() != 80)
    		sb.append(":" + new Integer(getServerPort()).toString());
    	sb.append(getContextPath());
    	sb.append(getRequestURI());
    	if (!getParameterMap().isEmpty()) {
  		  sb.append('?');
		  for (String parameterName : parameters.keySet()) {
			  sb.append(parameterName);
			  sb.append('=');
			  String[] values = parameters.get(parameterName);
			  for (int i = 0; i < values.length; i++){
				  if(i >0)
					  sb.append(',');
				  sb.append(values[i]);
			  }
		  }    		
    	}
        return sb;
    }

    public String getServletPath() {
        return "/mockServletPath/";
    }
    Object session;
    /**
     * @param s the session to set
     */
    public void setSession(Object s){
      session = s;
    }
    public HttpSession getSession(boolean create) {
      if (create)
        return new MockHttpSession();
      else
        return (HttpSession)session;
    }

    public HttpSession getSession() {
      return (HttpSession)session;
    }

    public boolean isRequestedSessionIdValid() {
        return false;
    }

    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    public Object getAttribute(String arg0) {
        return null;
    }

    public Enumeration<String> getAttributeNames() {
        return null;
    }

    String charEncoding = "ISO-8859-1";
    public String getCharacterEncoding() {
      return charEncoding;
    }

    public void setCharacterEncoding(String ce) throws UnsupportedEncodingException {
      if (ce != null && ce.equals("UnsupportedEncoding"))
        throw new UnsupportedEncodingException();
      charEncoding = ce;
    }

    public int getContentLength() {
        return 0;
    }

    String contentType = null;
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public String getContentType() {
        return contentType;
    }

    String content = null;
    public void setContent(String content) { 
    	this.content = content;
    }
    public ServletInputStream getInputStream() throws IOException {
		System.err.println("getInputStream");    	
        return new MockServletInputStream(content);
    }

    /**
     * Set a parameter.
     */
    public void setParameter(String name, String value) { 
      parameters.put(name, new String[] {value});
    }
    public String getParameter(String name) {
      if (parameters.get(name) == null)
        return null;
      return  parameters.get(name)[0];
    }

    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(parameters.keySet());
    }

    public String[] getParameterValues(String key) {
      return parameters.get(key) ;
    }

    public Map<String,String[]> getParameterMap() {
        return parameters;
    }

    public String getProtocol() {
        return null;
    }

    String scheme = "http";
    /**
     * @param s the scheme to set
     */
    public void setScheme(String s) {
      scheme = s;
    }
    public String getScheme() {
        return scheme;
    }

    String serverName = "localhost";
    public String getServerName() {
        return serverName;
    }
    public void setServerName(String serverName) { 
    	this.serverName = serverName;
    }

    int port = 80;
    public int getServerPort() {
        return port;
    }
    public void setPort(int port) { 
    	this.port = port;
    }

    public BufferedReader getReader() throws IOException {
        return null;
    }

    public String getRemoteAddr() {
        return null;
    }

    public String getRemoteHost() {
        return null;
    }

    public void setAttribute(String arg0, Object arg1) {
    }

    public void removeAttribute(String arg0) {
    }

    public Locale getLocale() {
        return null;
    }

    public Enumeration<Locale> getLocales() {
        return null;
    }

    public boolean isSecure() {
        return false;
    }

    public RequestDispatcher getRequestDispatcher(String arg0) {
        return new RequestDispatcher() {
        
            public void include(ServletRequest arg00, ServletResponse arg1)
                    throws ServletException, IOException {
            }
        
            public void forward(ServletRequest arg00, ServletResponse arg1)
                    throws ServletException, IOException {
            }
        };
    }

    public String getRealPath(String arg0) {
        return "test";
    }

    public String getLocalAddr() {
      throw new RuntimeException("TODO No one else has ever called this method." +
                                 " Do you really want to start now?");
      
    }

    public String getLocalName() {
      throw new RuntimeException("TODO No one else has ever called this method." +
                                 " Do you really want to start now?");
      
    }

    public int getLocalPort() {
      throw new RuntimeException("TODO No one else has ever called this method." +
                                 " Do you really want to start now?");
      
    }

    public int getRemotePort() {
      throw new RuntimeException("TODO No one else has ever called this method." +
                                 " Do you really want to start now?");
      
    }


    
}