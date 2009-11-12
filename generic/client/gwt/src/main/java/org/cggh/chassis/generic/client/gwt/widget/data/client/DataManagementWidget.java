/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client;


import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.CreateDataFileSuccessEvent;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.CreateDataFileSuccessHandler;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.DataFileActionEvent;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.DataFileActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.EditDataFileWidget;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.MyDataFilesWidget;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.NewDataFileWidget;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.UpdateDataFileSuccessEvent;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.UpdateDataFileSuccessHandler;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.UploadDataFileRevisionSuccessEvent;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.UploadDataFileRevisionSuccessHandler;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.UploadDataFileRevisionWidget;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.ViewDataFileWidget;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.CreateDatasetSuccessEvent;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.CreateDatasetSuccessHandler;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.EditDatasetWidget;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.MyDatasetsWidget;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.NewDatasetWidget;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.ViewDatasetWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.CancelEvent;
import org.cggh.chassis.generic.widget.client.CancelHandler;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.ErrorEvent;
import org.cggh.chassis.generic.widget.client.ErrorHandler;
import org.cggh.chassis.generic.widget.client.WidgetMemory;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class DataManagementWidget extends ChassisWidget {

	
	
	





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
	private UploadDataFileRevisionWidget uploadDataFileRevisionWidget;





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		this.memory = new Memory();
		
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
		this.uploadDataFileRevisionWidget = new UploadDataFileRevisionWidget();
		
		this.newDatasetWidget = new NewDatasetWidget();
		this.viewDatasetWidget = new ViewDatasetWidget();
		this.editDatasetWidget = new EditDatasetWidget();
		this.myDatasetsWidget = new MyDatasetsWidget();
		
		this.add(this.newDataFileWidget);
		this.add(this.viewDataFileWidget);
		this.add(this.editDataFileWidget);
		this.add(this.myDataFilesWidget);
		this.add(this.uploadDataFileRevisionWidget);
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
		
		this.bindMenuBar();
		
		this.registerHandlersForNewDataFileWidgetEvents();
		this.registerHandlersForViewDataFileWidgetEvents();
		this.registerHandlersForUploadDataFileRevisionWidgetEvents();
		this.registerHandlersForMyDataFilesWidgetEvents();
		this.registerHandlersForEditDataFileWidgetEvents();
		this.registerHandlersForNewDatasetWidgetEvents();

		// TODO add handlers for other child widget events
		
		log.leave();
	}

	
	
	



	/**
	 * 
	 */
	private void bindMenuBar() {
		log.enter("bindMenuBar");
		
		Command newDataFileMenuCommand = new Command() {
			public void execute() {	setActiveChild(newDataFileWidget); }
		};
		
		Command myDataFilesMenuCommand = new Command() {
			public void execute() {
				setActiveChild(myDataFilesWidget);
			}
		};
		
		Command newDatasetMenuCommand = new Command() {
			public void execute() {	setActiveChild(newDatasetWidget); }
		};
		
		Command myDatasetsMenuCommand = new Command() {
			public void execute() {	setActiveChild(myDatasetsWidget); }
		};

		// normally we should render the UI components (in this case, menu items)
		// during the rendering phase, then add event handlers here, but we have 
		// to actually create the menu items here (during binding phase) because 
		// the GWT API does not allow you to add a menu item without a command
		this.menuBar.addItem(new MenuItem("new data file", newDataFileMenuCommand));
		this.menuBar.addItem(new MenuItem("my data files", myDataFilesMenuCommand));
		this.menuBar.addSeparator();
		this.menuBar.addItem(new MenuItem("new dataset", newDatasetMenuCommand));
		this.menuBar.addItem(new MenuItem("my datasets", myDatasetsMenuCommand));

		log.leave();
	}





	
	
	/**
	 * 
	 */
	private void registerHandlersForNewDataFileWidgetEvents() {
		log.enter("registerHandlersForNewDataFileWidgetEvents");
		
		HandlerRegistration a = this.newDataFileWidget.addCancelHandler(new CommonCancelHandler());

		HandlerRegistration b = this.newDataFileWidget.addSuccessHandler(new CreateDataFileSuccessHandler() {
			
			public void onSuccess(CreateDataFileSuccessEvent e) {
				log.enter("onCreateDataFileSuccess");
				
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
				
				editDataFileWidget.setEntry(e.getEntry());
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
				
				viewDataFileWidget.viewEntry(e.getDataFileEntry().getId());
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

		HandlerRegistration b = this.editDataFileWidget.addSuccessHandler(new UpdateDataFileSuccessHandler() {
			
			public void onSuccess(UpdateDataFileSuccessEvent e) {
				log.enter("onSuccess");
				
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
		
		// TODO Auto-generated method stub
		HandlerRegistration a = this.newDatasetWidget.addCancelHandler(new CommonCancelHandler());
		
		HandlerRegistration b = this.newDatasetWidget.addErrorHandler(new CommonErrorHandler());
		
		HandlerRegistration c = this.newDatasetWidget.addSuccessHandler(new CreateDatasetSuccessHandler() {
			
			public void onSuccess(CreateDatasetSuccessEvent e) {
				log.enter("onSuccess");
				
				viewDatasetWidget.setCurrentEntry(e.getEntry().getId());
				setActiveChild(viewDatasetWidget);
				
				log.leave();
				
			}
		});
		
		log.leave();
		
	}





	/**
	 * @param child
	 */
	protected void setActiveChild(Widget child) {
		this.setActiveChild(child, true);
	}

	
	
	
	
	protected void setActiveChild(Widget child, boolean memorise) {
		log.enter("setActiveChild");
		
		this.activeChild = child;
		this.syncUI();
		
		if (child == myDataFilesWidget) {
			myDataFilesWidget.refreshDataFiles();
		}

		if (child instanceof ChassisWidget) {
			ChassisWidget cw = (ChassisWidget) child;
			this.memory.setChild(cw.getMemory());
		}
		else {
			this.memory.setChild(null);
		}
		
		if (memorise) {
			this.memory.memorise();
		}
		
		log.leave();
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#syncUI()
	 */
	@Override
	protected void syncUI() {
		log.enter("syncUI");
		
		if (this.activeChild == null) this.hideAll();
		else this.showOnly(this.activeChild);
		
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
		
		this.clearChildWidgetEventHandlers();
		
		log.leave();
	}

	
	
	
	public MenuBar getMenu() {
		return this.menuBar;
	}
	
	
	
	
	
	/**
	 * @author aliman
	 *
	 */
	public class Memory extends WidgetMemory {
		private Log log = LogFactory.getLog(Memory.class);

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.WidgetMemory#createMnemonic()
		 */
		@Override
		public String createMnemonic() {
			log.enter("createMnemonic");

			String mnemonic = null;
			
			if (activeChild != null && activeChild instanceof ChassisWidget) {
				mnemonic = this.createMnemonic((ChassisWidget)activeChild);
			}
			
			log.debug("mnemonic: "+mnemonic);

			log.leave();
			return mnemonic;
		}
		
		protected String createMnemonic(ChassisWidget w) {
			// turn the widget name into something slightly more aesthetically pleasing
			return w.getName().toLowerCase().replaceAll("widget", "");
		}

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.WidgetMemory#remember(java.lang.String)
		 */
		@Override
		public Deferred<WidgetMemory> remember(String mnemonic) {
			log.enter("remember");

			Deferred<WidgetMemory> def = new Deferred<WidgetMemory>();
			
			for (Widget w : DataManagementWidget.this) {
				if (w instanceof ChassisWidget) {
					if (this.createMnemonic((ChassisWidget)w).equals(mnemonic)) {
						setActiveChild(w, false);
					}
				}
			}
			
			def.callback(this); // no async action so callback immediately

			log.leave();
			return def;
		}

	}



	

	/**
	 * @author aliman
	 *
	 */
	public class CommonErrorHandler implements ErrorHandler {
		private Log log = LogFactory.getLog(CommonErrorHandler.class);

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.ErrorHandler#onError(org.cggh.chassis.generic.widget.client.ErrorEvent)
		 */
		public void onError(ErrorEvent e) {
			log.enter("onError");

			Window.alert("an unexpected error has occurred");
			log.error("an unexpected error has occurred", e.getException());

			log.leave();
		}

	}
	
	
	
	
	/**
	 * @author aliman
	 *
	 */
	public class CommonCancelHandler implements CancelHandler {
		private Log log = LogFactory.getLog(CommonCancelHandler.class);
			
		public void onCancel(CancelEvent e) {
			log.enter("onCancel");
			
			History.back();
			
			log.leave();
		}

	}








}
