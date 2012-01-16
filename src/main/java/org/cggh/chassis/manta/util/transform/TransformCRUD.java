package org.cggh.chassis.manta.util.transform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import org.apache.abdera.protocol.client.AbderaClient;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.abdera.protocol.client.RequestOptions;

public class TransformCRUD {

	private final Logger logger = Logger.getLogger(this.getClass().getPackage().getName());
	
	
	public String retrieveUrlResponseAsStringUsingUrlAsStringAndRequestOptions(String urlAsString, RequestOptions requestOptions) throws IOException {

		StringBuffer urlResponseAsStringBuffer = new StringBuffer();
		
			AbderaClient abderaClient = new AbderaClient();
			
			ClientResponse clientResponse = abderaClient.get(urlAsString, requestOptions);
			
			//This method doesn't authenticate:
			//URL urlAsURL = new URL(urlAsString);
			//URLConnection urlConnection = urlAsURL.openConnection();
			
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientResponse.getInputStream()));
			String line;

			//Note: Replace end-of-line characters.
			
	        while ((line = bufferedReader.readLine()) != null) 
	        	urlResponseAsStringBuffer.append(line).append("\n");
	        bufferedReader.close();
			
	        //
	        //this.getLogger().info("urlResponseAsString = " + urlResponseAsStringBuffer.toString());
		
		return urlResponseAsStringBuffer.toString();
	}


	public Logger getLogger() {
		return logger;
	}
}
