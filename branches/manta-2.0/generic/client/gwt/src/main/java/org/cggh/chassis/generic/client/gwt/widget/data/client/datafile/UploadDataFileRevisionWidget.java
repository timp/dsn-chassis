/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class UploadDataFileRevisionWidget extends
		DelegatingWidget<UploadDataFileRevisionWidgetModel, UploadDataFileRevisionWidgetRenderer> {
	
	
	
	
	
	private Log log = LogFactory.getLog(UploadDataFileRevisionWidget.class);

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected UploadDataFileRevisionWidgetModel createModel() {
		return new UploadDataFileRevisionWidgetModel();
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected UploadDataFileRevisionWidgetRenderer createRenderer() {
		return new UploadDataFileRevisionWidgetRenderer(this);
	}
	
	
	
	

	
	
	public HandlerRegistration addSuccessHandler(UploadDataFileRevisionSuccessHandler h) {
		return this.addHandler(h, UploadDataFileRevisionSuccessEvent.TYPE);
	}





	/**
	 * @param dataFileEntry
	 */
	public void setEntry(DataFileEntry dataFileEntry) {
		log.enter("setEntry");

		this.model.setEntry(dataFileEntry);
		
		log.leave();
	}






	
}
