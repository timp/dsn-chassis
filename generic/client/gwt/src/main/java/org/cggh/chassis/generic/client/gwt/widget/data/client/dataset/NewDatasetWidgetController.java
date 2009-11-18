/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.atom.client.ui.AtomCrudWidgetModel;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;

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
