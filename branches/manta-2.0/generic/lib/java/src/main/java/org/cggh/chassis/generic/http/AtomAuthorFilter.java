package org.cggh.chassis.generic.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author timp
 * @since 26 Feb 2010
 */
public final class AtomAuthorFilter extends HttpFilter {
	enum HttpMethod {
		HEAD(), GET(), PUT(), POST(), DELETE();
	}

	@Override
	public void doHttpFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request.getContentType() != null
				&& request.getContentType().startsWith("application/atom+xml")) {

			switch (HttpMethod.valueOf(request.getMethod())) {
			case GET:
				BufferedHttpResponseWrapper getResponseWrapper = new BufferedHttpResponseWrapper(
						(HttpServletResponse) response);
				chain.doFilter(request, getResponseWrapper);
				if (isEntry(getResponseWrapper.getContent())) {
					String user = getUser(request);
					if (user == null) {
						response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
								"No user found");
						return;
					}

					List<String> authors = getAuthors(getResponseWrapper
							.getContent());
					if (!authors.contains(user)) {
						response.sendError(HttpServletResponse.SC_FORBIDDEN,
						"You may only update an item of which you are the author");
						return;
					}
				}
				response.setContentLength(getResponseWrapper.getBuffer().length);
				response.getOutputStream().write(getResponseWrapper.getBuffer());
				response.flushBuffer();
				break;
			case PUT:
			{
				String user = getUser(request);
				String url = request.getRequestURL().toString();
				HttpURLConnection connection = (HttpURLConnection) new URL(url)
						.openConnection();
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Content-Type", request.getContentType());
				String authorization = request.getHeader("Authorization");
				if (authorization != null)
					connection.setRequestProperty("Authorization",
							authorization);
				try {
					connection.connect();
				} catch (IOException e) { 
					throw new IOException("Could not GET " + url, e);
				}

				if (connection.getResponseCode() == HttpServletResponse.SC_OK) {
					String content = getContent(connection);
					if (isEntry(content)) {
						if (user == null) {
							response.sendError(
									HttpServletResponse.SC_UNAUTHORIZED,
									"No user found");
							return;
						}
						List<String> authors = getAuthors(content);
						if (!authors.contains(user)) {
							response
									.sendError(
											HttpServletResponse.SC_FORBIDDEN,
									"You may only update an item of which you are the author");
							System.out
									.println("401: You may only update an item of which you are the author");
							return;
						}
					}
					chain.doFilter(request, response);
				} else {
					System.err
							.println(connection.getResponseCode()
									+ ":You may only update an item of which you are the author");
					response
							.sendError(connection.getResponseCode(),
									"You may only update an item of which you are the author");
				}
			}
				break;
			case POST:
			{
				String url = request.getRequestURL().toString();
				HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Content-Type", request.getContentType());
				String authorization = request.getHeader("Authorization");
				if (authorization != null)
					connection.setRequestProperty("Authorization",
							authorization);
				try {
					connection.connect();
				} catch (IOException e) { 
					throw new IOException("Could not GET " + url, e);
				}


				//System.err.println("POST-" + 
				//		connection.getResponseCode()
				//		+ ": " + connection.getResponseMessage());
				if (connection.getResponseCode() == HttpServletResponse.SC_OK) {
					String content = getContent(connection);
					//System.err.println("Got:" + content);
					if (isFeed(content)) {
						chain.doFilter(request, response);
					} else { 
						//System.err.println(
						//		HttpServletResponse.SC_BAD_REQUEST
						//	+ ": You may only post to feed URLs");
						response.sendError(
								HttpServletResponse.SC_BAD_REQUEST, "You may only post to feed URLs");
					}
				} else if (connection.getResponseCode() == HttpServletResponse.SC_NOT_FOUND) {
					chain.doFilter(request, response);					
				} else {
					response.sendError(
							connection.getResponseCode(), connection.getResponseMessage());
				}
			}
				break;
			case DELETE:
				response.sendError(HttpServletResponse.SC_FORBIDDEN,
						"You may not use this system to delete.");
				break;
			default:
				response.sendError(
						HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Unexpected value of request method: "
								+ request.getMethod());
				throw new RuntimeException(
						"Unexpected value of request method: "
								+ request.getMethod());
			}
		} else
			chain.doFilter(request, response);
	}

	private boolean isEntry(String content) {
		return content.startsWith("<atom:entry");
	}

	private boolean isFeed(String content) {
		return content.startsWith("<atom:feed");
	}

	private String getContent(HttpURLConnection connection) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(connection
				.getInputStream()));
		String line;
		String content = "";
		while ((line = in.readLine()) != null)
			content += line;
		in.close();
		return content;
	}

	private static List<String> getAuthors(String atomEntry) throws IOException {
		ArrayList<String> authors = new ArrayList<String>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		// NOTE we need our own javax.xml.parsers.ParserConfigurationException
		// to avoid the catch here
		db = factory.newDocumentBuilder();
		
		InputSource inStream = new InputSource();
		inStream.setCharacterStream(new StringReader(atomEntry));
		Document doc;
		try {
			doc = db.parse(inStream);
		} catch (SAXException e) {
			throw new RuntimeException("Malformed XML", e);
		} 

		doc.getDocumentElement().normalize();
		NodeList authorNodes = doc.getElementsByTagName("atom:author");
		if (authorNodes.getLength() > 1)
			throw new RuntimeException("More than one author element in "
					+ atomEntry);
		else if (authorNodes.getLength() == 0)
			throw new RuntimeException("No author element in " + atomEntry);
		NodeList emailNodes = authorNodes.item(0).getChildNodes();
		if (emailNodes.getLength() < 1)
			throw new RuntimeException(
					"No email elements in author element in " + atomEntry);
		for (int s = 0; s < emailNodes.getLength(); s++) {
			Node emailNode = emailNodes.item(s);
			authors.add(emailNode.getTextContent());
		}
		return authors;
	}

	private static String getUser(HttpServletRequest request) {
		String basicType = "Basic ";
		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null) {
			if (authorizationHeader.indexOf(basicType) < 0)
				throw new IllegalArgumentException("Authorization not '"
						+ basicType + "':" + authorizationHeader);
			String user = new String(new Base64().decode(authorizationHeader
					.replace("Basic ", "").getBytes())).split(":")[0];
			return user;
		} else
			return null;
	}

}
