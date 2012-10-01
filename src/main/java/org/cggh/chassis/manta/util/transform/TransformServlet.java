package org.cggh.chassis.manta.util.transform;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.abdera.protocol.client.RequestOptions;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Servlet implementation class TransformServlet
 */
public class TransformServlet extends HttpServlet {
	
	
       
	/**
	 * 
	 */
	private static final long serialVersionUID = -5711435834398315873L;
	private final Logger logger = Logger.getLogger(this.getClass().getPackage().getName());
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransformServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		  if (request.getPathInfo() == null || request.getPathInfo().equals("")) {
			  
			  TransformFunctions transformFunctions = new TransformFunctions();
			  List<String> headerAcceptsAsStringList = transformFunctions.convertAcceptHeaderAsStringIntoHeaderAcceptsAsStringList(request.getHeader("Accept"));
			  
			  if (headerAcceptsAsStringList.contains("text/csv") || headerAcceptsAsStringList.contains("*/*")) {
			  
				  response.setContentType("text/csv; charset=utf-8");

				  
					  
					  if (!transformFunctions.isInvalidURL(request.getParameter("study"))) {
						  
						  
						  //FIXME: Restrict the URLs that will be processed, e.g. by domain.
						  
						  
						  try {
							  
							RequestOptions requestOptions = createRequestOptions(request);
						  
							TransformCRUD transformCRUD = new TransformCRUD();
							
							//More verbosely
							//String studyUrlResponseAsString = transformCRUD.retrieveUrlResponseAsStringUsingUrlAsStringAndRequestOptions(request.getParameter("study"), requestOptions)
							//this.getLogger().info(studyUrlResponseAsString);
	
						  	// Checking that the URL response is actually XML is achieved through attempting to parse it.
							Document studyXmlAsDocument = transformFunctions.convertXmlAsStringIntoXmlAsDocument(transformCRUD.retrieveUrlResponseAsStringUsingUrlAsStringAndRequestOptions(request.getParameter("study"), requestOptions));
	
							//TODO: How can this alter depending on the environment?
							String fieldLabelMappingsUrlAsString = "http://localhost:8080/repository/service/content/config/field-label-mappings";
							
						    Document fieldLabelMappingsXmlAsDocument = transformFunctions.convertXmlAsStringIntoXmlAsDocument(transformCRUD.retrieveUrlResponseAsStringUsingUrlAsStringAndRequestOptions(fieldLabelMappingsUrlAsString, requestOptions));
							
						    HashMap<String, String> fieldLabelRegExpMappingsAsPatternKeyedHashMap = transformFunctions.convertFieldLabelMappingsXmlAsDocumentIntoFieldLabelMappingsAsPatternKeyedHashMap(fieldLabelMappingsXmlAsDocument);
	
						    
						    
						    
							
							//Note: Could introduce a base prefix here.
							String parentNodeBaseXPathAsString = null;
							ArrayList<FieldModel> dataAsFieldModelArrayListWithXpathFieldLabels = transformFunctions.convertNodeListIntoFieldModelArrayListWithXPathFieldLabels(studyXmlAsDocument.getChildNodes(), parentNodeBaseXPathAsString);

							
							//NOTE: This isn't the most efficient way of achieving this, but it breaks the task down into separate concerns. 
							//Remove field labels to ignore from fieldModelArrayList
							HashMap<String, String> fieldLabelsToIgnoreAsPatternKeyedHashMap = transformFunctions.convertFieldLabelMappingsXmlAsDocumentIntoFieldLabelsToIgnoreAsPatternKeyedHashMap(fieldLabelMappingsXmlAsDocument);
						    
							dataAsFieldModelArrayListWithXpathFieldLabels = transformFunctions.removeFieldsToIgnoreFromFieldModelArrayListUsingFieldModelArrayListAndFieldLabelsToIgnoreAsPatternKeyedHashMap(dataAsFieldModelArrayListWithXpathFieldLabels, fieldLabelsToIgnoreAsPatternKeyedHashMap);
							
							
							//FIXME: sanitize
							String filename = transformFunctions.convertURLAsStringIntoFileName(request.getParameter("study"));							
							
							
								if (request.getParameter("format") != null && request.getParameter("format").equals("rows")) { 
								
									String dataAsCSVRowsString = transformFunctions.convertDataAsFieldModelArrayListIntoCSVRowsWithMappedCustomFieldLabelsAsStringUsingFieldLabelRegExpMappingsAsPatternKeyedHashMap(dataAsFieldModelArrayListWithXpathFieldLabels, fieldLabelRegExpMappingsAsPatternKeyedHashMap); 
							
									this.respondWithStringAsFileUsingResponseAndStringAndFilename(response, dataAsCSVRowsString, filename);
							
								} else if (request.getParameter("format") != null && request.getParameter("format").equals("columns")) {
								  
									String dataAsCSVColumnsString = transformFunctions.convertDataAsFieldModelArrayListIntoCSVColumnsWithMappedCustomFieldLabelsAsStringUsingFieldLabelRegExpMappingsAsPatternKeyedHashMap(dataAsFieldModelArrayListWithXpathFieldLabels, fieldLabelRegExpMappingsAsPatternKeyedHashMap); 
									
									this.respondWithStringAsFileUsingResponseAndStringAndFilename(response, dataAsCSVColumnsString, filename);
							
								  
								} else {
								  
									response.setContentType("text/plain");
								  	response.getWriter().println("Unhandled format: " + request.getParameter("format"));
								  
								}
							  
						  } catch (IOException e) {
							  e.printStackTrace();
						  } catch (ParserConfigurationException e) {
							e.printStackTrace();
						} catch (SAXException e) {
							
							//e.printStackTrace();
							response.setContentType("text/plain");
							response.getWriter().println("Invalid XML from URL: " + request.getParameter("study"));
							
						}
						  
						  
						  
						  
					  } else {
						 
						  response.setContentType("text/plain");
						  response.getWriter().println("Invalid study URL: " + request.getParameter("study"));
						  
					  }

		              
			  } else {

				response.setContentType("text/plain");
				response.getWriter().println("Unhandled Header Accept: " + request.getHeader("Accept"));
					
			  }
				  
		  } else {
			 
			  response.setContentType("text/plain");
			  response.getWriter().println("Unhandled Path Info: " + request.getPathInfo());
		  }
		
		
	}

	private void respondWithStringAsFileUsingResponseAndStringAndFilename(
			HttpServletResponse response, String string,
			String filename) throws IOException {
		
		if (string != null) {
			  
	            response.setHeader("Content-Disposition", "attachment; fileName=" + filename);

	            response.setContentLength(string.getBytes().length + 3);
	            //Include Byte Order Mark to tell Excel that it's a UTF-8 file
	            response.getOutputStream().print((char)0xEF);
	            response.getOutputStream().print((char)0xBB);
	            response.getOutputStream().print((char)0xBF);
	            
	            int length = 0;
	            
	            byte[] byteBuffer = new byte[2048];
	            
	              DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(string.getBytes()));
	
	              while ((length = dataInputStream.read(byteBuffer)) != -1) {
	            	  response.getOutputStream().write(byteBuffer, 0, length);
	              }
	
	              dataInputStream.close();
	              response.getOutputStream().flush();
	              response.getOutputStream().close();	
		
	              
		  } else {
			
			//TODO: Is this an error, or is there just no data?
			response.setContentType("text/plain");
			response.getWriter().println("Error: string is null.");
		  }
		
	}

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
	
	public Logger getLogger() {
		return logger;
	}	
	
}
