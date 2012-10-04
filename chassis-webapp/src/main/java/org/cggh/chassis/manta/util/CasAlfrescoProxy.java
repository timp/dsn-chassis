package org.cggh.chassis.manta.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

/**
 * Servlet implementation class ChangeContentType
 */
public class CasAlfrescoProxy extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// private final static String ALFRESCO_WEBAPP_URL =
	// "http://46.137.92.130:8080/alfresco";
	private final static String ALFRESCO_WEBAPP_URL_CONFIG = "alfrescoApp";
	public final static String CS_PARAM_ALF_TICKET = "alfTicket";
	private static Log logger = LogFactory.getLog(CasAlfrescoProxy.class);

	/**
	 * Default constructor.
	 */
	public CasAlfrescoProxy() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpClient client = new DefaultHttpClient();

		HttpGet method;
		int statusCode;
		String ticketXML = null;
		try {
			ticketXML = CasAlfrescoProxy.getAlfrescoTicket(req, client);
		} catch (NamingException e) {
			logger.error(
					"Need to set JNDI variable alfrescoApp if using Alfresco",
					e);
		} catch (HttpException e) {
			logger.error(
					"Http exception thrown",
					e);
		}

		if (ticketXML == null) {
			return;
		}
		String ticket = CasAlfrescoProxy.extractTicket(ticketXML);

		// Should really check if there are any existing parameters - this
		// assumes that there are none
		String service = req.getParameter("url") + "?alf_ticket=" + ticket;

		method = new HttpGet(service);
		HttpResponse httpResponse = client.execute(method);
		statusCode = httpResponse.getStatusLine().getStatusCode();

		if (statusCode == HttpStatus.SC_OK) {
			InputStream is = httpResponse.getEntity().getContent();
			// do something with the input stream
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			PrintWriter out = new PrintWriter(resp.getOutputStream(), true);
			// Copy the headers
			Header[] headers = httpResponse.getAllHeaders();
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
		}
		resp.setStatus(statusCode);
	}

	public static String getAlfrescoTicket(HttpServletRequest req,
			HttpClient client) throws UnsupportedEncodingException,
			IOException, HttpException, ServletException, NamingException {

		String username = CASUtil.getUsername(req);
		// Read out the ticket id
		String ticket = null;
		String alfrescoWebAppURL = LookupJNDI
				.<String> getEnvEntry(ALFRESCO_WEBAPP_URL_CONFIG);
		if (alfrescoWebAppURL == null) {
			return (null);
		}
		String proxyticket = CASUtil.getProxyTicket(req, alfrescoWebAppURL);

		if (proxyticket == null) {
			return (null);
		}
		String casLoginUrl = alfrescoWebAppURL + "/service/api/logincas?u="
				+ URLEncoder.encode(username, "UTF-8") + "&t="
				+ URLEncoder.encode(proxyticket, "UTF-8");

		HttpGet method = new HttpGet(casLoginUrl);
		method.setHeader("cookie", req.getHeader("cookie"));
		HttpResponse resp = client.execute(method);
		// Read back the ticket
		if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			InputStream is = resp.getEntity().getContent();
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
						+ resp.getStatusLine().getStatusCode());
			}
		}
		
		return ticket;
	}

	public static String extractTicket(String responseText)
			throws ServletException {
		String ticket = null;
		try {
			ticket = DocumentHelper.parseText(responseText).getRootElement()
					.getTextTrim();
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