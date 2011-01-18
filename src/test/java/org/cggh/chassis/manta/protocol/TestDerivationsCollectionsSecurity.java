package org.cggh.chassis.manta.protocol;

import static org.cggh.chassis.manta.protocol.ConstantsForTests.*;

import org.apache.abdera.model.Entry;
import org.apache.abdera.protocol.client.ClientResponse;




public class TestDerivationsCollectionsSecurity extends AtomSecurityTestCase {

	@Override
	protected boolean collectionIsVersioned() {
		return false;
	}

	@Override
	protected String getSuperUser() {
		return USER_ADAM;
	}

	@Override
	protected String[] getUsersAllowedCreateMedia() {
		return null; // no media in this collection
	}

	@Override
	protected String[] getUsersAllowedCreateMember() {
		return new String[] { USER_CURTIS };
	}

	@Override
	protected String[] getUsersAllowedDeleteMedia() {
		return null; // no media in this collection
	}

	@Override
	protected String[] getUsersAllowedDeleteMember() {
		return new String[] { USER_ADAM };
	}

	@Override
	protected String[] getUsersAllowedListCollection() {
		return new String[] { USER_ADAM, USER_CURTIS, USER_PETE };
	}

	@Override
	protected String[] getUsersAllowedMultiCreate() {
		return null;
	}

	@Override
	protected String[] getUsersAllowedRetrieveCollectionAcl() {
		return new String[] { USER_ADAM };
	}

	@Override
	protected String[] getUsersAllowedRetrieveMedia() {
		return null; // no media in this collection
	}

	@Override
	protected String[] getUsersAllowedRetrieveMediaAcl() {
		return null; // no media in this collection
	}

	@Override
	protected String[] getUsersAllowedRetrieveMember() {
		return new String[] { USER_ADAM, USER_CURTIS, USER_PETE };
	}

	@Override
	protected String[] getUsersAllowedRetrieveMemberAcl() {
		return new String[] { USER_ADAM };
	}

	@Override
	protected String[] getUsersAllowedRetrieveMemberHistory() {
		return null; // collection is not versioned
	}

	@Override
	protected String[] getUsersAllowedRetrieveMemberRevision() {
		return null; // collection is not versioned
	}

	@Override
	protected String[] getUsersAllowedUpdateCollection() {
		return new String[] { USER_ADAM };
	}

	@Override
	protected String[] getUsersAllowedUpdateCollectionAcl() {
		return new String[] { USER_ADAM };
	}

	@Override
	protected String[] getUsersAllowedUpdateMedia() {
		return null; // no media in this collection
	}

	@Override
	protected String[] getUsersAllowedUpdateMediaAcl() {
		return null; // no media in this collection
	}

	@Override
	protected String[] getUsersAllowedUpdateMember() {
		return new String[] { USER_ADAM };
	}

	@Override
	protected String[] getUsersAllowedUpdateMemberAcl() {
		return new String[] { USER_ADAM };
	}

	@Override
	protected String setupCollection() {

		Entry e = newStudy("Cora's Study");
		ClientResponse r = ac.post(URL_STUDIES_COLLECTION, e, authn(USER_CORA));
		e = getEntry(r);
		String location = e.getLink(REL_DERIVATIONS).getHref().toString();
		r.release();

		return location;
	}

	@Override
	protected String setupMediaResource() {

		return null; // no media in this collection

	}

	@Override
	protected String setupMember() {

		String collectionLocation = this.setupCollection();
		
		Entry e = newDerivation();
		ClientResponse r = ac.post(collectionLocation, e, authn(USER_CURTIS));
		String location = r.getLocation().toString();
		r.release();

		return location;

	}

}
