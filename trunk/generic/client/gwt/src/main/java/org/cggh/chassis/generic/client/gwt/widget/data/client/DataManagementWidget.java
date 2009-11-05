/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client;
import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidget;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class DataManagementWidget extends ChassisWidget {

	
	
	
	public static final String NAME = "dataManagementWidget";

	
	
	
	private Log log;
	private NewDataFileWidget newDataFileWidget;
	private MyDataFilesWidget myDataFilesWidget;
	private NewDatasetWidget newDatasetWidget;
	private MyDatasetsWidget myDatasetsWidget;
	private ViewDataFileWidget viewDataFileWidget;
	private EditDataFileWidget editDataFileWidget;
	private EditDatasetWidget editDatasetWidget;
	private ViewDatasetWidget viewDatasetWidget;
	private MenuBar menuBar;




	private Widget activeChild;
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#getName()
	 */
	@Override
	protected String getName() {
		return NAME;
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		// TODO

		log.leave();
	}
	
	
	

	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(DataManagementWidget.class);
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#renderUI()
	 */
	@Override
	protected void renderUI() {
		log.enter("renderUI");
		
		this.newDataFileWidget = new NewDataFileWidget();
		this.viewDataFileWidget = new ViewDataFileWidget();
		this.editDataFileWidget = new EditDataFileWidget();
		this.myDataFilesWidget = new MyDataFilesWidget();
		
		this.newDatasetWidget = new NewDatasetWidget();
		this.viewDatasetWidget = new ViewDatasetWidget();
		this.editDatasetWidget = new EditDatasetWidget();
		this.myDatasetsWidget = new MyDatasetsWidget();
		
		this.add(this.newDataFileWidget);
		this.add(this.viewDataFileWidget);
		this.add(this.editDataFileWidget);
		this.add(this.myDataFilesWidget);
		this.add(this.newDatasetWidget);
		this.add(this.viewDatasetWidget);
		this.add(this.editDatasetWidget);
		this.add(this.myDatasetsWidget);
		
		this.menuBar = new MenuBar(true);
		
		log.leave();
	}

	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#bindUI()
	 */
	@Override
	protected void bindUI() {
		log.enter("bindUI");
		
		Command newDataFileMenuCommand = new Command() {
			public void execute() {	setActiveChild(newDataFileWidget); }
		};
		
		Command myDataFilesMenuCommand = new Command() {
			public void execute() {	setActiveChild(myDataFilesWidget); }
		};
		
		Command newDatasetMenuCommand = new Command() {
			public void execute() {	setActiveChild(newDatasetWidget); }
		};
		
		Command myDatasetsMenuCommand = new Command() {
			public void execute() {	setActiveChild(myDatasetsWidget); }
		};

		this.menuBar.addItem(new MenuItem("new data file", newDataFileMenuCommand));
		this.menuBar.addItem(new MenuItem("my data files", myDataFilesMenuCommand));
		this.menuBar.addItem(new MenuItem("new dataset", newDatasetMenuCommand));
		this.menuBar.addItem(new MenuItem("my datasets", myDatasetsMenuCommand));

		log.leave();
	}

	
	
	
	
	/**
	 * @param child
	 */
	protected void setActiveChild(Widget child) {
		log.enter("setActiveChild");
		
		this.activeChild = child;
		this.syncUI();
		
		log.leave();
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#syncUI()
	 */
	@Override
	protected void syncUI() {
		log.enter("syncUI");
		
		this.hideAllWidgets();
		this.showActiveChild();
		
		log.leave();
	}
	
	
	
	
	/**
	 * 
	 */
	private void hideAllWidgets() {
		log.enter("hideAllChildren");
		
		for (Widget w : this.getWidgets()) {
			w.setVisible(false);
		}
		
		log.leave();
	}




	/**
	 * 
	 */
	private void showActiveChild() {
		log.enter("showActiveChild");
		
		if (this.activeChild != null) this.activeChild.setVisible(true);
		
		log.leave();
		
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#destroy()
	 */
	@Override
	public void destroy() {
		log.enter("destroy");
		
		this.unbindUI();
		
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

	
	
	
	public MenuBar getMenu() {
		return this.menuBar;
	}
	
	
	
	

}
