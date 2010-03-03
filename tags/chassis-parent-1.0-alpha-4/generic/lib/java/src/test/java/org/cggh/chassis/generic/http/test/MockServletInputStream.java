/**
 * 
 */
package org.cggh.chassis.generic.http.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ServletInputStream;

/**
 * @author timp
 *
 */
public class MockServletInputStream extends ServletInputStream {

	ByteArrayInputStream bais; 
	public MockServletInputStream(String content) {
		this.bais = new ByteArrayInputStream(content.getBytes());
	}
	@Override
	public int read() throws IOException {		
		return bais.read();
	}

}
