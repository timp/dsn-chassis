package org.cggh.chassis.wwarn.ui.common.client;

/**
 * A bridge between the a javascript config map and java.
 */
public class Config {

	
	public static final String USER_EMAIL = "user.email";
	public static final String COLLECTION_STUDIES_URL = "collection.studies.url";
	public static final String COLLECTION_MEDIA_URL = "collection.media.url";
	public static final String COLLECTION_SUBMISSIONS_URL = "collection.submissions.url";
	public static final String COLLECTION_REVIEWS_URL = "collection.reviews.url";
	public static final String COLLECTION_DERIVATIONS_URL = "collection.derivations.url";
	public static final String QUERY_STUDIES_URL = "query.studies.url";
	public static final String QUERY_SUBMISSIONS_URL = "query.submissions.url";
	public static final String QUERY_MEDIA_URL = "query.media.url";
	public static final String FORMHANDLER_FILEUPLOAD_URL = "formhandler.fileupload.url";
	public static final String QUESTIONNAIRE_STUDY_URL = "questionnaire.study.url";
	public static final String QUERY_FILESTOREVIEW_URL = "query.filestoreview.url";
	public static final String QUERY_FILESTOCLEAN_URL = "query.filestoclean.url";
	public static final String FORMHANDLER_ANONYMIZEDFILEUPLOAD_URL = "formhandler.anonymizedfileupload.url";
	
	
    /**
     * Get a value from the javascript config object;
     * @param key
     * @return value
     * @throws NullPointerException if not found
     */
    public static String get(String key) { 
    	String it = getNative(key);
    	if(it == null)
			throw new NullPointerException("No entry in Config for " + key);
    	return it;
    }
	private static native String getNative(String key) /*-{
		return $wnd.config[key];
	}-*/;
	
	
	
	
}
