/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;


import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.client.gwt.widget.admin.collection.client.AdminCollectionsWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidget;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

/**
 * @author aliman
 *
 */
public class AdministratorHomeWidget extends ChassisWidget {

	
	
	
	// utility fields
	private Log log;
	
	
	
	
	// UI fields
	private AdminCollectionsWidget adminCollectionsWidget;
	
	
	
	
	
	// state fields
	private String[][] collections = {
			{ "Studies", Configuration.getStudyCollectionUrl() },	
			{ "Data Files", Configuration.getDataFileCollectionUrl() },
			{ "Datasets", Configuration.getDatasetCollectionUrl() },
			{ "Media", Configuration.getMediaCollectionUrl() },
			{ "Submissions", Configuration.getSubmissionCollectionUrl() },
			{ "Sandbox", Configuration.getSandboxCollectionUrl() }
	};


	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");
		
		// nothing to do
		
		log.leave();
	}




	
	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(AdministratorHomeWidget.class);
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#renderUI()
	 */
	@Override
	protected void renderUI() {
		log.enter("renderUI");
		
		this.add(new HTML("<h2>Administrator Home</h2>"));
		
		FlowPanel collectionsPanel = new FlowPanel();
		this.add(collectionsPanel);
		
		this.adminCollectionsWidget = new AdminCollectionsWidget(collections);
		collectionsPanel.add(this.adminCollectionsWidget);
		
		log.leave();
	}


	
	


	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#bindUI()
	 */
	@Override
	protected void bindUI() {
		log.enter("bindUI");
		
		// nothing to do
		
		log.leave();
	}







	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#syncUI()
	 */
	@Override
	protected void syncUI() {
		log.enter("syncUI");
		
		adminCollectionsWidget.refreshAll();
		
		log.leave();
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#unbindUI()
	 */
	@Override
	protected void unbindUI() {
		log.enter("unbindUI");
		
		// nothing to do
		
		log.leave();
	}
	
	
	
}
