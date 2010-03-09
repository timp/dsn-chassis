/**
 * 
 */
package org.cggh.chassis.generic.miniatom.client.ext;

/**
 * @author aliman
 *
 */
public class Chassis {

	
	
	public static final String NSURI = "http://www.cggh.org/2009/chassis/xmlns/";
	public static final String PREFIX = "chassis";


	public static final String ELEMENT_STUDY = "study";
	public static final String ELEMENT_MODULE = "module";
	public static final String ELEMENT_REVIEW = "review";
	public static final String ELEMENT_OUTCOME = "outcome";
	public static final String ELEMENT_SUBMISSIONPUBLISHED = "submission-published";
	public static final String ELEMENT_REVIEWPUBLISHED = "review-published";
	public static final String ELEMENT_REVIEWSUMMARY = "review-summary";
	public static final String ELEMENT_DERIVATION = "derivation";
	
	public static final String ATTRIBUTE_TERM = "term";
	public static final String ATTRIBUTE_LABEL = "label";
	
	
	public static final String SCHEME_FILETYPES = "http://www.cggh.org/2010/chassis/scheme/FileTypes";

	
	
	public static final String TERM_DATAFILE = "http://www.cggh.org/2010/chassis/terms/DataFile";
	public static final String TERM_DATADICTIONARY = "http://www.cggh.org/2010/chassis/terms/DataDictionary";
	public static final String TERM_PROTOCOL = "http://www.cggh.org/2010/chassis/terms/Protocol";
	public static final String TERM_OTHER = "http://www.cggh.org/2010/chassis/terms/Other";

	public static final String TERM_PERSONALDATAREVIEW = "http://www.cggh.org/2010/chassis/terms/PersonalDataReview";
	public static final String TERM_REMOVEPERSONALDATA = "http://www.cggh.org/2010/chassis/terms/RemovePersonalData";
	
	
	public static final String REL_SUBMISSIONPART = "http://www.cggh.org/2010/chassis/terms/submissionPart";
	public static final String REL_ORIGINSTUDY = "http://www.cggh.org/2010/chassis/terms/originStudy";
	public static final String REL_REVIEWSUBJECT = "http://www.cggh.org/2010/chassis/terms/reviewSubject";
	public static final String REL_DERIVATIONINPUT = "http://www.cggh.org/2010/chassis/terms/derivationInput";
	public static final String REL_DERIVATIONOUTPUT = "http://www.cggh.org/2010/chassis/terms/derivationOutput";
	
		
	
	public static final String QUERYPARAM_ID = "id";
	public static final String QUERYPARAM_SUBMITTED = "submitted";
	public static final String QUERYPARAMVALUE_NO = "no";
	
	
    public static enum Module { 
    	CLINICAL     ("clinical"), 
    	MOLECULAR    ("molecular"), 
    	INVITRO      ("invitro"),
    	PHARMACOLOGY ("pharmacology")
    	;
    	private String name;
    	private Module(String name) {
    		this.name = name;
    	}
    	public String getName() {return name;}
    }
}
