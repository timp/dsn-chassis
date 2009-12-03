/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;


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
public class EditDataFileForm 
	extends BaseForm<DataFileEntry, DataFileFeed, EditDataFileFormRenderer> {
	
	
	
	// utility fields
	private Log log;
	
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		super.init();
		
		log.leave();
	}

	
	
	
	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(EditDataFileForm.class); 
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.forms.client.BaseForm#createAtomFactory()
	 */
	@Override
	protected AtomFactory<DataFileEntry, DataFileFeed> createAtomFactory() {
		return new DataFileFactory();
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.forms.client.BaseForm#createRenderer()
	 */
	@Override
	protected EditDataFileFormRenderer createRenderer() {
		log.enter("createRenderer");
		
		EditDataFileFormRenderer renderer = new EditDataFileFormRenderer(this);
		
		log.leave();
		return renderer;
	}
	
	
	
	
	
	public static class Resources extends org.cggh.chassis.generic.client.gwt.forms.client.BaseForm.Resources {
		
		public Resources() {
			super(EditDataFileForm.class.getName());
		}
		
	}

	

}
