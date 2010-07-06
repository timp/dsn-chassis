package org.cggh.chassis.manta.protocol;


import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.abdera.protocol.client.RequestOptions;

import junit.framework.TestCase;
import static org.cggh.chassis.manta.protocol.ConstantsForTests.*;
import static org.cggh.chassis.manta.protocol.UtilsForTests.*;




/**
 * This test case verifies the security model for the /drafts collection. For the
 * definition of the security model, see http://code.google.com/p/dsn-chassis/wiki/MantaSecurityModel
 * 
 * @author aliman
 *
 */
public class TestDrafts extends TestCase {

	
	
	
	// N.B. you need to call response.release() between requests otherwise
	// the tests will hang!
	
	
	
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	
	
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	
	
	/**
	 * Contributors (role) are allowed to list the drafts collection.
	 */
	public void test_allow_contributor_role_list_collection() {
		
		ClientResponse r = ac.get(URL_DRAFTS_COLLECTION, authn(USER_CORA));
		assertEquals(200, r.getStatus());
		r.release();
		
	}
	
	
	
	
	/**
	 * Contributors (role) are allowed to create drafts.
	 */
	public void test_allow_contributor_role_create_member() {
		
		Entry d = newDraft("test");
		ClientResponse r = ac.post(URL_DRAFTS_COLLECTION, d, authn(USER_CORA));
		assertEquals(201, r.getStatus());
		r.release();
		
	}
	
	
	
	
	/**
	 * Users are allowed to retrieve drafts that they have created.
	 */
	public void test_allow_creator_user_retrieve_member() {
		
		ClientResponse r;
		RequestOptions u1 = authn(USER_CORA), u2 = authn(USER_COLIN);
		Entry d1, d2;
		String l1, l2;
		Feed f1, f2;
		
		// setup test
		
		d1 = newDraft("Cora's Draft");
		r = ac.post(URL_DRAFTS_COLLECTION, d1, u1);
		l1 = r.getLocation().toString();
		r.release();
		
		d2 = newDraft("Colin's Draft");
		r = ac.post(URL_DRAFTS_COLLECTION, d2, u2);
		l2 = r.getLocation().toString();
		r.release();
		
		// verify they can retrieve their own drafts
		
		r = ac.get(l1, u1);
		assertEquals(200, r.getStatus());
		r.release();
		
		r = ac.get(l2, u2);
		assertEquals(200, r.getStatus());
		r.release();
		
		// verify they cannot retrieve each other's drafts
		
		r = ac.get(l1, u2);
		assertEquals(403, r.getStatus());
		r.release();
		
		r = ac.get(l2, u1);
		assertEquals(403, r.getStatus());
		r.release();
		
		// verify the collection feed respects retrieval permissions
		
		r = ac.get(URL_DRAFTS_COLLECTION, u1);
		f1 = getFeed(r);
		for (Entry e : f1.getEntries()) {
			assertEquals(1, e.getAuthors().size());
			assertEquals(USER_CORA, e.getAuthor().getEmail());
		}
		r.release();
				
		r = ac.get(URL_DRAFTS_COLLECTION, u2);
		f2 = getFeed(r);
		for (Entry e : f2.getEntries()) {
			assertEquals(1, e.getAuthors().size());
			assertEquals(USER_COLIN, e.getAuthor().getEmail());
		}
		r.release();
				
	}
	
	
	
	/**
	 * Users are allowed to update drafts that they have created.
	 */
	public void test_allow_creator_user_update_member() {
		
		ClientResponse r;
		RequestOptions u1 = authn(USER_CORA), u2 = authn(USER_COLIN);
		Entry d1, d2;
		String l1, l2;

		// setup test

		d1 = newDraft("Cora's Draft");
		r = ac.post(URL_DRAFTS_COLLECTION, d1, u1);
		l1 = r.getLocation().toString();
		r.release();

		d2 = newDraft("Colin's Draft");
		r = ac.post(URL_DRAFTS_COLLECTION, d2, u2);
		l2 = r.getLocation().toString();
		r.release();

		// verify they can update their own drafts

		d1.setTitle("Cora's Draft Updated");
		r = ac.put(l1, d1, u1);
		assertEquals(200, r.getStatus());
		r.release();

		d2.setTitle("Colin's Draft Updated");
		r = ac.put(l2, d2, u2);
		assertEquals(200, r.getStatus());
		r.release();

		// verify they cannot update each other's drafts

		r = ac.put(l2, d1, u1);
		assertEquals(403, r.getStatus());
		r.release();

		r = ac.put(l1, d2, u2);
		assertEquals(403, r.getStatus());
		r.release();
		
	}
		



	/**
	 * Users are allowed to delete drafts that they have created.
	 */
	public void test_allow_creator_user_delete_member() {
		
		ClientResponse r;
		RequestOptions u1 = authn(USER_CORA), u2 = authn(USER_COLIN);
		Entry d1, d2;
		String l1, l2;

		// setup test
		
		d1 = newDraft("Cora's Draft");
		r = ac.post(URL_DRAFTS_COLLECTION, d1, u1);
		l1 = r.getLocation().toString();
		r.release();
		
		d2 = newDraft("Colin's Draft");
		r = ac.post(URL_DRAFTS_COLLECTION, d2, u2);
		l2 = r.getLocation().toString();
		r.release();
		
		// verify they can delete their own drafts
		
		r = ac.delete(l1, u1);
		assertEquals(204, r.getStatus()); 
		r.release();
		
		r = ac.delete(l2, u2);
		assertEquals(204, r.getStatus()); 
		r.release();
		
		// setup test again
		
		r = ac.post(URL_DRAFTS_COLLECTION, d1, u1);
		l1 = r.getLocation().toString();
		r.release();
		
		r = ac.post(URL_DRAFTS_COLLECTION, d2, u2);
		l2 = r.getLocation().toString();
		r.release();
		
		// verify they cannot update each other's drafts
		
		r = ac.delete(l2, u1);
		assertEquals(403, r.getStatus());
		r.release();
		
		r = ac.delete(l1, u2);
		assertEquals(403, r.getStatus());
		r.release();
		
	}
	
	
	
	public void test_deny_list_collection() {
		
		// define expectations
		
		Set<String> allowed = new HashSet<String>();
		allowed.add(USER_CORA); allowed.add(USER_COLIN); allowed.add(USER_ADAM); 
		
		Set<String> denied = allUsers();
		denied.removeAll(allowed);
		
		// run tests
		
		assertListCollectionForbidden(URL_DRAFTS_COLLECTION, denied);
		
	}
	
	
	
	
	public void test_deny_update_collection() {

		// define expectations
		
		Set<String> allowed = new HashSet<String>();
		allowed.add(USER_ADAM); 
		
		Set<String> denied = allUsers();
		denied.removeAll(allowed);
		
		// run tests
		
		Feed f = newFeed("test");
		assertUpdateCollectionForbidden(URL_DRAFTS_COLLECTION, f, denied);
		
	}
	
	
	
	
	public void test_deny_retrieve_collection_acl() {
		
		// define expectations
		
		Set<String> allowed = new HashSet<String>();
		allowed.add(USER_ADAM); 
		
		Set<String> denied = allUsers();
		denied.removeAll(allowed);

		// run tests
		
		assertRetrieveCollectionAclForbidden(URL_DRAFTS_COLLECTION, denied);
		
	}
	
	
	
	
	public void test_deny_update_collection_acl() {

		// define expectations
		
		Set<String> allowed = new HashSet<String>();
		allowed.add(USER_ADAM); 
		
		Set<String> denied = allUsers();
		denied.removeAll(allowed);

		// run tests
		
		assertUpdateCollectionAclForbidden(URL_DRAFTS_COLLECTION, denied);
		 
	}
	
	
	
	
	public void test_deny_create_member() {

		// define expectations
		
		Set<String> allowed = new HashSet<String>();
		allowed.add(USER_CORA); allowed.add(USER_COLIN);
		
		Set<String> denied = allUsers();
		denied.removeAll(allowed);

		// run tests
		
		assertCreateMemberForbidden(URL_DRAFTS_COLLECTION, newEntry("test"), denied);

	}
	
	

	public void test_deny_retrieve_member() {
		
		// setup test

		Entry d = newDraft("Cora's Draft");
		ClientResponse r = ac.post(URL_DRAFTS_COLLECTION, d, authn(USER_CORA));
		String location = r.getLocation().toString();
		r.release();

		// define expectations
		
		Set<String> allowed = new HashSet<String>();
		allowed.add(USER_CORA); allowed.add(USER_ADAM); 
		
		Set<String> denied = allUsers();
		denied.removeAll(allowed);

		// run tests
		
		assertRetrieveMemberForbidden(location, denied);
		
	}
	
	
	
	
	public void test_deny_update_member() {
		
		Entry d;
		String location;
		ClientResponse r;
		
		// setup test

		d = newDraft("Cora's Draft");
		r = ac.post(URL_DRAFTS_COLLECTION, d, authn(USER_CORA));
		location = r.getLocation().toString();
		r.release();

		// define expectations
		
		Set<String> allowed = new HashSet<String>();
		allowed.add(USER_CORA); allowed.add(USER_ADAM);
		
		Set<String> denied = allUsers();
		denied.removeAll(allowed);

		// run tests
		
		d.setTitle("updated");
		assertUpdateMemberForbidden(location, d, denied);
		
	}
	
	
	
	
	public void test_deny_delete_member() {
		
		Entry d;
		String location;
		ClientResponse r;
		
		// setup test

		d = newDraft("Cora's Draft");
		r = ac.post(URL_DRAFTS_COLLECTION, d, authn(USER_CORA));
		location = r.getLocation().toString();
		r.release();

		// define expectations
		
		Set<String> allowed = new HashSet<String>();
		allowed.add(USER_CORA); allowed.add(USER_ADAM);
		
		Set<String> denied = allUsers();
		denied.removeAll(allowed);

		// run tests
		
		Map<String,Integer> results = deleteMember(location, denied);
		
		// verify results
		
		for (String user : denied) {
			assertEquals(user,SC_FORBIDDEN, results.get(user));
		}
		
	}
	
	
	
	
		
	public void test_deny_create_media() {
		
		// define expectations
		
		Set<String> denied = allUsers(); // no-one can create media in this collection

		// run tests
		
		Action<InputStream> streamGenerator = new Action<InputStream>() {

			@Override
			public InputStream apply() {
				return this.getClass().getClassLoader().getResourceAsStream("spreadsheet1.xls");
			}
			
		};
		
		assertCreateMediaForbidden(URL_DRAFTS_COLLECTION, streamGenerator, "application/vnd.ms-excel", denied);

	}
	
	
	
	
	public void test_deny_retrieve_media() {
		
		// trivial if no-one can create media

	}
	
	
	
	
	public void test_deny_update_media() {
		
		// trivial if no-one can create media

	}
	
	
	
	
	public void test_deny_delete_media() {
		
		// trivial if no-one can create media

	}
	
	
	
	
	public void test_deny_retrieve_member_acl() {
		
		Entry d;
		String location;
		ClientResponse r;
		
		// setup test

		d = newDraft("Cora's Draft");
		r = ac.post(URL_DRAFTS_COLLECTION, d, authn(USER_CORA));
		location = r.getLocation().toString();
		r.release();
		
		r = ac.get(location, authn(USER_ADAM));
		d = getEntry(r);
		location = d.getLink(REL_ATOMBEAT_SECURITY_DESCRIPTOR).getHref().toString();
		r.release();
		
		// define expectations
		
		Set<String> allowed = new HashSet<String>();
		allowed.add(USER_ADAM); 
		
		Set<String> denied = allUsers();
		denied.removeAll(allowed);

		// run tests
		
		Map<String,Integer> results = retrieveMember(location, denied);
		
		// verify results
		
		for (String user : denied) {
			assertEquals(user,SC_FORBIDDEN, results.get(user));
		}
		
	}
	
	
	
	
	public void test_deny_update_member_acl() {
		
		Entry d, s;
		String location;
		ClientResponse r;
		
		// setup test

		d = newDraft("Cora's Draft");
		r = ac.post(URL_DRAFTS_COLLECTION, d, authn(USER_CORA));
		location = r.getLocation().toString();
		r.release();
		
		r = ac.get(location, authn(USER_ADAM));
		d = getEntry(r);
		location = d.getLink(REL_ATOMBEAT_SECURITY_DESCRIPTOR).getHref().toString();
		r.release();
		
		// define expectations
		
		Set<String> allowed = new HashSet<String>();
		allowed.add(USER_ADAM); 
		
		Set<String> denied = allUsers();
		denied.removeAll(allowed);

		// run tests
		
		s = newSecurityDescriptor();
		Map<String,Integer> results = updateMember(location, s, denied);
		
		// verify results
		
		for (String user : denied) {
			assertEquals(user,SC_FORBIDDEN, results.get(user));
		}
		
	}
	
	

	
	public void test_deny_retrieve_media_acl() {
		
		// trivial if no-one can create media

	}
	
	
	
	
	public void test_deny_update_media_acl() {
		
		// trivial if no-one can create media

	}
	
	
	
	
	public void test_deny_retrieve_member_history() {
		
		Entry d;
		String location;
		ClientResponse r;
		
		// setup test

		d = newDraft("Cora's Draft");
		r = ac.post(URL_DRAFTS_COLLECTION, d, authn(USER_CORA));
		d = getEntry(r);
		location = d.getLink(REL_HISTORY).getHref().toString();
		r.release();
		
		// define expectations
		
		Set<String> allowed = new HashSet<String>();
		allowed.add(USER_ADAM); 
		
		Set<String> denied = allUsers();
		denied.removeAll(allowed);

		// run tests
		
		Map<String,Integer> results = listCollection(location, denied);
		
		// verify results
		
		for (String user : denied) {
			assertEquals(user,SC_FORBIDDEN, results.get(user));
		}
		
	}

	
	
	
	public void test_deny_retrieve_member_revision() {
		
		Entry d;
		String location;
		ClientResponse r;
		
		// setup test

		d = newDraft("Cora's Draft");
		r = ac.post(URL_DRAFTS_COLLECTION, d, authn(USER_CORA));
		d = getEntry(r);
		location = d.getLink(REL_HISTORY).getHref().toString();
		r.release();
		
		r = ac.get(location, authn(USER_ADAM));
		Feed f = getFeed(r);
		location = f.getEntries().get(0).getLink(REL_THIS_REVISION).getHref().toString();
		r.release();
		
		// define expectations
		
		Set<String> allowed = new HashSet<String>();
		allowed.add(USER_ADAM); 
		
		Set<String> denied = allUsers();
		denied.removeAll(allowed);

		// run tests
		
		Map<String,Integer> results = retrieveMember(location, denied);
		
		// verify results
		
		for (String user : denied) {
			assertEquals(user,SC_FORBIDDEN, results.get(user));
		}
		
	}
	
	

	
	public void test_deny_multi_create() {

		// define expectations
		
		Set<String> denied = allUsers();
		
		// run tests
		
		Feed f = newFeed("test multi-create");
		f.addEntry(newEntry("test1"));
		f.addEntry(newEntry("test2"));
		
		Map<String,Integer> results = multiCreate(URL_DRAFTS_COLLECTION, f, denied);
		
		// verify results
		
		for (String user : denied) {
			assertEquals(user,SC_FORBIDDEN, results.get(user));
		}
		
	}
	
	
	
	
}
