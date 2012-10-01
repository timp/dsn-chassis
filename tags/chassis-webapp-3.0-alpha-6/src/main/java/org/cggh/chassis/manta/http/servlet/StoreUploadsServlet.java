package org.cggh.chassis.manta.http.servlet;

import org.apache.log4j.Logger;
import org.cggh.chassis.manta.security.ProxyServlet;
import org.cggh.chassis.manta.util.ConfigurableNamespacePrefixMapperImpl;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.w3._2005.atom.Entry;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class StoreUploadsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -227666119754951587L;
//	private Abdera abdera = new Abdera();

	private static final Logger logger = Logger
			.getLogger(StoreUploadsServlet.class.getName());

	private static final DocumentBuilderFactory factory;
	private static DocumentBuilder builder;

	static {

		factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);

		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void init(ServletConfig config) throws ServletException {

		if (builder == null)
			throw new ServletException("document build is not initialised");

	}

	public StoreUploadsServlet() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse res) {

		// return aggregated document
		res.setContentType("application/atom+xml");
		res.setStatus(500);
		try {

			org.w3c.dom.Document requestDocument = builder.parse(request
					.getInputStream());

			// get target collection uri
			Element requestDocumentElement = requestDocument
					.getDocumentElement();
			String targetCollectionUri = requestDocumentElement
					.getAttribute("target");

			// process uploads
			NodeList uploadNodeList = requestDocument
					.getElementsByTagName("upload");

			List<UploadResponse> responsesList = new ArrayList<UploadResponse>();

			for (int i = 0; i < uploadNodeList.getLength(); i++) {

				Element uploadElement = (Element) uploadNodeList.item(i);
				UploadResponse response = storeUpload(request, uploadElement,
						targetCollectionUri);

				responsesList.add(response);

			}

			// aggregate responses into a single document
			/*
			 * org.w3c.dom.Document aggregatedResponseDocument = builder
			 * .newDocument(); Element aggregatedResponseDocumentRoot =
			 * aggregatedResponseDocument
			 * .createElementNS("http://www.w3.org/2005/Atom", "feed");
			 * aggregatedResponseDocument
			 * .appendChild(aggregatedResponseDocumentRoot);
			 * 
			 * int responseStatus = 200; for (UploadResponse response :
			 * responsesList) { if (response.getStatus() != 201) {
			 * responseStatus = 207; } org.w3c.dom.Document responseDocument =
			 * response.getDocument(); Element documentElement =
			 * responseDocument.getDocumentElement(); Node
			 * importedDocumentElement = aggregatedResponseDocument
			 * .importNode(documentElement, true);
			 * aggregatedResponseDocumentRoot
			 * .appendChild(importedDocumentElement); }
			 */
			org.w3._2005.atom.Feed feed = new org.w3._2005.atom.Feed();

			int responseStatus = 200;
			List<org.w3._2005.atom.Entry> entries = new Vector<org.w3._2005.atom.Entry>();
			for (UploadResponse response : responsesList) {
				if (response.getStatus() != 201) {
					responseStatus = 207;
				}
				entries.add(response.getEntry());
			}
			feed.setEntry(entries);
			// return aggregated document
			
			res.setContentType("application/atom+xml;type=feed;charset=UTF-8");
			res.setStatus(responseStatus);

			Jaxb2Marshaller jaxb = new Jaxb2Marshaller();
			jaxb.setClassesToBeBound(org.w3._2005.atom.Feed.class);
			jaxb.marshal(feed, new StreamResult(res.getWriter()));
			/*
			 * // Prepare the DOM document for writing Source source = new
			 * DOMSource(aggregatedResponseDocument);
			 * 
			 * // Prepare the output Result result = new
			 * StreamResult(res.getOutputStream());
			 * 
			 * // Write the DOM document to the output Transformer xformer =
			 * TransformerFactory.newInstance() .newTransformer();
			 * xformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			 * xformer.setOutputProperty(OutputKeys.INDENT, "yes");
			 * xformer.transform(source, result);
			 */
		} catch (BadRequestException e) {

			sendBadRequest(e.getLocalizedMessage(), res);

		} catch (Throwable t) {

			logger.error("StoreUploadsServlet", t);

		}

	}

	private UploadResponse storeUpload(HttpServletRequest req, Element ue,
			String targetCollectionUri) throws BadRequestException,
			DOMException, IOException, SAXException, ServletException {

		UploadResponse uploadResponse = new UploadResponse();
		
		MockHttpServletResponse mockResp = new MockHttpServletResponse();

		String fileName = ue.getAttribute("filename");
		String mediaType = ue.getAttribute("mediatype");
		String size = ue.getAttribute("size");
		String typeTerm = ue.getAttribute("typeterm");
		String typeLabel = ue.getAttribute("typelabel");

		URL tempFileUrl = new URL(ue.getTextContent());
		InputStream uploadInputStream = tempFileUrl.openStream();
		MockHttpServletRequest mockReq = new MockHttpServletRequest();
		
		setRequestURL(targetCollectionUri, mockReq, req);

		mockReq.setSession(req.getSession());
		
		mockReq.addHeader("Slug", fileName);
		mockReq.addHeader("Content-Length", size);
		mockReq.addHeader("Content-Type", mediaType);
		org.w3._2005.atom.Entry savedEntry = null;

		savedEntry = ProxyServlet.doProxiedMethod(mockReq, mockResp,
				HttpMethod.POST, uploadInputStream,
				org.w3._2005.atom.Entry.class);
		// String sEntry = ProxyServlet.<String>doProxiedMethod(mockReq,
		// mockResp, HttpMethod.POST, uploadInputStream, String.class);
	//	System.out.println(mockResp.getStatus());
	//	System.out.println(mockResp.getContentAsString());

		uploadResponse.setStatus(mockResp.getStatus());
		
		if (mockResp.getStatus() == HttpServletResponse.SC_CREATED) {
			// if success, assume atom, try to append type category and put back
			
			org.w3._2005.atom.Category c = new org.w3._2005.atom.Category();
			c.setLabel(typeLabel);
			c.setTerm(typeTerm);
			c.setScheme("http://www.cggh.org/2010/chassis/scheme/FileTypes");
			savedEntry.setCategory(c);
			
			//We need to update the title as headers have to be ASCII (according to the RFC)
			//therefore file names containing accented characters need to be changed
			String utf8Name = new String(fileName.getBytes("UTF8"),"UTF8");
			savedEntry.getTitle().setContent(utf8Name);
			updateEntry(savedEntry, req);
		}
		uploadResponse.setEntry(savedEntry);
		/*
		 * Using Abdera URL tempFileUrl = new URL(ue.getTextContent());
		 * InputStream uploadInputStream = tempFileUrl.openStream();
		 * 
		 * AbderaClient atomClient = new AbderaClient(); RequestOptions
		 * requestOptions = createRequestOptions(req);
		 * 
		 * if (fileName != null) { requestOptions.setHeader("Slug", fileName); }
		 * 
		 * InputStreamRequestEntity uploadEntity = new InputStreamRequestEntity(
		 * uploadInputStream, mediaType);
		 * 
		 * ClientResponse response = atomClient.post(targetCollectionUri,
		 * uploadEntity, requestOptions);
		 * 
		 * uploadResponse.setStatus(response.getStatus()); if
		 * (response.getStatus() == 201) {
		 * 
		 * // if success, assume atom, try to append type category and put back
		 * Document<Entry> mediaLinkEntryDocument = response
		 * .getDocument(abdera.getParser()); Entry mediaLinkEntry =
		 * mediaLinkEntryDocument.getRoot(); mediaLinkEntry.addCategory(
		 * "http://www.cggh.org/2010/chassis/scheme/FileTypes", typeTerm,
		 * typeLabel); String editLocation =
		 * mediaLinkEntry.getEditLink().getHref() .toASCIIString();
		 * 
		 * response = atomClient.put(editLocation, mediaLinkEntryDocument,
		 * createRequestOptions(req));
		 * uploadResponse.setUpdateStatus(response.getStatus()); returnDocument
		 * = builder.parse(response.getInputStream());
		 * uploadResponse.setUpdateDocument(returnDocument); }
		 * 
		 * else {
		 * 
		 * // error document returnDocument =
		 * builder.parse(response.getInputStream());
		 * 
		 * } uploadResponse.setDocument(returnDocument);
		 */
		return uploadResponse;
	}

	public static void setRequestURL(String targetCollectionUri,
			MockHttpServletRequest mockReq, HttpServletRequest req) throws MalformedURLException {
		URL targ = new URL(targetCollectionUri);
		String serverName = targ.getHost();
		int port = targ.getPort();
		String proto = targ.getProtocol();
		mockReq.setServerName(serverName);

		mockReq.setServerPort(port);

		mockReq.setRequestURI(targ.getPath());
		mockReq.setProtocol(proto);
		mockReq.setScheme(proto);
		if (req != null) {
			mockReq.setScheme(req.getScheme());
			mockReq.setServerName(req.getServerName());
			mockReq.setServerPort(req.getServerPort());
		}

	}

    public static String requestToURL(HttpServletRequest req) {
        String serverName = req.getServerName();
        int port = req.getServerPort();
        String scheme = req.getScheme();
        String host = req.getHeader("x-forwarded-host");
        //If forwarded assume forwarded from https
        if (host != null && host.length() > 0) {
            if (!host.contains(":")) {
                port = -1;
                scheme = "https";
            }
            serverName = host;
        }
        StringBuilder url = new StringBuilder(scheme);
        url.append("://").append(serverName);

        if (port > 0) {
            url.append(':').append(port);
        }
        url.append(req.getRequestURI());
        return url.toString();
    }

	private org.w3._2005.atom.Entry updateEntry(
			Entry savedEntry, HttpServletRequest req)
			throws MalformedURLException, IOException, ServletException {
		org.w3._2005.atom.Entry response = null;
		MockHttpServletRequest mockReq;
		MockHttpServletResponse mockResp;
		HttpSession session = req.getSession();
		String editLocation = null;
		Iterator<org.w3._2005.atom.Link> iter = savedEntry.getLink().iterator();
		while (iter.hasNext()) {
			org.w3._2005.atom.Link link = iter.next();
			if (link.getRel().equals("edit")) {
				editLocation = link.getHref();
			}
		}
		mockReq = new MockHttpServletRequest();
		mockResp = new MockHttpServletResponse();
		mockReq.setSession(session);
		mockReq.addHeader("Content-Type", "application/atom+xml;type=entry");
		
		setRequestURL(editLocation, mockReq, req);
		
		Jaxb2Marshaller jaxb = new Jaxb2Marshaller();
		jaxb.setClassesToBeBound(org.w3._2005.atom.Entry.class);

		Map<String, String> mapping = new HashMap<String, String>();
		mapping.put("http://www.w3.org/2005/Atom", "atom");
		mapping.put("http://www.w3.org/2007/app", "app");
		mapping.put("http://purl.org/atombeat/xmlns", "atombeat");
		mapping.put("http://purl.org/atompub/revision/1.0", "ar");
		mapping.put("http://www.cggh.org/2010/chassis/manta/xmlns", "manta");
		ConfigurableNamespacePrefixMapperImpl mapper = new ConfigurableNamespacePrefixMapperImpl();
		mapper.setMapping(mapping);
		HashMap<String, Object> jaxbMarshallerProperties = new HashMap<String, Object>();
		// jaxbMarshallerProperties.put(javax.xml.bind.Marshaller.JAXB_FRAGMENT,Boolean.TRUE);
		jaxbMarshallerProperties.put(
				javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		jaxbMarshallerProperties.put("com.sun.xml.bind.namespacePrefixMapper",
				mapper);
		// jaxb.setJaxbContextProperties(jaxbContextProperties );
		jaxb.setMarshallerProperties(jaxbMarshallerProperties);
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		jaxb.marshal(savedEntry, new StreamResult(out));

		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		/*
		 * .toString()
		 * 
		 * .replace("<ns2:", "<atom:").replace("xmlns:ns2", "xmlns:atom")
		 * .getBytes());
		 */
		response = ProxyServlet.doProxiedMethod(mockReq, mockResp,
				HttpMethod.PUT, in, org.w3._2005.atom.Entry.class);

		return (response);
	}

	private void sendBadRequest(String message, HttpServletResponse res) {
		// TODO Auto-generated method stub
		try {
			res.sendError(500, "TODO");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* When using Abedera
	private RequestOptions createRequestOptions(HttpServletRequest req) {
		RequestOptions requestOptions = new RequestOptions();
		String authorizationHeader = req.getHeader("Authorization");
		if (authorizationHeader != null) {
			requestOptions.setAuthorization(authorizationHeader);
		}
		String cookieHeader = req.getHeader("Cookie");
		if (cookieHeader != null) {
			requestOptions.setHeader("Cookie", cookieHeader);
		}
		return requestOptions;
	}
*/
}
