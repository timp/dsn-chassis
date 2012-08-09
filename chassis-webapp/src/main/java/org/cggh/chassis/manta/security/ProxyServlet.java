package org.cggh.chassis.manta.security;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.cggh.chassis.manta.util.CASUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class ProxyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6740547993430324899L;
	private Logger logger = Logger.getLogger(ProxyServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	//	super.doGet(req, resp);
		doProxiedMethod(req, resp, HttpMethod.GET);
	}

	private void doProxiedMethod(HttpServletRequest req,
			HttpServletResponse resp, HttpMethod methodType)
			throws ServletException, IOException {
		String target = req.getRequestURL().toString();
		String dest = target.replaceFirst("repository", "repo");
		// .replaceFirst("http://localhost:8080", "https://kwiat33");

		String proxyticket = CASUtil.getProxyTicket(req, dest);

		RestTemplate restClient = new RestTemplate();

		HttpHeaders requestHeaders = new HttpHeaders();
		@SuppressWarnings("unchecked")
		Enumeration<String> reqHeaders = req.getHeaderNames();
		while (reqHeaders.hasMoreElements()) {
			String hName = reqHeaders.nextElement();
			@SuppressWarnings("unchecked")
			Enumeration<String> hvalues = req.getHeaders(hName);
			while (hvalues.hasMoreElements()) {
				String hvalue = hvalues.nextElement();
				requestHeaders.set(hName,hvalue);
//				if (logger.isDebugEnabled()) {
//					logger.debug("Setting request header " + hName
//							+ " to:" + hvalue);
//				}
			}
		}
	//	requestHeaders.set("Content-type:", req.getHeader("Content-type"));
	//	requestHeaders.set("Accept:", req.getHeader("Accept"));
		
		String in;
		if (methodType == HttpMethod.GET) {
			in = "";
		} else {
			InputStream is = req.getInputStream();
			in = new Scanner(is).useDelimiter("\\A").next();
		}
		/* If you want to validate the ticket */
		// String
		// newDest="https://kwiat33/sso/proxyValidate?service="+dest+"&ticket={ticket}";
		// dest = newDest;

		// Comment out if validating ticket
		if (proxyticket != null) {
			dest += "?ticket={ticket}";
		}

		logger.debug(dest);

		HttpEntity<String> requestEntity = new HttpEntity<String>(in,
				requestHeaders);
		HttpEntity<String> response = null;
		try {
			long start = System.currentTimeMillis();
			if (proxyticket == null) {
				response = restClient.exchange(dest, methodType, requestEntity,
						String.class);
			} else {
				response = restClient.exchange(dest, methodType, requestEntity,
						String.class, proxyticket);
			}
			long end = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug("Retrieved " + dest + " in " + (end - start));
			}
		} catch (HttpClientErrorException e) {
			if (logger.isDebugEnabled()) {
				// studyMarshaller.marshal(study, new StreamResult(System.out));
				logger.debug(in);
			}
			String msg = "Unable to send to Chassis Service:";
			if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {

				// String out = e.getResponseBodyAsString();
				// StringReader sr = new StringReader(out);

				// sr.close();
			}
			logger.debug(e.getResponseBodyAsString());
			
			resp.setStatus(e.getStatusCode().value());
			resp.sendError(e.getStatusCode().value(),e.getStatusText());
//			throw new ServletException(msg, e);
			return;
		}
		HttpHeaders respHeaders = response.getHeaders();

		Iterator<Entry<String, List<String>>> iter = respHeaders.entrySet()
				.iterator();
		while (iter.hasNext()) {
			Entry<String, List<String>> head = iter.next();
			if (!(head.getKey().contains("Cookie") || head.getKey().equals(
					"Transfer-Encoding"))) {
				Iterator<String> values = head.getValue().iterator();
				while (values.hasNext()) {
					String value = values.next();
					resp.setHeader(head.getKey(), value);
//					if (logger.isDebugEnabled()) {
//						logger.debug("Setting response header " + head.getKey()
//								+ " to:" + value);
//					}
				}
			}
		}
		resp.getWriter().print(response.getBody());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	//	super.doPost(req, resp);
		doProxiedMethod(req, resp, HttpMethod.POST);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	//	super.doPut(req, resp);
		doProxiedMethod(req, resp, HttpMethod.PUT);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	//	super.doDelete(req, resp);
		doProxiedMethod(req, resp, HttpMethod.DELETE);
	}

}
