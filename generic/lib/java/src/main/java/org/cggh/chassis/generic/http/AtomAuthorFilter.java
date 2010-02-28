/**
 * 
 */
package org.cggh.chassis.generic.http;

import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * @author timp
 * @since 26 Feb 2010
 */
public final class AtomAuthorFilter extends HttpFilter {
    enum HttpMethod { 
    	HEAD(),
    	GET(),
    	PUT(),
    	POST(),
    	DELETE()
    	;
    }
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.http.HttpFilter#doHttpFilter(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doHttpFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
        if (request.getContentType() != null && 
        		request.getContentType().startsWith("application/atom+xml")) {
            
            switch (HttpMethod.valueOf(request.getMethod())) {
    		case GET:
                BufferedHttpResponseWrapper responseWrapper = new BufferedHttpResponseWrapper((HttpServletResponse) response);
    	        chain.doFilter(request, responseWrapper);
        		System.err.println("get:" + responseWrapper.getContent());
            	if (responseWrapper.getContent().startsWith("<atom:entry")) {
            		String user = getUser(request);
            		System.err.println("user"+user);
            		if (user == null){
            			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No user found");
            			return;            			
            		}
            		
            		List<String> authors = getAuthors(responseWrapper.getContent());
            		if (!authors.contains(user)) {
              			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You may only interact with your own stuff");
            			return;
            		}
            	} 
                response.setContentLength(responseWrapper.getBuffer().length);
                response.getOutputStream().write(responseWrapper.getBuffer());
                response.flushBuffer();		
    			break;
    		case PUT:
    			if (mayPut(request))
    				chain.doFilter(request, response);
    			else {
        			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You may only update an item of which you are the author");
        			return;
    			}
    			break;
    		case POST:
    	        chain.doFilter(request, response);
		
    			break;
    		case DELETE:
    	        chain.doFilter(request, response);
		
    			break;
    		default:
    			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected value of request method: " + request.getMethod());
    			throw new RuntimeException("Unexpected value of request method: " + request.getMethod());
    		}
        } else
	        chain.doFilter(request, response);
	}

	private static boolean mayPut(HttpServletRequest request ) throws IOException {
		    String url = getUrl(request);
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", request.getContentType());
			String authorization = request.getHeader("Authorization");
			if (authorization != null)
				connection.setRequestProperty("Authorization", authorization);
			connection.connect();
			if (connection.getResponseCode() == HttpServletResponse.SC_OK)
				return true;
			if (connection.getResponseCode() == HttpServletResponse.SC_NOT_FOUND)
				return true;
			return false;
	 }

	 @SuppressWarnings("unchecked")
	private static String getUrl(HttpServletRequest request) {
		StringBuffer urlBuffer = request.getRequestURL();
		Map <String, String[]>parameters = request.getParameterMap(); 
		if (!parameters.isEmpty()) {
		  urlBuffer.append('?');
		  for (String parameterName : parameters.keySet()) {
			  urlBuffer.append(parameterName);
			  urlBuffer.append('=');
			  String[] values = parameters.get(parameterName);
			  for (int i = 0; i < values.length; i++){
				  if(i >0)
					  urlBuffer.append(',');
				  urlBuffer.append(values[i]);
			  }
		  }
		}
		String url = urlBuffer.toString();
		return url;
	}
	
	private static List<String> getAuthors(String atomEntry) { 
		ArrayList<String> authors = new ArrayList<String>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
		InputSource inStream = new InputSource();
		inStream.setCharacterStream(new StringReader(atomEntry));
		Document doc;
		try {
			doc = db.parse(inStream);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		doc.getDocumentElement().normalize();
		NodeList authorNodes = doc.getElementsByTagName("atom:author");
		if (authorNodes.getLength() > 1)
			throw new RuntimeException("More than one author element in " + atomEntry);
		else if (authorNodes.getLength() == 0)
			throw new RuntimeException("No author element in " + atomEntry);
		NodeList emailNodes = authorNodes.item(0).getChildNodes();
		if (emailNodes.getLength() < 1)
			throw new RuntimeException("No email elements in author element in " + atomEntry);
		for (int s = 0; s < emailNodes.getLength(); s++) {
			Node emailNode = emailNodes.item(s);
			Element emailElement = (Element)emailNode;
			authors.add(emailElement.getTextContent());
		}
		return authors;
	}

	private static String getUser(HttpServletRequest request) {
		String basicType = "Basic ";
		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null) {
			if (authorizationHeader.indexOf(basicType) < 0 )
				throw new IllegalArgumentException("Authorization not '" + basicType + 
						"':" + authorizationHeader);
			String user =   new String(new Base64().decode(
					authorizationHeader.replace("Basic ","").getBytes())).split(":")[0];
			return user;
		} else 
			return null;
	}

}
