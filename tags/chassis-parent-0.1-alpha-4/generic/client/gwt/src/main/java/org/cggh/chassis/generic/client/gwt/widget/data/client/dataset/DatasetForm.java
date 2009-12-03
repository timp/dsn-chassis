/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.atom.client.AtomFactory;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetFactory;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetFeed;
import org.cggh.chassis.generic.client.gwt.forms.client.BaseForm;

/**
 * @author aliman
 *
 */
public class DatasetForm extends
		BaseForm<DatasetEntry, DatasetFeed, DatasetFormRenderer> {

	
	
	
	
	


	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.forms.client.BaseForm#createAtomFactory()
	 */
	@Override
	protected AtomFactory<DatasetEntry, DatasetFeed> createAtomFactory() {
		return new DatasetFactory();
	}







	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected DatasetFormRenderer createRenderer() {
		return new DatasetFormRenderer(this);
	}

	
	
	
	
	public static class Resources extends org.cggh.chassis.generic.client.gwt.forms.client.BaseForm.Resources {
		
		public Resources() {
			super(DatasetForm.class.getName());
		}
		
	}




}
