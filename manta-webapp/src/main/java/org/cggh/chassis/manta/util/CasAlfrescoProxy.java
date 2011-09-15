package org.cggh.chassis.manta.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;

/**
 * Servlet implementation class ChangeContentType
 */
public class CasAlfrescoProxy extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// private final static String ALFRESCO_WEBAPP_URL =
	// "http://46.137.92.130:8080/alfresco";
	private final static String ALFRESCO_WEBAPP_URL = "https://iwright-VirtualBox/alfresco";
	public final static String CS_PARAM_ALF_TICKET = "alfTicket";
	private static Log logger = LogFactory.getLog(CasAlfrescoProxy.class);
	private ServletContext servletContext;

	/**
	 * Default constructor.
	 */
	public CasAlfrescoProxy() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession httpSess = req.getSession(true);
		servletContext = httpSess.getServletContext();
		HttpClient client = new HttpClient();
		
		GetMethod method;
		int statusCode;
		String ticketXML = CasAlfrescoProxy.getAlfrescoTicket(req, client);
		
		String ticket = CasAlfrescoProxy.extractTicket(ticketXML);

		//Should really check if there are any existing parameters - this assumes that there are none
		String service = req.getParameter("url") + "?alf_ticket=" + ticket;

		method = new GetMethod(service);
		statusCode = client.executeMethod(method);

		InputStream is = method.getResponseBodyAsStream();
		// do something with the input stream
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		PrintWriter out = new PrintWriter(resp.getOutputStream(), true);
		// Copy the headers
		Header headers[] = method.getResponseHeaders();
		for (int i = 0; i < headers.length; i++) {
			Header head = headers[i];
			resp.setHeader(head.getName(), head.getValue());
		}
		// Copy the content
		String line;
		while ((line = in.readLine()) != null) {
			out.print(line);
		}
		in.close();
		out.close();
		resp.setStatus(statusCode);
		method.releaseConnection();

		/*
		 * HttpClient 4.0:
		 * 
		 * HttpClient client = new DefaultHttpClient(); HttpGet method = new
		 * HttpGet(url); HttpResponse httpResponse = client.execute(method); int
		 * statusCode = httpResponse.getStatusLine().getStatusCode(); if
		 * (statusCode == HttpStatus.SC_OK) { InputStream is =
		 * httpResponse.getEntity().getContent(); // do something with the input
		 * stream }
		 */

	}

	public static String getAlfrescoTicket(HttpServletRequest req, HttpClient client)
			throws UnsupportedEncodingException, IOException, HttpException,
			ServletException {
		
		HttpSession httpSess = req.getSession(true);
		
		// Get CAS information
		Assertion assertion = (Assertion) httpSess
				.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
		if (assertion == null) {
			return "";
		}
		String username = assertion.getPrincipal().getName();
		// Read out the ticket id
		String ticket = null;

		String proxyticket = assertion.getPrincipal().getProxyTicketFor(
				ALFRESCO_WEBAPP_URL);

		String casLoginUrl = ALFRESCO_WEBAPP_URL + "/service/api/logincas?u="
				+ URLEncoder.encode(username, "UTF-8") + "&t="
				+ URLEncoder.encode(proxyticket, "UTF-8");

		
		GetMethod method = new GetMethod(casLoginUrl);
		method.setRequestHeader("cookie", req.getHeader("cookie"));
		int statusCode = client.executeMethod(method);
		// Read back the ticket
		if (statusCode == 200) {
			InputStream is = method.getResponseBodyAsStream();
			// do something with the input stream
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			String line;
			String responseText = "";
			while ((line = in.readLine()) != null) {
				responseText += line;
			}
			in.close();
			ticket = responseText;
			
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("Authentication failed, received response code: "
						+ statusCode);
			}
		}
		method.releaseConnection();
		return ticket;
	}

	public static String extractTicket(String responseText)
			throws ServletException {
		String ticket = null;
		try {
			ticket = DocumentHelper.parseText(responseText)
					.getRootElement().getTextTrim();
		} catch (DocumentException de) {
			// The ticket that came back was unparseable or invalid
			// This will cause the entire handshake to fail
			throw new ServletException(
					"Unable to retrieve ticket from Alfresco", de);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Parsed ticket: " + ticket);
		}
		return ticket;
	}

}