package org.cggh.chassis.manta.util;

import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;

import javax.servlet.ServletException;

import org.cggh.chassis.manta.protocol.ConstantsForTests;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.feed.AtomFeedHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.w3._2005.atom.Content;
import org.w3._2005.atom.Entry;
import org.w3._2005.atom.Title;

public class AtomUtil<T, E> {

	private Integer status;
	private E response;
	private String errorResponse;
	private Class requestClass;
	
	public AtomUtil(Class<T> cl) {
		requestClass = cl;
	}

	// static final Abdera abdera = new Abdera();
	// static final Factory factory = abdera.getFactory();
	// static final AbderaClient ac = new AbderaClient(abdera);

	/*
	 * public SyndFeed getFeed(String url, String user, String pass) {
	 * 
	 * BasicRequestFactory s = new BasicRequestFactory();
	 * 
	 * // It is possible that you need to adjust the Media Type associated with
	 * // the SyndFeedHttpMessageConverter. By default, the converter is //
	 * associated with application/rss+xml and application/atom+xml. An RSS //
	 * feed might instead have a media type of text/xml, for example. The //
	 * following code illustrates how to set the media type.
	 * 
	 * // Set the alternate mime type for the message converter
	 * AtomFeedHttpMessageConverter syndFeedConverter = new
	 * AtomFeedHttpMessageConverter();
	 * syndFeedConverter.setSupportedMediaTypes(Collections
	 * .singletonList(MediaType.TEXT_XML));
	 * 
	 * s.setUser(user); s.setPassword(pass); // Create a new RestTemplate
	 * instance RestTemplate restTemplate = new RestTemplate(s);
	 * 
	 * // Add the ROME message converter
	 * restTemplate.getMessageConverters().add(syndFeedConverter);
	 * 
	 * // Make the HTTP GET request, marshaling the response to a SyndFeed //
	 * object SyndFeed feed = null;
	 * 
	 * try { feed = restTemplate.getForObject(url, SyndFeed.class); } catch
	 * (HttpClientErrorException e) { // e.printStackTrace(); status =
	 * e.getStatusCode().value(); return feed; }
	 * 
	 * return feed; }
	 */

	private T processRequest(String target, HttpMethod methodType, T send,
			String user, String pass) {

		String dest = target.replaceFirst("repository", "repo");
		// .replaceFirst("http://localhost:8080", "https://kwiat33");

		BasicRequestFactory s = new BasicRequestFactory();
		s.setUser(user);
		s.setPassword(pass);
		RestTemplate restClient = new RestTemplate(s);

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("Content-Type", "application/atom+xml;type=entry");
		HttpEntity<T> requestEntity = new HttpEntity<T>(send, requestHeaders);
		HttpEntity<E> resp = null;
		try {

			resp = (HttpEntity<E>) restClient.exchange(dest, methodType,
					requestEntity, requestClass);

		} catch (HttpClientErrorException e) {

			String msg = "Unable to send to Chassis Service:";
			if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {

				// String out = e.getResponseBodyAsString();
				// StringReader sr = new StringReader(out);

				// sr.close();
			}

			status = e.getStatusCode().value();
			// throw new ServletException(msg, e);
			errorResponse = e.getResponseBodyAsString();
			return null;
		}

		return (T) resp.getBody();
	}

	public T createEntry(String target, String title, String user,
			String pass) {
		Entry e = new Entry();
		
		 Title t = new Title();
		 t.setContent(title);
		 ((Entry) e).setTitle(t);

		 Content c = new Content();
		 c.setType(ConstantsForTests.MEDIATYPE_MANTA);
		 e.setContent(c);
		 
		return processRequest(target, HttpMethod.POST, (T) e, user, pass);

	}

	public T fetch(String target, String user, String pass) {
		return processRequest(target, HttpMethod.GET, (T) "", user, pass);
	}
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
