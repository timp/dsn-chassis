/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client;

import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileFeed;
import org.cggh.chassis.generic.client.gwt.forms.client.BaseFormRenderer;
import org.cggh.chassis.generic.client.gwt.widget.data.client.DataFileForm.Resources;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;


/**
 * @author aliman
 *
 */
public class DataFileFormRenderer extends BaseFormRenderer<DataFileEntry, DataFileFeed> {

	
	
	
	private Log log = LogFactory.getLog(DataFileFormRenderer.class);
	
	
	
	public DataFileFormRenderer(DataFileForm owner) {
		super(owner);
		log.enter("<constructor>");
		// nothing to do
		log.leave();
	}

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.forms.client.BaseFormRenderer#createResources()
	 */
	@Override
	protected org.cggh.chassis.generic.client.gwt.forms.client.BaseForm.Resources createResources() {
		return new Resources();
	}
	
	
	
}
