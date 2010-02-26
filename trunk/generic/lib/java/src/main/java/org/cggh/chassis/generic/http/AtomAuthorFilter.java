/**
 * 
 */
package org.cggh.chassis.generic.http;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

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
public class AtomAuthorFilter extends HttpFilter {
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
        BufferedHttpResponseWrapper responseWrapper = new BufferedHttpResponseWrapper((HttpServletResponse) response);
        chain.doFilter(request, responseWrapper);
        
        byte[] content = responseWrapper.getBuffer();
		

		System.err.println("Content:" + new String(content));

        
        if (request.getContentType() != null && request.getContentType().startsWith("application/atom+xml")) {
    		switch (HttpMethod.valueOf(request.getMethod())) {
    		case GET:
            	
            	if (content != null) { 
            		List<String> authors = getAuthors(new String(content));
            		if (!authors.contains(getUser(request))) {
            		      System.err.println("AtomAuthorFilter Committed:" + response.isCommitted());
            			//response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            			//response.sendRedirect("Http://google.com");
            			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You may only interact with your own stuff");
            			return;
            		}
            	} else 
            		System.err.println("Content null");
		
    			break;
    		case PUT:
		
    			break;
    		case POST:
		
    			break;
    		case DELETE:
		
    			break;
    		default:
    			throw new RuntimeException("Unexpected value of request method: " + request.getMethod());
    		}
        }
        System.err.println("AtomAuthorFilter2 Committed:" + response.isCommitted());
        response.setContentLength(content.length);
        response.getOutputStream().write(content);
        response.flushBuffer();
        System.err.println("AtomAuthorFilter3 Committed:" + response.isCommitted());
	}

	
	List<String> getAuthors(String atomEntry) { 
		ArrayList<String> authors = new ArrayList<String>();
		System.err.println("Extracting authors from " + atomEntry);
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

	String getUser(HttpServletRequest request) {
		return  new String(new Base64().decode(
				request.getHeader("Authorization").replace("Basic ","").getBytes())).split(":")[0];
	}

}
