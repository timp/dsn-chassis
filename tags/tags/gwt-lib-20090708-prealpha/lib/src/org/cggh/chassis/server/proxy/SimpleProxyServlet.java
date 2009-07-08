/**
 * $Id$
 */
package org.cggh.chassis.server.proxy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class SimpleProxyServlet extends HttpServlet {

	private String hostname;
	private int port;

	@Override
	public void init() throws ServletException {
		super.init();

		hostname = getInitParameter("hostname");
		assert (hostname != null && hostname.length() > 0);

		String portStr = getInitParameter("port");
		port = Integer.parseInt(portStr, 10);
		assert (port > 0);
	}

}
