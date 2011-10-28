package org.cggh.common;

import org.apache.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


public class DefaultSAXErrorHandler implements ErrorHandler {

	/** Set up logger for later use */
	private static Logger c_logger = Logger.getLogger(XMLUtilities.class);

	/**
	 * @see ErrorHandler#error(SAXParseException)
	 */
	public void error(SAXParseException arg0) throws SAXException {
		c_logger.error(buildMessage(arg0, "ERROR"));
		throw arg0; // Rethrow
	}

	/**
	 * @see ErrorHandler#fatalError(SAXParseException)
	 */
	public void fatalError(SAXParseException arg0) throws SAXException {
		c_logger.fatal(buildMessage(arg0, "FATAL"));
		throw arg0; // Rethrow
	}

	/**
	 * @see ErrorHandler#warning(SAXParseException)
	 */
	public void warning(SAXParseException arg0) throws SAXException {
		c_logger.warn(buildMessage(arg0, "WARNING"));
		// Don't rethrow on a warning... instead continue
	}

	/**
	 * Build the Error Message
	 * @param	arg0 	Exception for which we want to build the message
	 * @param	level	Level of the error
	 * @return 	String containing the Error causing the failure
	 */
	private String buildMessage(SAXParseException arg0, String level) {

		StringBuffer msg = new StringBuffer(180);

		msg.append("A SAX ");
		msg.append(level);
		msg.append(" level error has been raised by the Parser.\n");
		msg.append("Detailed message: " + arg0.getMessage() + "\n");
		msg.append("Line number:      " + arg0.getLineNumber() + "\n");
		msg.append("Column number:    " + arg0.getColumnNumber() + "\n");
		msg.append("Public Id:        " + arg0.getPublicId() + "\n");
		msg.append("System Id:        " + arg0.getSystemId() + "\n");

		return msg.toString();

	}


}
