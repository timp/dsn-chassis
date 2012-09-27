package org.cggh.chassis.manta.util;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.crypto.codec.Base64;

public class BasicRequestFactory extends SimpleClientHttpRequestFactory {
	private String user;
	private String pass;

	public void setUser(String u) {
		user = u;
	}

	public void setPassword(String p) {
		pass = p;
	}

	@Override
	protected void prepareConnection(HttpURLConnection connection,
			String httpMethod) throws IOException {
		super.prepareConnection(connection, httpMethod);

		// Basic Authentication
		String authorisation = user + ":" + pass;
		byte[] encodedAuthorisation = Base64.encode(authorisation.getBytes());
		connection.setRequestProperty("Authorization", "Basic "
				+ new String(encodedAuthorisation));
	}
}
