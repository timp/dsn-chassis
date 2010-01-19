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

	
	
	public static final String SCHEME_FILETYPES = "http://www.cggh.org/2010/chassis/scheme/FileTypes";

	
	
	public static final String TERM_DATAFILE = "http://www.cggh.org/2010/chassis/terms/DataFile";
	public static final String TERM_DATADICTIONARY = "http://www.cggh.org/2010/chassis/terms/DataDictionary";
	public static final String TERM_PROTOCOL = "http://www.cggh.org/2010/chassis/terms/Protocol";
	public static final String TERM_OTHER = "http://www.cggh.org/2010/chassis/terms/Other";

	
	
	public static final String REL_SUBMISSIONPART = "http://www.cggh.org/2010/chassis/terms/submissionPart";
	public static final String REL_ORIGINSTUDY = "http://www.cggh.org/2010/chassis/terms/originStudy";
	
		
	
	public static final String QUERYPARAM_ID = "id";
	public static final String QUERYPARAM_SUBMITTED = "submitted";
	public static final String QUERYPARAMVALUE_NO = "no";
	
	
    public static enum Module { 
    	CLINICAL     ("clinical", "Clinical"), 
    	MOLECULAR    ("molecular", "Molecular"), 
    	INVITRO      ("invitro", "<i>in vitro</i>"),
    	PHARMACOLOGY ("pharmacology", "Pharmacology")
    	;
    	private String name;
    	private String htmlDisplay;
    	private Module(String name, String htmlDisplay) {
    		this.name = name;
    		this.htmlDisplay = htmlDisplay;    		
    	}
    	public String toString(){ return htmlDisplay;}
    	public String getName() {return name;}
    }
}
