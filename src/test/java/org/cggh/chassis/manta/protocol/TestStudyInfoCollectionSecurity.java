package org.cggh.chassis.manta.protocol;



import static org.cggh.chassis.manta.protocol.ConstantsForTests.*;

import org.apache.abdera.model.Entry;
import org.apache.abdera.protocol.client.ClientResponse;




public class TestStudyInfoCollectionSecurity extends AtomSecurityTestCase {

	@Override
	protected boolean collectionIsVersioned() {
		return true;
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
		return null; // no-one can create members directly, they are auto-created
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
		return new String[] { USER_ADAM, USER_CORA, USER_COLIN, USER_CURTIS };
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
		return new String[] { USER_ADAM, USER_CORA, USER_CURTIS };
	}

	@Override
	protected String[] getUsersAllowedRetrieveMemberAcl() {
		return new String[] { USER_ADAM };
	}

	@Override
	protected String[] getUsersAllowedRetrieveMemberHistory() {
		return new String[] { USER_ADAM, USER_CORA, USER_CURTIS };
	}

	@Override
	protected String[] getUsersAllowedRetrieveMemberRevision() {
		return new String[] { USER_ADAM, USER_CORA, USER_CURTIS };
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
		return new String[] { USER_ADAM, USER_CORA, USER_CURTIS };
	}

	@Override
	protected String[] getUsersAllowedUpdateMemberAcl() {
		return new String[] { USER_ADAM };
	}

	@Override
	protected String setupCollection() {
		return URL_STUDY_INFO_COLLECTION;
	}

	@Override
	protected String setupMediaResource() {
		return null; // no media in this collection
	}

	@Override
	protected String setupMember() {

		Entry e = newStudy("Cora's Study");
		ClientResponse r = ac.post(URL_STUDIES_COLLECTION, e, authn(USER_CORA));
		e = getEntry(r);
		String location = e.getLink(REL_STUDY_INFO).getHref().toString();
		r.release();

		return location;
		
	}

}
