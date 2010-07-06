package org.cggh.chassis.manta.protocol;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.abdera.model.Category;
import org.apache.abdera.model.Content;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Element;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Link;
import org.apache.abdera.parser.ParseException;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.abdera.protocol.client.RequestOptions;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

import junit.framework.TestCase;
import static org.cggh.chassis.manta.protocol.ConstantsForTests.*;
import static org.cggh.chassis.manta.protocol.UtilsForTests.*;

public class TestContributorReturnsToSubmitMoreFiles extends TestCase {

	
	
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	
	
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
	
	
	public void testListStudies() {
		
		// create a couple of studies
		createStudy("My First Study", USER_CORA);
		createStudy("My Second Study", USER_CORA);
		
		// list the studies
		ClientResponse response = ac.get(URL_STUDIES_COLLECTION, authn(USER_CORA));
		assertEquals(200, response.getStatus());
		Feed feed = getFeed(response); assertNotNull(feed);
		assertTrue(feed.getEntries().size() >= 2); // could be more if other tests run first
		response.release();
		
		// check feed entries include security descriptor inline, so we can display administrators in table
		Entry first = feed.getEntries().get(0);
		Link securityDescriptorLink = first.getLink(REL_ATOMBEAT_SECURITY_DESCRIPTOR); assertNotNull(securityDescriptorLink);
		List<Element> inlineElements = securityDescriptorLink.getExtensions(QNAME_AE_INLINE); assertEquals(1, inlineElements.size());
		Element inline = inlineElements.get(0); assertEquals(1, inline.getElements().size());
		Entry inlineEntry = inline.getFirstChild(); assertNotNull(inlineEntry);
		Content descriptorContent = inlineEntry.getContentElement(); assertNotNull(descriptorContent);
		assertEquals(MEDIATYPE_ATOMBEAT, descriptorContent.getMimeType().toString());
		assertEquals(1, descriptorContent.getElements().size());
		
	}
	
	
	
	public void testRetrieveStudyAndRelatedForDashboard() {
		
		ClientResponse response;
		Feed studiesFeed, submittedMediaFeed;
		Entry studyEntry, securityDescriptorEntry, studyInfoEntry;
		Link editLink, securityDescriptorLink, studyInfoLink, submittedMediaLink;
		
		// create a study, to ensure there is at least one
		createStudy("My First Study", USER_CORA);

		// list studies
		response = ac.get(URL_STUDIES_COLLECTION, authn(USER_CORA)); assertEquals(200, response.getStatus());
		studiesFeed = getFeed(response); assertNotNull(studiesFeed); assertTrue(studiesFeed.getEntries().size()>0);
		studyEntry = studiesFeed.getEntries().get(0);
		editLink = studyEntry.getEditLink(); assertNotNull(editLink);
		response.release();
		
		// retrieve first study in list
		response = ac.get(editLink.getHref().toString(), authn(USER_CORA)); assertEquals(200, response.getStatus());
		studyEntry = getEntry(response); assertNotNull(studyEntry);
		securityDescriptorLink = studyEntry.getLink(REL_ATOMBEAT_SECURITY_DESCRIPTOR); assertNotNull(securityDescriptorLink);
		studyInfoLink = studyEntry.getLink(REL_STUDY_INFO); assertNotNull(studyInfoLink);
		submittedMediaLink = studyEntry.getLink(REL_SUBMITTED_MEDIA); assertNotNull(submittedMediaLink);
		response.release();
		
		// retrieve security descriptor
		response = ac.get(securityDescriptorLink.getHref().toString(), authn(USER_CORA)); assertEquals(200, response.getStatus());
		securityDescriptorEntry = getEntry(response); assertNotNull(securityDescriptorEntry);
		response.release();
		
		// retrieve submitted media 
		response = ac.get(submittedMediaLink.getHref().toString(), authn(USER_CORA)); assertEquals(200, response.getStatus());
		submittedMediaFeed = getFeed(response); assertNotNull(submittedMediaFeed);
		response.release();
		
		// retrieve study info
		response = ac.get(studyInfoLink.getHref().toString(), authn(USER_CORA)); assertEquals(200, response.getStatus());
		studyInfoEntry = getEntry(response); assertNotNull(studyInfoEntry);
		response.release();

	}
	
	
	
	
	public void testSubmitMoreFiles() throws Exception {
		
		ClientResponse response;
		Feed submittedMediaFeed;
		Entry studyEntry, mediaLinkEntry;
		Link editLink, editMediaLink, submittedMediaLink;
		String submittedMediaCollectionUrl;
		int submittedMedia;
		Document<Entry> responseDoc;
		
		// create a study, to ensure there is at least one
		studyEntry = createStudy("My First Study", USER_CORA);
		submittedMediaLink = studyEntry.getLink(REL_SUBMITTED_MEDIA); assertNotNull(submittedMediaLink);
		submittedMediaCollectionUrl = submittedMediaLink.getHref().toString();

		// retrieve submitted media feed
		response = ac.get(submittedMediaCollectionUrl, authn(USER_CORA)); assertEquals(200, response.getStatus());
		submittedMediaFeed = getFeed(response); assertNotNull(submittedMediaFeed);
		submittedMedia = submittedMediaFeed.getEntries().size();
		response.release();
		
		// upload a file via Atom POST
		InputStream content = this.getClass().getClassLoader().getResourceAsStream("spreadsheet1.xls");
		RequestOptions options = authn(USER_CORA);
		options.setContentType("application/vnd.ms-excel");
		response = ac.post(submittedMediaCollectionUrl, content, options);
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
		
		// upload a file via multipart/form-data
		PostMethod post = new PostMethod(submittedMediaCollectionUrl);
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
		
		// retrieve submitted media again, check feed size
		response = ac.get(submittedMediaCollectionUrl, authn(USER_CORA)); assertEquals(200, response.getStatus());
		submittedMediaFeed = getFeed(response); assertNotNull(submittedMediaFeed);
		assertEquals(submittedMedia+2, submittedMediaFeed.getEntries().size());
		response.release();
		
	}
	
	
	
}
