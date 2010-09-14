package org.cggh.chassis.generic.http.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class MockFilterChain implements FilterChain {
	
	private final static String ROOT = "src/test/java/org/cggh/chassis/generic/http/test/";
	
	
	String returnFlag ="Nothing, 404";
	
	public void doFilter(ServletRequest request, ServletResponse response)
			throws IOException, ServletException {
		
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		
		if (returnFlag.equals("Nothing, 404")) { 
			httpResponse.setStatus(404);			
		} else if (returnFlag.equals("Nothing, 200")) { 
			httpResponse.setStatus(200);
		} else if (returnFlag.equals("Not atom, 200")) {
			sendFile(httpResponse.getOutputStream(),"notAtom.txt");
			httpResponse.setStatus(200);
		} else if (returnFlag.equals("Atom Entry, 200")) {
			sendFile(httpResponse.getOutputStream(), "entry.atom");
			httpResponse.setStatus(200);
		} else if (returnFlag.equals("Atom Feed, 200")) {
			sendFile(httpResponse.getOutputStream(), "emptyStudiesFeed.atom");
			httpResponse.setStatus(200);
		} else if (returnFlag.equals("Malformed, 200")) {
			sendFile(httpResponse.getOutputStream(), "malformed.atom");
			httpResponse.setStatus(200);
		} else if (returnFlag.equals("No author, 200")) {
			sendFile(httpResponse.getOutputStream(), "noAuthor.atom");
			httpResponse.setStatus(200);
		} else if (returnFlag.equals("No email, 200")) {
			sendFile(httpResponse.getOutputStream(), "noEmail.atom");
			httpResponse.setStatus(200);
		} else if (returnFlag.equals("Two authors, 200")) {
			sendFile(httpResponse.getOutputStream(), "twoAuthors.atom");
			httpResponse.setStatus(200);
		} else if (returnFlag.equals("Atom Entry, 500")) {
			sendFile(httpResponse.getOutputStream(), "entry.atom");
			httpResponse.setStatus(500);
		} else { 
			throw new RuntimeException("Unconfigued");
		}
	
		
	}
    public void setReturnFlag(String returnFlag) { 
    	this.returnFlag = returnFlag;
    }
    
    private void sendFile(OutputStream output, String fileName) throws IOException { 
		String fileSystemName = ROOT + fileName;
		FileInputStream fis = new FileInputStream(fileSystemName);
		sendBytes(fis, output);
		fis.close();
    	
    }
    
	private static void sendBytes(FileInputStream fis, OutputStream os)
			throws IOException {
		byte[] buffer = new byte[1024];
		int bytes = 0;

		while ((bytes = fis.read(buffer)) != -1) {
			os.write(buffer, 0, bytes);
		}
	}

}
