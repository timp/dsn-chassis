package org.cggh.chassis.manta.http.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * A class to wrap dx.doi.org and return testable statuses.
 * 
 * @author timp
 * @since 17 Jun 2010
 */
public class DOILookup extends HttpServlet {

	private static final long serialVersionUID = -5048380360952603272L;

	public static final HttpClient client = new HttpClient();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String doi = getCleanDOI(req);
		if (doi != null) {
			String url = "http://dx.doi.org/" + doi;
			HttpMethod get = new GetMethod(url);
			client.executeMethod(get);
			if (new String(get.getResponseBody()).contains("Not Found")) {
				resp.setStatus(404);

				printHeader(resp, "Resource not found");

				resp.getWriter().println("<p>" + doi + " could not be dereferenced at <a href=\"" + url + "\">" + url + "</a>.</p>");

				printFooter(resp);
			} else {
				resp.setStatus(200);

				printHeader(resp, "Resource found");

				resp.getWriter().println("<p>Found " + doi + " at <a href=\"" + url + "\">" + url + "</a>.</p>");

				printFooter(resp);
			}
		} else {
			resp.setStatus(200);

			printHeader(resp, "Resolve a DOI");

			resp.getWriter().println("  <form method=\"get\" action=\"\">");
			resp.getWriter().println("   <p>");
			resp.getWriter().println("    Enter a <a href=\"http://dx.doi.org/10.1000/1\">DOI</a>");
			resp.getWriter().println("    <input type=\"text\" name=\"doi\" />");
			resp.getWriter().println("   </p>");
			resp.getWriter().println("   <p>");
			resp.getWriter().println("    <input type=\"submit\" />");
			resp.getWriter().println("   </p>");
			resp.getWriter().println("  </form>");

			printFooter(resp);
		}
	}

	private void printHeader(HttpServletResponse resp, String title) throws IOException {
		resp.setContentType("text/html; charset=utf-8");
		resp.getWriter().println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" ");
		resp.getWriter().println("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
		resp.getWriter().println("<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\" xml:lang=\"en\">");
		resp.getWriter().println(" <head>");
		resp.getWriter().println("  <meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" />");

		resp.getWriter().println("  <title>");
		resp.getWriter().println(title);
		resp.getWriter().println("  </title>");
		resp.getWriter().println(" </head>");
		resp.getWriter().println(" <body>");
		resp.getWriter().println("  <h1>");
		resp.getWriter().println(title);
		resp.getWriter().println("  </h1>");
	}

	private void printFooter(HttpServletResponse resp) throws IOException {
		resp.getWriter().println(" </body>");
		resp.getWriter().println("</html>");
	}

	static String getCleanDOI(HttpServletRequest req) {
		String doi = getDOI(req);
		if (doi != null) {
			doi = doi.replaceFirst("^/", "");
			doi = doi.replaceFirst("^(doi|DOI):(//)?", "");
		}
		return doi;
	}

	static String getDOI(HttpServletRequest req) {
		if (req.getPathInfo() != null)
			if (req.getPathInfo() != "")
				if (req.getPathInfo() != "/")
					return req.getPathInfo();
		if (req.getQueryString() != null)
			if (req.getQueryString() != "")
				if (!req.getQueryString().contains("="))
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
