/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client;

import org.cggh.chassis.generic.atom.rewrite.client.AtomFactory;
import org.cggh.chassis.generic.atom.rewrite.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atom.rewrite.client.datafile.DataFileFactory;
import org.cggh.chassis.generic.atom.rewrite.client.datafile.DataFileFeed;
import org.cggh.chassis.generic.client.gwt.forms.client.BaseForm;
import org.cggh.chassis.generic.client.gwt.forms.client.BaseFormRenderer;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author aliman
 *
 */
public class DataFileForm extends BaseForm<DataFileEntry, DataFileFeed> {

	
	
	
	private Log log = LogFactory.getLog(DataFileForm.class);
	
	
	
	
	public DataFileForm() {
		log.enter("<constructor>");
		// nothing to do
		log.leave();
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.forms.client.BaseForm#createRenderer()
	 */
	@Override
	protected BaseFormRenderer<DataFileEntry> createRenderer() {
		return new DataFileFormRenderer();
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.forms.client.BaseForm#createFactory()
	 */
	@Override
	protected AtomFactory<DataFileEntry, DataFileFeed> createAtomFactory() {
		return new DataFileFactory();
	}
	
	

	public static class Resources extends org.cggh.chassis.generic.client.gwt.forms.client.BaseForm.Resources {
		
		public Resources() {
			super(DataFileForm.class.getName());
		}
		
	}






}
