/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.submission;

import org.cggh.chassis.generic.atom.rewrite.client.AtomEntryValidator;
import org.cggh.chassis.generic.atom.rewrite.client.ValidationReport;
import org.cggh.chassis.generic.atomext.client.shared.ModulesValidator;


/**
 * @author aliman
 *
 */
public class SubmissionEntryValidator extends AtomEntryValidator<SubmissionEntry> {

	
	

	protected Integer minStudyLinks = null;
	protected Integer maxStudyLinks = null;
	protected String minStudyLinksMessage = "at least " + N + " study links are required";
	protected String maxStudyLinksMessage = "at most " + N + " study links are required";
	protected ModulesValidator modulesValidator;
	
	
	
	
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
	
	public void setModulesValidator(ModulesValidator modulesValidator) {
		this.modulesValidator = modulesValidator;
	}

	public ModulesValidator getModulesValidator() {
		return modulesValidator;
	}
	
	public void validate(ValidationReport report, SubmissionEntry entry) {
		super.validate(report, entry);
		
		validateMinCardinality(this.minStudyLinks, entry.getStudyLinks(), this.minStudyLinksMessage, report);
		validateMaxCardinality(this.maxStudyLinks, entry.getStudyLinks(), this.maxStudyLinksMessage, report);
		
		if (this.modulesValidator != null) {
			this.modulesValidator.validate(report, entry.getSubmission());
		}
		
	}
	
}
