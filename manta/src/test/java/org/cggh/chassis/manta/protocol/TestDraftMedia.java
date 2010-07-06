package org.cggh.chassis.manta.protocol;



import java.io.File;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.Part;

import junit.framework.TestCase;
import static org.cggh.chassis.manta.protocol.ConstantsForTests.*;
import static org.cggh.chassis.manta.protocol.UtilsForTests.*;




/**
 * This test case verifies the security model for any of the /media/draft/* collections. 
 * For the definition of the security model, see http://code.google.com/p/dsn-chassis/wiki/MantaSecurityModel
 * 
 * @author aliman
 *
 */
public class TestDraftMedia extends TestCase {

	
	
	
	// N.B. you need to call response.release() between requests otherwise
	// the tests will hang!
	
	
	
	
	Entry draft;
	String collectionUrl;
	
	
	
	protected void setUp() throws Exception {
		super.setUp();

		draft = newDraft("test");
		ClientResponse r = ac.post(URL_DRAFTS_COLLECTION, draft, authn(USER_CORA));
		draft = getEntry(r);
		collectionUrl = draft.getLink(REL_DRAFT_MEDIA).getHref().toString();
		r.release();

	}

	
	
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	
	
	public void test_allow_creator_list_collection() {
		
		ClientResponse r;
		
		r = ac.get(collectionUrl, authn(USER_CORA));
		assertEquals(200, r.getStatus());
		r.release();

	}
	
	
	
	
	public void test_allow_creator_create_media() {
		
		Integer result;
		
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
	
	
	
	
	public void test_allow_creator_retrieve_media() {
		
		// setup test
		
		Entry mediaLink = createMediaAndReturnEntry("spreadsheet1.xls", "application/vnd.ms-excel", collectionUrl, USER_CORA); 
		String location = mediaLink.getEditMediaLink().getHref().toString();
		
		// verify retrieve media
		
		ClientResponse r = ac.get(location, authn(USER_CORA));
		assertEquals(200, r.getStatus());
		r.release();
		
	}
	
	
	

	public void test_allow_creator_delete_media() {

		// setup test
		
		Entry mediaLink = createMediaAndReturnEntry("spreadsheet1.xls", "application/vnd.ms-excel", collectionUrl, USER_CORA); 
		String location = mediaLink.getEditMediaLink().getHref().toString();
		
		// verify delete media
		
		ClientResponse r = ac.delete(location, authn(USER_CORA));
		assertEquals(204, r.getStatus());
		r.release();

	}
	
	
	
	
	public void test_allow_creator_retrieve_member() {

		// setup test
		
		Entry mediaLink = createMediaAndReturnEntry("spreadsheet1.xls", "application/vnd.ms-excel", collectionUrl, USER_CORA); 
		String location = mediaLink.getEditLink().getHref().toString();
		
		// verify retrieve member
		
		ClientResponse r = ac.get(location, authn(USER_CORA));
		assertEquals(200, r.getStatus());
		r.release();

	}
	
	
	
	
	public void test_allow_creator_update_member() {

		// setup test
		
		Entry mediaLink = createMediaAndReturnEntry("spreadsheet1.xls", "application/vnd.ms-excel", collectionUrl, USER_CORA); 
		String location = mediaLink.getEditLink().getHref().toString();
		
		// verify update member
		
		mediaLink.setTitle("foobar");
		ClientResponse r = ac.put(location, mediaLink, authn(USER_CORA));
		assertEquals(200, r.getStatus());
		r.release();

	}
	
	
	
	
	
	public void test_deny_list_collection() {

		// define expectations
		
		Set<String> allowed = new HashSet<String>();
		allowed.add(USER_CORA); allowed.add(USER_ADAM); 
		
		Set<String> denied = allUsers();
		denied.removeAll(allowed);
		
		// run tests
		
		assertListCollectionForbidden(collectionUrl, denied);
		
	}
	
	
	
	
	public void test_deny_update_collection() {

		// define expectations
		
		Set<String> allowed = new HashSet<String>();
		allowed.add(USER_ADAM); 
		
		Set<String> denied = allUsers();
		denied.removeAll(allowed);
		
		// run tests
		
		Feed f = newFeed("test");
		assertUpdateCollectionForbidden(collectionUrl, f, denied);

	}
	
	
	
	
	public void test_deny_retrieve_collection_acl() {

		// define expectations
		
		Set<String> allowed = new HashSet<String>();
		allowed.add(USER_ADAM); 
		
		Set<String> denied = allUsers();
		denied.removeAll(allowed);

		// run tests
		
		assertRetrieveCollectionAclForbidden(collectionUrl, denied);
		
	}
	
	
	
	
	public void test_deny_update_collection_acl() {

		// define expectations
		
		Set<String> allowed = new HashSet<String>();
		allowed.add(USER_ADAM); 
		
		Set<String> denied = allUsers();
		denied.removeAll(allowed);

		// run tests
		
		assertUpdateCollectionAclForbidden(collectionUrl, denied);

	}
	
	
	
	
	public void test_deny_create_member() {

		// define expectations
		
		Set<String> denied = allUsers();

		// run tests
		
		assertCreateMemberForbidden(collectionUrl, newEntry("test"), denied);
		
	}
	
	

	public void test_deny_retrieve_member() {
		
		// setup test

		Entry mediaLink = createMediaAndReturnEntry("spreadsheet1.xls", "application/vnd.ms-excel", collectionUrl, USER_CORA); 
		String location = mediaLink.getEditLink().getHref().toString();

		// define expectations
		
		Set<String> allowed = new HashSet<String>();
		allowed.add(USER_ADAM); allowed.add(USER_CORA);
		
		Set<String> denied = allUsers();
		denied.removeAll(allowed);

		// run tests
		
		assertRetrieveMemberForbidden(location, denied);
		
	}
	
	
	
	
	public void test_deny_update_member() {

		// setup test

		Entry mediaLink = createMediaAndReturnEntry("spreadsheet1.xls", "application/vnd.ms-excel", collectionUrl, USER_CORA); 
		String location = mediaLink.getEditLink().getHref().toString();

		// define expectations
		
		Set<String> allowed = new HashSet<String>();
		allowed.add(USER_ADAM); allowed.add(USER_CORA);
		
		Set<String> denied = allUsers();
		denied.removeAll(allowed);

		// run tests
		
		mediaLink.setTitle("updated");
		assertUpdateMemberForbidden(location, mediaLink, denied);

	}	
	
	
	
	
	public void test_deny_delete_member() {

		// trivial because no-one can create members, and delete of media-link
		// entry is treated as delete media operation
		
	}
	
	
	
		
	public void test_deny_create_media() {

		// define expectations
		
		Set<String> allowed = new HashSet<String>();
		allowed.add(USER_CORA);
		
		Set<String> denied = allUsers();
		denied.removeAll(allowed);

		// run tests
		
		Action<InputStream> streamGenerator = new Action<InputStream>() {

			@Override
			public InputStream apply() {
				return this.getClass().getClassLoader().getResourceAsStream("spreadsheet1.xls");
			}
			
		};
		
		assertCreateMediaForbidden(collectionUrl, streamGenerator, "application/vnd.ms-excel", denied);

	}
	
	
	
	
	public void test_deny_retrieve_media() {

		// setup test

		Entry mediaLink = createMediaAndReturnEntry("spreadsheet1.xls", "application/vnd.ms-excel", collectionUrl, USER_CORA); 
		String location = mediaLink.getEditMediaLink().getHref().toString();

		// define expectations
		
		Set<String> allowed = new HashSet<String>();
		allowed.add(USER_ADAM); allowed.add(USER_CORA);
		
		Set<String> denied = allUsers();
		denied.removeAll(allowed);

		// run tests
		
		assertRetrieveMediaForbidden(location, denied);

	}
	
	
	
	
	public void test_deny_update_media() {
		fail("todo");
	}
	
	
	
	
	public void test_deny_delete_media() {
		fail("todo");
	}
	
	
	
	
	public void test_deny_retrieve_member_acl() {
		fail("todo");
	}
	
	
	
	
	public void test_deny_update_member_acl() {
		fail("todo");
	}
	
	

	
	public void test_deny_retrieve_media_acl() {
		fail("todo");
	}
	
	
	
	
	public void test_deny_update_media_acl() {
		fail("todo");
	}
	
	
	
	
	public void test_deny_retrieve_member_history() {
		fail("todo");
	}

	
	
	
	public void test_deny_retrieve_member_revision() {
		fail("todo");
	}
	
	

	
	public void test_deny_multi_create() {
		fail("todo");
	}
	
	
	
	
}
