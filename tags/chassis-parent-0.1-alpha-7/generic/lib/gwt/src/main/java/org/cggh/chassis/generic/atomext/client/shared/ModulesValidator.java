/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.shared;

import org.cggh.chassis.generic.atom.client.ValidationReport;
import org.cggh.chassis.generic.atom.client.Validator;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author aliman
 *
 */
public class ModulesValidator extends Validator<HasModules> {
	
	
	
	private Log log = LogFactory.getLog(ModulesValidator.class);

	protected Integer minModules = null;
	protected Integer maxModules = null;
	protected String minModulesMessage = "at least " + N + " modules are required";
	protected String maxModulesMessage = "at most " + N + " modules are required";

	
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
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.rewrite.client.Validator#validate(org.cggh.chassis.generic.atom.rewrite.client.ValidationReport, java.lang.Object)
	 */
	@Override
	public void validate(ValidationReport report, HasModules t) {
		log.enter("validate");

		validateMinCardinality(this.minModules, t.getModules(), this.minModulesMessage, report);
		validateMaxCardinality(this.maxModules, t.getModules(), this.maxModulesMessage, report);

		log.leave();

	}

	
	
	
}
