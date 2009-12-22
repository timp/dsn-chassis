/**
 * 
 */
package org.cggh.chassis.wwarn.client.gwt.main.client;

import org.cggh.chassis.generic.client.gwt.application.client.ChassisGeneric;
import org.cggh.chassis.generic.log.client.AllenSauerLog;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.core.client.EntryPoint;



/**
 * @author aliman
 *
 */
public class MainEntryPoint implements EntryPoint {
	
	
	
	static {
		LogFactory.create = AllenSauerLog.create;
		LogFactory.hide("*");
//		LogFactory.show("org.cggh.chassis.generic.xquestion.client.*");
		LogFactory.show("org.cggh.chassis.generic.widget.client.*");
		LogFactory.show("org.cggh.chassis.generic.async.client.*");
//		LogFactory.show("org.cggh.chassis.generic.client.gwt.main.*");
//		LogFactory.show("org.cggh.chassis.generic.client.gwt.application.*");
//		LogFactory.show("org.cggh.chassis.generic.client.gwt.common.*");
//		LogFactory.show("org.cggh.chassis.generic.client.gwt.widget.data.client.DataManagementWidget");
//		LogFactory.show("org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.DatasetDataSharingWidget");
//		LogFactory.show("org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.ShareDatasetWidget*");
//		LogFactory.show("org.cggh.chassis.generic.client.gwt.widget.study.*");
		LogFactory.show("org.cggh.chassis.generic.client.gwt.widget.submission.client.SelectCuratorWidget*");
	}




	private Log log = LogFactory.getLog(MainEntryPoint.class);
	
	

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		log.enter("onModuleLoad");
		
		ChassisGeneric.run();

		log.leave();
	}

	
	
	
}