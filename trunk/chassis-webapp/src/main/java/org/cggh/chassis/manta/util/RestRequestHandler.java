package org.cggh.chassis.manta.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.cggh.chassis.manta.security.ProxyServlet;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RequestCallback;

public class RestRequestHandler implements RequestCallback {

	private HttpServletRequest req;
	private InputStream inputStream;
	
	private static Logger logger = Logger.getLogger(RestRequestHandler.class);
	
	public RestRequestHandler(HttpServletRequest request, InputStream is) {
		req = request;
		inputStream = is;
	}
	
	@Override
	public void doWithRequest(ClientHttpRequest arg0) throws IOException {
		
		Enumeration<String> reqHeaders = req.getHeaderNames();
		while (reqHeaders.hasMoreElements()) {
			String hName = reqHeaders.nextElement();
			@SuppressWarnings("unchecked")
			Enumeration<String> hvalues = req.getHeaders(hName);
			while (hvalues.hasMoreElements()) {
				String hvalue = hvalues.nextElement();
				if (!hName.equalsIgnoreCase("cookie")) {
					arg0.getHeaders().set(hName, hvalue);
					if (logger.isDebugEnabled()) {
						logger.debug("Setting request header " + hName + " to:"
								+ hvalue);
					}
				}
			}
		}
		
		String in;
		HttpMethod methodType = arg0.getMethod();
		if (methodType != HttpMethod.GET) {
			in = new Scanner(inputStream).useDelimiter("\\A").next();
			arg0.getBody().write(in.getBytes());
			if (logger.isDebugEnabled()) {
				logger.debug(in);
			}
		}
	}

}
