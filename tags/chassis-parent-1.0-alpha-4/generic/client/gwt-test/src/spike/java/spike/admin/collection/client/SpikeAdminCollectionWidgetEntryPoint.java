/**
 * 
 */
package spike.admin.collection.client;

import org.cggh.chassis.generic.client.gwt.configuration.client.JsConfiguration;
import org.cggh.chassis.generic.client.gwt.widget.admin.client.AdminCollectionWidget;
import static org.cggh.chassis.generic.widget.client.HtmlElements.*;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
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
		
		root.add(h1Widget("Spike Admin Collection Widget"));

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

		root.add(h2Widget("Studies"));
		
		studies = new AdminCollectionWidget("Studies", JsConfiguration.getStudyCollectionUrl());
		root.add(studies);
		
		root.add(h2Widget("Submissions"));
		
		submissions = new AdminCollectionWidget("Submissions", JsConfiguration.getSubmissionCollectionUrl());
		root.add(submissions);

		root.add(h2Widget("Data Files"));
		
		datafiles = new AdminCollectionWidget("Data Files", JsConfiguration.getDataFileCollectionUrl());
		root.add(datafiles);

		studies.refreshStatus();
		submissions.refreshStatus();
		datafiles.refreshStatus();
		
	}

}
