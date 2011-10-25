package org.cggh.common;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xml.serialize.DOMSerializer;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;



/**
 * This class provides static helper methods for doing various
 * XML manipulations.
 * 
 * Each method has two interfaces, one with and one without an ErrorHandler.
 * If the method without the ErrorHandler is called the default 
 * ElsDefaultErrorHandler is called to log the error. 
 * Alternatively the user of this class can build
 * their own ErrorHandler and pass it into the alternative method interface.
 */
public class XMLUtilities {

	/** String buffer size */
	private static final int BUF = 1024;

	/** Create ErrorHandler once */
	private static ErrorHandler c_errorHandler 
		= new DefaultSAXErrorHandler();	
	
	/**
	 * Disable the default constructor.
	 */
	private XMLUtilities() {
	}

	/**
	 * Convert a w3c.org.dom.Element object into a string.
	 *
	 * @param	e		The Element to convert to a string
	 * @return 	Serialized string.     
	 * @throws XMLUtilitiesException if a null element is passed
	 * @since
	 * 
	 */
	public static String elementToString(Element e) 
	throws XMLUtilitiesException {
		
		if (e == null) {
			throw new XMLUtilitiesException(
				"Null Element received into elementToString"
				+ "(Element e)",
				null);
		}

		StringWriter buffer = new StringWriter(BUF);
				
		OutputFormat of = new OutputFormat("XML", "UTF-8", true);
		of.setOmitXMLDeclaration(false);
		XMLSerializer serial = new XMLSerializer(buffer, of);
		try {
			DOMSerializer domSerializer = serial.asDOMSerializer();
			domSerializer.serialize(e);
		} catch (IOException ioe) {
			throw new XMLUtilitiesException(
				"IOException occurred in elementToStringLog "
				+ "(raised by XMLSerialiser). This should never happen!",
				ioe);
		}

		return buffer.toString();
	}

	/**
	 * Convert a w3c.org.dom.Element object into a string.
	 * Specifically formatted for logging purposes
	 * 
	 * @param  e		The Element to convert to a string
	 * @return Serialized string.     
	 * @throws XMLUtilitiesException if a null element is passed
	 * @since
	 * 
	 */
	public static String elementToStringLog(Element e)
	throws XMLUtilitiesException {
		
		if (e == null) {
			throw new XMLUtilitiesException(
				"Null Element received into elementToString"
				+ "(Element e)",
				null);
		}

		StringWriter buffer = new StringWriter(BUF);

		OutputFormat of = new OutputFormat("XML", "UTF-8", false);
		of.setOmitXMLDeclaration(true);
		of.setLineSeparator("");

		XMLSerializer serial = new XMLSerializer(buffer, of);
		try {
			DOMSerializer domSerializer = serial.asDOMSerializer();
			domSerializer.serialize(e);
		} catch (IOException ioe) {
			throw new XMLUtilitiesException(
				"IOException occurred in elementToStringLog "
				+ "(raised by XMLSerialiser). This should never happen!",
				ioe);
		}

		return buffer.toString();
	}

	/**
	 * Create a DOM Document Builder
	 * 
	 * @return	DocumentBuilder object.     
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 * 
	 */
	public static DocumentBuilder getDocumentBuilder()
		throws XMLUtilitiesException {
		return getDocumentBuilder(c_errorHandler);
	}

	/**
	 * Create a DOM Document Builder
	 * 
	 * @param	eh				SAX ErrorHandler object
	 * @return	DocumentBuilder
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 * 
	 */
	public static DocumentBuilder getDocumentBuilder(ErrorHandler eh)
		throws XMLUtilitiesException {
		return getDocumentBuilder(eh, false);
	}

	/**
	 * Create a DOM Document Builder
	 * 
	 * @param	eh						SAX ErrorHandler object
	 * @param	validating				Parser validation
	 * @return	DocumentBuilder
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 * 
	 */
	public static DocumentBuilder getDocumentBuilder(
		ErrorHandler eh,
		boolean validating)
		throws XMLUtilitiesException {
		//Create the DOM from the String using JAXP
		try {
			if (eh == null) {
				throw new XMLUtilitiesException(
					"Null ErrorHandler received into getDocumentBuilder"
					+ "(ErrorHandler eh, boolean validating)",
					null);
			}

			DocumentBuilderFactory factory = 
									DocumentBuilderFactory.newInstance();

			// Setup validation
			factory.setValidating(validating);

			// turn on namespaces
			factory.setNamespaceAware(true);

			DocumentBuilder builder = factory.newDocumentBuilder();
			builder.setErrorHandler(eh);
			
			return builder;
		} catch (ParserConfigurationException pce) {
			StringBuffer msg = new StringBuffer(BUF);
			msg.append("ParserConfiguration Exception raised because");
			msg.append(" the underlying parser");
			msg.append(" does not support the requested features;");
			msg.append(" setValidating: ");
			msg.append(validating);
			msg.append("; setNameSpaceAware: true");
			XMLUtilitiesException xue =
				new XMLUtilitiesException(msg.toString(), pce);
			throw xue;
		} catch (FactoryConfigurationError fe) {
			// DocumentBuilderFactory.newInstance() should throw a 
			// ClassNotFoundException if it can't locate the factory
			// class. However, what it does throw is a 
			// FactoryConfigurationError. Very few programs are prepared 
			// to respond to errors as opposed to exceptions. 
			// Therefore we've converted it to a ClassNotFoundException
			String msg = "JAXP factory class not found";
			throw (new XMLUtilitiesException(msg, fe));
		}
	}

	/**
	 * Create an empty DOM document.
	 * Use default Errorhandler
	 * 
	 * @return 	Empty Document object.
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 * 
	 */
	public static Document getDocument() throws XMLUtilitiesException {
		return getDocument(c_errorHandler);
	}

	/**
	 * Create an empty DOM document.
	 * Use passed in Errorhandler
	 * Default validating to false
	 * 
	 * @param	eh						SAX ErrorHandler object
	 * @return  Document 				Empty document object.
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 * 
	 */
	public static Document getDocument(ErrorHandler eh)
		throws XMLUtilitiesException {
		return getDocument(eh, false);
	}

	/**
	 * Create an empty DOM document
	 * Use passed in Errorhandler
	 * Turn validating on or off as passed in
	 * 
	 * @param	eh						SAX ErrorHandler object
	 * @param	validating				Parser validation
	 * @return  Document 				Empty document object.
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 * 
	 */
	public static Document getDocument(ErrorHandler eh, boolean validating)
		throws XMLUtilitiesException {
		//Create the DOM from the String using JAXP
		DocumentBuilder builder = getDocumentBuilder(eh, validating);
		return builder.newDocument();
	}

	/**
	 * Create a DOM document from a string.
	 * Use default Errorhandler
	 * 
	 * @param 	xmlString	String the string containg XML that needs to be 
	 * converted to a Document.
	 * @return 	Document	DOM containing de-serialized XML.     
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 * 
	 */
	public static Document getDocument(String xmlString)
		throws XMLUtilitiesException {
		return getDocument(xmlString, c_errorHandler);
	}

	/**
	 * Create a DOM document from a string.
	 * Use passed in Errorhandler
	 * Default validating to false
	 * 
	 * @param 	xmlString	String the string containg XML that needs to be 
	 * converted to a Document.
	 * @param	eh			SAX ErrorHandler object
	 * @return 	Document	DOM containing de-serialized XML.     
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 * 
	 */
	public static Document getDocument(String xmlString, ErrorHandler eh)
		throws XMLUtilitiesException {
		return getDocument(xmlString, eh, false);
	}

	/**
	 * Create a DOM document from a string.
	 * Use passed in Errorhandler
	 * Use passed in validating
	 * 
	 * @param 	xmlString	String the string containg XML that needs to be 
	 * converted to a Document.
	 * @param	eh			SAX ErrorHandler object
	 * @param	validating	Parser validation
	 * @return 	Document	DOM containing de-serialized XML.     
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 * 
	 */
	public static Document getDocument(
		String xmlString,
		ErrorHandler eh,
		boolean validating)
		throws XMLUtilitiesException {

		if (!ObjectUtilities.validString(xmlString)) {
			throw new XMLUtilitiesException(
				"Null xmlString passed into getDocument"
				+ "(String xmlString, ErrorHandler eh, boolean validating)",
				null);
		}

		InputSource iSource = new InputSource(new StringReader(xmlString));
		return getDocument(iSource, eh, validating);
	}
	/**
	 * Create a DOM document from a URI.
	 * Use default Errorhandler
	 * 
	 * @param 	uri			URI pointing to an XML stream .
	 * @return 	Document 	DOM containing de-serialized XML.     
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 * 
	 */
	public static Document getDocument(URI uri) throws XMLUtilitiesException {
		return getDocument(uri, c_errorHandler);
	}

	/**
	 * Create a DOM document from a URI.
	 * Use passed in Errorhandler
	 * Default validating to false
	 * 
	 * @param 	uri			URI pointing to an XML stream .
	 * @param	eh			SAX ErrorHandler object
	 * @return 	Document 	DOM containing de-serialized XML.     
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 * 
	 */
	public static Document getDocument(URI uri, ErrorHandler eh)
		throws XMLUtilitiesException {
		return getDocument(uri, eh, false);
	}

	/**
	 * Create a DOM document from a URI.
	 * Use passed in Errorhandler
	 * Use passed in validating
	 * 
	 * @param 	uri			URI pointing to an XML stream .
	 * @param	eh			SAX ErrorHandler object
	 * @param	validating	Parser validation
	 * @return 	Document 	DOM containing de-serialized XML.     
	 * @since
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 */
	public static Document getDocument(URI uri, ErrorHandler eh, boolean validating)
		throws XMLUtilitiesException {

		if (uri == null) {
			throw new XMLUtilitiesException(
				"Null uri passed into getDocument"
				+ "(URI uri, ErrorHandler eh, boolean validating)",
				null);
		}

		InputSource iSource = new InputSource(uri.getPath());
		return getDocument(iSource, eh, validating);
	}

	/**
	 * Create a DOM document from a URL.
	 * Use default Errorhandler 
	 * 
	 * @param 	url			URL pointing to an XML stream .
	 * @return 	Document 	DOM containing de-serialized XML.     
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 * 
	 */
	public static Document getDocument(URL url) throws XMLUtilitiesException {
		return getDocument(url, c_errorHandler);
	}

	/**
	 * Create a DOM document from a URL.
	 * Use passed in Errorhandler
	 * Default validating to false
	 * 
	 * @param 	url			URL pointing to an XML stream .
	 * @param	eh			SAX ErrorHandler object
	 * @return 	Document 	DOM containing de-serialized XML.     
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 * 
	 */
	public static Document getDocument(URL url, ErrorHandler eh)
		  throws XMLUtilitiesException {
		return getDocument(url, eh, false);
	}

	/**
	 * Create a DOM document from a URL.
	 * Use passed in Errorhandler
	 * Use passed in validating
	 * 
	 * @param 	url			URL pointing to an XML stream .
	 * @param	eh			SAX ErrorHandler object
	 * @param	validating	Parser validation
	 * @return 	Document 	DOM containing de-serialized XML.     
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 * 
	 */
	public static Document getDocument(URL url,	ErrorHandler eh, boolean validating)
	  	throws XMLUtilitiesException {
		try {
			if (url == null) {
				throw new XMLUtilitiesException(
					"Null url passed to XMLUtilities.getDocument"
					+ "(URL, ErrorHandler, Validating)",
					null);
			}

			InputSource iSource = new InputSource(url.openStream());
			return getDocument(iSource, eh, validating);
		} catch (IOException ioe) {
			XMLUtilitiesException xue =
				new XMLUtilitiesException(
					"IO Exception raised. URL being processed = " + url,
					ioe);
			throw xue;
		}
	}

	/**
	 * Utility to create a DOM document from a File.
	 * Use default Errorhandler
	 * 
	 * @param 	file		File object containing an XML stream .
	 * @return 	Document 	DOM containing de-serialized XML
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 */
	public static Document getDocument(File file) 
		throws XMLUtilitiesException {
		return getDocument(file, c_errorHandler);
	}

	/**
	 * Utility to create a DOM document from a File.
	 * Use passed in Errorhandler
	 * Default validating off
	 * 
	 * @param 	file		File object containing XML to be converted into a 
	 * 						Document object
	 * @param	eh			SAX ErrorHandler object
	 * @return 	Document 	DOM object containing de-serialized XML     
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 * 
	 */
	public static Document getDocument(File file, ErrorHandler eh)
		throws XMLUtilitiesException {
		return getDocument(file, eh, false);
	}

	/**
	 * Utility to create a DOM document from a File.
	 * Use passed in Errorhandler
	 * Use passed in validating
	 * 
	 * @param 	file		File object containing XML to be 
	 * converted into a Document 
	 * 						object
	 * @param	eh			SAX ErrorHandler object
	 * @param	validating	Parser validation
	 * @return 	Document 	DOM object containing de-serialized XML
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 * 
	 */
	public static Document getDocument(
		File file,
		ErrorHandler eh,
		boolean validating)
		throws XMLUtilitiesException {

		FileInputStream fileInputStream = null;

		try {
			if (file == null) {
				throw new XMLUtilitiesException(
					"Null file passed to XMLUtilities.getDocument"
					+ "(File file, ErrorHandler eh, boolean validating)",
					null);
			}

			fileInputStream = new FileInputStream(file);
		} catch (IOException ioe) {
			new XMLUtilitiesException(
				"IOException raised attempting to open file: " 
				+ file.getAbsolutePath(),
				ioe);
		}
		return getDocument(fileInputStream, eh, validating);
	}

	/**
	 * Utility to create a DOM document from an InputStream.
	 * Use default ErrorHandler
	 * 
	 * @param 	iStream		InputStream object containing XML to be converted
	 * 						into a Document object
	 * @return 	Document 	DOM object containing de-serialized XML.     
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 */
	public static Document getDocument(InputStream iStream)
		throws XMLUtilitiesException {
		return getDocument(iStream, c_errorHandler);
	}

	/**
	 * Utility to create a DOM document from an InputStream.
	 * Use passed in ErrorHandler
	 * Default validating to false
	 * 
	 * @param 	iStream		InputStream object containing XML to be 
	 * 						converted into a Document object
	 * @param	eh			SAX ErrorHandler object
	 * @return 	Document 	DOM object containing de-serialized XML.     
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 * 
	 */
	public static Document getDocument(InputStream iStream, ErrorHandler eh)
		throws XMLUtilitiesException {
		return getDocument(iStream, null, eh, false);
	}

	/**
	 * Utility to create a DOM document from an InputStream.
	 * Use passed in ErrorHandler
	 * Use passed in validating
	 * 
	 * @param 	iStream		InputStream object containing XML to be 
	 * 						converted into a Document object
	 * @param	eh			SAX ErrorHandler object
	 * @param	validating	Parser validation
	 * @return 	Document 	DOM object containing de-serialized XML.     
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 * 
	 */
	public static Document getDocument(
		InputStream iStream,
		ErrorHandler eh,
		boolean validating)
		throws XMLUtilitiesException {
		return getDocument(iStream, null, eh, validating);
	}

	/**
	 * Utility to create a DOM document from an InputStream.
	 * Use default ErrorHandler
	 * Default validating to false
	 * 
	 * @param 	iStream		InputStream object containing XML 
	 * 						to be converted into a Document object
	 * @param	systemId	String provides a base for resolving relative URIs
	 * @return 	Document 	DOM object containing de-serialized XML
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 * 
	 */
	public static Document getDocument(InputStream iStream, String systemId)
		throws XMLUtilitiesException {
		return getDocument(iStream, systemId, c_errorHandler, false);
	}

	/**
	 * Utility to create a DOM document from an InputStream.
	 * Use default ErrorHandler
	 * use passed in validating
	 * 
	 * @param 	iStream		InputStream object containing XML 
	 * 						to be converted into a Document object
	 * @param	systemId	String provides a base for resolving relative URIs
	 * @param	validating	Parser validation
	 * @return 	Document 	DOM object containing de-serialized XML
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 */
	public static Document getDocument(
		InputStream iStream,
		String systemId,
		boolean validating)
		throws XMLUtilitiesException {
		return getDocument(iStream, systemId, c_errorHandler, validating);
	}

	/**
	 * Utility to create a DOM document from an InputStream.
	 * Use passed in ErrorHandler
	 * Default validating to false
	 * 
	 * @param 	iStream		InputStream object containing XML 
	 * 						to be converted into a Document object
	 * @param	systemId	String provides a base for resolving relative URIs
	 * @param	eh			SAX ErrorHandler object
	 * @return 	Document 	DOM object containing de-serialized XML.     
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 * 
	 */
	public static Document getDocument(
		InputStream iStream,
		String systemId,
		ErrorHandler eh)
		throws XMLUtilitiesException {
		InputSource inputSource = null;

		if (iStream == null) {
			throw new XMLUtilitiesException(
				"Null iStream passed to XMLUtilities.getDocument"
				+ "(InputStream iStream, String systemId, ErrorHandler eh)",
				null);
		}
		inputSource = new InputSource(iStream);
		
		if (ObjectUtilities.validString(systemId)) {
			inputSource.setSystemId(systemId);
		}

		return getDocument(inputSource, eh, false);
	}

	/**
	 * Utility to create a DOM document from an InputStream.
	 * Use passed in ErrorHandler
	 * Use passed in validating
	 * 
	 * @param 	iStream		InputStream object containing XML to be converted
	 * 						into a Document object
	 * @param	systemId	String provides a base for resolving relative URIs
	 * @param	eh			SAX ErrorHandler object
	 * @param	validating	Parser validation
	 * @return 	Document 	DOM object containing de-serialized XML.     
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 * 
	 */
	public static Document getDocument(
		InputStream iStream,
		String systemId,
		ErrorHandler eh,
		boolean validating)
		throws XMLUtilitiesException {
		InputSource inputSource = null;

		if (iStream == null) {
			throw new XMLUtilitiesException(
				"Null iStream passed to XMLUtilities.getDocument"
				+ "(InputStream iStream, String systemId, ErrorHandler eh,"
				+ " boolean validating)",
				null);
		}
		
		inputSource = new InputSource(iStream);

		if (ObjectUtilities.validString(systemId)) {		
			inputSource.setSystemId(systemId);
		}

		return getDocument(inputSource, eh, validating);
	}

	/**
	 * Utility to create a DOM document from an InputSource
	 * Use default ErrorHandler
	 * 
	 * @param 	iSource		InputSource object containing XML to be converted.
	 * @return 	Document 	DOM object containing de-serialized XML.     
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 * 
	 */
	public static Document getDocument(InputSource iSource)
		throws XMLUtilitiesException {
		return getDocument(iSource, c_errorHandler);
	}

	/**
	 * Utility to create a DOM document from an InputSource
	 * Use passed in ErrorHandler
	 * Default validating to false
	 * 
	 * @param 	iSource		InputSource object containing XML to 
	 * be converted.
	 * @param	eh			SAX ErrorHandler object
	 * @return 	Document 	DOM object containing de-serialized XML.     
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 * 
	 */
	public static Document getDocument(InputSource iSource, ErrorHandler eh)
		throws XMLUtilitiesException {
		return getDocument(iSource, eh, false);
	}

	/**
	 * Utility to create a DOM document from an InputSource
	 * Use passed in ErrorHandler
	 * Use passed in validating
	 * 
	 * @param 	iSource		InputSource object containing XML to be converted.
	 * @param	eh			SAX ErrorHandler object
	 * @param	validating	Validating on or off
	 * @return 	Document 	DOM object containing de-serialized XML.     
	 * @throws	XMLUtilitiesException if an error occurs and the 
	 * document can't be returned
	 * @since
	 * 
	 */
	public static Document getDocument(
		InputSource iSource,
		ErrorHandler eh,
		boolean validating)
		throws XMLUtilitiesException {
		try {
			if (iSource == null) {
				throw new NullPointerException("Null iSource passed to "
				+ "XMLUtilities.getDocument(InputSource iSource, "
				+ "ErrorHandler eh, boolean validating)");
			}

			// Get a builder passing the errorhandler and the validating flag in
			DocumentBuilder builder = getDocumentBuilder(eh, validating);

			// Parse the file
			return (builder.parse(iSource));
		} catch (SAXException se) {
			XMLUtilitiesException xue =
				new XMLUtilitiesException(
					"Problems parsing the document:" + se.getMessage(),
					se);
			throw xue;
		} catch (FactoryConfigurationError e) {
			String msg = "Could not locate a JAXP factory class.";
			XMLUtilitiesException xue =
				new XMLUtilitiesException(msg, e);
			throw xue;
		} catch (IOException ioe) {
			XMLUtilitiesException xue =
				new XMLUtilitiesException(
					"IOException occurring attempting to parse document"
					+ " in InputSource:" + iSource,
					ioe);
			throw xue;
		}
	}

	/**
	  * Utility to validate an XML InputStream using a parser.
	  * 
	  * @param 	file		File object containing the XML File to be validated.
	  * @return boolean 	Indicates success or failure.     
	  * @throws	XMLUtilitiesException if an error occurs and the 
	  * document can't be returned
	  * @since
	  * 
	  */
	public static boolean validateXML(File file) throws XMLUtilitiesException {
		return validateXML(file, c_errorHandler);
	}

	/**
	  * Utility to validate an XML InputStream using a parser.
	  * 
	  * @param 	file		File object containing the XML File to be validated.
	  * @param	eh			SAX ErrorHandler object
	  * @return boolean 	Indicates success or failure.     
	  * @throws	XMLUtilitiesException if an error occurs and the 
	  * document can't be returned
	  * @since
	  * 
	  */
	public static boolean validateXML(File file, ErrorHandler eh)
		throws XMLUtilitiesException {
		try {
			if (file == null) {
				throw new XMLUtilitiesException(
					"Null file passed to "
					+ "XMLUtilities.validateXML(File file, ErrorHandler eh)",
					null);
			}
			FileInputStream fileInputStream = new FileInputStream(file);
			return validateXML(fileInputStream, eh);
		} catch (FileNotFoundException fne) {
			XMLUtilitiesException xue =
				new XMLUtilitiesException(
					"File Not Found Exception raised. File Location: "
					+ file.getAbsolutePath(),
					fne);
			throw xue;
		}
	}

	/**
	  * Utility to validate an XML InputStream using a parser.
	  * 
	  * @param 	uri			String containing the URI to the 
	  * file to be validated.
	  * @return boolean 	Indicates success or failure.     
	  * @throws	XMLUtilitiesException if an error occurs and the 
	  * document can't be returned
	  * @since
	  * 
	  */
	public static boolean validateXML(String uri) throws XMLUtilitiesException {
		return validateXML(uri, c_errorHandler);
	}

	/**
	  * Utility to validate an XML InputStream using a parser.
	  * 
	  * @param 	uri			String containing the URI to the 
	  * file to be validated.
	  * @param	eh			SAX ErrorHandler object
	  * @return boolean 	Indicates success or failure.     
	  * @throws	XMLUtilitiesException if an error occurs and the 
	  * document can't be returned
	  * @since
	  * 
	  */
	public static boolean validateXML(String uri, ErrorHandler eh)
		throws XMLUtilitiesException {
		try {			
			if (!ObjectUtilities.validString(uri)) {
				throw new XMLUtilitiesException(
					"Null or empty uri passed to "
					+ "XMLUtilities.validateXML(String uri, ErrorHandler eh)",
					null);
			}			

			URL url = new URL(uri);
			InputStream inputStream = url.openStream();
			return validateXML(inputStream, eh);
		} catch (MalformedURLException mue) {
			XMLUtilitiesException xue =
				new XMLUtilitiesException("URI: " 
				+ uri, mue);
			throw xue;
		} catch (IOException ioe) {
			XMLUtilitiesException xue =
				new XMLUtilitiesException("URI: " 
				+ uri, ioe);
			throw xue;
		}
	}

	/**
	  * Utility to validate an XML InputStream using a parser.
	  * 
	  * @param 	iStream		InputStream containing the XML File to be validated.
	  * @return boolean 	Indicates success or failure.     
	  * @throws	XMLUtilitiesException if an error occurs and the 
 	  * document can't be returned
	  * @since
	  * 
	  */
	public static boolean validateXML(InputStream iStream)
		throws XMLUtilitiesException {
		return validateXML(iStream, c_errorHandler);
	}

	/**
	  * Utility to validate an XML InputStream using a parser.
	  * 
	  * @param 	iStream		InputStream containing the XML File to be validated.
	  * @param	eh			SAX ErrorHandler object
	  * @return boolean 	Indicates success or failure.     
	  * @throws	XMLUtilitiesException if an error occurs and the 
	  * document can't be returned
	  * @since
	  * 
	  */
	public static boolean validateXML(InputStream iStream, ErrorHandler eh)
		throws XMLUtilitiesException {
		return validateXML(iStream, null, eh);
	}

	/**
	  * Utility to validate an XML InputStream using a parser.
	  * 
	  * @param 	iStream		InputStream containing the XML File to be validated.
	  * @param	systemId	String provides a base for resolving relative URIs
	  * @return boolean 	Indicates success or failure.     
	  * @throws	XMLUtilitiesException if an error occurs and the 
	  * document can't be returned
	  * @since
	  * 
	  */
	public static boolean validateXML(InputStream iStream, String systemId)
		throws XMLUtilitiesException {
		return validateXML(iStream, systemId, c_errorHandler);
	}

	/**
	  * Utility to validate an XML InputStream using a parser.
	  * 
	  * @param 	iStream		InputStream containing the XML File to be validated.
	  * @param	systemId	String provides a base for resolving relative URIs
	  * @param	eh			SAX ErrorHandler object
	  * @return boolean 	Indicates success or failure.     
	  * @throws	XMLUtilitiesException if an error occurs and the 
	  * document can't be returned
	  * @since
	  * 
	  */
	public static boolean validateXML(
		InputStream iStream,
		String systemId,
		ErrorHandler eh)
		throws XMLUtilitiesException {
		InputSource inputSource = null;

		if (iStream == null) {
			throw new XMLUtilitiesException(
				"Null iStream passed to XMLUtilities.validateXML"
				+ "(InputStream iStream, String systemId, ErrorHandler eh)",
				null);
		}

		if (!ObjectUtilities.validString(systemId)) {
			throw new XMLUtilitiesException(
				"Null or empty systemId passed to XMLUtilities.validateXML"
				+ "(InputStream iStream, String systemId, ErrorHandler eh,"
				+ " boolean validating)",
				null);			
		}

		inputSource = new InputSource(iStream);
		
		if (ObjectUtilities.validString(systemId)) {
			inputSource.setSystemId(systemId);
		}

		return validateXML(inputSource, eh);
	}

	/**
	  * Utility to validate an XML InputSource using a parser.
	  * 
	  * @param 	iSource		InputSource object containing the XML 
	  * File to be validated.
	  * @return boolean 	Indicates success or failure.     
	  * @throws	XMLUtilitiesException if an error occurs and the 
 	  * document can't be returned
	  * @since
	  * 
	  */
	public static boolean validateXML(InputSource iSource)
		throws XMLUtilitiesException {
		return validateXML(iSource, c_errorHandler);
	}

	/**
	  * Utility to validate an XML InputSource using a parser.
	  * 
	  * @param 	iSource		InputSource object containing the 
	  * XML File to be validated.
	  * @param	eh			SAX ErrorHandler object
	  * @return boolean 	Indicates success or failure.     
	  * @throws	XMLUtilitiesException if an error occurs and the 
	  * document can't be returned
	  * @since
	  * 
	  */
	public static boolean validateXML(InputSource iSource, ErrorHandler eh)
		throws XMLUtilitiesException {
		try {
			if (iSource == null) {
				throw new XMLUtilitiesException(
					"Null iSource passed to XMLUtilities.validateXML"
					+ "(InputSource iSource, ErrorHandler eh)",
					null);
			}

			if (eh == null) {
				throw new XMLUtilitiesException(
					"Null ErrorHandler received into validateXML"
					+ "(InputSource iSource, ErrorHandler eh)",
					null);
			}

			// Get Document Builder Factory
			DocumentBuilderFactory factory = 
									DocumentBuilderFactory.newInstance();

			// Turn on validation, and turn on namespaces
			factory.setValidating(true);
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Sort out the errorhandler
			builder.setErrorHandler(eh);

			// Parse the passed in XML	
			builder.parse(iSource);
			
		} catch (ParserConfigurationException pce) {
			throw  new XMLUtilitiesException(
					"ParserConfiguration Exception raised because the"
					+ " underlying parser does not support the requested"
					+ " features; setValidating: true; setNameSpaceAware: true",
					pce);
		} catch (IOException ioe) {
			throw new XMLUtilitiesException(
				"IOException Exception raised attempting to process"
				 + " file received in InputSource: "
				 + iSource,
				ioe);
		} catch (SAXException se) {
			throw	new XMLUtilitiesException(
					"SaxException Exception raised because the underlying"
					+ " parser does not support the requested features;"
					+ " setValidating: true; setNameSpaceAware: true",
					se);
		} catch (FactoryConfigurationError e) {
			throw new XMLUtilitiesException(
						"Could not locate a JAXP factory class.", 
						e);
		}
		return true;
	}
}
