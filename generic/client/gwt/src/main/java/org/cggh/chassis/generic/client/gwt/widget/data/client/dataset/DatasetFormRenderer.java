/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetFeed;
import org.cggh.chassis.generic.client.gwt.forms.client.BaseFormRenderer;
import org.cggh.chassis.generic.client.gwt.forms.client.BaseForm.Resources;

/**
 * @author aliman
 *
 */
public class DatasetFormRenderer 
	extends BaseFormRenderer<DatasetEntry, DatasetFeed> {
	
	
	
	
	
	/**
	 * @param owner
	 */
	public DatasetFormRenderer(DatasetForm owner) {
		super(owner);
	}

	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.forms.client.BaseFormRenderer#createResources()
	 */
	@Override
	protected Resources createResources() {
		return new DatasetForm.Resources();
	}

	
	
	
}
