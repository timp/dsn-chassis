package org.cggh.chassis.manta.protocol;



import static org.cggh.chassis.manta.protocol.ConstantsForTests.*;





public class TestAllPersonalDataReviewsCollectionSecurity extends AtomSecurityTestCase {

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
		return null; // recursive collection, no direct members or media resources
	}
	
	@Override
	protected String[] getUsersAllowedCreateMember() {
		return null; // recursive collection, no direct members or media resources
	}

	@Override
	protected String[] getUsersAllowedDeleteMedia() {
		return null; // no media allowed in this collection
	}

	@Override
	protected String[] getUsersAllowedDeleteMember() {
		return null; // recursive collection, no direct members or media resources
	}

	@Override
	protected String[] getUsersAllowedListCollection() {
		return new String[] { USER_ADAM, USER_PETE }; 
	}

	@Override
	protected String[] getUsersAllowedMultiCreate() {
		return null; // recursive collection, no direct members or media resources
	}

	@Override
	protected String[] getUsersAllowedRetrieveCollectionAcl() {
		return new String[] { USER_ADAM }; 
	}

	@Override
	protected String[] getUsersAllowedRetrieveMedia() {
		return null; // recursive collection, no direct members or media resources
	}

	@Override
	protected String[] getUsersAllowedRetrieveMediaAcl() {
		return null; // recursive collection, no direct members or media resources
	}

	@Override
	protected String[] getUsersAllowedRetrieveMember() {
		return null; // recursive collection, no direct members or media resources
	}

	@Override
	protected String[] getUsersAllowedRetrieveMemberAcl() {
		return null; // recursive collection, no direct members or media resources
	}

	@Override
	protected String[] getUsersAllowedRetrieveMemberHistory() {
		return null; // recursive collection, no direct members or media resources
	}

	@Override
	protected String[] getUsersAllowedRetrieveMemberRevision() {
		return null; // recursive collection, no direct members or media resources
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
		return null; // recursive collection, no direct members or media resources
	}

	@Override
	protected String[] getUsersAllowedUpdateMediaAcl() {
		return null; // recursive collection, no direct members or media resources
	}

	@Override
	protected String[] getUsersAllowedUpdateMember() {
		return null; // recursive collection, no direct members or media resources
	}

	@Override
	protected String[] getUsersAllowedUpdateMemberAcl() {
		return null; // recursive collection, no direct members or media resources
	}

	@Override
	protected String setupCollection() {
		return URL_ALL_PERSONAL_DATA_REVIEWS_COLLECTION;
	}

	@Override
	protected String setupMediaResource() {
		return null; // recursive collection, no direct members or media resources
	}

	@Override
	protected String setupMember() {
		return null; // recursive collection, no direct members or media resources
	}

}
