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
import javax.xml.namespace.QName;

import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Content;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Element;
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
		log.debug(Config.COLLECTION_MEDIA_URL + ": " + this.mediaCollectionUrl);

		this.derivationsCollectionUrl = servletConfig.getInitParameter(Config.COLLECTION_DERIVATIONS_URL);

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
			return;

		}

		try {

			ServletFileUpload servletFileUpload = new ServletFileUpload();

			FileItemIterator fileItemIterator = servletFileUpload.getItemIterator(request);			
			
			Map<String,String> formFields = new HashMap<String,String>();
			

			
			Entry mediaEntry = null;
			
			while (fileItemIterator.hasNext()) {
				
				FileItemStream fileItemStream = fileItemIterator.next();
								
				String fieldName = fileItemStream.getFieldName();
				

				
				// if this is the file field and isn't a form field.
				
				if (CleanFileWidgetRenderer.FIELD_FILE.equals(fieldName) && !fileItemStream.isFormField()) {
					
					// Check the file stream for viruses, which will throw an exception if found.
					InputStream in = checkFileItemStreamForVirus(fileItemStream);
					
					// Assume no virus (since no exception was thrown).
					
					// This is needed to provide the file's name in the put request.
					formFields.put(CleanFileWidgetRenderer.FIELD_FILENAME, fileItemStream.getName()); 
					
					log.debug("posting media entry....");
					
					mediaEntry = postMediaEntry(request, in, fileItemStream.getContentType());

					log.debug("done posting media entry.");
					
					
				} 
				else if (fileItemStream.isFormField()) {
					
					InputStream inputStream = fileItemStream.openStream();
					String fieldValue = Streams.asString(inputStream);
					
					log.debug("got fieldName: " + fieldName + " with value: " + fieldValue);
					
					formFields.put(fieldName, fieldValue);
					
				}
				else {
					
					if (CleanFileWidgetRenderer.FIELD_FILE.equals(fieldName)) {
						respondWithError(response, "The file field is not a form field.", HttpServletResponse.SC_BAD_REQUEST);
						return;
					} else {
						respondWithError(response, "Unhandled logical path while parsing request.", HttpServletResponse.SC_BAD_REQUEST);
						return;
					}
					
					//break;
				}
				
			}
			
			
			if (mediaEntry != null) {
				
				// Put the author, the title, the summary and the type into the media entry.
				
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				String mediaAuthor = userDetails.getUsername();
				String mediaTitle = formFields.get(CleanFileWidgetRenderer.FIELD_FILENAME);
				
				log.debug("got mediaTitle: " + mediaTitle);
				
				// The notes goes in the derivation instead.
				//String mediaSummary = formFields.get(UploadFileForm.FIELD_SUMMARY);
				String mediaSummary = null;
				
				// Get the type from the original media entry.

				String mediaTerm = formFields.get(CleanFileWidgetRenderer.FIELD_FILETOBECLEANEDTYPE);
				String mediaLabel = formFields.get(CleanFileWidgetRenderer.FIELD_FILETOBECLEANEDOTHERTYPE);
				
				log.debug("got mediaTerm: " + mediaTerm);
				log.debug("got mediaLabel: " + mediaLabel);
				
				log.debug("putting media entry...");
				
				putMediaEntry(request, mediaEntry, mediaAuthor, mediaTitle, mediaSummary, mediaTerm, mediaLabel);
				
				log.debug("finished putting media entry.");
				


				

				// Post the derivation entry
				
				
				String derivationType = Chassis.TERM_REMOVEPERSONALDATA;
				String derivationSummary = formFields.get(CleanFileWidgetRenderer.FIELD_SUMMARY);
				
				log.debug("got derivationSummary: " + derivationSummary);
				
				// FIXME: Resolve this problem. 
				String derivationInputHref = formFields.get(CleanFileWidgetRenderer.FIELD_FILETOBECLEANEDID); // assume id is really url
				
				String derivationOutputHref = mediaEntry.getEditLink().getHref().toASCIIString();
				
				Entry derivationEntry = postDerivationEntry(request, derivationType, derivationSummary, derivationInputHref, derivationOutputHref);
				
				
				
				if (derivationEntry == null) {
					
					respondWithError(response, "Derivation was not created.", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					return;

					
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
				
				log.error("mediaEntry was null");
				
				respondWithError(response, "Media was not found or created.", HttpServletResponse.SC_BAD_REQUEST);
				return;
				
			}
			
			

		} catch (ContainsVirusException e) {
			respondWithError(response, "A virus has been detected: " + e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
			return;

		} catch (Throwable t) {
			
			
			
			respondWithError(response, "The server encountered an internal error which prevented it from completing the request: " + t.getMessage(), 
					HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

			t.printStackTrace();
			
			return;

		}
		
		
	}
	

	private Entry postDerivationEntry(HttpServletRequest request, String derivationType, String derivationSummary, String derivationInputHref,
			String derivationOutputHref) throws IOException {

		AbderaClient abderaClient = createAbderaClient(request);		
		
		RequestOptions requestOptions = createRequestOptions(request);

		Entry derivationEntryElement = abdera.newEntry();
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		derivationEntryElement.addAuthor(userDetails.getUsername());

		derivationEntryElement.addLink(derivationInputHref, Chassis.REL_DERIVATIONINPUT);
		derivationEntryElement.addLink(derivationOutputHref, Chassis.REL_DERIVATIONOUTPUT);
		
		Factory factory = abdera.getFactory();
		Entry derivationEntry = factory.newEntry();
		Content derivationContent = factory.newContent(Content.Type.XML, derivationEntry);
		Element derivationElement = factory.newElement(new QName(Chassis.NSURI, Chassis.ELEMENT_DERIVATION, Chassis.PREFIX), derivationContent);
		derivationElement.setAttributeValue("type", Chassis.TERM_REMOVEPERSONALDATA);
		
		derivationEntryElement.setContent(derivationElement);
		
		log.debug("adding summary: " + derivationSummary);
		
		derivationEntryElement.setSummary(derivationSummary);
		
		log.debug("added summary.");
		
		log.debug("posting derivation...");

		ClientResponse clientResponse = abderaClient.post(this.derivationsCollectionUrl, derivationEntryElement, requestOptions);
		return extractEntry(clientResponse);
		
	}




	private void putMediaEntry(HttpServletRequest request, Entry mediaEntry, String mediaAuthor, String mediaTitle, String mediaSummary, String mediaTerm, String mediaLabel) {
		
		AbderaClient abderaClient = createAbderaClient(request);
		
		RequestOptions requestOptions = createRequestOptions(request);			
		
		mediaEntry.addAuthor(null, mediaAuthor, null);
		
		if (mediaTitle != null) {
				mediaEntry.setTitle(mediaTitle);
		}

		if (mediaSummary != null) {
			mediaEntry.setSummary(mediaSummary);
		}
		
		if (mediaLabel != null) {
			
			if (mediaLabel == "") {
				
				log.debug("mediaLabel was a blank string");
				
			} else {
			
				log.debug("mediaLabel was not null nor a blank string");
			}
			
		} else {
			
			log.debug("mediaLabel was null");

		}
		
		mediaEntry.addCategory(Chassis.SCHEME_FILETYPES, mediaTerm, mediaLabel);
		

		// handle relative urls
		String entryUrl = mediaCollectionUrl + mediaEntry.getEditLink().getHref().toASCIIString(); 

		// needed, otherwise content type application/xml, which exist rejects
		requestOptions.setContentType("application/atom+xml; type=entry; charset=UTF-8"); 

		log.debug("putting changes to media entry");
		
		@SuppressWarnings("unused")
		ClientResponse clientResponse = abderaClient.put(entryUrl, mediaEntry, requestOptions);
		
		log.debug("done putting changes to media entry");
	}




	private Entry postMediaEntry(HttpServletRequest request, InputStream in, String contentType) throws IOException {

		AbderaClient abderaClient = createAbderaClient(request);
		
		RequestOptions requestOptions = createRequestOptions(request);		
		
		InputStreamRequestEntity inputStreamRequestEntity = new InputStreamRequestEntity(in, contentType);
		
		ClientResponse clientResponse = abderaClient.post(this.mediaCollectionUrl, inputStreamRequestEntity, requestOptions);
		
		Entry mediaEntry = extractEntry(clientResponse);
		
		return mediaEntry;
		
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	private InputStream checkFileItemStreamForVirus(FileItemStream fileItemStream) throws FileUploadException, IOException, ScannerException {

	    InputStream inputStream = fileItemStream.openStream();		
		
	    ClamAntiVirusScanner clamScanner = new ClamAntiVirusScanner();
	    
    	try { 
    		
			InputStream recreatedInputStream = clamScanner.performScan(inputStream);
    		return recreatedInputStream;
    		
    	} catch (ClamAntiVirusNotRunningException e) {

    		// TODO: What should happen when virus scan fails?
    		
    		if (!ClamAntiVirusScanner.DEVELOPMENT_INSTALLATION) {
    			throw e;
    		}
    		
    	} catch (ContainsVirusException e) { 			       
    		throw new ContainsVirusException("The file " + fileItemStream.getName() + " appears to contain a virus.", e);
        }
    	
    	return null;
    	
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
		return;
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		respondWithError(response, "only POST requests are allowed", HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		return;
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		respondWithError(response, "only POST requests are allowed", HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		return;
	}

	/**
	 * @see HttpServlet#doHead(HttpServletRequest, HttpServletResponse)
	 */
	protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		respondWithError(response, "only POST requests are allowed", HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		return;
	}

	/**
	 * @see HttpServlet#doOptions(HttpServletRequest, HttpServletResponse)
	 */
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		respondWithError(response, "only POST requests are allowed", HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		return;
	}

	/**
	 * @see HttpServlet#doTrace(HttpServletRequest, HttpServletResponse)
	 */
	protected void doTrace(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		respondWithError(response, "only POST requests are allowed", HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		return;
	}
	
	
	

	
	



}
