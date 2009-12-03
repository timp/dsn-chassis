/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.study;

import org.cggh.chassis.generic.atom.client.AtomEntryValidator;
import org.cggh.chassis.generic.atom.client.ValidationReport;
import org.cggh.chassis.generic.atomext.client.shared.ModulesValidator;


/**
 * @author aliman
 *
 */
public class StudyEntryValidator extends AtomEntryValidator<StudyEntry> {

	protected ModulesValidator modulesValidator;
	
	public void setModulesValidator(ModulesValidator modulesValidator) {
		this.modulesValidator = modulesValidator;
	}

	public ModulesValidator getModulesValidator() {
		return modulesValidator;
	}
	
	public void validate(ValidationReport report, StudyEntry entry) {
		super.validate(report, entry);
		
		if (this.modulesValidator != null) {
			this.modulesValidator.validate(report, entry.getStudy());
		}
		
	}
	
}
