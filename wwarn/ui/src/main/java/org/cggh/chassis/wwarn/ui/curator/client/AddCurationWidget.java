/**
 * 
 */
package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.MultiWidget;

import org.cggh.chassis.generic.widget.client.WidgetEventChannel;


/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 * @author timp
 *
 */
public class AddCurationWidget 
	 	extends MultiWidget {

	private static final Log log = LogFactory.getLog(AddCurationWidget.class);
	



	public UploadCuratedDataFilesWidget uploadCuratedDataFilesWidget;


	public SelectDerivationFilesWidget selectDerivationFilesWidget;


	public CurationSummaryWidget curationSummaryWidget;


	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
	}
	
	@Override
	public void refresh() {
		log.enter("refresh");
		// TODO get data 
		log.leave();	
	}
	
	
	
	

	

}
