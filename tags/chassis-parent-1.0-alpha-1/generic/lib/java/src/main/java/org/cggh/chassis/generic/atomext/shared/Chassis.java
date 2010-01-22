/**
 * 
 */
package org.cggh.chassis.generic.atomext.shared;

/**
 * @author aliman
 *
 */
public class Chassis {
	
	public static final String NSURI = "http://www.cggh.org/2009/chassis/xmlns/";
	public static final String PREFIX = "chassis";
	public static final String SCHEME_TYPES = "http://www.cggh.org/2009/chassis/type/";

	public static class Rel {

		public static final String STUDY = "chassis.study";
		public static final String SUBMISSION = "chassis.submission";
		public static final String REVISION = "chassis.revision";
		public static final String DATAFILE = "chassis.datafile";
		public static final String DATASET = "chassis.dataset";
		public static final String REVIEW = "chassis.review";
		
	}

	public static class Element {

		public static final String STUDY = "study";
		public static final String MODULE = "module";
		public static final String SUBMISSION = "submission";
		public static final String STARTYEAR = "startYear";
		public static final String ENDYEAR = "endYear";
		public static final String REVIEW = "review";
		public static final String CURATOR = "curator";

	}
	
	public static class Type {
		
		public static final String SUBMISSION = "Submission";
		public static final String STUDY = "Study";
		public static final String DATAFILE = "DataFile";
		public static final String MEDIARESOURCE = "MediaResource";
		public static final String DATASET = "Dataset";
		public static final String REVIEW = "Review";
		public static final String USER = "User";

	}
	
}
