/**
 * 
 */
package spike.admin.collection.client;

import org.cggh.chassis.generic.client.gwt.configuration.client.JsConfiguration;
import org.cggh.chassis.generic.client.gwt.widget.admin.collection.client.AdminCollectionWidget;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
public class SpikeAdminCollectionWidgetEntryPoint implements EntryPoint {

	private AdminCollectionWidget studies;
	private AdminCollectionWidget submissions;
	private AdminCollectionWidget datafiles;

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {

		RootPanel root = RootPanel.get();
		
		root.add(new HTML("<h1>Spike Admin Collection Widget</h1>"));

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

		root.add(buttonsPanel);

		root.add(new HTML("<h2>Studies</h2>"));
		
		studies = new AdminCollectionWidget("Studies", JsConfiguration.getStudyCollectionUrl());
		root.add(studies);
		
		root.add(new HTML("<h2>Submissions</h2>"));
		
		submissions = new AdminCollectionWidget("Submissions", JsConfiguration.getSubmissionCollectionUrl());
		root.add(submissions);

		root.add(new HTML("<h2>Data Files</h2>"));
		
		datafiles = new AdminCollectionWidget("Data Files", JsConfiguration.getDataFileCollectionUrl());
		root.add(datafiles);

		studies.refreshStatus();
		submissions.refreshStatus();
		datafiles.refreshStatus();
		
	}

}
