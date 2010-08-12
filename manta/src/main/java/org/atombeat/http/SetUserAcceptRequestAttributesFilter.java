package org.atombeat.http;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.atombeat.http.HttpFilter;

/**
 * Set the language and country explicitly, as the raw headers give a list, 
 * which we do not wish to process within XQL.
 *  
 *  Note that we need to wrap value in xml tags for Orbeon's sake. 
 *  
 * @author timp
 */
public class SetUserAcceptRequestAttributesFilter extends HttpFilter {

	
	private Log log = LogFactory.getLog(this.getClass());
	
	public static final String USERLANGUAGE_REQUEST_ATTRIBUTE_KEY     = "user-language";
	public static final String USER_COUNTRY_REQUEST_ATTRIBUTE_KEY     = "user-country";
	
	/**
	 * Add the preferred language and country as separate attributes, 
	 * defaulted to the server settings.
	 *  
	 * See:
	 *  Accept: http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html
	 *  Language: http://www.rfc-editor.org/rfc/rfc3282.txt
	 *  
	 * eg:
	 * Accept-Language: da, en-gb;q=0.8, en;q=0.7
	 *  would mean: 
	"I prefer Danish, but will accept British English and
   other types of English." A language-range matches a language-tag if
   it exactly equals the tag, or if it exactly equals a prefix of the
   tag such that the first tag character following the prefix is "-".
   The special range "*", if present in the Accept-Language field,
   matches every tag not matched by any other range present in the
   Accept-Language field.
	 */
	@Override
	public void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		log.debug("request inbound");

		request.setAttribute(USERLANGUAGE_REQUEST_ATTRIBUTE_KEY, "<language>" + request.getLocale().getLanguage() + "</language>");
		request.setAttribute(USER_COUNTRY_REQUEST_ATTRIBUTE_KEY, "<country>" + request.getLocale().getCountry() + "</country>");

		chain.doFilter(request, response);

		log.debug("response outbound");
	}

}
