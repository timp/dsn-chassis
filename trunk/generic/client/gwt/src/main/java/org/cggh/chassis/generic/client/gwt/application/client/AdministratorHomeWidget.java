/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.client.gwt.widget.admin.collection.client.AdminCollectionWidget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author aliman
 *
 */
public class AdministratorHomeWidget extends Composite {

	
	
	
	private static final String STYLENAME_BASE = "chassis-administratorHome";
	private static final String STYLENAME_RESOURCECOLLECTIONS = STYLENAME_BASE + "-resourceCollections";
	private Panel canvas = new FlowPanel();
	
	
	
	
	public AdministratorHomeWidget() {
		this.canvas.addStyleName(STYLENAME_BASE);
		this.canvas.add(new HTML("<h2>Administrator Home</h2>"));
		this.constructAdminCollectionWidgets();
		this.initWidget(this.canvas);
	}




	/**
	 * 
	 */
	private void constructAdminCollectionWidgets() {
		
		FlowPanel collectionsPanel = new FlowPanel();
		collectionsPanel.addStyleName(STYLENAME_RESOURCECOLLECTIONS);
		this.canvas.add(collectionsPanel);
		
		collectionsPanel.add(new HTML("<h3>Data &amp; Metadata Collections</h3>"));
				
		final AdminCollectionWidget studies = new AdminCollectionWidget("Studies", Configuration.getStudyFeedURL());
		final AdminCollectionWidget submissions = new AdminCollectionWidget("Submissions", Configuration.getSubmissionFeedURL());
		final AdminCollectionWidget datafiles = new AdminCollectionWidget("Data Files", Configuration.getDataFileFeedURL());

		Button refreshAllButton = new Button();
		refreshAllButton.setText("refresh all");
		refreshAllButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent arg0) {
				studies.refreshStatus();
				submissions.refreshStatus();
				datafiles.refreshStatus();
			}
			
		});

		Button createAllButton = new Button();
		createAllButton.setText("create all");
		createAllButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent arg0) {
				studies.createCollection();
				submissions.createCollection();
				datafiles.createCollection();
			}
			
		});

		HorizontalPanel buttonsPanel = new HorizontalPanel();
		buttonsPanel.add(refreshAllButton);
		buttonsPanel.add(createAllButton);

		collectionsPanel.add(buttonsPanel);

		collectionsPanel.add(new HTML("<h4>Studies</h4>"));
		
		collectionsPanel.add(studies);
		
		collectionsPanel.add(new HTML("<h4>Submissions</h4>"));
		
		collectionsPanel.add(submissions);

		collectionsPanel.add(new HTML("<h4>Data Files</h4>"));
		
		collectionsPanel.add(datafiles);

		studies.refreshStatus();
		submissions.refreshStatus();
		datafiles.refreshStatus();
		
		// TODO Auto-generated method stub
		
	}
	
	
	
}
