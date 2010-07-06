package org.cggh.chassis.manta.protocol;

import static junit.framework.Assert.fail;
import static org.cggh.chassis.manta.protocol.ConstantsForTests.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Element;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
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
	static final AbderaClient ac = new AbderaClient(abdera);
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

	
	
	
	public static Feed getFeed(ClientResponse response) {
		
		Document<Feed> feedDoc = response.getDocument();
		return feedDoc.getRoot();
		
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
	
	
	
	
	public static Feed newFeed(String title) {
		
		Feed f = factory.newFeed();
		f.setTitle(title);
		return f;
		
	}
	

	public static Entry newEntry(String title) {

		Entry f = factory.newEntry();
		f.setTitle(title);
		return f;

	}

	public static Entry newStudyInfo() {

		Entry entry = factory.newEntry();
		Element studyInfoElement = factory.newElement(QNAME_STUDY_INFO);
		entry.setContent(studyInfoElement);
		entry.getContentElement().setMimeType(MEDIATYPE_MANTA);
		
		return entry;

	}
	
	


	public static Entry newSecurityDescriptor() {

		Entry entry = factory.newEntry();
		Element securityDescriptorElement = factory.newElement(QNAME_ATOMBEAT_SECURITY_DESCRIPTOR);
		entry.setContent(securityDescriptorElement);
		entry.getContentElement().setMimeType(MEDIATYPE_ATOMBEAT);
		factory.newElement(QNAME_ATOMBEAT_ACL, securityDescriptorElement);
		
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
	
	
	
	public static Entry createStudy(String title, String user) {

		Entry entry = newStudy(title);
		return createMemberAndReturnEntry(entry, URL_STUDIES_COLLECTION, user);

	}
	

	public static Entry createDraft(String title, String user) {

		Entry entry = newDraft(title);
		return createMemberAndReturnEntry(entry, URL_DRAFTS_COLLECTION, user);

	}
	
	
	
	public static Entry createMemberAndReturnEntry(Entry entry, String collectionUrl, String user) {

		ClientResponse response = ac.post(collectionUrl, entry, authn(user));
		assert 201 == response.getStatus() : "unexpected status code";
		entry = getEntry(response);
		assert entry != null : "entry is null";
		response.release();
		
		return entry;

	}
	
	
	
	public static Entry createMediaAndReturnEntry(String filePath, String contentType, String collectionUrl, String user) {
		
		InputStream content = UtilsForTests.class.getClassLoader().getResourceAsStream(filePath);
		RequestOptions options = authn(user);
		options.setContentType(contentType);
		ClientResponse response = ac.post(collectionUrl, content, options);
		assert 201 == response.getStatus() : "unexpected status code";
		Entry entry = getEntry(response);
		assert entry != null : "entry is null";
		response.release();
		
		return entry;

	}
	
	

// N.B. this doesn't work, because Abdera uses a streaming model, so any attempt
// to read the feed after response.release() causes an XMLStreamException
// ...
//	public static Feed listCollection(String collectionUrl, String user) {
//
//		ClientResponse response = abderaClient.get(collectionUrl, authn(user));
//		assert 200 == response.getStatus() : "unexpected status code";
//		Feed feed = getFeed(response); 
//		assert feed != null : "feed is null";
//		response.release();
//
//		return feed;
//		
//	}
//
//	
//	
//	public static Feed listStudies(String user) {
//		
//		return listCollection(URL_STUDIES_COLLECTION, user);
//		
//	}
	
	
	
	
	public static Integer listCollection(String collectionUrl, String user) {
		
		ClientResponse r = ac.get(collectionUrl, authn(user));
		Integer status = r.getStatus();
		r.release();
		
		return status;
		
	}
	

	

	public static Map<String,Integer> listCollection(String collectionUrl, Set<String> users) {
		
		Map<String,Integer> results = new HashMap<String,Integer>();
		
		for (String user : users) {
			results.put(user, listCollection(collectionUrl, user));
		}
		
		return results;
		
	}
	
	
	
	public static void assertListCollectionForbidden(String collectionUrl, Set<String> users) {
		
		Map<String,Integer> results = listCollection(collectionUrl, users);
		
		// verify results
		
		for (String user : users) {
			Assert.assertEquals(user, SC_FORBIDDEN, results.get(user));
		}
	
	}
	
	
	
	
	public static Integer updateCollection(String collectionUrl, Feed f, String user) {
		
		ClientResponse r = ac.put(collectionUrl, f, authn(user));
		Integer status = r.getStatus();
		r.release();
		
		return status;
		
	}
	

	

	public static Map<String,Integer> updateCollection(String collectionUrl, Feed f, Set<String> users) {
		
		Map<String,Integer> results = new HashMap<String,Integer>();
		
		for (String user : users) {
			results.put(user, updateCollection(collectionUrl, f, user));
		}
		
		return results;
		
	}
	
	
	
	
	public static void assertUpdateCollectionForbidden(String collectionUrl, Feed f, Set<String> users) {

		Map<String,Integer> results = updateCollection(collectionUrl, f, users);
		
		// verify results
		
		for (String user : users) {
			Assert.assertEquals(user, SC_FORBIDDEN, results.get(user));
		}
		
	}
	
	
		
	
	public static Integer multiCreate(String collectionUrl, Feed f, String user) {
		
		ClientResponse r = ac.post(collectionUrl, f, authn(user));
		Integer status = r.getStatus();
		r.release();
		
		return status;
		
	}
	

	

	public static Map<String,Integer> multiCreate(String collectionUrl, Feed f, Set<String> users) {
		
		Map<String,Integer> results = new HashMap<String,Integer>();
		
		for (String user : users) {
			results.put(user, multiCreate(collectionUrl, f, user));
		}
		
		return results;
		
	}
	
	
	
	
	public static Integer createMember(String collectionUrl, Entry entry, String user) {

		ClientResponse r = ac.post(collectionUrl, entry, authn(user));
		Integer status = r.getStatus();
		r.release();

		return status;

	}
	
	
	
	
	public static Map<String,Integer> createMember(String collectionUrl, Entry entry, Set<String> users) {
		
		Map<String,Integer> results = new HashMap<String,Integer>();
		
		for (String user : users) {
			results.put(user, createMember(collectionUrl, entry, user));
		}
		
		return results;
		
	}
	
	

	
	public static void assertCreateMemberForbidden(String collectionUrl, Entry entry, Set<String> users) {
		
		Map<String,Integer> results = createMember(collectionUrl, entry, users);
		
		// verify results
		
		for (String user : users) {
			Assert.assertEquals(user, SC_FORBIDDEN, results.get(user));
		}

	}
	
	
	
	
	public static Integer retrieveMember(String location, String user) {

		ClientResponse r = ac.get(location, authn(user));
		Integer status = r.getStatus();
		r.release();

		return status;

	}
	
	
	
	
	public static Map<String,Integer> retrieveMember(String location, Set<String> users) {
		
		Map<String,Integer> results = new HashMap<String,Integer>();
		
		for (String user : users) {
			results.put(user, retrieveMember(location, user));
		}
		
		return results;
		
	}
	
	
	
	
	public static void assertRetrieveMemberForbidden(String location, Set<String> users) {

		Map<String,Integer> results = retrieveMember(location, users);
		
		// verify results
		
		for (String user : users) {
			Assert.assertEquals(user, SC_FORBIDDEN, results.get(user));
		}
		
	}
	
	
	

	public static Integer updateMember(String location, Entry entry, String user) {

		ClientResponse r = ac.put(location, entry, authn(user));
		Integer status = r.getStatus();
		r.release();

		return status;

	}
	
	
	
	
	public static Map<String,Integer> updateMember(String location, Entry entry, Set<String> users) {
		
		Map<String,Integer> results = new HashMap<String,Integer>();
		
		for (String user : users) {
			results.put(user, updateMember(location, entry, user));
		}
		
		return results;
		
	}
	
	
	
	
	public static void assertUpdateMemberForbidden(String location, Entry entry, Set<String> users) {
		
		Map<String,Integer> results = updateMember(location, entry, users);
		 
		// verify results
		
		for (String user : users) {
			Assert.assertEquals(user, SC_FORBIDDEN, results.get(user));
		}

	}

		
	public static Integer deleteMember(String location, String user) {

		ClientResponse r = ac.delete(location, authn(user));
		Integer status = r.getStatus();
		r.release();

		return status;

	}
	
	
	
	
	public static Map<String,Integer> deleteMember(String location, Set<String> users) {
		
		Map<String,Integer> results = new HashMap<String,Integer>();
		
		for (String user : users) {
			results.put(user, deleteMember(location, user));
		}
		
		return results;
		
	}
	
	
	

	public static Integer createMedia(String collectionUrl, InputStream content, String contentType, String user) {

		RequestOptions options = authn(user);
		options.setContentType(contentType);
		ClientResponse response = ac.post(collectionUrl, content, options);
		Integer status = response.getStatus();
		response.release();
		
		return status;

	}
	

	
	
	public static Map<String,Integer> createMedia(String collectionUrl, Action<InputStream> streamGenerator, String contentType, Set<String> users) {
		
		Map<String,Integer> results = new HashMap<String,Integer>();
		
		for (String user : users) {
			results.put(user, createMedia(collectionUrl, streamGenerator.apply(), contentType, user));
		}
		
		return results;
		
	}
	
	
	
	public static Integer retrieveMedia(String location, String user) {

		ClientResponse r = ac.get(location, authn(user));
		Integer status = r.getStatus();
		r.release();

		return status;

	}
	
	
	
	
	public static Map<String,Integer> retrieveMedia(String location, Set<String> users) {
		
		Map<String,Integer> results = new HashMap<String,Integer>();
		
		for (String user : users) {
			results.put(user, retrieveMedia(location, user));
		}
		
		return results;
		
	}
	
	
	
	public static void assertRetrieveMediaForbidden(String location, Set<String> users) {

		Map<String,Integer> results = retrieveMedia(location, users);
		
		// verify results
		
		for (String user : users) {
			Assert.assertEquals(user, SC_FORBIDDEN, results.get(user));
		}
		
	}
	
	
	


	
	
	
	public static void assertCreateMediaForbidden(String collectionUrl, Action<InputStream> streamGenerator, String contentType, Set<String> users) {
		
		Map<String,Integer> results = createMedia(collectionUrl, streamGenerator, contentType, users);
		 
		// verify results
		
		for (String user : users) {
			Assert.assertEquals(user, SC_FORBIDDEN, results.get(user));
		}

	}

	
	
	
	public static void assertRetrieveCollectionAclForbidden(String collectionUrl, Set<String> users) {
		
		Feed f;
		String location;
		ClientResponse r;
		
		// setup test

		r = ac.get(collectionUrl, authn(USER_ADAM));
		f = getFeed(r);
		location = f.getLink(REL_ATOMBEAT_SECURITY_DESCRIPTOR).getHref().toString();
		r.release();

		// run tests
		
		assertRetrieveMemberForbidden(location, users);

	}

	
	

	public static void assertUpdateCollectionAclForbidden(String collectionUrl, Set<String> users) {
		
		Feed f;
		Entry s;
		String location;
		ClientResponse r;
		
		// setup test

		r = ac.get(collectionUrl, authn(USER_ADAM));
		f = getFeed(r);
		location = f.getLink(REL_ATOMBEAT_SECURITY_DESCRIPTOR).getHref().toString();
		r.release();

		// run tests
		
		s = newSecurityDescriptor();
		assertUpdateMemberForbidden(location, s, users);

	}


}
