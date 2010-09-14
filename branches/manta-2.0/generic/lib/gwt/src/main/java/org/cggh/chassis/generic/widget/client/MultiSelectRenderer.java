/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import static org.cggh.chassis.generic.widget.client.HtmlElements.*;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;

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
		
		this.selectPanel.clear();
		
		List<String> values = this.selectModel.getSelectedValues();
		
		Map<String,String> items = this.selectModel.getItems();
		
		boolean more = (items.keySet().size() > values.size());
		
		for (int i=0; i<values.size(); i++) {
			
			FlowPanel p = new FlowPanel();
			
			final int index = i;
			
			String value = values.get(index);
			log.debug("found value: "+value+"; label: "+items.get(value));
			
			p.add(new InlineLabel(items.get(value)));
			
			Button removeButton = new Button("remove");
			removeButton.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					log.enter("onClick");
					
					removeItem(index);
					
					log.leave();
				}
			});
			p.add(removeButton);

			if (more) {
				
				log.debug("there are more items, provide add button");
				
				Button addButton = new Button("add another");
				addButton.addClickHandler(new ClickHandler() {
					
					public void onClick(ClickEvent event) {
						log.enter("onClick");
						
						addItem(index+1);
						
						log.leave();
					}
				});
				p.add(addButton);
			}
			
			this.selectPanel.add(p);

		}
		
		if (values.size() == 0) {

			log.debug("no selected values found");
			
			Button addButton = new Button("add");
			addButton.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					log.enter("onClick");
					
					addItem(0);
					
					log.leave();
				}
			});
			this.selectPanel.add(addButton);

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
		
		final DialogBox db = new DialogBox();
		FlowPanel content = new FlowPanel();
		db.setText("add item");
		db.setWidget(content);
		
		content.add(pWidget("Please select one..."));
		
		final ListBox lb = new ListBox();
		for (Entry<String,String> entry : this.selectModel.getItems().entrySet()) {
			String item = entry.getValue();
			String value = entry.getKey();
			if (!selectModel.getSelectedValues().contains(value)) {
				lb.addItem(item, value);
			}
		}

		content.add(lb);
		
		Button ok = new Button("ok");
		content.add(ok);
		ok.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				selectModel.addValue(index, lb.getValue(lb.getSelectedIndex()));
				db.hide();
				syncSelectUI();
			}
		});

		Button cancel = new Button("cancel");
		content.add(cancel);
		cancel.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				db.hide();
			}
		});

		db.center();
		db.show();
		
		log.leave();
	}




}
