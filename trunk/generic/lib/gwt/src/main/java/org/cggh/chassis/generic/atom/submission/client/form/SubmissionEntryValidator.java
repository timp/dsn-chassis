/**
 * 
 */
package org.cggh.chassis.generic.atom.submission.client.form;

import legacy.org.cggh.chassis.generic.atom.vanilla.client.form.AtomEntryValidator;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.form.ValidationReport;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;


/**
 * @author aliman
 *
 */
public class SubmissionEntryValidator extends AtomEntryValidator<SubmissionEntry> {

	protected Integer minModules = null;
	protected Integer maxModules = null;
	protected Integer minStudyLinks = null;
	protected Integer maxStudyLinks = null;
	
	protected String minModulesMessage = "at least {n} modules are required";
	protected String maxModulesMessage = "at most {n} modules are required";

	protected String minStudyLinksMessage = "at least {n} study links are required";
	protected String maxStudyLinksMessage = "at most {n} study links are required";
	
	/**
	 * @return the minModules
	 */
	public Integer getMinModules() {
		return minModules;
	}
	
	/**
	 * @param minModules the minModules to set
	 */
	public void setMinModules(Integer minModules) {
		this.minModules = minModules;
	}
	
	/**
	 * @return the maxModules
	 */
	public Integer getMaxModules() {
		return maxModules;
	}
	
	/**
	 * @param maxModules the maxModules to set
	 */
	public void setMaxModules(Integer maxModules) {
		this.maxModules = maxModules;
	}
	
	/**
	 * @return the minStudyLinks
	 */
	public Integer getMinStudyLinks() {
		return minStudyLinks;
	}
	
	/**
	 * @param minStudyLinks the minStudyLinks to set
	 */
	public void setMinStudyLinks(Integer minStudyLinks) {
		this.minStudyLinks = minStudyLinks;
	}
	
	/**
	 * @return the maxStudyLinks
	 */
	public Integer getMaxStudyLinks() {
		return maxStudyLinks;
	}
	
	/**
	 * @param maxStudyLinks the maxStudyLinks to set
	 */
	public void setMaxStudyLinks(Integer maxStudyLinks) {
		this.maxStudyLinks = maxStudyLinks;
	}
	
	/**
	 * @return the minModulesMessage
	 */
	public String getMinModulesMessage() {
		return minModulesMessage;
	}
	
	/**
	 * @param minModulesMessage the minModulesMessage to set
	 */
	public void setMinModulesMessage(String minModulesMessage) {
		this.minModulesMessage = minModulesMessage;
	}
	
	/**
	 * @return the maxModulesMessage
	 */
	public String getMaxModulesMessage() {
		return maxModulesMessage;
	}
	
	/**
	 * @param maxModulesMessage the maxModulesMessage to set
	 */
	public void setMaxModulesMessage(String maxModulesMessage) {
		this.maxModulesMessage = maxModulesMessage;
	}
	
	/**
	 * @return the minStudyLinksMessage
	 */
	public String getMinStudyLinksMessage() {
		return minStudyLinksMessage;
	}
	
	/**
	 * @param minStudyLinksMessage the minStudyLinksMessage to set
	 */
	public void setMinStudyLinksMessage(String minStudyLinksMessage) {
		this.minStudyLinksMessage = minStudyLinksMessage;
	}
	
	/**
	 * @return the maxStudyLinksMessage
	 */
	public String getMaxStudyLinksMessage() {
		return maxStudyLinksMessage;
	}
	
	/**
	 * @param maxStudyLinksMessage the maxStudyLinksMessage to set
	 */
	public void setMaxStudyLinksMessage(String maxStudyLinksMessage) {
		this.maxStudyLinksMessage = maxStudyLinksMessage;
	}
	
	
	
	
	public void validate(ValidationReport report, SubmissionEntry entry) {
		super.validate(report, entry);
		
		validateMinCardinality(this.minModules, entry.getModules(), this.minModulesMessage, report);
		validateMinCardinality(this.minStudyLinks, entry.getStudyLinks(), this.minStudyLinksMessage, report);
		
		validateMaxCardinality(this.maxModules, entry.getModules(), this.maxModulesMessage, report);
		validateMaxCardinality(this.maxStudyLinks, entry.getStudyLinks(), this.maxStudyLinksMessage, report);
		
	}
	
	
	
	

}
