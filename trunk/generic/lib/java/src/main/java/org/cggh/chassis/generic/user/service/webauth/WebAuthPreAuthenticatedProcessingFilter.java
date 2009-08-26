/**
 * 
 */
package org.cggh.chassis.generic.user.service.webauth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.ui.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * @author raok
 *
 */
public class WebAuthPreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.preauth.AbstractPreAuthenticatedProcessingFilter#getPreAuthenticatedCredentials(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest arg0) {
		//Required override, not used, but must not return null.
		return "";
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.preauth.AbstractPreAuthenticatedProcessingFilter#getPreAuthenticatedPrincipal(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest arg0) {
		//TODO throw more appropriate errors.
		
		//Get the WEBAUTH_USER WebAuth Header which should be added by a WebAuth proxy server.
		//This is of the form: 'user<webAuthUserId>/gmap@GMAP.NET'
		String webAuthUserString = (String) arg0.getHeader("WEBAUTH_USER");
		
		//Extract the webAuthUserId.
		String webAuthUserIdString;
		if (webAuthUserString != null)
		{
			Pattern regEx = Pattern.compile("^user(\\d+)/gmap@GMAP\\.NET$");
			Matcher matcher = regEx.matcher(webAuthUserString);
			
			//throw exception if webAuth attribute is invalid or not found
			if (!matcher.find())
			{
				throw new Error ("WebAuth attribute invalid: " + webAuthUserString);
			}
			
			//Store webAuthUserId
			webAuthUserIdString = matcher.group(1);
		}
		else
		{
			throw new Error ("WebAuth attribute not found.");
		}
		
		return webAuthUserIdString;
	}

	/* (non-Javadoc)
	 * @see org.springframework.core.Ordered#getOrder()
	 */
	public int getOrder() {
        return org.springframework.security.ui.FilterChainOrder.PRE_AUTH_FILTER;
	}

}
