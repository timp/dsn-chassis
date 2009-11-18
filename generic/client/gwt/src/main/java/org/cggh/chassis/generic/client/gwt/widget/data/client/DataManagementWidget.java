/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client;


import org.cggh.chassis.generic.atom.client.ui.CreateSuccessEvent;
import org.cggh.chassis.generic.atom.client.ui.CreateSuccessHandler;
import org.cggh.chassis.generic.atom.client.ui.UpdateSuccessEvent;
import org.cggh.chassis.generic.atom.client.ui.UpdateSuccessHandler;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.DataFileActionEvent;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.DataFileActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.EditDataFileWidget;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.MyDataFilesWidget;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.NewDataFileWidget;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.UploadDataFileRevisionSuccessEvent;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.UploadDataFileRevisionSuccessHandler;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.UploadDataFileRevisionWidget;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.ViewDataFileWidget;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.DatasetActionEvent;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.DatasetActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.EditDatasetWidget;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.MyDatasetsWidget;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.NewDatasetWidget;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.ViewDatasetWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.MenuEvent;
import org.cggh.chassis.generic.widget.client.MultiWidget;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class DataManagementWidget 
	extends MultiWidget {

	
	
	


	// utility fields
	private Log log = LogFactory.getLog(DataManagementWidget.class);
	
	
	
	
	
	// UI fields
	private NewDataFileWidget newDataFileWidget;
	private MyDataFilesWidget myDataFilesWidget;
	private NewDatasetWidget newDatasetWidget;
	private MyDatasetsWidget myDatasetsWidget;
	private ViewDataFileWidget viewDataFileWidget;
	private EditDataFileWidget editDataFileWidget;
	private EditDatasetWidget editDatasetWidget;
	private ViewDatasetWidget viewDatasetWidget;
	private UploadDataFileRevisionWidget uploadDataFileRevisionWidget;






	@Override
	protected void renderMainChildren() {
		log.enter("renderMainChildren");
		
		this.newDataFileWidget = new NewDataFileWidget();
		this.viewDataFileWidget = new ViewDataFileWidget();
		this.editDataFileWidget = new EditDataFileWidget();
		this.myDataFilesWidget = new MyDataFilesWidget();
		this.uploadDataFileRevisionWidget = new UploadDataFileRevisionWidget();
		
		this.newDatasetWidget = new NewDatasetWidget();
		this.viewDatasetWidget = new ViewDatasetWidget();
		this.editDatasetWidget = new EditDatasetWidget();
		this.myDatasetsWidget = new MyDatasetsWidget();
		
		this.mainChildren.add(this.newDataFileWidget);
		this.mainChildren.add(this.viewDataFileWidget);
		this.mainChildren.add(this.editDataFileWidget);
		this.mainChildren.add(this.myDataFilesWidget);
		this.mainChildren.add(this.uploadDataFileRevisionWidget);
		this.mainChildren.add(this.newDatasetWidget);
		this.mainChildren.add(this.viewDatasetWidget);
		this.mainChildren.add(this.editDatasetWidget);
		this.mainChildren.add(this.myDatasetsWidget);
		
		log.leave();
	}

	
	
	


	@Override
	protected void renderMenuBar() {
		log.enter("renderMenuBar");
		
		Command newDataFileMenuCommand = new Command() {
			public void execute() {	
				setActiveChild(newDataFileWidget); 
				fireEvent(new MenuEvent());
			}
		};
		
		Command myDataFilesMenuCommand = new Command() {
			public void execute() {
				setActiveChild(myDataFilesWidget);
				fireEvent(new MenuEvent());
			}
		};
		
		Command newDatasetMenuCommand = new Command() {
			public void execute() {	
				setActiveChild(newDatasetWidget); 
				fireEvent(new MenuEvent());
			}
		};
		
		Command myDatasetsMenuCommand = new Command() {
			public void execute() {	
				setActiveChild(myDatasetsWidget); 
				fireEvent(new MenuEvent());
			}
		};

		// normally we should render the UI components (in this case, menu items)
		// during the rendering phase, then add event handlers later during the
		// binding phase, but the GWT API doesn't let us
		this.menu.addItem(new MenuItem("new data file", newDataFileMenuCommand));
		this.menu.addItem(new MenuItem("my data files", myDataFilesMenuCommand));
		this.menu.addSeparator();
		this.menu.addItem(new MenuItem("new dataset", newDatasetMenuCommand));
		this.menu.addItem(new MenuItem("my datasets", myDatasetsMenuCommand));

		log.leave();
	}





	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.MultiWidget#registerHandlersForChildWidgetEvents()
	 */
	@Override
	protected void registerHandlersForChildWidgetEvents() {

		this.registerHandlersForNewDataFileWidgetEvents();
		this.registerHandlersForViewDataFileWidgetEvents();
		this.registerHandlersForUploadDataFileRevisionWidgetEvents();
		this.registerHandlersForMyDataFilesWidgetEvents();
		this.registerHandlersForEditDataFileWidgetEvents();

		this.registerHandlersForNewDatasetWidgetEvents();
		this.registerHandlersForViewDatasetWidgetEvents();
		this.registerHandlersForMyDatasetsWidgetEvents();
		this.registerHandlersForEditDatasetWidgetEvents();

	}





	


	/**
	 * 
	 */
	private void registerHandlersForNewDataFileWidgetEvents() {
		log.enter("registerHandlersForNewDataFileWidgetEvents");
		
		HandlerRegistration a = this.newDataFileWidget.addCancelHandler(new CommonCancelHandler());

		HandlerRegistration b = this.newDataFileWidget.addCreateSuccessHandler(new CreateSuccessHandler<DataFileEntry>() {
			
			public void onCreateSuccess(CreateSuccessEvent<DataFileEntry> e) {
				log.enter("onCreateSuccess");
				
				viewDataFileWidget.viewEntry(e.getEntry().getId());
				setActiveChild(viewDataFileWidget);
				
				log.leave();
			}
			
		});
		
		HandlerRegistration c = this.newDataFileWidget.addErrorHandler(new CommonErrorHandler());
		
		this.childWidgetEventHandlerRegistrations.add(a);
		this.childWidgetEventHandlerRegistrations.add(b);
		this.childWidgetEventHandlerRegistrations.add(c);
		
		log.leave();
	}


	
	
	
	/**
	 * 
	 */
	private void registerHandlersForViewDataFileWidgetEvents() {
		log.enter("registerHandlersForViewDataFileWidgetEvents");
		
		HandlerRegistration a = this.viewDataFileWidget.addEditDataFileActionHandler(new DataFileActionHandler() {
			private Log log = LogFactory.getLog(this.getClass());
			public void onAction(DataFileActionEvent e) {
				log.enter("onAction");
				
				editDataFileWidget.editEntry(e.getEntry());
				setActiveChild(editDataFileWidget);
				
				log.leave();
			}
		});
		
		HandlerRegistration b = this.viewDataFileWidget.addUploadRevisionActionHandler(new DataFileActionHandler() {
			private Log log = LogFactory.getLog(this.getClass());
			public void onAction(DataFileActionEvent e) {
				log.enter("onAction");
				
				uploadDataFileRevisionWidget.setEntry(e.getEntry());
				setActiveChild(uploadDataFileRevisionWidget);
				
				log.leave();
			}
		});
		
		this.childWidgetEventHandlerRegistrations.add(a);
		this.childWidgetEventHandlerRegistrations.add(b);
		
		log.leave();
	}




	/**
	 * 
	 */
	private void registerHandlersForUploadDataFileRevisionWidgetEvents() {
		log.enter("registerHandlersForUploadDataFileRevisionWidgetEvents");
		
		HandlerRegistration a = this.uploadDataFileRevisionWidget.addSuccessHandler(new UploadDataFileRevisionSuccessHandler() {
			private Log log = LogFactory.getLog(this.getClass());
			public void onUploadDataFileRevisionSuccess(UploadDataFileRevisionSuccessEvent e) {
				log.enter("onUploadDataFileRevisionSuccess");
				
				viewDataFileWidget.viewEntry(e.getEntry().getId());
				setActiveChild(viewDataFileWidget);
				
				log.leave();
			}
		});

		HandlerRegistration b = this.uploadDataFileRevisionWidget.addCancelHandler(new CommonCancelHandler());
		
		this.childWidgetEventHandlerRegistrations.add(a);
		this.childWidgetEventHandlerRegistrations.add(b);
		
		log.leave();
	}




	
	/**
	 * 
	 */
	private void registerHandlersForMyDataFilesWidgetEvents() {
		log.enter("registerHandlersForMyDataFilesWidgetEvents");
		
		HandlerRegistration a = this.myDataFilesWidget.addViewDataFileActionHandler(new DataFileActionHandler() {
			
			public void onAction(DataFileActionEvent e) {
				log.enter("onAction");
				
				viewDataFileWidget.viewEntry(e.getEntry().getId());
				setActiveChild(viewDataFileWidget);
				
				log.leave();
			}
		});

		this.childWidgetEventHandlerRegistrations.add(a);

		log.leave();
	}






	/**
	 * 
	 */
	private void registerHandlersForEditDataFileWidgetEvents() {
		log.enter("registerHandlersForEditDataFileWidgetEvents");
		
		HandlerRegistration a = this.editDataFileWidget.addCancelHandler(new CommonCancelHandler());

		HandlerRegistration b = this.editDataFileWidget.addUpdateSuccessHandler(new UpdateSuccessHandler<DataFileEntry>() {
			
			public void onUpdateSuccess(UpdateSuccessEvent<DataFileEntry> e) {
				log.enter("onUpdateSuccess");
				
				viewDataFileWidget.viewEntry(e.getEntry().getId());
				setActiveChild(viewDataFileWidget);
				
				log.leave();
			}
			
		});
		
		HandlerRegistration c = this.editDataFileWidget.addErrorHandler(new CommonErrorHandler());
		
		this.childWidgetEventHandlerRegistrations.add(a);
		this.childWidgetEventHandlerRegistrations.add(b);
		this.childWidgetEventHandlerRegistrations.add(c);		

		log.leave();
	}




	/**
	 * 
	 */
	private void registerHandlersForNewDatasetWidgetEvents() {
		log.enter("registerHandlersForNewDatasetWidgetEvents");
		
		HandlerRegistration a = this.newDatasetWidget.addCancelHandler(new CommonCancelHandler());
		
		HandlerRegistration b = this.newDatasetWidget.addErrorHandler(new CommonErrorHandler());
		
		HandlerRegistration c = this.newDatasetWidget.addCreateSuccessHandler(new CreateSuccessHandler<DatasetEntry>() {
			
			public void onCreateSuccess(CreateSuccessEvent<DatasetEntry> e) {
				log.enter("onSuccess");
				
				viewDatasetWidget.viewEntry(e.getEntry().getId());
				setActiveChild(viewDatasetWidget);
				
				log.leave();
				
			}
		});
		
		this.childWidgetEventHandlerRegistrations.add(a);
		this.childWidgetEventHandlerRegistrations.add(b);
		this.childWidgetEventHandlerRegistrations.add(c);		
		
		log.leave();
		
	}




	/**
	 * 
	 */
	private void registerHandlersForViewDatasetWidgetEvents() {

		HandlerRegistration a  = this.viewDatasetWidget.addEditDatasetActionHandler(new DatasetActionHandler() {
			
			public void onAction(DatasetActionEvent e) {

//				editDatasetWidget.setEntry(e.getEntry());
				editDatasetWidget.editEntry(e.getEntry());
				setActiveChild(editDatasetWidget);
				
			}
		});
		
		this.childWidgetEventHandlerRegistrations.add(a);
		
	}





	/**
	 * 
	 */
	private void registerHandlersForMyDatasetsWidgetEvents() {

		HandlerRegistration a = this.myDatasetsWidget.addViewDatasetActionHandler(new DatasetActionHandler() {
			
			public void onAction(DatasetActionEvent e) {
				log.enter("onAction");
				
				viewDatasetWidget.viewEntry(e.getEntry().getId());
				setActiveChild(viewDatasetWidget);
				
				log.leave();
			}
		});

		this.childWidgetEventHandlerRegistrations.add(a);
		
	}




	
	/**
	 * 
	 */
	private void registerHandlersForEditDatasetWidgetEvents() {
		log.enter("registerHandlersForEditDatasetWidgetEvents");
		
		HandlerRegistration a = this.editDatasetWidget.addCancelHandler(new CommonCancelHandler());

		HandlerRegistration b = this.editDatasetWidget.addUpdateSuccessHandler(new UpdateSuccessHandler<DatasetEntry>() {
			
			public void onUpdateSuccess(UpdateSuccessEvent<DatasetEntry> e) {
				log.enter("onUpdateSuccess");
				
				viewDatasetWidget.viewEntry(e.getEntry().getId());
				setActiveChild(viewDatasetWidget);
				
				log.leave();
			}
			
		});
		
		HandlerRegistration c = this.editDatasetWidget.addErrorHandler(new CommonErrorHandler());
		
		this.childWidgetEventHandlerRegistrations.add(a);
		this.childWidgetEventHandlerRegistrations.add(b);
		this.childWidgetEventHandlerRegistrations.add(c);		

		log.leave();
	}









	@Override
	protected void setActiveChild(Widget child, boolean memorise) {
		log.enter("setActiveChild");
		
		super.setActiveChild(child, memorise);
		
		if (child == newDatasetWidget) {
			newDatasetWidget.reset();
		}
		
		if (child == myDataFilesWidget) {
			myDataFilesWidget.refreshDataFiles();
		}

		if (child == myDatasetsWidget) {
			myDatasetsWidget.refreshDatasets();
		}

		log.leave();
	}






}
