package org.cggh.chassis.generic.service.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
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
import org.cggh.chassis.generic.atomext.shared.Chassis;
import org.cggh.chassis.generic.atomext.shared.ChassisConstants;

/**
 * Servlet implementation class DataFileUploadServlet
 */
public class UploadDataFileRevisionServlet extends HttpServlet {
	
	
	
	private static final long serialVersionUID = 1L;
	
	
	
	public static final String INITPARAM_MEDIACOLLECTIONURL = "mediaCollectionUrl";
	public static final String INITPARAM_DATAFILESCOLLECTIONURL = "dataFilesCollectionUrl";



	private String mediaCollectionUrl;
	private String dataFilesCollectionUrl;
	
	
	private Abdera abdera = new Abdera();
    private Log log = LogFactory.getLog(UploadDataFileRevisionServlet.class);
	
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadDataFileRevisionServlet() {
        super();
    }

    
    
    
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {

		this.mediaCollectionUrl = config.getInitParameter(INITPARAM_MEDIACOLLECTIONURL);
		log.info(INITPARAM_MEDIACOLLECTIONURL + ": "+ this.mediaCollectionUrl);
		
		// guard condition
		if (this.mediaCollectionUrl == null) {
			throw new ServletException("init param "+INITPARAM_MEDIACOLLECTIONURL+" must be provided");
		}

		this.dataFilesCollectionUrl = config.getInitParameter(INITPARAM_DATAFILESCOLLECTIONURL);
		log.info(INITPARAM_DATAFILESCOLLECTIONURL + ": "+ this.dataFilesCollectionUrl);
		
		// guard condition
		if (this.dataFilesCollectionUrl == null) {
			throw new ServletException("init param "+INITPARAM_DATAFILESCOLLECTIONURL+" must be provided");
		}

	}

	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		// guard condition 
		if (!isMultipart) {

			sendError(response, "only multipart content is supported", HttpServletResponse.SC_BAD_REQUEST);
			return;
			
		}

		try {

			// variable to store form fields in
			Map<String,String> fields = new HashMap<String,String>();
			
			// variable to store initial atom entry in
			Entry mediaEntry = parseRequestAndStreamMedia(fields, request);
			
			// now try to persist media metadata
			mediaEntry = putMediaMetadata(request, mediaEntry, fields);
			
			// now try to create a new data file entry
			Entry dataFileEntry = putDataFile(request, mediaEntry, fields);
			
			// assume if we reach here, can return success
			respondWithEntry(response, dataFileEntry);

		}
		catch (Throwable t) {

			t.printStackTrace();
			sendError(response, "the server encountered an internal error which prevented it from completing the request", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

		}
		
		
	}
	
	
	

	/**
	 * @param fields
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws FileUploadException 
	 */
	private Entry parseRequestAndStreamMedia(
		Map<String, String> fields,
		HttpServletRequest request) 
		throws FileUploadException, IOException {
		
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
		        
			    if (ChassisConstants.FIELD_MEDIA.equals(fieldName)) {
			    	
			        // process the input stream
			    	fields.put(ChassisConstants.FIELD_FILENAME, item.getName()); // TODO what happens if this field set as form data?

			    	mediaEntry = postMediaResource(request, stream, contentType);
			        
			    }
		        
		    }
		}	
		
		return mediaEntry;
	}




	/**
	 * @param client
	 * @param options
	 * @param mediaEntry
	 * @param fields
	 * @return
	 * @throws IOException 
	 */
	private Entry putDataFile(
			HttpServletRequest request, 
			Entry mediaEntry,
			Map<String, String> fields) throws IOException {
		
		if (fields.containsKey(ChassisConstants.FIELD_DATAFILEURL) && !fields.get(ChassisConstants.FIELD_DATAFILEURL).equals("")) {
			
			AbderaClient client = createAbderaClient(request);
			
			RequestOptions options = createRequestOptions(request);

			String url = this.dataFilesCollectionUrl + fields.get(ChassisConstants.FIELD_DATAFILEURL); // assume relative

			log.info("retrieving datafile entry to update: "+url);
			ClientResponse res = client.get(url, options);
			
			Entry dataFileEntry = this.getEntry(res);
			
			// add link to new revision
			String revisionLink = mediaEntry.getEditLink().getHref().toASCIIString();
			dataFileEntry.addLink(revisionLink, Chassis.Rel.REVISION);
			
			// POST new datafile
			log.info("putting revised data file to: "+url);

			options.setContentType("application/atom+xml; type=entry; charset=UTF-8"); // needed, otherwise content type application/xml, which exist rejects

			res = client.put(url, dataFileEntry, options);

			dataFileEntry = getEntry(res);
			
			return dataFileEntry;
		}
		else {
			
			log.warn("no data file url provided, media was persisted but no data file was updated");
			return null;
			
		}
	}




	private Entry putMediaMetadata(HttpServletRequest request, Entry mediaEntry, Map<String, String> fields) throws IOException {

		AbderaClient client = createAbderaClient(request);
	
		RequestOptions options = createRequestOptions(request);

		mediaEntry.addCategory(Chassis.SCHEME_TYPES, Chassis.Type.MEDIARESOURCE, null);
		
		if (fields.containsKey(ChassisConstants.FIELD_AUTHOREMAIL)) {
			String authoremail = fields.get(ChassisConstants.FIELD_AUTHOREMAIL);
			mediaEntry.addAuthor(null, authoremail, null);
		}
		
		if (fields.containsKey(ChassisConstants.FIELD_FILENAME)) {
			String title = fields.get(ChassisConstants.FIELD_FILENAME);
			mediaEntry.setTitle(title);
		}

		if (fields.containsKey(ChassisConstants.FIELD_SUMMARY)) {
			String summary = fields.get(ChassisConstants.FIELD_SUMMARY);
			mediaEntry.setSummary(summary);
		}

		String entryUrl = mediaCollectionUrl + mediaEntry.getEditLink().getHref().toASCIIString(); // handle relative urls

		options.setContentType("application/atom+xml; type=entry; charset=UTF-8"); // needed, otherwise content type application/xml, which exist rejects

		ClientResponse res = client.put(entryUrl, mediaEntry, options);

		mediaEntry = getEntry(res);

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

	
	
	private AbderaClient createAbderaClient(HttpServletRequest request) {

		AbderaClient client = new AbderaClient();

		// forward cookies
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				client.addCookie(cookie.getDomain(), cookie.getName(), cookie.getValue(), cookie.getPath(), cookie.getMaxAge(), cookie.getSecure());
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
			StringWriter sw = new StringWriter();
			doc.writeTo(sw);
			log.info(sw.getBuffer().toString());
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
