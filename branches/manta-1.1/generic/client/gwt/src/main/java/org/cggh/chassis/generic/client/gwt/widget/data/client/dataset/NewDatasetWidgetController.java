/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetModel;

/**
 * @author aliman
 *
 */
public class NewDatasetWidgetController
	extends DatasetCrudWidgetController {

	
	
	
	public NewDatasetWidgetController(
			NewDatasetWidget owner,
			AtomCrudWidgetModel<DatasetEntry> model
	) {
		super(owner, model); 
	}

	
}
