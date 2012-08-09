package org.cggh.chassis.manta.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
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

	private static Log logger = LogFactory.getLog(GetFiles.class);

	PrintWriter out; // also used for debug logging

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	public GetFiles() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
		 * for (Enumeration headers=request.getHeaderNames();
		 * headers.hasMoreElements();) { String headerName =
		 * (String)headers.nextElement(); System.out.println("Name = " +
		 * headerName); System.out.println("Value = " +
		 * request.getHeader(headerName)); } HttpSession session =
		 * request.getSession();
		 */
		HttpClient client = new DefaultHttpClient();
		String collectionUrl = "http://localhost:8080/repository/service/content/media/curated";
		ArrayList<AtomEntry> curatedMediaEntries;
		ArrayList<AtomEntry> explorerFileEntries = new ArrayList<AtomEntry>();
		try {

			response.setHeader("Content-Type", "text/csv");
			response.setHeader("Content-Disposition",
					"attachment; filename=explorerfiles.csv");

			// get all the file entries
			curatedMediaEntries = getEntries(request, client, collectionUrl,
					null);

			out = new PrintWriter(response.getOutputStream(), true);

			// Augment each curated file entry with its origin study field
			for (AtomEntry entry : curatedMediaEntries) {

				// if
				// (entry.getCategory().equals("http://www.cggh.org/2010/chassis/terms/Explorer"))
				// {
				if (entry.getCategory() != null) {
					// Can ignore the return
					getEntries(request, client, entry.getOrigin(), entry);
					explorerFileEntries.add(entry);
					if (logger.isDebugEnabled()) {
						logger.debug("Adding explorer file entry "
								+ entry.getOriginStudy().getId());
					}
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("filtering out " + entry.getCategory());
					}
				}
			}
			Hashtable<String, AtomEntry> latest = new Hashtable<String, AtomEntry>();
			for (AtomEntry explorerFileAtomEntry : explorerFileEntries) {
				String key = explorerFileAtomEntry.getOriginStudy().getId()
						+ ":" + explorerFileAtomEntry.getTitle();
				AtomEntry currentEntry = latest.get(key);
				if (currentEntry != null) {
					Date cDate = null;
					try {
						cDate = dateFormat.parse(currentEntry.getPublished());
					} catch (ParseException e) {
						throw new RuntimeException(e);
					}
					Date tDate = null;
					try {
						tDate = dateFormat.parse(explorerFileAtomEntry
								.getPublished());
					} catch (ParseException e) {
						throw new RuntimeException(e);
					}
					if (tDate.after(cDate)) {
						latest.put(key, explorerFileAtomEntry);
						if (logger.isDebugEnabled()) {
							logger.debug("Putting earlier into latest:" + key + " "
									+ tDate + ">" + cDate);
						}
					} else {
						if (logger.isDebugEnabled()) {
							logger.debug("Excluding later from latest:" + key + " "
									+ tDate + "<" + cDate);
						}
					}
				} else {
					latest.put(key, explorerFileAtomEntry);
					if (logger.isDebugEnabled()) {
						logger.debug("Putting unknown into latest:" + key);
					}
				}
			}

			out.println("chassisId,title, publish, uploadDate, modules, url");

			for (AtomEntry entry : explorerFileEntries) {
				String key = entry.getOriginStudy().getId() + ":"
						+ entry.getTitle();
				if (entry.equals(latest.get(key))) {
					AtomEntry origin = entry.getOriginStudy();

					out.print(origin.getId());
					out.print(",");
					out.print(entry.getTitle());
					out.print(",");
					out.print(origin.getDisplay());
					out.print(",");
					out.print(entry.getPublished());
					out.print(",");
					out.print(origin.getModules());
					out.print(",");
					out.print(entry.getSelf());
					out.println();
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("Ignoring " + key);
					}
				}
			}

			out.close();
		} catch (SAXException e) {
			e.printStackTrace(System.err);
			// if there's a parse error it's probably because the user isn't
			// authenticated
			// and the login page isn't valid XML
			response.setStatus(HttpStatus.SC_FORBIDDEN);
			out.print(e.getMessage());
		} catch (RuntimeException e) {
			response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			logger.error("Runtime exception", e);
		} catch (HttpException e) {
			response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			logger.error("Http exception", e);
		} catch (ParserConfigurationException e) {
			response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			logger.error("Parser config exception", e);
		}
	}

	/**
	 * 
	 * If studyEntry is not null then augment it.
	 * 
	 * @param request
	 * @param client
	 * @param url
	 * @param mediaEntry
	 *            may be null
	 * @return a list with all AtomEntries from the Curated Media collection or
	 *         a specific Study AtomEntry
	 * @throws ParserConfigurationException 
	 */
	private ArrayList<AtomEntry> getEntries(HttpServletRequest request,
			HttpClient client, String url, AtomEntry mediaEntry)
			throws IOException, HttpException, SAXException, ParserConfigurationException {
		ArrayList<AtomEntry> ret = new ArrayList<AtomEntry>();

		HttpGet method = new HttpGet(url);
		method.setHeader("cookie", request.getHeader("cookie"));
		HttpResponse httpResponse = client.execute(method);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if (statusCode == HttpStatus.SC_OK) {
			InputStream is = httpResponse.getEntity().getContent();

			InputSource inputSource = new InputSource(is);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = dbf.newDocumentBuilder();

			Document doc = parser.parse(inputSource);

			// atom:entry is the outermost enclosing tag of an AtomEntry but
			// repeated for an AtomFeed

			NodeList entries = doc.getElementsByTagNameNS(
					"http://www.w3.org/2005/Atom", "entry");
			Element entryElement;
			int i = 0;
			while ((entryElement = (Element) entries.item(i++)) != null) {
				AtomEntry atomEntry = new AtomEntry();
				parseEntry(entryElement, atomEntry);
				if (mediaEntry != null) { // we are augmenting
					mediaEntry.setOriginStudy(atomEntry); // atomEntry will be a
															// Study here
				}
				ret.add(atomEntry);
			}
			is.close();
		} else
			throw new RuntimeException("Http status code other than "
					+ HttpStatus.SC_OK + " returned: " + statusCode);

		return ret;
	}

	/**
	 * Generic function to parse an AtomEntry and populate the article object.
	 * Some specifics for either a study or a media entry.
	 */
	private void parseEntry(Element entry, AtomEntry article) {
		NodeList categories = entry.getElementsByTagNameNS(
				"http://www.w3.org/2005/Atom", "category");
		if (categories.getLength() > 0) {
			Element objEl = (Element) categories.item(0);

			String term = objEl.getAttribute("term");
			article.setCategory(term);
		}

		NodeList ids = entry.getElementsByTagNameNS(
				"http://www.cggh.org/2010/chassis/manta/xmlns", "id");
		if (ids.getLength() > 0) {
			Element objEl = (Element) ids.item(0);

			String id = objEl.getFirstChild().getNodeValue();
			article.setId(id);
		}

		Element titleEl = (Element) entry.getElementsByTagNameNS(
				"http://www.w3.org/2005/Atom", "title").item(0);
		article.setTitle(titleEl.getFirstChild().getNodeValue());

		Element pubEl = (Element) entry.getElementsByTagNameNS(
				"http://www.w3.org/2005/Atom", "published").item(0);
		article.setPublished(pubEl.getFirstChild().getNodeValue());

		NodeList linksEl = entry.getElementsByTagNameNS(
				"http://www.w3.org/2005/Atom", "link");
		Element link;
		int j = 0;
		while ((link = (Element) linksEl.item(j++)) != null) {
			String rel = link.getAttribute("rel");
			String href = link.getAttribute("href");
			if (rel.equals("self")) {
				article.setSelf(href);
			} else if (rel
					.equals("http://www.cggh.org/2010/chassis/terms/originStudy")) {
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