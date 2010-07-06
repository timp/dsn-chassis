package org.cggh.chassis.manta.protocol;

import java.util.List;

import org.apache.abdera.i18n.iri.IRI;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Link;
import org.apache.abdera.protocol.client.ClientResponse;

import junit.framework.TestCase;
import static org.cggh.chassis.manta.protocol.ConstantsForTests.*;
import static org.cggh.chassis.manta.protocol.UtilsForTests.*;

public class TestContributorBreaksDuringStudyRegistration extends TestCase {

	
	
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	
	
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
	
	public void testContributorBreaksDuringStudyRegistration() {

		Entry draftEntry;
		Feed draftsFeed;
		List<Entry> drafts;
		ClientResponse response;
		IRI location;
		
		String title = Double.toString(Math.random());
		
		// create a draft
		draftEntry = newDraft(title);
		response = ac.post(URL_DRAFTS_COLLECTION, draftEntry, authn(USER_CORA));
		assertEquals(201, response.getStatus());
		location = response.getLocation(); assertNotNull(location);
		draftEntry = getEntry(response); assertNotNull(draftEntry);
		assertNotNull(draftEntry.getEditLink());
		response.release();

		// break, come back and list the drafts
		response = ac.get(URL_DRAFTS_COLLECTION, authn(USER_CORA));
		assertEquals(200, response.getStatus());
		draftsFeed = getFeed(response);
		drafts = draftsFeed.getEntries();
		assertTrue(drafts.size()>=1);
		draftEntry = null;
		for (Entry d : drafts) {
			if (title.equals(d.getTitle())) {
				draftEntry = d;
			}
		}
		assertNotNull(draftEntry);
		response.release();
		
		// update the draft again
		draftEntry.setTitle("proper title");
		response = ac.put(draftEntry.getEditLink().getHref().toString(), draftEntry, authn(USER_CORA));
		assertEquals(200, response.getStatus());
		draftEntry = getEntry(response);
		assertEquals("proper title", draftEntry.getTitle());
		response.release();

	}
	
	
	
}
