package org.cggh.chassis.manta.http.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * @author timp
 * @since 17 Jun 2010
 */
public class DOILookup extends HttpServlet {

	private static final long serialVersionUID = -5048380360952603272L;

	public static final HttpClient client = new HttpClient();
	
	public DOILookup() {
		System.err.println("DOILookup constructor");
	}

	@Override
	public void init(ServletConfig config) throws ServletException {

		System.err.println("DOILookup init");

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String doi = getCleanDOI(req);
		if (doi != null) {
			HttpMethod get = new GetMethod("http://dx.doi.org/" + doi);
			client.executeMethod(get);
			if (new String(get.getResponseBody()).contains("Not Found")) { 
				resp.setStatus(404);
				resp.setContentType("text/html");
				printHeader(resp, "Resource not found");
				resp.getWriter().println("</html>");
			} else { 
				resp.setStatus(200);
				resp.setContentType("text/html");
				printHeader(resp, "Resource found");
				resp.getWriter().println("<p>Found " + doi + "</p>");				
				resp.getWriter().println("</html>");				
			}
		} else {
			resp.setStatus(200);
			resp.setContentType("text/html");
			
			printHeader(resp, "Resolve a DOI");

			resp.getWriter().println("<p>");
			resp.getWriter().println(
					"Enter a <a href=\"http://dx.doi.org/10.1000/1\">DOI</a>");
			resp.getWriter().println("</p>");

			resp.getWriter().println("<form method=\"GET\" action=\"\">");

			resp.getWriter().println(
					"doi: <input type=\"text\" name=\"doi\" size=\"70\" />");
			resp.getWriter().println("<br/>");
			resp.getWriter().println("<input type=\"SUBMIT\">");

			resp.getWriter().println("</form>");

			resp.getWriter().println("</body>");
			resp.getWriter().println("</html>");

		}
	}

	private void printHeader(HttpServletResponse resp, String title) throws IOException {
		resp.getWriter()
				.println(
						"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		resp.getWriter().println("<html>");
		resp.getWriter().println("<head>");
		resp.getWriter().println("<title>");
		resp.getWriter().println(title);
		resp.getWriter().println("</title>");
		resp.getWriter().println("</head>");
		resp.getWriter().println("<body>");
		resp.getWriter().println("<h1>");
		resp.getWriter().println(title);
		resp.getWriter().println("</h1>");

	}

	private String getCleanDOI(HttpServletRequest req) {
		String doi = getDOI(req);
		if (doi != null)
			doi = doi.replaceFirst("^(doi|DOI):(//)?", "");
		return doi;
	}

	private String getDOI(HttpServletRequest req) {
		if (req.getPathInfo() != null)
			if (req.getPathInfo() != "")
				if (req.getPathInfo() != "/")
					return req.getPathInfo();
		if (req.getQueryString() != null)
			if (req.getQueryString() != "")
				if (!req.getQueryString().contains("?"))
					return req.getQueryString();
		if (req.getParameter("DOI") != null)
			if (req.getParameter("DOI") != "")
				return req.getParameter("DOI");
		if (req.getParameter("doi") != null)
			if (req.getParameter("doi") != "")
				return req.getParameter("doi");

		return null;
	}
}
