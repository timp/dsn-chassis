package org.cggh.chassis.manta.protocol;



import static org.cggh.chassis.manta.protocol.ConstantsForTests.*;

import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.abdera.protocol.client.RequestOptions;




public class TestDraftsCollectionSecurity extends AtomSecurityTestCase {

	
	
	
	@Override
	protected String setupCollection() {
		return URL_DRAFTS_COLLECTION;
	}

	
	
	
	@Override
	protected String setupMember() {

		Entry d = newDraft("Cora's Draft");
		ClientResponse r = ac.post(URL_DRAFTS_COLLECTION, d, authn(USER_CORA));
		String location = r.getLocation().toString();
		r.release();

		return location;
		
	}




	@Override
	protected String[] getUsersAllowedListCollection() {
		
		return new String[] {
				USER_CORA,
				USER_COLIN,
				USER_ADAM
		};

	}




	@Override
	protected String[] getUsersAllowedCreateMember() {

		return new String[] {
				USER_CORA,
				USER_COLIN
		};

	}




	@Override
	protected String[] getUsersAllowedRetrieveMember() {

		return new String[] {
				USER_CORA,
				USER_ADAM
		};

	}




	@Override
	protected String[] getUsersAllowedUpdateMember() {

		return new String[] {
				USER_CORA,
				USER_ADAM
		};

	}




	@Override
	protected String[] getUsersAllowedDeleteMember() {

		return new String[] {
				USER_CORA,
				USER_ADAM
		};

	}




	@Override
	protected String[] getUsersAllowedCreateMedia() {
		
		return null;
		
	}




	@Override
	protected String[] getUsersAllowedRetrieveMedia() {
		
		return null;
		
	}




	@Override
	protected String[] getUsersAllowedUpdateMedia() {
		
		return null;
		
	}

	
	
	
	@Override
	protected String[] getUsersAllowedDeleteMedia() {
		
		return null;
		
	}




	@Override
	protected String[] getUsersAllowedUpdateCollection() {

		return new String[] {
				USER_ADAM
		};

	}




	@Override
	protected String[] getUsersAllowedRetrieveMemberAcl() {

		return new String[] {
				USER_ADAM
		};

	}




	@Override
	protected String[] getUsersAllowedUpdateMemberAcl() {

		return new String[] {
				USER_ADAM
		};

	}




	@Override
	protected String[] getUsersAllowedRetrieveCollectionAcl() {

		return new String[] {
				USER_ADAM
		};

	}




	@Override
	protected String[] getUsersAllowedUpdateCollectionAcl() {

		return new String[] {
				USER_ADAM
		};

	}




	@Override
	protected String[] getUsersAllowedRetrieveMediaAcl() {
		return null;
	}




	@Override
	protected String[] getUsersAllowedUpdateMediaAcl() {
		return null;
	}




	@Override
	protected String[] getUsersAllowedRetrieveMemberHistory() {

		return new String[] {
				USER_ADAM
		};

	}




	@Override
	protected String[] getUsersAllowedRetrieveMemberRevision() {

		return new String[] {
				USER_ADAM
		};

	}




	@Override
	protected String[] getUsersAllowedMultiCreate() {
		return null;
	}


	
	

	
	
	public void testAllowContributorRoleListCollection() {
		
		ClientResponse r = ac.get(URL_DRAFTS_COLLECTION, authn(USER_CORA));
		assertEquals(200, r.getStatus());
		r.release();
		
	}
	
	
	
	
	public void testAllowContributorRoleCreateMember() {
		
		Entry d = newDraft("test");
		ClientResponse r = ac.post(URL_DRAFTS_COLLECTION, d, authn(USER_CORA));
		assertEquals(201, r.getStatus());
		r.release();
		
	}
	
	
	
	
	public void testAllowCreatorUserRetrieveMember() {
		
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
	
	
	
	public void testAllowCreatorUserUpdateMember() {
		
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
		



	public void testAllowCreatorUserDeleteMember() {
		
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




	@Override
	protected String setupMediaResource() {
		return null;
	}




	@Override
	protected boolean collectionIsVersioned() {
		return true;
	}
	
	
	
	@Override
	protected String getSuperUser() {
		return USER_ADAM;
	}
	
	
	


}
