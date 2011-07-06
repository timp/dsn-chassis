package org.cggh.chassis.manta.util.transform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.apache.abdera.protocol.client.AbderaClient;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.abdera.protocol.client.RequestOptions;

public class TransformCRUD {

	
	
	
	public String retrieveUrlResponseAsStringUsingUrlAsStringAndRequestOptions(String urlAsString, RequestOptions requestOptions) throws IOException {

		StringBuffer urlResponseAsStringBuffer = new StringBuffer();
		
			AbderaClient atomClient = new AbderaClient();
			
			ClientResponse clientResponse = atomClient.get(urlAsString, requestOptions);
			
			//This method doesn't authenticate:
			//URL urlAsURL = new URL(urlAsString);
			//URLConnection urlConnection = urlAsURL.openConnection();
			
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientResponse.getInputStream()));
			String line;

	        while ((line = bufferedReader.readLine()) != null) 
	        	urlResponseAsStringBuffer.append(line);
	        bufferedReader.close();
			
		
		return urlResponseAsStringBuffer.toString();
	}

	public HashMap<String, String> retrieveFieldLabelRegExpMappingsAsPatternKeyedHashMap() {
		
		//TODO: Get the mapped values as a HashMap from the config collection using URL (hard-coded?)
		//http://localhost:8080/repository/service/content/config/field-label-mappings
		
		///////////
		HashMap<String, String> fieldLabelRegExpMappingsAsPatternKeyedHashMap = new HashMap<String, String>();
		
		//TODO: replace with sourced values
		fieldLabelRegExpMappingsAsPatternKeyedHashMap.put("/breakfast_menu\\[(\\d+)\\]/food\\[(\\d+)\\]/name\\[(\\d+)\\]", "Food$2Name");
		
		
		return fieldLabelRegExpMappingsAsPatternKeyedHashMap;
	}
}
