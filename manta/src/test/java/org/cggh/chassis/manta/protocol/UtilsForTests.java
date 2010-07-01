package org.cggh.chassis.manta.protocol;

import static junit.framework.Assert.fail;
import static org.cggh.chassis.manta.protocol.ConstantsForTests.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Element;
import org.apache.abdera.model.Entry;
import org.apache.abdera.protocol.client.AbderaClient;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.abdera.protocol.client.RequestOptions;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.BasicScheme;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;

public class UtilsForTests {

	
	
	
	static final Abdera abdera = new Abdera();
	static final Factory factory = abdera.getFactory();
	static final AbderaClient abderaClient = new AbderaClient(abdera);
	static final HttpClient httpClient = new HttpClient();
	static final BasicScheme basic = new BasicScheme();
	
	
	

	public static RequestOptions authn(RequestOptions options, String user, String pass) {

		String authorization = BasicScheme.authenticate(new UsernamePasswordCredentials(user, pass), "utf-8");
		options.setAuthorization(authorization);
		
		return options;

	}
	
	
	
	
	public static RequestOptions authn(RequestOptions options, String user) {

		return authn(options, user, ConstantsForTests.PASS);

	}
	
	
	
	
	public static RequestOptions authn(String user, String pass) {

		RequestOptions options = new RequestOptions();
		return authn(options, user, pass);

	}
	
	
	
	
	public static RequestOptions authn(String user) {
		
		return authn(user, ConstantsForTests.PASS);

	}
	
	
	
	public static Entry getEntry(ClientResponse response) {
		
		Document<Entry> entryDoc = response.getDocument();
		return entryDoc.getRoot();
		
	}

	
	
	
	public static Entry newDraft() {

		return newDraft("foobar");

	}
	
	
	
	public static Entry newDraft(String title) {

		Entry entry = factory.newEntry();
		entry.setTitle(title);
		Element draftElement = factory.newElement(QNAME_DRAFT);
		entry.setContent(draftElement);
		entry.getContentElement().setMimeType(MEDIATYPE_MANTA);
		
		return entry;

	}
	
	


	public static Entry newStudy(String title) {

		Entry entry = factory.newEntry();
		entry.setTitle(title);
		Element studyElement = factory.newElement(QNAME_STUDY);
		entry.setContent(studyElement);
		entry.getContentElement().setMimeType(MEDIATYPE_MANTA);
		
		return entry;

	}
	
	


	public static void authenticate(HttpMethod method, String user, String pass) {
		try {
			
			String authorization = basic.authenticate(new UsernamePasswordCredentials(user, pass), method);
			method.setRequestHeader("Authorization", authorization);
			
		} 
		catch (Throwable t) {
			
			t.printStackTrace();
			fail(t.getLocalizedMessage());
			
		}
	}

	
	
	
	public static Integer executeMethod(HttpMethod method) {
		
		Integer result = null;
		
		try {

			result = httpClient.executeMethod(method);

		} catch (HttpException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		} catch (IOException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		}
		
		return result;
		
	}
	
	
	

	
	public static Integer executeMethod(HttpMethod method, String user, String pass) {
		
		authenticate(method, user, pass);
 
		return executeMethod(method);

	}
	
	
	
	
	public static Integer executeMethod(HttpMethod method, String user) {
		
		return executeMethod(method, user, ConstantsForTests.PASS);

	}
	
	
	
	
	public static FilePart createFilePart(File file, String fileName, String contentType, String filePartName) {
		
		FilePart fp = null;

		try {
			
			fp = new FilePart(filePartName , fileName, file, contentType, null);

		} catch (FileNotFoundException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		}
		
		return fp;
		
	}
	
	
	
	public static void setMultipartRequestEntity(EntityEnclosingMethod method, Part[] parts) {
		
		MultipartRequestEntity entity = new MultipartRequestEntity(parts, method.getParams());
		method.setRequestEntity(entity);

	}
	
	
	

}
