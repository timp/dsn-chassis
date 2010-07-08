package org.cggh.chassis.manta.protocol;

import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;

public class ConstantsForTests {

	public static final String URL_SERVICE_BASE = "http://localhost:8081/manta/atombeat";
	public static final String URL_CONTENT_SERVICE = URL_SERVICE_BASE + "/content";
	public static final String URL_SECURITY_SERVICE = URL_SERVICE_BASE + "/security";
	public static final String URL_HISTORY_SERVICE = URL_SERVICE_BASE + "/history";
	public static final String URL_STUDIES_COLLECTION = URL_CONTENT_SERVICE + "/studies";
	public static final String URL_STUDY_INFO_COLLECTION = URL_CONTENT_SERVICE + "/study-info";
	public static final String URL_DRAFTS_COLLECTION = URL_CONTENT_SERVICE + "/drafts";
	public static final String URL_ALL_SUBMITTED_MEDIA_COLLECTION = URL_CONTENT_SERVICE + "/media/submitted";
	public static final String URL_ALL_CURATED_MEDIA_COLLECTION = URL_CONTENT_SERVICE + "/media/curated";
	public static final String URL_ALL_PERSONAL_DATA_REVIEWS_COLLECTION = URL_CONTENT_SERVICE + "/reviews/personal-data";
	public static final String URL_ALL_DERIVATIONS_COLLECTION = URL_CONTENT_SERVICE + "/derivations";

	public static final QName QNAME_DRAFT = new QName("", "draft");
	public static final QName QNAME_STUDY = new QName("", "study");
	public static final QName QNAME_STUDY_INFO = new QName("", "study-info");
	public static final QName QNAME_DERIVATION = new QName("", "derivation");
	public static final QName QNAME_REVIEW = new QName("", "review");

	public static final String XMLNS_ATOMBEAT = "http://purl.org/atombeat/xmlns";
	public static final String XMLNS_ATOM = "http://www.w3.org/2005/Atom";
	public static final String XMLNS_AE = "http://purl.org/atom/ext/";
	
	public static final QName QNAME_ATOMBEAT_SECURITY_DESCRIPTOR = new QName(XMLNS_ATOMBEAT, "security-descriptor");
	public static final QName QNAME_ATOMBEAT_ACL = new QName(XMLNS_ATOMBEAT, "acl");
	public static final QName QNAME_AE_INLINE = new QName(XMLNS_AE, "inline");

	public static final String MEDIATYPE_MANTA = "application/vnd.chassis-manta+xml";
	public static final String MEDIATYPE_ATOMBEAT = "application/vnd.atombeat+xml";
	
	public static final String USER_URSULA = "ursula@example.org"; // administrator
	public static final String USER_ADAM = "adam@example.org"; // administrator
	public static final String USER_CORA = "cora@example.org"; // contributor
	public static final String USER_COLIN = "colin@example.org"; // contributor
	public static final String USER_CURTIS = "curtis@example.org"; // curator
	public static final String USER_PETE = "pete@example.org"; // personal data reviewer

	public static final Set<String> allUsers() {
		Set<String> users = new HashSet<String>();
		users.add(USER_URSULA);
		users.add(USER_ADAM);
		users.add(USER_CORA);
		users.add(USER_COLIN);
		users.add(USER_CURTIS);
		users.add(USER_PETE);
		return users;
	}
	
	public static final String PASS = "bar";
	
	public static final String REL_DRAFT_MEDIA = "http://www.cggh.org/2010/chassis/terms/draftMedia";
	public static final String REL_SUBMITTED_MEDIA = "http://www.cggh.org/2010/chassis/terms/submittedMedia";
	public static final String REL_CURATED_MEDIA = "http://www.cggh.org/2010/chassis/terms/curatedMedia";
	public static final String REL_DERIVATIONS = "http://www.cggh.org/2010/chassis/terms/derivations";
	public static final String REL_STUDY_INFO = "http://www.cggh.org/2010/chassis/terms/studyInfo";
	public static final String REL_PERSONAL_DATA_REVIEWS = "http://www.cggh.org/2010/chassis/terms/personalDataReviews";

	public static final String REL_ATOMBEAT_SECURITY_DESCRIPTOR = "http://purl.org/atombeat/rel/security-descriptor";
	public static final String REL_ATOMBEAT_MEDIA_SECURITY_DESCRIPTOR = "http://purl.org/atombeat/rel/media-security-descriptor";

	public static final String REL_HISTORY = "history";
	public static final String REL_THIS_REVISION = "this-revision";
	public static final String REL_INITIAL_REVISION = "initial-revision";
	public static final String REL_PREVIOUS_REVISION = "previous-revision";
	public static final String REL_NEXT_REVISION = "next-revision";
	public static final String REL_CURRENT_REVISION = "current-revision";

	public static final Integer SC_OK = new Integer(200);
	public static final Integer SC_CREATED = new Integer(201);
	public static final Integer SC_NO_CONTENT = new Integer(204);
	public static final Integer SC_FORBIDDEN = new Integer(403);
}
