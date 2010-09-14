package org.cggh.chassis.wwarn.ui.submitter.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Entry;
import org.apache.abdera.protocol.Response.ResponseType;
import org.apache.abdera.protocol.client.AbderaClient;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.abdera.protocol.client.RequestOptions;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cggh.chassis.generic.miniatom.client.ext.Chassis;
import org.cggh.chassis.wwarn.ui.common.client.Config;
import org.cggh.chassis.wwarn.ui.submitter.client.UploadFileForm;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;




/**
 * Servlet implementation class DataFileUploadServlet
 */
public class UploadFileFormSubmitHandler extends HttpServlet {
	
	
	private static final long serialVersionUID = 7564286884231124635L;

	private String mediaCollectionUrl;
	private Abdera abdera = new Abdera();
    private Log log = LogFactory.getLog(UploadFileFormSubmitHandler.class);
	

    
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadFileFormSubmitHandler() {
        super();
    }

    
    
    
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {

		this.mediaCollectionUrl = config.getInitParameter(Config.COLLECTION_MEDIA_URL);
		log.info(Config.COLLECTION_MEDIA_URL + ": "+ this.mediaCollectionUrl);
		
		// guard condition
		if (this.mediaCollectionUrl == null) {
			throw new ServletException("init param "+Config.COLLECTION_MEDIA_URL+" must be provided");
		}

	}

	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		// guard condition 
		if (!isMultipart) {

			sendError(response, "Only multipart content is supported.", HttpServletResponse.SC_BAD_REQUEST);
			return;
			
		}

		try {

			// variable to store form fields in
			Map<String,String> fields = new HashMap<String,String>();
			
			// variable to store initial atom entry in
			Entry mediaEntry = parseRequestAndStreamMedia(fields, request);
			
			// now try to persist media metadata
			mediaEntry = putMediaMetadata(request, mediaEntry, fields);
			
			// assume if we reach here, can return success
			respondWithEntry(response, mediaEntry);

		} catch (ContainsVirusException e) {
			sendError(response, "A virus has been detected: " + e.getMessage(), 
					HttpServletResponse.SC_BAD_REQUEST);			
		} catch (Throwable t) {

			sendError(response, "The server encountered an internal error which prevented it from completing the request: " + t.getMessage(), 
					HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

			t.printStackTrace();
		}
		
		
	}
	
	
	

	private Entry parseRequestAndStreamMedia(Map<String, String> fields,
											 HttpServletRequest request) 
			throws FileUploadException, IOException, ScannerException {
		
		Entry mediaEntry = null;
		
		// create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload();

		// parse the request
		FileItemIterator iter = upload.getItemIterator(request);

		while (iter.hasNext()) {

			FileItemStream item = iter.next();
		    String fieldName = item.getFieldName();
		    InputStream stream = item.openStream();

		    if (item.isFormField()) {
		    	
		    	String value = Streams.asString(stream);
		        
		    	log.info("Form field " + fieldName + " with value " + value + " detected.");
		        
		        fields.put(fieldName, value);
		        
		    } else {
		    	
			    String contentType = item.getContentType();

			    log.info("File field " + fieldName + " with file name " + item.getName() + ", content type " + contentType + " detected.");
		        
			    if (UploadFileForm.FIELD_FILE.equals(fieldName)) {
			    	
				    log.debug("File field " + fieldName + " with file name " + item.getName() + ", content type " + contentType + " detected.");

		    		if (!ClamAntiVirusScanner.DEVELOPMENT_INSTALLATION) {
		    			ClamAntiVirusScanner clamScanner = new ClamAntiVirusScanner();
		    			try { 
		    				stream = clamScanner.performScan(stream);
		    				// TODO It might be better to succeed but email administrator
		    			} catch (ContainsVirusException e) { 			       
		    				throw new ContainsVirusException("The file " + item.getName() + " appears to contain a virus.", e);
		    			}
		    		}

				    // process the input stream
			    	// TODO what happens if this field set as form data?
			    	fields.put(UploadFileForm.FIELD_FILENAME, item.getName()); 

			    	mediaEntry = postMediaResource(request, stream, contentType);
			        
			    } else { 
			    	
			    	log.info("Unexpected file field " + fieldName);

			    }
		        
		    }
		}	
		
		return mediaEntry;
	}



	
	private Entry postMediaResource(HttpServletRequest request, InputStream stream, String contentType) throws IOException {

		Entry entry = null;

		AbderaClient client = createAbderaClient(request);
	
		RequestOptions options = createRequestOptions(request);

		InputStreamRequestEntity entity = new InputStreamRequestEntity(stream, contentType);
		
		ClientResponse res = client.post(mediaCollectionUrl, entity, options);
		
		entry = getEntry(res);

		return entry;
	}

	
	
	
	
	private Entry putMediaMetadata(HttpServletRequest request, Entry mediaEntry, Map<String, String> fields) throws IOException {

		AbderaClient client = createAbderaClient(request);
	
		RequestOptions options = createRequestOptions(request);

		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mediaEntry.addAuthor(null, user.getUsername(), null);
		
		String title = fields.get(UploadFileForm.FIELD_FILENAME);
		if (title != null) mediaEntry.setTitle(title);

		String summary = fields.get(UploadFileForm.FIELD_SUMMARY);
		if (summary != null) mediaEntry.setSummary(summary);
		
		String type = fields.get(UploadFileForm.FIELD_TYPE);
		if (type != null) {
	
			String label = null;

			if (type.equals(Chassis.TERM_OTHER)) {
				label = fields.get(UploadFileForm.FIELD_OTHERTYPE);
			}

			mediaEntry.addCategory(Chassis.SCHEME_FILETYPES, type, label);

		}

		String entryUrl = mediaEntry.getEditLink().getHref().toASCIIString(); 

		options.setContentType("application/atom+xml; type=entry; charset=UTF-8"); 

		ClientResponse res = client.put(entryUrl, mediaEntry, options);

		mediaEntry = getEntry(res);

		return mediaEntry;
	}




	private AbderaClient createAbderaClient(HttpServletRequest request) {

		AbderaClient client = new AbderaClient();

		// forward cookies
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String domain = cookie.getDomain();
				if (domain == null) domain = request.getLocalName();
				String name = cookie.getName();
				String value = cookie.getValue();
				String path = cookie.getPath();
				if (path == null) path = "/"; 
				int maxAge = cookie.getMaxAge();
				boolean secure = cookie.getSecure();
				log.info("forwarding cookie, domain: "+domain+", name: "+name+", value: "+value+", path: "+path+", maxAge: "+maxAge+", secure: "+secure);
				client.addCookie(domain, name, value, path, maxAge, secure);
			}
		}

		return client;
	}
	
	
	
	private RequestOptions createRequestOptions(HttpServletRequest request) {
		
		RequestOptions options = new RequestOptions();
		
		// forward Authorization header and cookies
		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null) {
			log.info("found auth header: "+authorizationHeader);
			options.setAuthorization(authorizationHeader);
		}
		else {
			log.info("did not find auth header");
		}
		
		return options;
	}

	
	
	
	private Entry getEntry(ClientResponse res) throws IOException {
		
		Entry entry = null;
		
		if (res.getType() == ResponseType.SUCCESS) {

			log.info("request success");

			Document<Entry> doc = res.getDocument(abdera.getParser());
			entry = doc.getRoot();

			return entry;

		}
		else {
			
			log.error("request failed, "+res.getStatus()+" "+res.getStatusText()+"\n");
			
			String message = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(res.getInputStream()));
			String line = reader.readLine();
			while (line != null) {
				message += line;
				line = reader.readLine();
			}
			
			throw new RuntimeException(message);
			
		}

	}
	
	
	
	private void respondWithEntry(HttpServletResponse response, Entry entry) throws IOException {

		response.setStatus(HttpServletResponse.SC_OK);

		// workaround for GWT forms - needs content-type text/html
		response.setContentType("text/html"); 
		
		// embed content in body comment so it is preserved and available to GWT client
		String content = "<html><head><title>response</title></head><body><!--" + entry.toString() + "--></body></html>";
		
		response.getWriter().print(content);
		response.flushBuffer();
	}




	protected void sendError(HttpServletResponse response, String content, int code) throws IOException {
		
		response.setStatus(code);
		response.setContentType("text/html");
		response.getWriter().print(content);
		response.flushBuffer();

	}



	

	
	
	


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sendError(response, "only POST requests are allowed", HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sendError(response, "only POST requests are allowed", HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sendError(response, "only POST requests are allowed", HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * @see HttpServlet#doHead(HttpServletRequest, HttpServletResponse)
	 */
	protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sendError(response, "only POST requests are allowed", HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * @see HttpServlet#doOptions(HttpServletRequest, HttpServletResponse)
	 */
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sendError(response, "only POST requests are allowed", HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * @see HttpServlet#doTrace(HttpServletRequest, HttpServletResponse)
	 */
	protected void doTrace(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sendError(response, "only POST requests are allowed", HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}
	
	
	

	
	



}
