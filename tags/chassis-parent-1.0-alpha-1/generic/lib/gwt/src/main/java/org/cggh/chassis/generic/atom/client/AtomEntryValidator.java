/**
 * 
 */
package org.cggh.chassis.generic.atom.client;


/**
 * @author aliman
 *
 */
public class AtomEntryValidator
	<E extends AtomEntry>
	extends Validator<E> {

	protected Boolean titleRequired = null;
	protected Boolean summaryRequired = null;
	protected Integer minAuthors = null;
	protected Integer maxAuthors = null;
	protected Integer minCategories = null;
	protected Integer maxCategories = null;
	protected Integer minLinks = null;
	protected Integer maxLinks = null;
	
	protected String titleRequiredMessage = "title is required"; // default
	protected String summaryRequiredMessage = "summary is required"; // default
	protected String minAuthorsMessage = "at least {n} authors are required"; // default
	protected String maxAuthorsMessage = "at most {n} authors are allowed"; // default
	protected String minCategoriesMessage = "at least {n} categories are required"; // default
	protected String maxCategoriesMessage = "at most {n} categories are allowed"; // default
	protected String minLinksMessage = "at least {n} links are required"; // default
	protected String maxLinksMessage = "at most {n} links are allowed"; // default
	
	
	
	/**
	 * @return the titleRequired
	 */
	public Boolean getTitleRequired() {
		return titleRequired;
	}
	
	/**
	 * @param titleRequired the titleRequired to set
	 */
	public void setTitleRequired(Boolean titleRequired) {
		this.titleRequired = titleRequired;
	}
	
	/**
	 * @return the summaryRequired
	 */
	public Boolean getSummaryRequired() {
		return summaryRequired;
	}
	
	/**
	 * @param summaryRequired the summaryRequired to set
	 */
	public void setSummaryRequired(Boolean summaryRequired) {
		this.summaryRequired = summaryRequired;
	}
	
	/**
	 * @return the minAuthors
	 */
	public Integer getMinAuthors() {
		return minAuthors;
	}
	
	/**
	 * @param minAuthors the minAuthors to set
	 */
	public void setMinAuthors(Integer minAuthors) {
		this.minAuthors = minAuthors;
	}
	
	/**
	 * @return the maxAuthors
	 */
	public Integer getMaxAuthors() {
		return maxAuthors;
	}
	/**
	 * @param maxAuthors the maxAuthors to set
	 */
	public void setMaxAuthors(Integer maxAuthors) {
		this.maxAuthors = maxAuthors;
	}
	
	/**
	 * @return the minCategories
	 */
	public Integer getMinCategories() {
		return minCategories;
	}
	/**
	 * @param minCategories the minCategories to set
	 */
	public void setMinCategories(Integer minCategories) {
		this.minCategories = minCategories;
	}
	
	/**
	 * @return the maxCategories
	 */
	public Integer getMaxCategories() {
		return maxCategories;
	}
	/**
	 * @param maxCategories the maxCategories to set
	 */
	public void setMaxCategories(Integer maxCategories) {
		this.maxCategories = maxCategories;
	}
	
	/**
	 * @return the minLinks
	 */
	public Integer getMinLinks() {
		return minLinks;
	}
	
	/**
	 * @param minLinks the minLinks to set
	 */
	public void setMinLinks(Integer minLinks) {
		this.minLinks = minLinks;
	}
	
	/**
	 * @return the maxLinks
	 */
	public Integer getMaxLinks() {
		return maxLinks;
	}
	
	/**
	 * @param maxLinks the maxLinks to set
	 */
	public void setMaxLinks(Integer maxLinks) {
		this.maxLinks = maxLinks;
	}
	
	/**
	 * @return the titleRequiredMessage
	 */
	public String getTitleRequiredMessage() {
		return titleRequiredMessage;
	}

	/**
	 * @param titleRequiredMessage the titleRequiredMessage to set
	 */
	public void setTitleRequiredMessage(String titleRequiredMessage) {
		this.titleRequiredMessage = titleRequiredMessage;
	}

	/**
	 * @return the summaryRequiredMessage
	 */
	public String getSummaryRequiredMessage() {
		return summaryRequiredMessage;
	}

	/**
	 * @param summaryRequiredMessage the summaryRequiredMessage to set
	 */
	public void setSummaryRequiredMessage(String summaryRequiredMessage) {
		this.summaryRequiredMessage = summaryRequiredMessage;
	}

	/**
	 * @return the minAuthorsMessage
	 */
	public String getMinAuthorsMessage() {
		return minAuthorsMessage;
	}

	/**
	 * @param minAuthorsMessage the minAuthorsMessage to set
	 */
	public void setMinAuthorsMessage(String minAuthorsMessage) {
		this.minAuthorsMessage = minAuthorsMessage;
	}

	/**
	 * @return the maxAuthorsMessage
	 */
	public String getMaxAuthorsMessage() {
		return maxAuthorsMessage;
	}

	/**
	 * @param maxAuthorsMessage the maxAuthorsMessage to set
	 */
	public void setMaxAuthorsMessage(String maxAuthorsMessage) {
		this.maxAuthorsMessage = maxAuthorsMessage;
	}

	/**
	 * @return the minCategoriesMessage
	 */
	public String getMinCategoriesMessage() {
		return minCategoriesMessage;
	}

	/**
	 * @param minCategoriesMessage the minCategoriesMessage to set
	 */
	public void setMinCategoriesMessage(String minCategoriesMessage) {
		this.minCategoriesMessage = minCategoriesMessage;
	}

	/**
	 * @return the maxCategoriesMessage
	 */
	public String getMaxCategoriesMessage() {
		return maxCategoriesMessage;
	}

	/**
	 * @param maxCategoriesMessage the maxCategoriesMessage to set
	 */
	public void setMaxCategoriesMessage(String maxCategoriesMessage) {
		this.maxCategoriesMessage = maxCategoriesMessage;
	}

	/**
	 * @return the minLinksMessage
	 */
	public String getMinLinksMessage() {
		return minLinksMessage;
	}

	/**
	 * @param minLinksMessage the minLinksMessage to set
	 */
	public void setMinLinksMessage(String minLinksMessage) {
		this.minLinksMessage = minLinksMessage;
	}

	/**
	 * @return the maxLinksMessage
	 */
	public String getMaxLinksMessage() {
		return maxLinksMessage;
	}

	/**
	 * @param maxLinksMessage the maxLinksMessage to set
	 */
	public void setMaxLinksMessage(String maxLinksMessage) {
		this.maxLinksMessage = maxLinksMessage;
	}

	
	
	
	@Override
	public void validate(ValidationReport report, E entry) {
		
		validateRequired(this.titleRequired, entry.getTitle(), this.titleRequiredMessage, report);
		validateRequired(this.summaryRequired, entry.getSummary(), this.summaryRequiredMessage, report);
		
		validateMinCardinality(this.minAuthors, entry.getAuthors(), this.minAuthorsMessage, report);
		validateMinCardinality(this.minCategories, entry.getCategories(), this.minCategoriesMessage, report);
		validateMinCardinality(this.minLinks, entry.getLinks(), this.minLinksMessage, report);
		
		validateMaxCardinality(this.maxAuthors, entry.getAuthors(), this.maxAuthorsMessage, report);
		validateMaxCardinality(this.maxCategories, entry.getCategories(), this.maxCategoriesMessage, report);
		validateMaxCardinality(this.maxLinks, entry.getLinks(), this.maxLinksMessage, report);
		
	}
	
	
	
	
	
}
