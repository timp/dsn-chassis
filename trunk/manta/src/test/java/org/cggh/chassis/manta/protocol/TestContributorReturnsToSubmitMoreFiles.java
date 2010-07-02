package org.cggh.chassis.manta.protocol;

import java.util.List;

import org.apache.abdera.model.Content;
import org.apache.abdera.model.Element;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Link;
import org.apache.abdera.protocol.client.ClientResponse;

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
		ClientResponse response = abderaClient.get(URL_STUDIES_COLLECTION, authn(USER_CORA));
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
		response = abderaClient.get(URL_STUDIES_COLLECTION, authn(USER_CORA)); assertEquals(200, response.getStatus());
		studiesFeed = getFeed(response); assertNotNull(studiesFeed); assertTrue(studiesFeed.getEntries().size()>0);
		studyEntry = studiesFeed.getEntries().get(0);
		editLink = studyEntry.getEditLink(); assertNotNull(editLink);
		response.release();
		
		// retrieve first study in list
		response = abderaClient.get(editLink.getHref().toString(), authn(USER_CORA)); assertEquals(200, response.getStatus());
		studyEntry = getEntry(response); assertNotNull(studyEntry);
		securityDescriptorLink = studyEntry.getLink(REL_ATOMBEAT_SECURITY_DESCRIPTOR); assertNotNull(securityDescriptorLink);
		studyInfoLink = studyEntry.getLink(REL_STUDY_INFO); assertNotNull(studyInfoLink);
		submittedMediaLink = studyEntry.getLink(REL_SUBMITTED_MEDIA); assertNotNull(submittedMediaLink);
		response.release();
		
		// retrieve security descriptor
		response = abderaClient.get(securityDescriptorLink.getHref().toString(), authn(USER_CORA)); assertEquals(200, response.getStatus());
		securityDescriptorEntry = getEntry(response); assertNotNull(securityDescriptorEntry);
		response.release();
		
		// retrieve submitted media 
		response = abderaClient.get(submittedMediaLink.getHref().toString(), authn(USER_CORA)); assertEquals(200, response.getStatus());
		submittedMediaFeed = getFeed(response); assertNotNull(submittedMediaFeed);
		response.release();
		
		// retrieve study info
		response = abderaClient.get(studyInfoLink.getHref().toString(), authn(USER_CORA)); assertEquals(200, response.getStatus());
		studyInfoEntry = getEntry(response); assertNotNull(studyInfoEntry);
		response.release();

	}
	
	
	
	
	
}
