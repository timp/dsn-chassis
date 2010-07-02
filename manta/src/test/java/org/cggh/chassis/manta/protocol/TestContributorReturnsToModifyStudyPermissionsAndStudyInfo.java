package org.cggh.chassis.manta.protocol;

import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Link;
import org.apache.abdera.protocol.client.ClientResponse;

import junit.framework.TestCase;
import static org.cggh.chassis.manta.protocol.ConstantsForTests.*;
import static org.cggh.chassis.manta.protocol.UtilsForTests.*;

public class TestContributorReturnsToModifyStudyPermissionsAndStudyInfo extends TestCase {

	
	
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	
	
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
	
	
	public void testUpdateStudy() {

		Entry studyEntry, studyInfoEntry, securityDescriptorEntry;
		Link editLink, securityDescriptorLink, studyInfoLink;
		ClientResponse response;
		
		// create a study, to ensure there is at least one
		studyEntry = createStudy("My First Study", USER_CORA);
		editLink = studyEntry.getEditLink();
		securityDescriptorLink = studyEntry.getLink(REL_ATOMBEAT_SECURITY_DESCRIPTOR);
		studyInfoLink = studyEntry.getLink(REL_STUDY_INFO);
		assertEquals("My First Study", studyEntry.getTitle());
		
		// modify study
		studyEntry.setTitle("Study Modified");
		response = abderaClient.put(editLink.getHref().toString(), studyEntry, authn(USER_CORA));
		assertEquals(200, response.getStatus());
		studyEntry = getEntry(response);
		assertEquals("Study Modified", studyEntry.getTitle());
		response.release();
		
		// modify study info
		studyInfoEntry = newStudyInfo();
		response = abderaClient.put(studyInfoLink.getHref().toString(), studyInfoEntry, authn(USER_CORA));
		assertEquals(200, response.getStatus());
		response.release();
		
		// modify security descriptor
		securityDescriptorEntry = newSecurityDescriptor();
		response = abderaClient.put(securityDescriptorLink.getHref().toString(), securityDescriptorEntry, authn(USER_CORA));
		assertEquals(200, response.getStatus());
		response.release();
		
	}
}
