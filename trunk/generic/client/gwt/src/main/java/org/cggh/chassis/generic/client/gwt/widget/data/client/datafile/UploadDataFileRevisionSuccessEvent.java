/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;


/**
 * @author aliman
 *
 */
public class UploadDataFileRevisionSuccessEvent 
	extends DataFileEvent<UploadDataFileRevisionSuccessHandler> {
	
	public static final Type<UploadDataFileRevisionSuccessHandler> TYPE = new Type<UploadDataFileRevisionSuccessHandler>();
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(UploadDataFileRevisionSuccessHandler handler) {
		handler.onUploadDataFileRevisionSuccess(this);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<UploadDataFileRevisionSuccessHandler> getAssociatedType() {
		return TYPE;
	}
	
}