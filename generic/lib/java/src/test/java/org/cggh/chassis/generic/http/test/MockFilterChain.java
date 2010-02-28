package org.cggh.chassis.generic.http.test;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class MockFilterChain implements FilterChain {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response)
			throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse)response; 
		httpResponse.setStatus(200);
	}

}
