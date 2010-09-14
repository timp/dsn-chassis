/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileFeed;
import org.cggh.chassis.generic.client.gwt.forms.client.BaseFormRenderer;

/**
 * @author aliman
 *
 */
public class EditDataFileFormRenderer 
	extends BaseFormRenderer<DataFileEntry, DataFileFeed> {

	
	
	
	
	
	/**
	 * @param owner
	 */
	public EditDataFileFormRenderer(EditDataFileForm owner) {
		super(owner);
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.forms.client.BaseFormRenderer#createResources()
	 */
	@Override
	protected EditDataFileForm.Resources createResources() {
		return new EditDataFileForm.Resources();
	}

}
