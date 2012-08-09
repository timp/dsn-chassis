package org.cggh.chassis.manta.security;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.cggh.chassis.manta.util.CasAlfrescoProxy;

/**
 * @author iwright
 * 
 */
public class AlfrescoCASFilter implements Filter {

	private Log log = LogFactory.getLog(AlfrescoCASFilter.class);

	public static final String ALFRESCO_TICKET = "alf_ticket";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
	
		log.debug("request inbound");

		HttpClient client = new DefaultHttpClient();
		String ticket = null;
		try {
			ticket = CasAlfrescoProxy.getAlfrescoTicket((HttpServletRequest) request, client);
		} catch (NamingException e) {
			log.error("Need to set JNDI variable alfrescoApp if using Alfresco", e);
		} catch (HttpException e) {
			log.error("Failed to fetch alfresco ticket", e);
		}
		if (ticket != null) {
			request.setAttribute(ALFRESCO_TICKET, ticket);
		}
		log.debug("alfresco ticket:" + ticket);
		chain.doFilter(request, response);

		log.debug("response outbound");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
