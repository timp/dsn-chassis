package org.cggh.chassis.wwarn.ui.anonymizer.server;

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
import org.cggh.chassis.wwarn.ui.anonymizer.client.CleanFileWidgetRenderer;
import org.cggh.chassis.wwarn.ui.common.client.Config;
import org.cggh.chassis.wwarn.ui.anonymizer.server.ClamAntiVirusNotRunningException;
import org.cggh.chassis.wwarn.ui.anonymizer.server.ClamAntiVirusScanner;
import org.cggh.chassis.wwarn.ui.anonymizer.server.ContainsVirusException;
import org.cggh.chassis.wwarn.ui.anonymizer.server.ScannerException;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;




/**
 * Servlet implementation class DataFileUploadServlet
 */
public class CleanFileFormSubmitHandler extends HttpServlet {
	
	 private Log log = LogFactory.getLog(CleanFileFormSubmitHandler.class);
	 
	 private static final long serialVersionUID = 5355818071593333110L;
	
	// TODO: Centralise the ClamAntiVirus classes and exceptions, to wwarn.ui.common.server or filter. (see issue 168)

	// TODO: Perhaps make getters and setters for these variables, to centralise the origin of their values.
	private String mediaCollectionUrl;
	private String derivationsCollectionUrl;
	
	private Abdera abdera = new Abdera();
   
	

    
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CleanFileFormSubmitHandler() {
        super();
    }

    
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig servletConfig) throws ServletException {
		
		this.mediaCollectionUrl = servletConfig.getInitParameter(Config.COLLECTION_MEDIA_URL);
		log.info(Config.COLLECTION_MEDIA_URL + ": " + this.mediaCollectionUrl);

		this.derivationsCollectionUrl = servletConfig.getInitParameter(Config.COLLECTION_DERIVATIONS_URL);
		log.info(Config.COLLECTION_DERIVATIONS_URL + ": " + this.derivationsCollectionUrl);

		// Guarding conditions.
		if (this.mediaCollectionUrl == null) {
			throw new ServletException("init param " + Config.COLLECTION_MEDIA_URL + " must be provided");
		}		
		
		if (this.derivationsCollectionUrl == null) {
			throw new ServletException("init param " + Config.COLLECTION_DERIVATIONS_URL + " must be provided");
		}

	}

	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// guard condition 
		if (!ServletFileUpload.isMultipartContent(request)) {

			respondWithError(response, "Only multipart content is supported.", HttpServletResponse.SC_BAD_REQUEST);
			
			//TODO: Is this required?
			//return;
			
		}

		try {

			ServletFileUpload servletFileUpload = new ServletFileUpload();

			FileItemIterator fileItemIterator = servletFileUpload.getItemIterator(request);			
			
			Map<String,String> formFields = new HashMap<String,String>();
			
			AbderaClient abderaClient = createAbderaClient(request);
			
			RequestOptions requestOptions = createRequestOptions(request);
			
			Entry mediaEntry = null;
			
			while (fileItemIterator.hasNext()) {
				
				FileItemStream fileItemStream = fileItemIterator.next();
								
				String fieldName = fileItemStream.getFieldName();
				

				
				// if this is the file field and isn't a form field.
				
				if (CleanFileWidgetRenderer.FIELD_FILE.equals(fieldName) && !fileItemStream.isFormField()) {
					
					// Check the file stream for viruses, which will throw an exception if found.
					checkFileItemStreamForVirus(fileItemStream);
					
					// Assume no virus (since no exception was thrown).
					
					// TODO: Is this required?
					//formFields.put(CleanFileWidgetRenderer.FIELD_FILENAME, fileItemStream.getName()); 
					
					mediaEntry = postMediaEntry(abderaClient, requestOptions, fileItemStream);

					
					
				} 
				else if (fileItemStream.isFormField()) {
					
					InputStream inputStream = fileItemStream.openStream();
					String fieldValue = Streams.asString(inputStream);
					
					formFields.put(fieldName, fieldValue);
					
				}
				else {
					
					if (CleanFileWidgetRenderer.FIELD_FILE.equals(fieldName)) {
						respondWithError(response, "The file field is not a form field.", HttpServletResponse.SC_BAD_REQUEST);
					} else {
						respondWithError(response, "Unhandled logical path while parsing request.", HttpServletResponse.SC_BAD_REQUEST);
					}
					break;
				}
				
			}
			
			
			if (mediaEntry != null) {
				
				// Put the author, the title, the summary and the type into the media entry.
				
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				String mediaAuthor = userDetails.getUsername();
				String mediaTitle = formFields.get(CleanFileWidgetRenderer.FIELD_FILENAME);
				
				// The notes goes in the derivation instead.
				//String mediaSummary = formFields.get(UploadFileForm.FIELD_SUMMARY);
				String mediaSummary = null;
				
				putMediaEntry(abderaClient, requestOptions, mediaEntry, mediaAuthor, mediaTitle, mediaSummary);
				
				// Post the derivation entry
				
				String derivationType = Chassis.TERM_REMOVEPERSONALDATA;
				String derivationSummary = formFields.get(CleanFileWidgetRenderer.FIELD_SUMMARY);
				String derivationInputHref = formFields.get(CleanFileWidgetRenderer.FIELD_FILETOBECLEANED);
				String derivationOutputHref = mediaCollectionUrl + mediaEntry.getEditLink().getHref().toASCIIString();
				
				Entry derivationEntry = postDerivationEntry(abderaClient, requestOptions, derivationType, derivationSummary, derivationInputHref, derivationOutputHref);
				
				if (derivationEntry == null) {
					
					respondWithError(response, "Derivation was not created.", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					
				} else {
					
					response.setStatus(HttpServletResponse.SC_OK);
					
					// workaround for GWT forms - needs content-type text/html
					response.setContentType("text/html");
					
					// embed content in body comment so it is preserved and available to GWT client
					String content = "<html><head><title>response</title></head><body><!-- " + derivationEntry.toString() + " --></body></html>";
					
					response.getWriter().print(content);
					response.flushBuffer();
				}
				
			} else {
				
				respondWithError(response, "Media was not found or created.", HttpServletResponse.SC_BAD_REQUEST);
				
			}
			
			

		} catch (ContainsVirusException e) {
			respondWithError(response, "A virus has been detected: " + e.getMessage(), 
					HttpServletResponse.SC_BAD_REQUEST);			
		} catch (Throwable t) {

			respondWithError(response, "The server encountered an internal error which prevented it from completing the request: " + t.getMessage(), 
					HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

			t.printStackTrace();
		}
		
		
	}
	
	


	private Entry postDerivationEntry(AbderaClient abderaClient, RequestOptions requestOptions, String derivationType, String derivationSummary, String derivationInputHref,
			String derivationOutputHref) throws IOException {

		// TODO: finish this.
		
		Entry derivationEntryElement = abdera.newEntry();

		derivationEntryElement.addLink(Chassis.REL_DERIVATIONINPUT, derivationInputHref);
		derivationEntryElement.addLink(Chassis.REL_DERIVATIONOUTPUT, derivationOutputHref);

        
        return null;
		
	}




	private void putMediaEntry(AbderaClient abderaClient, RequestOptions requestOptions, Entry mediaEntry, String mediaAuthor, String mediaTitle, String mediaSummary) {
		
		
		mediaEntry.addAuthor(null, mediaAuthor, null);
		
		if (mediaTitle != null) {
				mediaEntry.setTitle(mediaTitle);
		}

		if (mediaSummary != null) {
			mediaEntry.setSummary(mediaSummary);
		}


		// handle relative urls
		String entryUrl = mediaCollectionUrl + mediaEntry.getEditLink().getHref().toASCIIString(); 

		// needed, otherwise content type application/xml, which exist rejects
		requestOptions.setContentType("application/atom+xml; type=entry; charset=UTF-8"); 

		@SuppressWarnings("unused")
		ClientResponse clientResponse = abderaClient.put(entryUrl, mediaEntry, requestOptions);
		
	}




	private Entry postMediaEntry(AbderaClient abderaClient, RequestOptions requestOptions, FileItemStream fileItemStream) throws IOException {
		
		InputStreamRequestEntity inputStreamRequestEntity = new InputStreamRequestEntity(fileItemStream.openStream(), fileItemStream.getContentType());
		
		ClientResponse clientResponse = abderaClient.post(this.mediaCollectionUrl, inputStreamRequestEntity, requestOptions);
		
		Entry mediaEntry = extractEntry(clientResponse);
		
		return mediaEntry;
		
	}



	private void checkFileItemStreamForVirus(FileItemStream fileItemStream) throws FileUploadException, IOException, ScannerException {

	    InputStream inputStream = fileItemStream.openStream();		
		
	    ClamAntiVirusScanner clamScanner = new ClamAntiVirusScanner();
    	try { 
    		
    		@SuppressWarnings("unused")
			InputStream recreatedInputStream = clamScanner.performScan(inputStream);
    		
    	} catch (ClamAntiVirusNotRunningException e) {

    		// TODO: What should happen when virus scan fails?
    		
    		if (!ClamAntiVirusScanner.DEVELOPMENT_INSTALLATION) {
    			throw e;
    		}
    		
    	} catch (ContainsVirusException e) { 			       
    		throw new ContainsVirusException("The file " + fileItemStream.getName() + " appears to contain a virus.", e);
        }
    	
	}




	private AbderaClient createAbderaClient(HttpServletRequest request) {

		AbderaClient client = new AbderaClient();

		// forward cookies
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String domain = cookie.getDomain();
				String name = cookie.getName();
				String value = cookie.getValue();
				String path = cookie.getPath();
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
		
		// forward Authorisation header and cookies
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

	
	
	
	private Entry extractEntry(ClientResponse clientResponse) throws IOException{
		
		if (clientResponse.getType() == ResponseType.SUCCESS) {

			log.debug("Request succeeded.");

			Document<Entry> doc = clientResponse.getDocument(abdera.getParser());
			
			return doc.getRoot();

		}
		else {
			
			log.error("Request failed. Status: " + clientResponse.getStatus() + " " + clientResponse.getStatusText() + "\n");
			
			String message = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientResponse.getInputStream()));
			String line = reader.readLine();
			while (line != null) {
				message += line;
				line = reader.readLine();
			}
			
			throw new RuntimeException(message);
			
		}

	}
	

	protected void respondWithError(HttpServletResponse response, String content, int code) throws IOException {
		
		response.setStatus(code);
		response.setContentType("text/html");
		response.getWriter().print(content);
		response.flushBuffer();

	}



	

	
	
	


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		respondWithError(response, "only POST requests are allowed", HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		respondWithError(response, "only POST requests are allowed", HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		respondWithError(response, "only POST requests are allowed", HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * @see HttpServlet#doHead(HttpServletRequest, HttpServletResponse)
	 */
	protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		respondWithError(response, "only POST requests are allowed", HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * @see HttpServlet#doOptions(HttpServletRequest, HttpServletResponse)
	 */
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		respondWithError(response, "only POST requests are allowed", HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * @see HttpServlet#doTrace(HttpServletRequest, HttpServletResponse)
	 */
	protected void doTrace(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		respondWithError(response, "only POST requests are allowed", HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}
	
	
	

	
	



}
