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
        "<atom:email>notalice@example.org</atom:email>" +
        "</atom:author>" + 
		"</atom:entry>" + 
		"\n";
	String returnFlag ="Nothing, 404";
	@Override
	public void doFilter(ServletRequest request, ServletResponse response)
			throws IOException, ServletException {
		
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		
		if (returnFlag.equals("Nothing, OK")) { 
			httpResponse.setStatus(200);
		} else if (returnFlag.equals("Not atom, OK")) {
			httpResponse.getOutputStream().print("Not atom content");
			httpResponse.setStatus(200);
		} else if (returnFlag.equals("Atom Entry, OK")) {
			httpResponse.getOutputStream().print(ATOM_ENTRY);
			httpResponse.setStatus(200);
		} else { 
			httpResponse.setStatus(404);			
		}
	
		
	}
    public void setReturnFlag(String returnFlag) { 
    	this.returnFlag = returnFlag;
    }
}
