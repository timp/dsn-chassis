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

	public static final String MEDIATYPE_MANTA = "application/vnd.chassis-manta+xml";
	
	public static final String USER_CORA = "cora@example.org";
	public static final String USER_COLIN = "colin@example.org";
	public static final String USER_CURTIS = "curtis@example.org";
	public static final String USER_PETE = "pete@example.org";
	public static final String USER_MURIEL = "muriel@example.org";
	public static final String USER_ADAM = "adam@example.org";
	
	public static final String PASS = "bar";
	
	public static final String REL_DRAFTMEDIA = "http://www.cggh.org/2010/chassis/terms/draftMedia";
}
