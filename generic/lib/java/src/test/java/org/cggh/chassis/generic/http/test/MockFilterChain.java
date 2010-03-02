package org.cggh.chassis.generic.http.test;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class MockFilterChain implements FilterChain {
	private final static String ATOM_ENTRY = 	
		"<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\">" + 
		"<atom:title>An Atomic Entry</atom:title>" + 
        "<atom:author>" + 
        "<atom:email>alice@example.org</atom:email>" +
        "</atom:author>" + 
		"</atom:entry>" + 
		"\n";
	private final static String BAD_ATOM_ENTRY_NOAUTHOR = 	
		"<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\">" + 
		"<atom:title>An Atomic Entry</atom:title>" + 
		"</atom:entry>" + 
		"\n";
	private final static String BAD_ATOM_ENTRY_NOEMAIL = 	
		"<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\">" + 
		"<atom:title>An Atomic Entry</atom:title>" + 
        "<atom:author>" + 
        "</atom:author>" + 
		"</atom:entry>" + 
		"\n";
	private final static String BAD_ATOM_ENTRY_MALFORMED = 	
		"<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\">" + 
		"<atom:title>An Atomic Entry</atom:title>" + 
		"\n";
	private final static String BAD_ATOM_ENTRY_TWOAUTHORS = 	
		"<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\">" + 
		"<atom:title>An Atomic Entry</atom:title>" + 
        "<atom:author>" + 
        "<atom:email>alice@example.org</atom:email>" +
        "</atom:author>" + 
        "<atom:author>" + 
        "<atom:email>alice@example.org</atom:email>" +
        "</atom:author>" + 
		"</atom:entry>" + 
		"\n";
	String returnFlag ="Nothing, 404";
	
	public void doFilter(ServletRequest request, ServletResponse response)
			throws IOException, ServletException {
		
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		
		if (returnFlag.equals("Nothing, 404")) { 
			httpResponse.setStatus(404);			
		} else if (returnFlag.equals("Nothing, OK")) { 
			httpResponse.setStatus(200);
		} else if (returnFlag.equals("Not atom, OK")) {
			httpResponse.getOutputStream().print("Not atom content");
			httpResponse.setStatus(200);
		} else if (returnFlag.equals("Atom Entry, OK")) {
			httpResponse.getOutputStream().print(ATOM_ENTRY);
			httpResponse.setStatus(200);
		} else if (returnFlag.equals("Malformed, OK")) {
			httpResponse.getOutputStream().print(BAD_ATOM_ENTRY_MALFORMED);
			httpResponse.setStatus(200);
		} else if (returnFlag.equals("No author, OK")) {
			httpResponse.getOutputStream().print(BAD_ATOM_ENTRY_NOAUTHOR);
			httpResponse.setStatus(200);
		} else if (returnFlag.equals("No email, OK")) {
			httpResponse.getOutputStream().print(BAD_ATOM_ENTRY_NOEMAIL);
			httpResponse.setStatus(200);
		} else if (returnFlag.equals("Two authors, OK")) {
			httpResponse.getOutputStream().print(BAD_ATOM_ENTRY_TWOAUTHORS);
			httpResponse.setStatus(200);
		} else if (returnFlag.equals("Atom Entry, 500")) {
			httpResponse.getOutputStream().print(ATOM_ENTRY);
			httpResponse.setStatus(500);
		} else { 
			throw new RuntimeException("Unconfigued");
		}
	
		
	}
    public void setReturnFlag(String returnFlag) { 
    	this.returnFlag = returnFlag;
    }
}
