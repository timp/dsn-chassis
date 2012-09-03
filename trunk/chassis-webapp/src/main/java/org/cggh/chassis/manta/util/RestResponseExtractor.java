package org.cggh.chassis.manta.util;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.ResponseExtractor;

public class RestResponseExtractor<T> extends HttpMessageConverterExtractor<T>
		implements ResponseExtractor<T> {

	private HttpHeaders headers;
	private HttpStatus statusCode;

	private static Logger logger = Logger
			.getLogger(RestResponseExtractor.class);

	public RestResponseExtractor(Class<T> responseType,
			List<HttpMessageConverter<?>> messageConverters) {
		super(responseType, messageConverters);
	}

	@Override
	public T extractData(ClientHttpResponse arg0) throws IOException {
		headers = arg0.getHeaders();
		statusCode = arg0.getStatusCode();
		T ret = null;
		if (!(statusCode == HttpStatus.NO_CONTENT || statusCode == HttpStatus.NOT_MODIFIED)) {
			ret = super.extractData(arg0);
		}
		return ret;
	}

	public HttpHeaders getHeaders() {
		return headers;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

}
