/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.MultiSelectModel.SelectItem;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

/**
 * @author aliman
 *
 */
public class MultiSelectRenderer 
	extends AsyncWidgetRenderer<AsyncWidgetModel> {

	
	
	
	private Log log = LogFactory.getLog(MultiSelectRenderer.class);
	private MultiSelectModel selectModel;
	private FlowPanel selectPanel;

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer#renderMainPanel()
	 */
	@Override
	protected void renderMainPanel() {
		log.enter("renderMainPanel");

		this.selectPanel = new FlowPanel();
		this.mainPanel.add(this.selectPanel);

		log.leave();
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer#registerHandlersForChildWidgetEvents()
	 */
	@Override
	protected void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");

		// nothing to do?

		log.leave();
	}





	/**
	 * 
	 */
	public void renderSelectUI() {
		log.enter("renderSelectUI");
		
		// nothing to do?
		
		log.leave();
	}





	/**
	 * @param selectModel
	 */
	public void bindSelectUI(MultiSelectModel selectModel) {
		log.enter("bindSelectUI");
		
		this.selectModel = selectModel;
		
		log.leave();
	}





	/**
	 * 
	 */
	public void syncSelectUI() {
		log.enter("syncSelectUI");
		
		List<SelectItem> items = this.selectModel.getItems();
		
		for (int i=0; i<items.size(); i++) {
			
			final int index = i;
			final SelectItem item = items.get(index);

			this.mainPanel.add(new Label(item.getLabel()));
			
			Button addButton = new Button("add");
			addButton.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					log.enter("onClick");
					
					addItem(index);
					
					log.leave();
				}
			});
			this.mainPanel.add(addButton);
			
			Button removeButton = new Button("remove");
			removeButton.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					log.enter("onClick");
					
					removeItem(index);
					
					log.leave();
				}
			});
			this.mainPanel.add(removeButton);
			
		}
		
		log.leave();
	}





	/**
	 * @param index
	 */
	protected void removeItem(int index) {
		log.enter("removeItem");
		
		selectModel.removeValue(index);
		syncSelectUI();
		
		log.leave();
	}





	/**
	 * @param index
	 */
	protected void addItem(final int index) {
		log.enter("addItem");
		
		// TODO Auto-generated method stub

		log.leave();
	}




}
