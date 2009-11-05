/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client;

import org.cggh.chassis.generic.atom.client.AtomFactory;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileFactory;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileFeed;
import org.cggh.chassis.generic.client.gwt.forms.client.BaseForm;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author aliman
 *
 */
public class DataFileForm extends BaseForm<DataFileEntry, DataFileFeed, DataFileFormRenderer> {

	
	
	
	private Log log = LogFactory.getLog(DataFileForm.class);
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		super.init();
		
		// nothing else to do

		log.leave();
	}
	

	
	
	
	
	public DataFileForm() {
		log.enter("<constructor>");
		// nothing to do
		log.leave();
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.forms.client.BaseForm#createRenderer()
	 */
	@Override
	protected DataFileFormRenderer createRenderer() {
		return new DataFileFormRenderer(this);
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



	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(DataFileForm.class);
	}






}
