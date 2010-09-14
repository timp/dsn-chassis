/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.admin.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class AdminCollectionWidgetRenderer implements AdminCollectionWidgetModelListener {

	
	
	
	private Grid canvas;
	Label titleLabel;
	Label urlLabel;
	Label statusTextLabel;
	HorizontalPanel actionsPanel;
	Label statusCodeLabel;
	private AdminCollectionWidgetController controller;

	public AdminCollectionWidgetRenderer(AdminCollectionWidgetController controller) {
		this.controller = controller;
		this.initCanvas();
	}
	
	
	
	/**
	 * 
	 */
	private void initCanvas() {

		this.canvas = new Grid(2, 5);
		this.canvas.setBorderWidth(1);
		this.canvas.setCellPadding(3);
		
		this.canvas.setHTML(0, 0, "<strong>Collection Title</strong>");
		this.canvas.setHTML(0, 1, "<strong>URL</strong>");
		this.canvas.setHTML(0, 2, "<strong>Status</strong>");
		this.canvas.setHTML(0, 4, "<strong>Actions</strong>");
		
		this.titleLabel = new Label();
		this.canvas.setWidget(1, 0, this.titleLabel);
		
		this.urlLabel = new Label();
		this.canvas.setWidget(1, 1, this.urlLabel);
		
		this.statusCodeLabel = new Label();
		this.canvas.setWidget(1, 2, this.statusCodeLabel);
		
		this.statusTextLabel = new Label();
		this.canvas.setWidget(1, 3, this.statusTextLabel);
		
		this.actionsPanel = new HorizontalPanel();
		this.canvas.setWidget(1, 4, this.actionsPanel);
	
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.admin.collection.client.AdminCollectionWidgetModelListener#onErrorChanged(boolean, boolean)
	 */
	public void onErrorChanged(boolean before, boolean error) {

		if (error) {
			this.actionsPanel.clear();
			this.actionsPanel.add(new Label("an unexpected error has occurred"));
			// TODO broadcast?
		}

	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.admin.collection.client.AdminCollectionWidgetModelListener#onPendingChanged(boolean, boolean)
	 */
	public void onPendingChanged(boolean before, boolean pending) {
		
		if (pending) {
			this.actionsPanel.clear();
			this.actionsPanel.add(new Label("pending..."));
			this.statusCodeLabel.setText("...");
			this.statusTextLabel.setText("...");
		}

	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.admin.collection.client.AdminCollectionWidgetModelListener#onResponseHeadersChanged(java.lang.String, java.lang.String)
	 */
	public void onResponseHeadersChanged(String before, String responseHeaders) {
		
		// TODO broadcast?

	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.admin.collection.client.AdminCollectionWidgetModelListener#onResponseTextChanged(java.lang.String, java.lang.String)
	 */
	public void onResponseTextChanged(String before, String responseText) {

		// TODO broadcast?

	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.admin.collection.client.AdminCollectionWidgetModelListener#onStatusCodeChanged(int, int)
	 */
	public void onStatusCodeChanged(int before, int statusCode) {
		
		this.statusCodeLabel.setText(Integer.toString(statusCode));
		
		this.actionsPanel.clear();
		
		Button refreshButton = new Button();
		refreshButton.setText("refresh");
		refreshButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent arg0) {
				controller.refreshStatus();
			}
			
		});
		this.actionsPanel.add(refreshButton);
		
		if (statusCode == 404) {

			Button createButton = new Button();
			createButton.setText("create");
			createButton.addClickHandler(new ClickHandler() {

				public void onClick(ClickEvent arg0) {
					controller.createCollection();
				}
				
			});
			this.actionsPanel.add(createButton);

		}
		
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.admin.collection.client.AdminCollectionWidgetModelListener#onStatusTextChanged(java.lang.String, java.lang.String)
	 */
	public void onStatusTextChanged(String before, String statusText) {
		
		this.statusTextLabel.setText(statusText);

	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.admin.collection.client.AdminCollectionWidgetModelListener#onTitleChanged(java.lang.String, java.lang.String)
	 */
	public void onTitleChanged(String before, String title) {
		
		this.titleLabel.setText(title);

	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.admin.collection.client.AdminCollectionWidgetModelListener#onUrlChanged(java.lang.String, java.lang.String)
	 */
	public void onUrlChanged(String before, String url) {
		
		this.urlLabel.setText(url);

	}



	/**
	 * @return
	 */
	public Widget getCanvas() {
		return this.canvas;
	}

}
