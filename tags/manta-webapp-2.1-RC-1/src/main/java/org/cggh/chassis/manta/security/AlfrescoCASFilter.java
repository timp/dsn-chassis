package org.cggh.chassis.manta.security;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.atombeat.http.HttpFilter;
import org.cggh.chassis.manta.util.CasAlfrescoProxy;

/**
 * @author iwright
 * 
 */
public class AlfrescoCASFilter extends HttpFilter {

	private Log log = LogFactory.getLog(this.getClass());

	public static final String ALFRESCO_TICKET = "alf_ticket";

	@Override
	public void doHttpFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.debug("request inbound");

		HttpClient client = new HttpClient();
		String ticket = null;
		try {
			ticket = CasAlfrescoProxy.getAlfrescoTicket(request, client);
		} catch (NamingException e) {
			log.error("Need to set JNDI variable alfrescoApp if using Alfresco", e);
		}
		if (ticket != null) {
			request.setAttribute(ALFRESCO_TICKET, ticket);
		}
		log.debug("alfresco ticket:" + ticket);
		chain.doFilter(request, response);

		log.debug("response outbound");
	}

}
