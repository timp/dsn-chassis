package org.cggh.chassis.wwarn.ui.common.client;

public class Config {

	
	
	
	public static final String USER_EMAIL = "user.email";
	public static final String COLLECTION_STUDIES_URL = "collection.studies.url";
	public static final String COLLECTION_MEDIA_URL = "collection.media.url";
	public static final String COLLECTION_SUBMISSIONS_URL = "collection.submissions.url";
	public static final String COLLECTION_REVIEWS_URL = "collection.reviews.url";
	public static final String QUERY_STUDIES_URL = "query.studies.url";
	public static final String QUERY_SUBMISSIONS_URL = "query.submissions.url";
	public static final String QUERY_MEDIA_URL = "query.media.url";
	public static final String FORMHANDLER_FILEUPLOAD_URL = "formhandler.fileupload.url";
	public static final String QUESTIONNAIRE_STUDY_URL = "questionnaire.study.url";
	
	
	
	public static native String get(String key) /*-{
		return $wnd.config[key];
	}-*/;
	
	
	
	
}
