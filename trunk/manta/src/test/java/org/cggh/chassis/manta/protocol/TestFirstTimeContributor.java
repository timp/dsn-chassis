package org.cggh.chassis.manta.protocol;

import java.io.File;
import java.io.InputStream;

import org.apache.abdera.i18n.iri.IRI;
import org.apache.abdera.model.Category;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
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
		
		Entry draftEntry;
		ClientResponse response;
		IRI location;
		
		// create a draft
		draftEntry = newDraft("foobar");
		response = ac.post(URL_DRAFTS_COLLECTION, draftEntry, authn(USER_CORA));
		assertEquals(201, response.getStatus());
		location = response.getLocation(); assertNotNull(location);
		draftEntry = getEntry(response); assertNotNull(draftEntry);
		assertNotNull(draftEntry.getEditLink());
		assertEquals(location.toString(), draftEntry.getEditLink().getHref().toString()); // check you could use either
		response.release();

		// update the draft
		draftEntry.setTitle("barfoo");
		response = ac.put(location.toString(), draftEntry, authn(USER_CORA));
		assertEquals(200, response.getStatus());
		draftEntry = getEntry(response);
		assertEquals("barfoo", draftEntry.getTitle());
		response.release();
		
	}
	
	
	
	
	public void testUploadDraftFilesViaAtom() {
		
		Entry draftEntry, mediaLinkEntry;
		ClientResponse response;
		Link draftMediaLink, editLink, editMediaLink;
		
		// create a draft
		draftEntry = createDraft("foobar", USER_CORA);
		draftMediaLink = draftEntry.getLink(REL_DRAFT_MEDIA); assertNotNull(draftMediaLink);
		
		// upload a file via Atom POST
		InputStream content = this.getClass().getClassLoader().getResourceAsStream("spreadsheet1.xls");
		RequestOptions options = authn(USER_CORA);
		options.setContentType("application/vnd.ms-excel");
		response = ac.post(draftMediaLink.getHref().toString(), content, options);
		assertEquals(201, response.getStatus());
		mediaLinkEntry = getEntry(response); assertNotNull(mediaLinkEntry);
		editLink = mediaLinkEntry.getEditLink(); assertNotNull(editLink);
		editMediaLink = mediaLinkEntry.getEditMediaLink(); assertNotNull(editMediaLink);
		response.release();
		
		// check user can update file metadata
		assertFalse(mediaLinkEntry.getTitle().equals("testing.xls"));
		mediaLinkEntry.setTitle("testing.xls");
		response = ac.put(editLink.getHref().toString(), mediaLinkEntry, authn(USER_CORA));
		assertEquals(200, response.getStatus());
		mediaLinkEntry = getEntry(response); assertNotNull(mediaLinkEntry);
		assertEquals("testing.xls", mediaLinkEntry.getTitle());
		response.release();
		
	}




	public void testUploadDraftFilesViaMultipartFormdata() throws Exception {
		
		Entry draftEntry, mediaLinkEntry;
		Document<Entry> responseDoc;
		Link draftMediaLink, editLink, editMediaLink;
		
		// create a draft
		draftEntry = createDraft("foobar", USER_CORA);
		draftMediaLink = draftEntry.getLink(REL_DRAFT_MEDIA); assertNotNull(draftMediaLink);
		
		// upload a file via multipart/form-data (need to use httpclient library directly)
		PostMethod post = new PostMethod(draftMediaLink.getHref().toString());
		File file = new File(this.getClass().getClassLoader().getResource("spreadsheet1.xls").getFile());
		FilePart fp = createFilePart(file, "spreadsheet1.xls", "application/vnd.ms-excel", "media");
		StringPart sp1 = new StringPart("summary", "this is a great spreadsheet");
		StringPart sp2 = new StringPart("category", "scheme=\"foo\"; term=\"bar\"; label=\"baz\"");
		Part[] parts = { fp , sp1 , sp2 };
		setMultipartRequestEntity(post, parts);
		int result = executeMethod(post, USER_CORA);
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

		Entry studyEntry;
		ClientResponse response;
		IRI location;
		
		// create a study
		studyEntry = newStudy("My First Study");
		response = ac.post(URL_STUDIES_COLLECTION, studyEntry, authn(USER_CORA));
		assertEquals(201, response.getStatus());
		location = response.getLocation(); assertNotNull(location);
		studyEntry = getEntry(response); assertNotNull(studyEntry);
		assertNotNull(studyEntry.getEditLink());
		assertEquals(location.toString(), studyEntry.getEditLink().getHref().toString()); // check you could use either
		assertEquals("My First Study", studyEntry.getTitle());
		response.release();

	}

	
	
	
	public void testUpdateStudyAcl() {

		Entry studyEntry, securityDescriptorEntry;
		ClientResponse response;
		Link securityDescriptorLink;
		
		// create a study
		studyEntry = createStudy("My First Study", USER_CORA);
		securityDescriptorLink = studyEntry.getLink(REL_ATOMBEAT_SECURITY_DESCRIPTOR); assertNotNull(securityDescriptorLink);
		
		// update security descriptor
		securityDescriptorEntry = newSecurityDescriptor();
		response = ac.put(securityDescriptorLink.getHref().toString(), securityDescriptorEntry, authn(USER_CORA));
		assertEquals(200, response.getStatus());
		response.release();
		
	}



	
	public void testSubmitDraftFiles() {
		
		// check that we can list the draft media collection and post the feed
		// to the submitted media collection

		Entry draftEntry, studyEntry;
		Feed draftMediaFeed, submittedMediaFeed;
		ClientResponse response;
		Link draftMediaLink, submittedMediaLink;
		String draftMediaCollectionUrl;

		// create a draft
		draftEntry = createDraft("foobar", USER_CORA);
		draftMediaLink = draftEntry.getLink(REL_DRAFT_MEDIA); assertNotNull(draftMediaLink);
		draftMediaCollectionUrl = draftMediaLink.getHref().toString(); assertNotNull(draftMediaCollectionUrl);
		
		// upload a couple of draft files via Atom POST
		createMediaAndReturnEntry("spreadsheet1.xls", "application/vdn.ms-excel", draftMediaCollectionUrl, USER_CORA);
		createMediaAndReturnEntry("spreadsheet1.xls", "application/vdn.ms-excel", draftMediaCollectionUrl, USER_CORA);
		
		// list the draft media collection
		response = ac.get(draftMediaCollectionUrl, authn(USER_CORA));
		draftMediaFeed = getFeed(response);
		assertEquals(2, draftMediaFeed.getEntries().size());
		response.release();
		
		// create a study
		studyEntry = createStudy("My First Study", USER_CORA);
		submittedMediaLink = studyEntry.getLink(REL_SUBMITTED_MEDIA); assertNotNull(submittedMediaLink);
		
		// now POST the draft media feed to the submitted media collection to copy uploads across
		response = ac.post(submittedMediaLink.getHref().toString(), draftMediaFeed, authn(USER_CORA));
		assertEquals(200, response.getStatus());
		submittedMediaFeed = getFeed(response); assertNotNull(submittedMediaFeed);
		assertEquals(2, submittedMediaFeed.getEntries().size());
		response.release();
		
	}


	
	
	public void testUpdateStudyInfo() {

		Entry studyEntry, studyInfoEntry;
		ClientResponse response;
		Link studyInfoLink;
		String studyInfoMemberUrl;

		// create a study
		studyEntry = createStudy("My First Study", USER_CORA);
		studyInfoLink = studyEntry.getLink(REL_STUDY_INFO); assertNotNull(studyInfoLink);
		studyInfoMemberUrl = studyInfoLink.getHref().toString();
		
		// retrieve study info
		response = ac.get(studyInfoMemberUrl, authn(USER_CORA));
		assertEquals(200, response.getStatus());
		studyInfoEntry = getEntry(response); assertNotNull(studyInfoEntry);
		response.release();
		
		// update study info
		assertFalse("foobar".equals(studyInfoEntry.getTitle()));
		studyInfoEntry.setTitle("foobar");
		response = ac.put(studyInfoMemberUrl, studyInfoEntry, authn(USER_CORA));
		assertEquals(200, response.getStatus());
		studyInfoEntry = getEntry(response);
		assertEquals("foobar", studyInfoEntry.getTitle());
		response.release();

	}
	

	
	
}
