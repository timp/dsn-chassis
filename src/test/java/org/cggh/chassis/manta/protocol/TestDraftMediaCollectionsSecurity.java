package org.cggh.chassis.manta.protocol;

import static org.cggh.chassis.manta.protocol.ConstantsForTests.*;

import java.io.File;
import java.io.InputStream;

import org.apache.abdera.model.Entry;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.Part;

public class TestDraftMediaCollectionsSecurity extends AtomSecurityTestCase {


	
	

	@Override
	protected String setupCollection() {

		Entry draft = newDraft("Cora's Draft");
		ClientResponse r = ac.post(URL_DRAFTS_COLLECTION, draft, authn(USER_CORA));
		draft = getEntry(r);
		String collectionUrl = draft.getLink(REL_DRAFT_MEDIA).getHref().toString();
		r.release();
		
		return collectionUrl;
		
	}

	
	
	
	@Override
	protected String setupMember() {

		// the only way to create a member is as a media link entry
		
		String collectionUrl = this.setupCollection();
		Entry mediaLink = createMediaAndReturnEntry("spreadsheet1.xls", "application/vnd.ms-excel", collectionUrl, USER_CORA); 
		String location = mediaLink.getEditLink().getHref().toString();

		return location;

	}

	
	
	
	@Override
	protected String setupMediaResource() {
		
		String collectionUrl = this.setupCollection();
		Entry mediaLink = createMediaAndReturnEntry("spreadsheet1.xls", "application/vnd.ms-excel", collectionUrl, USER_CORA); 
		String location = mediaLink.getEditLink().getHref().toString();

		return location;
		
	}
	
	
	
	@Override
	protected String[] getUsersAllowedCreateMedia() {
		return new String[] { USER_CORA	};
	}
	
	

	@Override
	protected String[] getUsersAllowedCreateMember() {
		return null;
	}

	
	
	@Override
	protected String[] getUsersAllowedDeleteMedia() {
		return new String[] { USER_CORA, USER_ADAM };
	}

	
	
	@Override
	protected String[] getUsersAllowedDeleteMember() {

		// because the test member is a media link entry, this is effectively
		// delete media
		return getUsersAllowedDeleteMedia();

	}

	
	
	@Override
	protected String[] getUsersAllowedListCollection() {
		return new String[] { USER_CORA, USER_ADAM };
	}

	
	
	@Override
	protected String[] getUsersAllowedMultiCreate() {
		return null;
	}

	
	
	@Override
	protected String[] getUsersAllowedRetrieveCollectionAcl() {
		return new String[] { USER_ADAM	};
	}

	
	
	@Override
	protected String[] getUsersAllowedRetrieveMedia() {
		return new String[] { USER_CORA, USER_ADAM };
	}

	
	
	@Override
	protected String[] getUsersAllowedRetrieveMediaAcl() {
		return new String[] { USER_ADAM	};
	}

	
	
	@Override
	protected String[] getUsersAllowedRetrieveMember() {
		return new String[] { USER_CORA, USER_ADAM };
	}

	
	
	@Override
	protected String[] getUsersAllowedRetrieveMemberAcl() {
		return new String[] { USER_ADAM	};
	}

	
	
	@Override
	protected String[] getUsersAllowedRetrieveMemberHistory() {
		return null; // collection not versioned
	}

	
	
	@Override
	protected String[] getUsersAllowedRetrieveMemberRevision() {
		return null; // collection not versioned
	}

	
	
	@Override
	protected String[] getUsersAllowedUpdateCollection() {
		return new String[] { USER_ADAM	};
	}

	
	
	@Override
	protected String[] getUsersAllowedUpdateCollectionAcl() {
		return new String[] { USER_ADAM	};
	}

	
	
	@Override
	protected String[] getUsersAllowedUpdateMedia() {
		return null;
	}

	
	
	@Override
	protected String[] getUsersAllowedUpdateMediaAcl() {
		return new String[] { USER_ADAM	};
	}

	
	
	@Override
	protected String[] getUsersAllowedUpdateMember() {
		return new String[] { USER_CORA, USER_ADAM };
	}

	
	
	@Override
	protected String[] getUsersAllowedUpdateMemberAcl() {
		return new String[] { USER_ADAM };
	}

	
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	

	public void testAllowCreatorUserListCollection() {
		
		ClientResponse r;
		String collectionUrl = this.setupCollection();
		
		r = ac.get(collectionUrl, authn(USER_CORA));
		assertEquals(200, r.getStatus());
		r.release();

	}
	
	
	
	
	public void testAllowCreatorUserCreateMedia() {
		
		Integer result;
		String collectionUrl = this.setupCollection();
		
		// verify create media via Atom POST
		
		InputStream content = this.getClass().getClassLoader().getResourceAsStream("spreadsheet1.xls");
		result = createMedia(collectionUrl, content, "application/vnd.ms-excel", USER_CORA);
		assertEquals(SC_CREATED, result);
		
		// verify create media via multipart/form-data

		PostMethod post = new PostMethod(collectionUrl);
		File file = new File(this.getClass().getClassLoader().getResource("spreadsheet1.xls").getFile());
		FilePart fp = createFilePart(file, "spreadsheet1.xls", "application/vnd.ms-excel", "media");
		Part[] parts = { fp };
		setMultipartRequestEntity(post, parts);
		result = executeMethod(post, USER_CORA);
		assertEquals(SC_CREATED, result);

	}
	
	
	
	
	public void testAllowCreatorUserRetrieveMedia() {
		
		// setup test
		
		String collectionUrl = this.setupCollection();
		Entry mediaLink = createMediaAndReturnEntry("spreadsheet1.xls", "application/vnd.ms-excel", collectionUrl, USER_CORA); 
		String location = mediaLink.getEditMediaLink().getHref().toString();
		
		// verify retrieve media
		
		ClientResponse r = ac.get(location, authn(USER_CORA));
		assertEquals(200, r.getStatus());
		r.release();
		
	}
	
	
	

	public void testAllowCreatorUserDeleteMedia() {

		// setup test
		
		String collectionUrl = this.setupCollection();
		Entry mediaLink = createMediaAndReturnEntry("spreadsheet1.xls", "application/vnd.ms-excel", collectionUrl, USER_CORA); 
		String location = mediaLink.getEditMediaLink().getHref().toString();
		
		// verify delete media
		
		ClientResponse r = ac.delete(location, authn(USER_CORA));
		assertEquals(204, r.getStatus());
		r.release();

	}
	
	
	
	
	public void testAllowCreatorUserRetrieveMember() {

		// setup test
		
		String collectionUrl = this.setupCollection();
		Entry mediaLink = createMediaAndReturnEntry("spreadsheet1.xls", "application/vnd.ms-excel", collectionUrl, USER_CORA); 
		String location = mediaLink.getEditLink().getHref().toString();
		
		// verify retrieve member
		
		ClientResponse r = ac.get(location, authn(USER_CORA));
		assertEquals(200, r.getStatus());
		r.release();

	}
	
	
	
	
	public void testAllowCreatorUserUpdateMember() {

		// setup test
		
		String collectionUrl = this.setupCollection();
		Entry mediaLink = createMediaAndReturnEntry("spreadsheet1.xls", "application/vnd.ms-excel", collectionUrl, USER_CORA); 
		String location = mediaLink.getEditLink().getHref().toString();
		
		// verify update member
		
		mediaLink.setTitle("foobar");
		ClientResponse r = ac.put(location, mediaLink, authn(USER_CORA));
		assertEquals(200, r.getStatus());
		r.release();

	}




	@Override
	protected boolean collectionIsVersioned() {
		return false;
	}




	@Override
	protected String getSuperUser() {
		return USER_ADAM;
	}
	
	
	

}
