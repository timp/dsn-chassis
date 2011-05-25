package org.cggh.chassis.manta.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * Servlet implementation class ChangeContentType
 */
public class ChangeContentType extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public ChangeContentType() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpClient client = new HttpClient();
		String url = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&retmode=xml&id="
				+ request.getParameter("id");
		GetMethod method = new GetMethod(url);
		int statusCode = client.executeMethod(method);
		if (statusCode == HttpStatus.SC_OK) {
			InputStream is = method.getResponseBodyAsStream();
			// do something with the input stream
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			PrintWriter out = new PrintWriter(response.getOutputStream(), true);
			// Copy the headers
			Header headers[] = method.getResponseHeaders();
			for (int i = 0; i < headers.length; i++) {
				Header head = headers[i];
				response.setHeader(head.getName(), head.getValue());
			}
			// Replace the content-type header
			response.setHeader("Content-Type", "text/xml");
			// Copy the content
			String line;
			while ((line = in.readLine()) != null) {
				out.print(line);
			}
			in.close();
			out.close();
		}
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
}