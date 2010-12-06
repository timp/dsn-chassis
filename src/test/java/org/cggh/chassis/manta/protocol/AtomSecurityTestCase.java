package org.cggh.chassis.manta.protocol;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

import junit.framework.Assert;
import junit.framework.TestCase;

import static org.cggh.chassis.manta.protocol.ConstantsForTests.*;

public abstract class AtomSecurityTestCase extends TestCase {
	
	
	
	/**
	 * 
	 * @return the collection URL
	 */
	protected abstract String setupCollection();
	
	/**
	 * 
	 * @return the member URL
	 */
	protected abstract String setupMember();
	
	/**
	 * 
	 * @return the media link URL
	 */
	protected abstract String setupMediaResource();
	
	/**
	 * 
	 * @return whether versioning is enabled for the collection under test
	 */
	protected abstract boolean collectionIsVersioned();
	
	
	
	
	protected abstract String[] getUsersAllowedListCollection();
	protected abstract String[] getUsersAllowedCreateMember();
	protected abstract String[] getUsersAllowedRetrieveMember();
	protected abstract String[] getUsersAllowedUpdateMember();
	protected abstract String[] getUsersAllowedDeleteMember();
	protected abstract String[] getUsersAllowedCreateMedia();
	protected abstract String[] getUsersAllowedRetrieveMedia();
	protected abstract String[] getUsersAllowedUpdateMedia();
	protected abstract String[] getUsersAllowedDeleteMedia();
	protected abstract String[] getUsersAllowedUpdateCollection();
	protected abstract String[] getUsersAllowedRetrieveMemberAcl();
	protected abstract String[] getUsersAllowedUpdateMemberAcl();
	protected abstract String[] getUsersAllowedRetrieveCollectionAcl();
	protected abstract String[] getUsersAllowedUpdateCollectionAcl();
	protected abstract String[] getUsersAllowedRetrieveMediaAcl();
	protected abstract String[] getUsersAllowedUpdateMediaAcl();
	protected abstract String[] getUsersAllowedRetrieveMemberHistory();
	protected abstract String[] getUsersAllowedRetrieveMemberRevision();
	protected abstract String[] getUsersAllowedMultiCreate();
	
	
	
	protected abstract String getSuperUser();
	
	
	
	
	public void testDenyListCollection() {
		
		String collectionLocation = this.setupCollection();
		if (collectionLocation != null) {
			Set<String> denied = userComplement(this.getUsersAllowedListCollection());
			assertListCollectionForbidden(collectionLocation, denied);
		}
		
	}
	
	
	
	public void testDenyCreateMember() {
		
		String collectionLocation = this.setupCollection();
		if (collectionLocation != null) {
			Set<String> denied = userComplement(this.getUsersAllowedCreateMember());
			assertCreateMemberForbidden(collectionLocation, denied);
		}
		
	}
	
	
	
	public void testDenyRetrieveMember() {
		
		String memberLocation = this.setupMember();
		if (memberLocation != null) {
			Set<String> denied = userComplement(this.getUsersAllowedRetrieveMember());
			assertRetrieveMemberForbidden(memberLocation, denied);
		}
		
	}
	
	
	
	
	public void testDenyUpdateMember() {
		
		String memberLocation = this.setupMember();
		if (memberLocation != null) {
			Set<String> denied = userComplement(this.getUsersAllowedUpdateMember());
			assertUpdateMemberForbidden(memberLocation, denied);
		}
		
	}
	
	
	
	
	public void testDenyDeleteMember() {
		
		String memberLocation = this.setupMember();
		if (memberLocation != null) {
			Set<String> denied = userComplement(this.getUsersAllowedDeleteMember());
			assertDeleteMemberForbidden(memberLocation, denied);
		}
		
	}
	
	
	
	public void testDenyCreateMedia() {
		
		String collectionLocation = this.setupCollection();
		if (collectionLocation != null) {
			Set<String> denied = userComplement(this.getUsersAllowedCreateMedia());
			Action<InputStream> streamGenerator = new Action<InputStream>() {
				@Override
				public InputStream apply() {
					return this.getClass().getClassLoader().getResourceAsStream("spreadsheet1.xls");
				}
			};
			assertCreateMediaForbidden(collectionLocation, streamGenerator, "application/vnd.ms-excel", denied);
		}
		
	}
	
	
	
	
	public void testDenyRetrieveMedia() {
		
		String mediaLinkLocation = this.setupMediaResource();
		
		if (mediaLinkLocation != null) {
			
			// find the media resource location
			
			ClientResponse r = ac.get(mediaLinkLocation, authn(getSuperUser()));
			Entry mediaLink = getEntry(r);
			String location = mediaLink.getEditMediaLink().getHref().toString();
			r.release();
			
			// run the tests
			
			Set<String> denied = userComplement(this.getUsersAllowedRetrieveMedia());
			assertRetrieveMediaForbidden(location, denied);
			
		}
		
	}
	
	
	
	
	public void testDenyUpdateMedia() {
		
		String mediaLinkLocation = this.setupMediaResource();

		if (mediaLinkLocation != null) {
		
			// find the media resource location

			ClientResponse r = ac.get(mediaLinkLocation, authn(getSuperUser()));
			Entry mediaLink = getEntry(r);
			String location = mediaLink.getEditMediaLink().getHref().toString();
			r.release();

			// run the tests

			Set<String> denied = userComplement(this.getUsersAllowedUpdateMedia());
			Action<InputStream> streamGenerator = new Action<InputStream>() {
				@Override
				public InputStream apply() {
					return this.getClass().getClassLoader().getResourceAsStream("spreadsheet2.xls");
				}
			};
			assertUpdateMediaForbidden(location, streamGenerator, "application/vnd.ms-excel", denied);
			
		}
		
	}
	
	
	
	
	public void testDenyDeleteMedia() {
		
		String mediaLinkLocation = this.setupMediaResource();
		
		if (mediaLinkLocation != null) {

			// find the media resource location

			ClientResponse r = ac.get(mediaLinkLocation, authn(getSuperUser()));
			Entry mediaLink = getEntry(r);
			String mediaLocation = mediaLink.getEditMediaLink().getHref().toString();
			r.release();
			
			// run the tests
			
			Set<String> denied = userComplement(this.getUsersAllowedDeleteMedia());
			assertDeleteMediaForbidden(mediaLinkLocation, denied);
			assertDeleteMediaForbidden(mediaLocation, denied);
			
		}

	}
	
	
	
	
	public void testDenyUpdateCollection() {
		
		String collectionLocation = this.setupCollection();
		if (collectionLocation != null) {
			Set<String> denied = userComplement(this.getUsersAllowedUpdateCollection());
			assertUpdateCollectionForbidden(collectionLocation, denied);
		}
		
	}
	
	
	
	public void testDenyRetrieveMemberAcl() {
		
		String memberLocation = this.setupMember();
		if (memberLocation != null) {
			Set<String> denied = userComplement(this.getUsersAllowedRetrieveMemberAcl());
			assertRetrieveMemberAclForbidden(memberLocation, getSuperUser(), denied);
		}
		
	}
	
	
	
	
	public void testDenyUpdateMemberAcl() {
		
		String memberLocation = this.setupMember();
		if (memberLocation != null) {
			Set<String> denied = userComplement(this.getUsersAllowedUpdateMemberAcl());
			assertUpdateMemberAclForbidden(memberLocation, getSuperUser(), denied);
		}
		
	}
	
	
	
	
	public void testDenyRetrieveCollectionAcl() {
		
		String collectionLocation = this.setupCollection();
		if (collectionLocation != null) {
			Set<String> denied = userComplement(this.getUsersAllowedRetrieveCollectionAcl());
			assertRetrieveCollectionAclForbidden(collectionLocation, getSuperUser(), denied);
		}
		
	}
	
	
	
	
	public void testDenyUpdateCollectionAcl() {
		
		String collectionLocation = this.setupCollection();
		if (collectionLocation != null) {
			Set<String> denied = userComplement(this.getUsersAllowedUpdateCollectionAcl());
			assertUpdateCollectionAclForbidden(collectionLocation, getSuperUser(), denied);
		}
		
	}
	
	
	

	public void testDenyRetrieveMediaAcl() {

		String mediaLinkLocation = this.setupMediaResource();
		
		if (mediaLinkLocation != null) {

			// run the tests
			
			Set<String> denied = userComplement(this.getUsersAllowedRetrieveMediaAcl());
			assertRetrieveMediaAclForbidden(mediaLinkLocation, getSuperUser(), denied);
			
		}

	}
	
	
	
	
	public void testDenyUpdateMediaAcl() {
		
		String mediaLinkLocation = this.setupMediaResource();
		
		if (mediaLinkLocation != null) {

			// run the tests
			
			Set<String> denied = userComplement(this.getUsersAllowedUpdateMediaAcl());
			assertUpdateMediaAclForbidden(mediaLinkLocation, getSuperUser(), denied);
			
		}
		
	}
	
	
	
	
	public void testDenyRetrieveMemberHistory() {
		
		String memberLocation = this.setupMember();
		if (memberLocation != null) {
			if (this.collectionIsVersioned()) {
				Set<String> denied = userComplement(this.getUsersAllowedRetrieveMemberHistory());
				assertRetrieveMemberHistoryForbidden(memberLocation, getSuperUser(), denied);
			}
			else
				assertMemberNotVersioned(memberLocation, getSuperUser());
		}
		
	}
	
	
	
	

	public void testDenyRetrieveMemberRevision() {
		
		String memberLocation = this.setupMember();
		if (memberLocation != null) {
			if (this.collectionIsVersioned()) {
				Set<String> denied = userComplement(this.getUsersAllowedRetrieveMemberRevision());
				assertRetrieveMemberRevisionForbidden(memberLocation, getSuperUser(), denied);
			}
			else
				assertMemberNotVersioned(memberLocation, getSuperUser());
		}
		
	}
	
	
	
	public void testDenyMultiCreate() {
		
		String collectionLocation = this.setupCollection();
		if (collectionLocation != null) {
			Set<String> denied = userComplement(this.getUsersAllowedMultiCreate());
			assertMultiCreateForbidden(collectionLocation, denied);
		}
	}

	

	public static Set<String> userComplement(String[] users) {
		Set<String> complement = allUsers();
		complement.removeAll(toSet(users));
		return complement;
	}
	
	
	
	public static <T> Set<T> toSet(T[] a) {
		
		Set<T> s = new HashSet<T>();
		if (a != null) {
			for (T t : a) {
				s.add(t);
			}
		}
		return s;
		
	}
	
	
	
	
	
	
	
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
	
	
	
	
	public static Entry newDerivation() {

		Entry entry = factory.newEntry();
		Element derivationElement = factory.newElement(QNAME_DERIVATION);
		entry.setContent(derivationElement);
		entry.getContentElement().setMimeType(MEDIATYPE_MANTA);
		
		return entry;

	}
	
	
	
	
	public static Entry newPersonalDataReview() {

		Entry entry = factory.newEntry();
		Element reviewElement = factory.newElement(QNAME_REVIEW);
		entry.setContent(reviewElement);
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
		
		InputStream content = AtomSecurityTestCase.class.getClassLoader().getResourceAsStream(filePath);
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
	
	
	
	
	public static void assertUpdateCollectionForbidden(String collectionUrl, Set<String> users) {

		Feed f = newFeed("test"); 
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
	
	
	
	
	public static void assertMultiCreateForbidden(String collectionUrl, Set<String> users) {
		
		Feed f = newFeed("test multi-create"); 
		f.addEntry(newEntry("test1"));
		f.addEntry(newEntry("test2"));
		
		Map<String,Integer> results = multiCreate(collectionUrl, f, users);
		
		// verify results
		
		for (String user : users) {
			Assert.assertEquals(user, SC_FORBIDDEN, results.get(user));
		}

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
	
	

	
	public static void assertCreateMemberForbidden(String collectionUrl, Set<String> users) {
		
		Entry e = newEntry("test");
		Map<String,Integer> results = createMember(collectionUrl, e, users);
		
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
	
	
	
	
	public static void assertUpdateMemberForbidden(String location, Set<String> users) {
		
		Entry e = newEntry("test");
		Map<String,Integer> results = updateMember(location, e, users);
		 
		// verify results
		
		for (String user : users) {
			Assert.assertEquals(user, SC_FORBIDDEN, results.get(user));
		}

	}


	
	public static void assertUpdateMemberForbidden(String location, Entry e, Set<String> users) {
		
		Map<String,Integer> results = updateMember(location, e, users);
		 
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
	
	

	public static void assertDeleteMemberForbidden(String location, Set<String> users) {
		
		Map<String,Integer> results = deleteMember(location, users);
		 
		// verify results
		
		for (String user : users) {
			Assert.assertEquals(user, SC_FORBIDDEN, results.get(user));
		}

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
	
	

	
	
	public static void assertCreateMediaForbidden(String collectionUrl, Action<InputStream> streamGenerator, String contentType, Set<String> users) {
		
		Map<String,Integer> results = createMedia(collectionUrl, streamGenerator, contentType, users);
		 
		// verify results
		
		for (String user : users) {
			Assert.assertEquals(user, SC_FORBIDDEN, results.get(user));
		}

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
	
	
	

	public static Integer updateMedia(String location, InputStream content, String contentType, String user) {

		RequestOptions options = authn(user);
		options.setContentType(contentType);
		ClientResponse response = ac.put(location, content, options);
		Integer status = response.getStatus();
		response.release();
		
		return status;

	}
	

	
	
	public static Map<String,Integer> updateMedia(String location, Action<InputStream> streamGenerator, String contentType, Set<String> users) {
		
		Map<String,Integer> results = new HashMap<String,Integer>();
		
		for (String user : users) {
			results.put(user, updateMedia(location, streamGenerator.apply(), contentType, user));
		}
		
		return results;
		
	}
	
	
	

	public static void assertUpdateMediaForbidden(String location, Action<InputStream> streamGenerator, String contentType, Set<String> users) {
		
		Map<String,Integer> results = updateMedia(location, streamGenerator, contentType, users);
		 
		// verify results
		
		for (String user : users) {
			Assert.assertEquals(user, SC_FORBIDDEN, results.get(user));
		}

	}

	
	
	

	public static Integer deleteMedia(String location, String user) {

		ClientResponse r = ac.delete(location, authn(user));
		Integer status = r.getStatus();
		r.release();

		return status;

	}
	
	
	
	
	public static Map<String,Integer> deleteMedia(String location, Set<String> users) {
		
		Map<String,Integer> results = new HashMap<String,Integer>();
		
		for (String user : users) {
			results.put(user, deleteMedia(location, user));
		}
		
		return results;
		
	}
	
	

	public static void assertDeleteMediaForbidden(String location, Set<String> users) {
		
		Map<String,Integer> results = deleteMedia(location, users);
		 
		// verify results
		
		for (String user : users) {
			Assert.assertEquals(user, SC_FORBIDDEN, results.get(user));
		}

	}

		

	
	public static void assertRetrieveCollectionAclForbidden(String collectionUrl, String su, Set<String> users) {
		
		Feed f;
		String location;
		ClientResponse r;
		
		// setup test

		// need to retrieve as superuser to get security descriptor link
		r = ac.get(collectionUrl, authn(su));
		f = getFeed(r);
		location = f.getLink(REL_ATOMBEAT_SECURITY_DESCRIPTOR).getHref().toString();
		r.release();

		// run tests
		
		assertRetrieveMemberForbidden(location, users);

	}

	
	

	public static void assertUpdateCollectionAclForbidden(String collectionUrl, String su, Set<String> users) {
		
		Feed f;
		Entry s;
		String location;
		ClientResponse r;
		
		// setup test

		// need to retrieve as superuser to get security descriptor link
		r = ac.get(collectionUrl, authn(su));
		f = getFeed(r);
		location = f.getLink(REL_ATOMBEAT_SECURITY_DESCRIPTOR).getHref().toString();
		r.release();

		// run tests
		
		s = newSecurityDescriptor();
		assertUpdateMemberForbidden(location, s, users);

	}

	
	
	
	public static void assertRetrieveMemberAclForbidden(String memberLocation, String su, Set<String> users) {
		
		Entry e;
		ClientResponse r;
		
		// setup test
		
		// need to retrieve as superuser to get security descriptor link
		r = ac.get(memberLocation, authn(su));
		e = getEntry(r);
		String location = e.getLink(REL_ATOMBEAT_SECURITY_DESCRIPTOR).getHref().toString();
		r.release();
		
		// run tests
		
		assertRetrieveMemberForbidden(location, users);
		
	}
	
	
	
	public static void assertUpdateMemberAclForbidden(String memberLocation, String su, Set<String> users) {
		
		Entry e;
		ClientResponse r;
		
		// setup test
		
		// need to retrieve as superuser to get security descriptor link
		r = ac.get(memberLocation, authn(su));
		e = getEntry(r);
		String location = e.getLink(REL_ATOMBEAT_SECURITY_DESCRIPTOR).getHref().toString();
		r.release();

		// run tests
		
		Entry s = newSecurityDescriptor();
		assertUpdateMemberForbidden(location, s, users);
		
	}




	public static void assertRetrieveMediaAclForbidden(String mediaLinkLocation, String su, Set<String> users) {
		
		Entry e;
		ClientResponse r;
		
		// setup test
		
		// need to retrieve as superuser to get security descriptor link
		r = ac.get(mediaLinkLocation, authn(su));
		e = getEntry(r);
		String location = e.getLink(REL_ATOMBEAT_MEDIA_SECURITY_DESCRIPTOR).getHref().toString();
		r.release();

		// run tests
		
		assertRetrieveMemberForbidden(location, users);
		
	}
	
	
	
	public static void assertUpdateMediaAclForbidden(String mediaLinkLocation, String su, Set<String> users) {
		
		Entry e;
		ClientResponse r;
		
		// setup test
		
		// need to retrieve as superuser to get security descriptor link
		r = ac.get(mediaLinkLocation, authn(su));
		e = getEntry(r);
		String location = e.getLink(REL_ATOMBEAT_MEDIA_SECURITY_DESCRIPTOR).getHref().toString();
		r.release();

		// run tests
		
		Entry s = newSecurityDescriptor();
		assertUpdateMemberForbidden(location, s, users);
		
	}
	
	
	
	public static void assertRetrieveMemberHistoryForbidden(String memberLocation, String su, Set<String> users) {

		Entry e;
		ClientResponse r;
		
		// setup test
		
		// need to retrieve as superuser to get history link
		r = ac.get(memberLocation, authn(su));
		e = getEntry(r);
		String location = e.getLink(REL_HISTORY).getHref().toString();
		r.release();

		// run tests
		
		assertListCollectionForbidden(location, users);

	}




	public static void assertRetrieveMemberRevisionForbidden(String memberLocation, String su, Set<String> users) {

		Entry e;
		ClientResponse r;
		
		// setup test
		
		// need to retrieve as superuser to get revision link
		r = ac.get(memberLocation, authn(su));
		e = getEntry(r);
		String location = e.getLink(REL_HISTORY).getHref().toString();
		r.release();

		r = ac.get(location, authn(su));
		Feed f = getFeed(r);
		location = f.getEntries().get(0).getLink(REL_THIS_REVISION).getHref().toString();
		r.release();

		// run tests
		
		assertRetrieveMemberForbidden(location, users);

	}



	public static void assertMemberNotVersioned(String memberLocation, String su) {

		Entry e;
		ClientResponse r;
		
		// need to retrieve as superuser to get history link if any
		r = ac.get(memberLocation, authn(su));
		e = getEntry(r);
		Assert.assertNull(e.getLink(REL_HISTORY));
		r.release();

	}




	
	
}
