package org.cggh.chassis.manta.http.servlet;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class StoreUploadsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -227666119754951587L;
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
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		
		try {
			
			Document reqd = builder.parse(req.getInputStream());
			
			// get target collection uri

			NodeList tcnl = reqd.getElementsByTagName("target-collection");
			
			// guard condition
			if (tcnl.getLength() != 1) {
				throw new BadRequestException("Exactly one target-collection element must be present.");
			}
			
			String tcu = ((Element)tcnl.item(0)).getTextContent();
			
			// process uploads
			
			NodeList fnl = reqd.getElementsByTagName("file");
			List<Document> resdl = new ArrayList<Document>();
			
			for (int i=0; i<fnl.getLength(); i++) {
				
				Element fe = (Element) fnl.item(i);
				Document resd = storeUpload(req, fe, tcu);
				resdl.add(resd);
				
			}
			
			// aggregate responses into a single document

			Document agd = builder.newDocument();
			Element root = agd.createElementNS("http://www.w3.org/2005/Atom", "feed");
			agd.appendChild(root);
			for (Document resd : resdl) {
				Element de = resd.getDocumentElement();
				Node imported = agd.importNode(de, true);
				root.appendChild(imported);
			}
			
			// return aggregated document
			res.setContentType("application/xml");
			res.setStatus(200);

			// Prepare the DOM document for writing 
			Source source = new DOMSource(agd); 
			
			// Prepare the output  
			Result result = new StreamResult(res.getOutputStream()); 
			
			// Write the DOM document to the output 
			Transformer xformer = TransformerFactory.newInstance().newTransformer(); 
			xformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	        xformer.setOutputProperty(OutputKeys.INDENT, "yes");
			xformer.transform(source, result); 
			
		} catch (BadRequestException e) {
		
			sendBadRequest(e.getLocalizedMessage(), res);
			
		} catch (SAXException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		
		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (Throwable t) {
			
			// TODO Auto-generated catch block
			t.printStackTrace();

		}

	}

	private Document storeUpload(HttpServletRequest req, Element fileElement, String targetCollectionUri) throws BadRequestException {
		
		// get upload element
		NodeList unl = fileElement.getElementsByTagName("upload");
		
		if (unl.getLength() != 1) {
			throw new BadRequestException("File elements must have exactly one upload child element.");
		}

		Element ue = (Element) unl.item(0);
		
		String fileName = ue.getAttribute("filename");
		String mediaType = ue.getAttribute("mediatype");
		
		try {
			
			URL tempFileUrl = new URL(ue.getTextContent());
			InputStream is = tempFileUrl.openStream();
			
			PostMethod method = new PostMethod(targetCollectionUri);
			RequestEntity entity = new InputStreamRequestEntity(is, is.available(), mediaType);
			method.setRequestEntity(entity);
			
			// forward headers
			method.setRequestHeader("Cookie", req.getHeader("Cookie"));
			method.setRequestHeader("Authorization", req.getHeader("Authorization"));

			HttpClient client = new HttpClient();
			int result = client.executeMethod(method);
			
			// whatever the result, parse response as document
			Document d = builder.parse(method.getResponseBodyAsStream());
			return d;

		} catch (MalformedURLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;
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

}
