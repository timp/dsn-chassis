package org.cggh.chassis.manta.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Return a CSV file listing all files of a type. 
 */
public class GetFiles extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetFiles() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
		for (Enumeration headers=request.getHeaderNames(); headers.hasMoreElements();) {
		    String headerName = (String)headers.nextElement();
		    System.out.println("Name = " + headerName);
		    System.out.println("Value = " + request.getHeader(headerName));
		}
		HttpSession session = request.getSession();
		 */
		HttpClient client = new HttpClient();
		String url = "http://localhost:8080/repository/service/content/media/curated";
		ArrayList<StudyEntry> entries;
		ArrayList<StudyEntry> filteredEntries = new ArrayList<StudyEntry>();
		try {
			entries = getEntries(request, client, url, null);

			//Now we fetch the origin study info
			for(StudyEntry entry : entries) {
				//Can ignore the return
				if (entry.getCategory().equals("http://www.cggh.org/2010/chassis/terms/Explorer")) {
					getEntries(request, client, "", entry);
					filteredEntries.add(entry);
				}
			}

			PrintWriter out = new PrintWriter(response.getOutputStream(), true);

			response.setHeader("Content-Type", "text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=explorerfiles.csv");
			// Copy the content
			String line = "";

			out.println("chassisStudyId, publish, uploadDate, modules, url");

			for(StudyEntry entry : filteredEntries) {
				StudyEntry origin = entry.getOriginStudy();


				out.print(origin.getId());
				out.print(",");
				out.print(origin.getDisplay());
				out.print(",");
				out.print(origin.getPublished());
				out.print(",");
				out.print(origin.getModules());
				out.print(",");
				out.println(entry.getSelf());
			}
			out.print(line);

			out.close();
		} catch (SAXException e) {
			e.printStackTrace(System.err);
			//if there's a parse error it's probably because the user isn't authenticated and the login page isn't valid XML
			response.setStatus(HttpStatus.SC_FORBIDDEN);
		}
	}

	/**
	 * If studyEntry is null then get the entries from explicitURL
	 * If studyEntry is not null then get the originStudy for that entry
	 * 
	 * @param request
	 * @param client
	 * @param explicitURL
	 * @param studyEntry
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 * @throws SAXException
	 */
	private ArrayList<StudyEntry> getEntries(HttpServletRequest request, HttpClient client,
			String explicitURL, StudyEntry studyEntry) throws IOException, HttpException, SAXException {
		ArrayList<StudyEntry> ret = new ArrayList<StudyEntry>();
		String url;
		if (studyEntry == null) {
			url = explicitURL;
		} else {
			url = studyEntry.getOrigin();
		}
		GetMethod method = new GetMethod(url);
		method.setRequestHeader("cookie", request.getHeader("cookie"));

		int statusCode = client.executeMethod(method);
		if (statusCode == HttpStatus.SC_OK) {
			InputStream is = method.getResponseBodyAsStream();
			InputSource inputSource = new InputSource(is);
			DOMParser parser = new DOMParser();

			parser.parse(inputSource);

			Document doc = parser.getDocument();

			NodeList entries = doc.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "entry");
			Element entry;
			int i = 0;
			while ((entry = (Element) entries.item(i++)) != null ) {
				StudyEntry article;

				article = new StudyEntry();

				parseEntry(entry, article);
				if (studyEntry != null) {
					studyEntry.setOriginStudy(article);
				}
				ret.add(article);
			}
			is.close();
		} else throw new RuntimeException("Http status code other than " + HttpStatus.SC_OK + " returned: " + statusCode);
		method.releaseConnection();
		return ret;
	}


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
	/**
	 * Generic function to parse an AtomEntry and populate the article object
	 * Some specifics for either a study or a media entry
	 */
	private void parseEntry(Element entry, StudyEntry article) {
		NodeList categories = entry.getElementsByTagNameNS("http://www.w3.org/2005/Atom","category");
		if (categories.getLength() > 0) {
			Element objEl = (Element) categories.item(0);

			String term = objEl.getAttribute("term");
			article.setCategory(term);
		}

		NodeList ids = entry.getElementsByTagNameNS("http://www.cggh.org/2010/chassis/manta/xmlns","id");
		if (ids.getLength() > 0) {
			Element objEl = (Element) ids.item(0);

			String id = objEl.getFirstChild().getNodeValue();
			article.setId(id);
		}
		Element pubEl = (Element) entry.getElementsByTagNameNS("http://www.w3.org/2005/Atom","published").item(0);

		article.setPublished(pubEl.getFirstChild().getNodeValue());
		NodeList linksEl = entry.getElementsByTagNameNS("http://www.w3.org/2005/Atom","link");
		Element link;
		int j = 0;
		while ((link = (Element) linksEl.item(j++)) != null) {
			String rel = link.getAttribute("rel");
			String href = link.getAttribute("href");
			if (rel.equals("self")) {
				article.setSelf(href);
			} else if (rel.equals("http://www.cggh.org/2010/chassis/terms/originStudy")) {
				article.setOrigin(href);
			}
		}

		NodeList modulesList = entry.getElementsByTagName("modules");
		if (modulesList.getLength() > 0) {
			Element modules = (Element) modulesList.item(0);
			if (modules.getFirstChild() != null) {
				article.setModules(modules.getFirstChild().getNodeValue());
			} else {
				article.setModules("");
			}
		}

		NodeList displayList = entry.getElementsByTagName("explorer-display");
		if (displayList.getLength() > 0) {
			Element display = (Element) displayList.item(0);

			article.setDisplay(display.getFirstChild().getNodeValue());
		}
	}
}