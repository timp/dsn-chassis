package org.cggh.chassis.manta.protocol;

import static org.cggh.chassis.manta.protocol.ConstantsForTests.*;

import org.apache.abdera.model.Entry;
import org.apache.abdera.protocol.client.ClientResponse;




public class TestCuratedMediaCollectionsSecurity extends AtomSecurityTestCase {

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
		return new String[] { USER_CURTIS };
	}

	@Override
	protected String[] getUsersAllowedCreateMember() {
		return null; // only media in this collection
	}

	@Override
	protected String[] getUsersAllowedDeleteMedia() {
		return new String[] { USER_ADAM };
	}

	@Override
	protected String[] getUsersAllowedDeleteMember() {

		// because the test member is a media link entry, this is effectively
		// delete media
		return getUsersAllowedDeleteMedia();

	}

	@Override
	protected String[] getUsersAllowedListCollection() {
		return new String[] { USER_ADAM, USER_CURTIS };
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
		return new String[] { USER_ADAM, USER_CURTIS };
	}

	@Override
	protected String[] getUsersAllowedRetrieveMediaAcl() {
		return new String[] { USER_ADAM };
	}

	@Override
	protected String[] getUsersAllowedRetrieveMember() {
		return new String[] { USER_ADAM, USER_CURTIS };
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
		return null;
	}

	@Override
	protected String[] getUsersAllowedUpdateMediaAcl() {
		return new String[] { USER_ADAM };
	}

	@Override
	protected String[] getUsersAllowedUpdateMember() {
		return new String[] { USER_ADAM, USER_CURTIS };
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
		String location = e.getLink(REL_CURATED_MEDIA).getHref().toString();
		r.release();

		return location;
	}

	@Override
	protected String setupMediaResource() {

		String collectionUrl = this.setupCollection();
		Entry mediaLink = createMediaAndReturnEntry("spreadsheet1.xls", "application/vnd.ms-excel", collectionUrl, USER_CURTIS); 
		String location = mediaLink.getEditLink().getHref().toString();

		return location;

	}

	@Override
	protected String setupMember() {

		// the only way to create a member is as a media link entry
		
		String collectionUrl = this.setupCollection();
		Entry mediaLink = createMediaAndReturnEntry("spreadsheet1.xls", "application/vnd.ms-excel", collectionUrl, USER_CURTIS); 
		String location = mediaLink.getEditLink().getHref().toString();

		return location;

	}

}
