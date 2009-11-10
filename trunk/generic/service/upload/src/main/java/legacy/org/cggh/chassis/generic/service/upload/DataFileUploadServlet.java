package legacy.org.cggh.chassis.generic.service.upload;

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
public class DataFileUploadServlet extends HttpServlet {
	
	
	
	private static final long serialVersionUID = 1L;
	
	
	
	public static final String INITPARAM_COLLECTIONURL = "atomCollectionUrl";



	private String collectionUrl;
	private Abdera abdera = new Abdera();
    private Log log = LogFactory.getLog(this.getClass());
	
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataFileUploadServlet() {
        super();
    }

    
    
    
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {

		this.collectionUrl = config.getInitParameter(INITPARAM_COLLECTIONURL);
		
		// guard condition
		if (this.collectionUrl == null) {
			throw new ServletException("init param "+INITPARAM_COLLECTIONURL+" must be provided");
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
			Entry entry = null;
			
			// create an abdera client
			AbderaClient client = createAbderaClient(request);
			
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
				        entry = persistFile(client, createRequestOptions(request), stream, contentType);
				    }
			        
			    }
			}
			
			// assume stream is persisted, now try to persist metadata
			entry = persistMetadata(client, createRequestOptions(request), entry, fields);
			
			// assume if we reach here, can return success
			respondWithEntry(response, entry);

		}
		catch (Throwable t) {

			t.printStackTrace();
			sendError(response, t.getLocalizedMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

		}
		
		
	}
	
	
	

	private void respondWithEntry(HttpServletResponse response, Entry entry) throws IOException {

		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/xml");
		
		// serialise entry
		entry.writeTo(response.getOutputStream());
		response.flushBuffer();
	}




	private Entry persistMetadata(AbderaClient client, RequestOptions options, Entry entry, Map<String, String> fields) throws IOException {

		// modify entry and put back
		
		// add category
		entry.addCategory(Chassis.SCHEME_TYPES, Chassis.Type.DATAFILE, null);
		
		if (fields.containsKey(ChassisConstants.FIELD_SUBMISSION)) {
			String href = fields.get(ChassisConstants.FIELD_SUBMISSION); // link to submission
			String rel = Chassis.Rel.SUBMISSION;
			entry.addLink(href, rel);
		}
		
		if (fields.containsKey(ChassisConstants.FIELD_AUTHOREMAIL)) {
			String authoremail = fields.get(ChassisConstants.FIELD_AUTHOREMAIL);
			entry.addAuthor(null, authoremail, null);
		}
		
		if (fields.containsKey(ChassisConstants.FIELD_SUMMARY)) {
			String summary = fields.get(ChassisConstants.FIELD_SUMMARY);
			entry.setSummary(summary);
		}

		if (fields.containsKey(ChassisConstants.FIELD_FILENAME)) {
			String title = fields.get(ChassisConstants.FIELD_FILENAME);
			entry.setTitle(title);
		}
		
		// ignore any other fields
		
		String entryUrl = collectionUrl + entry.getEditLink().getHref().toASCIIString();

		options.setContentType("application/atom+xml; type=entry; charset=UTF-8"); // needed, otherwise content type application/xml, which exist rejects
		ClientResponse res = client.put(entryUrl, entry, options);

		if (res.getType() == ResponseType.SUCCESS) {

			Document<Entry> doc = res.getDocument();
			entry = doc.getRoot();

			log.info("persisted metadata OK");
			log.info("title: "+entry.getTitle());
			log.info("edit link: "+entry.getEditLink());
			log.info("edit media link: "+entry.getEditMediaLink());
			log.info("chassis.submission link: "+entry.getLink(Chassis.Rel.SUBMISSION));
			
		}
		else {
			
			String message = "failed to persist metadata, response: "+res.getStatus()+" "+res.getStatusText()+"\n";
			BufferedReader reader = new BufferedReader(new InputStreamReader(res.getInputStream()));
			String line = reader.readLine();
			while (line != null) {
				message += line;
				line = reader.readLine();
			}
			throw new RuntimeException(message);

		}
		
		return entry;
	}




	private Entry persistFile(AbderaClient client, RequestOptions options, InputStream stream, String contentType) throws IOException {

		Entry entry = null;

		InputStreamRequestEntity entity = new InputStreamRequestEntity(stream, contentType);
		
		ClientResponse res = client.post(collectionUrl, entity, options);
		
		if (res.getType() == ResponseType.SUCCESS) {

			Document<Entry> doc = res.getDocument(abdera.getParser());
			entry = doc.getRoot();

			log.info("persisted file OK");
			log.info("title: "+entry.getTitle());
			log.info("edit link: "+entry.getEditLink());
			log.info("edit media link: "+entry.getEditMediaLink());
			log.info("chassis.submission link: "+entry.getLink(Chassis.Rel.SUBMISSION));

		}
		else {
			
			String message = "failed to persist file, response: "+res.getStatus()+" "+res.getStatusText()+"\n";
			BufferedReader reader = new BufferedReader(new InputStreamReader(res.getInputStream()));
			String line = reader.readLine();
			while (line != null) {
				message += line;
				line = reader.readLine();
			}
			throw new RuntimeException(message);
			
		}

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
	
	
	
	protected void sendError(HttpServletResponse response, String message, int code) throws IOException {
		
		response.setStatus(code);
		response.setContentType("text/plain");
		response.getWriter().print(message);
		response.flushBuffer();

	}





}
