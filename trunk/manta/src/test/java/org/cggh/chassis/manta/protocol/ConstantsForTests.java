package org.cggh.chassis.manta.protocol;

import javax.xml.namespace.QName;

public class ConstantsForTests {

	public static final String URL_SERVICE_BASE = "http://localhost:8081/manta/atombeat";
	public static final String URL_CONTENT_SERVICE = URL_SERVICE_BASE + "/content";
	public static final String URL_SECURITY_SERVICE = URL_SERVICE_BASE + "/security";
	public static final String URL_HISTORY_SERVICE = URL_SERVICE_BASE + "/history";
	public static final String URL_STUDIES_COLLECTION = URL_CONTENT_SERVICE + "/studies";
	public static final String URL_DRAFTS_COLLECTION = URL_CONTENT_SERVICE + "/drafts";

	public static final QName QNAME_DRAFT = new QName("", "draft");
	public static final QName QNAME_STUDY = new QName("", "study");

	public static final String XMLNS_ATOMBEAT = "http://purl.org/atombeat/xmlns";
	public static final String XMLNS_ATOM = "http://www.w3.org/2005/Atom";
	public static final String XMLNS_AE = "http://purl.org/atom/ext/";
	
	public static final QName QNAME_ATOMBEAT_SECURITY_DESCRIPTOR = new QName(XMLNS_ATOMBEAT, "security-descriptor");
	public static final QName QNAME_ATOMBEAT_ACL = new QName(XMLNS_ATOMBEAT, "acl");
	public static final QName QNAME_AE_INLINE = new QName(XMLNS_AE, "inline");

	public static final String MEDIATYPE_MANTA = "application/vnd.chassis-manta+xml";
	public static final String MEDIATYPE_ATOMBEAT = "application/vnd.atombeat+xml";
	
	public static final String USER_CORA = "cora@example.org";
	public static final String USER_COLIN = "colin@example.org";
	public static final String USER_CURTIS = "curtis@example.org";
	public static final String USER_PETE = "pete@example.org";
	public static final String USER_MURIEL = "muriel@example.org";
	public static final String USER_ADAM = "adam@example.org";
	
	public static final String PASS = "bar";
	
	public static final String REL_DRAFT_MEDIA = "http://www.cggh.org/2010/chassis/terms/draftMedia";
	public static final String REL_SUBMITTED_MEDIA = "http://www.cggh.org/2010/chassis/terms/submittedMedia";
	public static final String REL_STUDY_INFO = "http://www.cggh.org/2010/chassis/terms/studyInfo";

	public static final String REL_ATOMBEAT_SECURITY_DESCRIPTOR = "http://purl.org/atombeat/rel/security-descriptor";
}
