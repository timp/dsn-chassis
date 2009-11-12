/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetFeed;
import org.cggh.chassis.generic.client.gwt.forms.client.BaseForm;
import org.cggh.chassis.generic.client.gwt.forms.client.BaseFormRenderer;
import org.cggh.chassis.generic.client.gwt.forms.client.BaseForm.Resources;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author aliman
 *
 */
public class DatasetFormRenderer 
	extends BaseFormRenderer<DatasetEntry, DatasetFeed> {
	
	
	
	private Log log = LogFactory.getLog(DatasetFormRenderer.class);

	
	
	
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
