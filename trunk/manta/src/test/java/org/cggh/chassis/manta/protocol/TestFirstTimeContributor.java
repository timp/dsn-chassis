package org.cggh.chassis.manta.protocol;

import java.io.File;
import java.io.InputStream;

import org.apache.abdera.i18n.iri.IRI;
import org.apache.abdera.model.Category;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Link;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.abdera.protocol.client.RequestOptions;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

import junit.framework.TestCase;
import static org.cggh.chassis.manta.protocol.ConstantsForTests.*;
import static org.cggh.chassis.manta.protocol.UtilsForTests.*;




public class TestFirstTimeContributor extends TestCase {

	
	
	
	// N.B. you need to call response.release() between requests otherwise
	// the tests will hang!
	
	
	
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	
	
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	
	
	public void testCreateAndUpdateDraft() {
		
		String user = USER_CORA; // contributor
		Entry draftEntry;
		ClientResponse response;
		IRI location;
		
		// create a draft
		draftEntry = newDraft("foobar");
		response = abderaClient.post(URL_DRAFTS_COLLECTION, draftEntry, authn(user));
		assertEquals(201, response.getStatus());
		location = response.getLocation(); assertNotNull(location);
		draftEntry = getEntry(response); assertNotNull(draftEntry);
		assertNotNull(draftEntry.getEditLink());
		assertEquals(location.toString(), draftEntry.getEditLink().getHref().toString()); // check you could use either
		response.release();

		// update the draft
		draftEntry.setTitle("barfoo");
		response = abderaClient.put(location.toString(), draftEntry, authn(user));
		assertEquals(200, response.getStatus());
		draftEntry = getEntry(response);
		assertEquals("barfoo", draftEntry.getTitle());
		response.release();
		
	}
	
	
	
	
	public void testUploadDraftFilesViaAtom() {
		
		String user = USER_CORA; // contributor
		Entry draftEntry, mediaLinkEntry;
		ClientResponse response;
		Link draftMediaLink, editLink, editMediaLink;
		
		// create a draft
		draftEntry = newDraft("foobar");
		response = abderaClient.post(URL_DRAFTS_COLLECTION, draftEntry, authn(user));
		assertEquals(201, response.getStatus());
		draftEntry = getEntry(response);
		draftMediaLink = draftEntry.getLink(REL_DRAFTMEDIA); assertNotNull(draftMediaLink);
		response.release();
		
		// upload a file via Atom POST
		InputStream content = this.getClass().getClassLoader().getResourceAsStream("spreadsheet1.xls");
		RequestOptions options = authn(user);
		options.setContentType("application/vnd.ms-excel");
		response = abderaClient.post(draftMediaLink.getHref().toString(), content, options);
		assertEquals(201, response.getStatus());
		mediaLinkEntry = getEntry(response); assertNotNull(mediaLinkEntry);
		editLink = mediaLinkEntry.getEditLink(); assertNotNull(editLink);
		editMediaLink = mediaLinkEntry.getEditMediaLink(); assertNotNull(editMediaLink);
		response.release();
		
		// check user can update file metadata
		assertFalse(mediaLinkEntry.getTitle().equals("testing.xls"));
		mediaLinkEntry.setTitle("testing.xls");
		response = abderaClient.put(editLink.getHref().toString(), mediaLinkEntry, authn(user));
		assertEquals(200, response.getStatus());
		mediaLinkEntry = getEntry(response); assertNotNull(mediaLinkEntry);
		assertEquals("testing.xls", mediaLinkEntry.getTitle());
		response.release();
		
	}




	public void testUploadDraftFilesViaMultipartFormdata() throws Exception {
		
		String user = USER_CORA; // contributor
		Entry draftEntry, mediaLinkEntry;
		ClientResponse response;
		Document<Entry> responseDoc;
		Link draftMediaLink, editLink, editMediaLink;
		
		// create a draft
		draftEntry = newDraft("foobar");
		response = abderaClient.post(URL_DRAFTS_COLLECTION, draftEntry, authn(user));
		assertEquals(201, response.getStatus());
		draftEntry = getEntry(response);
		draftMediaLink = draftEntry.getLink(REL_DRAFTMEDIA); assertNotNull(draftMediaLink);
		response.release();
		
		// upload a file via multipart/form-data (need to use httpclient library directly)
		PostMethod post = new PostMethod(draftMediaLink.getHref().toString());
		File file = new File(this.getClass().getClassLoader().getResource("spreadsheet1.xls").getFile());
		FilePart fp = createFilePart(file, "spreadsheet1.xls", "application/vnd.ms-excel", "media");
		StringPart sp1 = new StringPart("summary", "this is a great spreadsheet");
		StringPart sp2 = new StringPart("category", "scheme=\"foo\"; term=\"bar\"; label=\"baz\"");
		Part[] parts = { fp , sp1 , sp2 };
		setMultipartRequestEntity(post, parts);
		int result = executeMethod(post, user);
		assertEquals(201, result);
		responseDoc = abdera.getParser().parse(post.getResponseBodyAsStream()); assertNotNull(responseDoc);
		mediaLinkEntry = responseDoc.getRoot(); assertNotNull(mediaLinkEntry);
		editLink = mediaLinkEntry.getEditLink(); assertNotNull(editLink);
		editMediaLink = mediaLinkEntry.getEditMediaLink(); assertNotNull(editMediaLink);
		assertEquals("spreadsheet1.xls", mediaLinkEntry.getTitle());
		assertEquals("this is a great spreadsheet", mediaLinkEntry.getSummary());
		assertEquals(1, mediaLinkEntry.getCategories().size());
		Category c = mediaLinkEntry.getCategories().get(0); assertNotNull(c);
		assertEquals("foo", c.getScheme().toString());
		assertEquals("bar", c.getTerm());
		assertEquals("baz", c.getLabel());
		
	}
	
	
	
	public void testCreateStudy() {

		String user = USER_CORA; // contributor
		Entry studyEntry;
		ClientResponse response;
		IRI location;
		
		// create a study
		studyEntry = newStudy("My First Study");
		response = abderaClient.post(URL_STUDIES_COLLECTION, studyEntry, authn(user));
		assertEquals(201, response.getStatus());
		location = response.getLocation(); assertNotNull(location);
		studyEntry = getEntry(response); assertNotNull(studyEntry);
		assertNotNull(studyEntry.getEditLink());
		assertEquals(location.toString(), studyEntry.getEditLink().getHref().toString()); // check you could use either
		assertEquals("My First Study", studyEntry.getTitle());
		response.release();

	}

	
	
	
	public void testUpdateStudyAcl() {

		String user = USER_CORA; // contributor
		Entry studyEntry;
		ClientResponse response;
		Link securityDescriptorLink;
		
		// create a study
		studyEntry = newStudy("My First Study");
		response = abderaClient.post(URL_STUDIES_COLLECTION, studyEntry, authn(user));
		assertEquals(201, response.getStatus());
		studyEntry = getEntry(response); assertNotNull(studyEntry);
		securityDescriptorLink = studyEntry.getLink("http://purl.org/atombeat/rel/security-descriptor"); assertNotNull(securityDescriptorLink);
		response.release();
		
		// update security descriptor
		

		fail("TODO");
	}



	
	public void testSubmitDraftFiles() {
		fail("TODO");
	}


	
	
	public void testUpdateStudyInfo() {
		fail("TODO");
	}
	

	
	
}
