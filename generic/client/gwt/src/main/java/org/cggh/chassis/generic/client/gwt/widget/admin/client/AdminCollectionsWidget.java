/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.admin.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class AdminCollectionsWidget extends ChassisWidget {
	
	
	
	// utility fields
	private Log log;

	
	
	// state fields
	String[][] collections;




	// UI fields
	private FlowPanel tableContainer;
	private Button refreshAllButton;
	private List<AdminCollectionWidget> collectionWidgets;
	private Button createAllButton;
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		this.collections = null;

		log.leave();
	}
	
	
	
	
	
	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(AdminCollectionsWidget.class);
	}





	public AdminCollectionsWidget(String[][] collections) {
		this.collections = collections;
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#renderUI()
	 */
	@Override
	protected void renderUI() {
		log.enter("renderUI");

		this.clear();
		
		this.collectionWidgets = new ArrayList<AdminCollectionWidget>();
		
		this.add(new HTML("<h3>Data &amp; Metadata Collections</h3>"));
		
		this.tableContainer = new FlowPanel();
		this.add(this.tableContainer);
		
		this.refreshAllButton = new Button();
		this.refreshAllButton.setText("refresh all");

		this.createAllButton = new Button();
		this.createAllButton.setText("create all");

		FlowPanel buttonsPanel = new FlowPanel();
		buttonsPanel.add(refreshAllButton);
		buttonsPanel.add(createAllButton);
		
		this.add(buttonsPanel);

		log.leave();
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#bindUI()
	 */
	@Override
	protected void bindUI() {
		log.enter("bindUI");

		HandlerRegistration a = this.refreshAllButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent arg0) {
				for (AdminCollectionWidget w : collectionWidgets) {
					w.refreshStatus();
				}
			}
			
		});

		HandlerRegistration b = createAllButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent arg0) {
				for (AdminCollectionWidget w : collectionWidgets) {
					w.createCollection();
				}
			}
			
		});
		
		this.childWidgetEventHandlerRegistrations.add(a);
		this.childWidgetEventHandlerRegistrations.add(b);

		log.leave();
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#syncUI()
	 */
	@Override
	protected void syncUI() {
		log.enter("syncUI");

		List<Widget[]> rows = new ArrayList<Widget[]>();
		
		Widget[] headerRow = {
			new HTML("<strong>Collection Title</strong>"),
			new HTML("<strong>URL</strong>"),
			new HTML("<strong>Status</strong>"),
			new HTML("<strong>Actions</strong>"),
		};
		
		rows.add(headerRow);

		this.collectionWidgets.clear();

		for (String[] collection : this.collections) {
			AdminCollectionWidget w = new AdminCollectionWidget(collection[0], collection[1]);
			this.collectionWidgets.add(w);
			FlowPanel statusPanel = new FlowPanel();
			statusPanel.add(w.getStatusCodeLabel());
			statusPanel.add(w.getStatusTextLabel());
			Widget[] row = {
				w.getTitleLabel(),
				w.getUrlLabel(),
				statusPanel,
				w.getActionsPanel()
			};
			rows.add(row);
		}
		
		FlexTable table = RenderUtils.renderResultsTable(rows);
		this.tableContainer.clear();
		this.tableContainer.add(table);

		log.leave();
	}

	
	
	

	/**
	 * 
	 */
	public void refreshAll() {
		log.enter("refreshAll");
		
		for (AdminCollectionWidget w : this.collectionWidgets) {
			w.refreshStatus();
		}
		
		log.leave();
	}

	

	
}
