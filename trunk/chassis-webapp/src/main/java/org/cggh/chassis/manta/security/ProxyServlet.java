package org.cggh.chassis.manta.security;

import org.apache.log4j.Logger;
import org.cggh.chassis.manta.http.servlet.StoreUploadsServlet;
import org.cggh.chassis.manta.util.CASUtil;
import org.cggh.chassis.manta.util.RestRequestHandler;
import org.cggh.chassis.manta.util.RestResponseExtractor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class ProxyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6740547993430324899L;
	private static Logger logger = Logger.getLogger(ProxyServlet.class);

	protected static <E> RestResponseExtractor<E> getResponseExtractor(
			Class responseClass, RestTemplate restTemplate) {
		return new RestResponseExtractor<E>(responseClass,
				restTemplate.getMessageConverters());
	}

	public static <E> E doProxiedMethod(HttpServletRequest req,
			HttpServletResponse resp, HttpMethod methodType, InputStream is,
			Class responseClass) throws ServletException, IOException {
		String target = req.getRequestURL().toString();
        	String url = StoreUploadsServlet.requestToURL(req);
	
		if (logger.isDebugEnabled()) {
			logger.debug("target:" + target);
			logger.debug("url:" + url);
/*
			Enumeration<String> reqHeaders = req.getHeaderNames();

			while (reqHeaders.hasMoreElements()) {
				String head = reqHeaders.nextElement();
				Enumeration<String> values = req.getHeaders(head);
				while (values.hasMoreElements()) {
					String value = values.nextElement();
					logger.debug("Request header " + head + ":" + value);
				}
			}
*/
		}
		String dest = url.replaceFirst("repository", "repo");
		String forwardHost = req.getHeader("x-forwarded-host");
		if (forwardHost != null) {
			//If we get here then the service is being called from behind an apache proxy
			String host = req.getHeader("host");
			//Assuming that repo is installed in the same server as repository
			//Replace apache host with tomcat host
			//This is necessary to make sure that the proxy ticket fetched from org.cggh.chassis.manta.util.CASUtil
			//has the right matching host
			String dest1 = dest.replaceFirst(forwardHost, host);
			//Assuming that tomcat is using http
			dest = dest1.replaceFirst("https","http");
			logger.debug("Destination:" + dest);
		}

		String nextSep = "?";
		Enumeration<String> params = req.getParameterNames();

		while (params.hasMoreElements()) {
			String param = params.nextElement();
			String value = req.getParameter(param);
			dest += nextSep + param + "=" + value;
			nextSep = "&";
		}
		String proxyticket = CASUtil.getProxyTicket(req, dest);

		RestTemplate restClient = new RestTemplate();

		RestRequestHandler reqHandler = new RestRequestHandler(req, is);
		RestResponseExtractor<E> responseHandler = ProxyServlet
				.<E> getResponseExtractor(responseClass, restClient);

		/* If you want to validate the ticket */
		// String newDest="https://alfresco/sso/proxyValidate?service="+dest+"&ticket={ticket}";
		// dest = newDest;

		// Comment out if validating ticket
		if (proxyticket != null) {
			dest += nextSep + "ticket={ticket}";
			nextSep = "&";
		}
		logger.debug(dest);

		E response;
		try {
			long start = System.currentTimeMillis();

			if (proxyticket == null) {
				response = restClient.<E> execute(dest, methodType, reqHandler,
						responseHandler);
			} else {
				response = restClient.<E> execute(dest, methodType, reqHandler,
						responseHandler, proxyticket);
			}
			long end = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug("Retrieved " + dest + " in " + (end - start));
			}
		} catch (HttpClientErrorException e) {

			String msg = "Unable to send to Chassis Service:";
			if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {

				// String out = e.getResponseBodyAsString();
				// StringReader sr = new StringReader(out);

				// sr.close();
			}
			logger.debug(e.getResponseBodyAsString());

			resp.setStatus(e.getStatusCode().value());
			resp.sendError(e.getStatusCode().value(), e.getStatusText());
			// throw new ServletException(msg, e);
			return null;
		}
		HttpHeaders respHeaders = responseHandler.getHeaders();

		Iterator<Entry<String, List<String>>> iter = respHeaders.entrySet()
				.iterator();
		while (iter.hasNext()) {
			Entry<String, List<String>> head = iter.next();
			String headName = head.getKey();
			if (!(headName.equalsIgnoreCase("Set-Cookie") || headName
					.equalsIgnoreCase("Transfer-Encoding") || headName.equals("Content-Encoding"))) {
				Iterator<String> values = head.getValue().iterator();
				while (values.hasNext()) {
					String value = values.next();
					resp.setHeader(headName, value);
					if (logger.isDebugEnabled()) {
						logger.debug("Setting response header " + headName
								+ " to:" + value);
					}
				}
			}
		}
		resp.setStatus(responseHandler.getStatusCode().value());
		if (logger.isDebugEnabled()) {
			logger.debug("Setting response code:" + responseHandler.getStatusCode());
		}
		return (response);
	}



    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// super.doGet(req, resp);
		String response = ProxyServlet.<String> doProxiedMethod(req, resp,
				HttpMethod.GET, req.getInputStream(), String.class);
		resp.getWriter().print(response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// super.doPost(req, resp);
		resp.getWriter().print(
				ProxyServlet.<String> doProxiedMethod(req, resp,
						HttpMethod.POST, req.getInputStream(), String.class));
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// super.doPut(req, resp);
		resp.getWriter().print(
				ProxyServlet.<String> doProxiedMethod(req, resp,
						HttpMethod.PUT, req.getInputStream(), String.class));
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// super.doDelete(req, resp);
		resp.getWriter().print(
				ProxyServlet.<String> doProxiedMethod(req, resp,
						HttpMethod.DELETE, req.getInputStream(), String.class));
	}

}
