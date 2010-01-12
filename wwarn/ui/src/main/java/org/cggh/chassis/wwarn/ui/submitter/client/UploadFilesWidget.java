package org.cggh.chassis.wwarn.ui.submitter.client;

import java.util.HashMap;
import java.util.Map;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.MapMemory;
import org.cggh.chassis.generic.widget.client.WidgetMemory;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;

public class UploadFilesWidget extends ChassisWidget {

	
	
	
	private Log log = LogFactory.getLog(UploadFilesWidget.class);
	
	
	
	
	private String titleId = HTMLPanel.createUniqueId();
	private String subTitleId = HTMLPanel.createUniqueId();
	private String viewStudyWidgetContainerId = HTMLPanel.createUniqueId();
	private String actionsParaId = HTMLPanel.createUniqueId();
	
	
	
	
	private String template = 
		"<h1 id=\""+titleId+"\"></h1>" +
		"<h2 id=\""+subTitleId+"\"></h2>" +
		"<p>Selected study: <span id=\""+viewStudyWidgetContainerId+"\"></span></p>" +
		"<p>TODO</p>" +
		"<p id=\""+actionsParaId+"\"></p>";
	
	
	

	// UI fields
	private HTMLPanel content;
	private Button proceedButton;
	private ViewStudyWidget viewStudyWidget;
	
	
	
	// state fields
	private String selectedStudyId;

	
	
	
	public UploadFilesWidget() {
		super();
		
		this.memory = new Memory();
		
	}
	
	
	
	
	@Override
	public void renderUI() {
	
		this.content = new HTMLPanel(this.template);
		
		this.content.add(new HTML("Submitter - Submit Data"), this.titleId); // TODO i18n
		
		this.content.add(new HTML("1. Select Study &gt; <span class=\"currentStep\">2. Upload Files</span> &gt; 3. Submit &gt; 4. Add Information"), this.subTitleId); // TODO i18n

		this.viewStudyWidget = new ViewStudyWidget();
		this.content.add(this.viewStudyWidget, this.viewStudyWidgetContainerId);
		
		this.proceedButton = new Button("Proceed &gt;&gt;"); // TODO i18n
		this.content.add(this.proceedButton, this.actionsParaId);
		
		// TODO
		
		this.add(this.content);
	}
	
	
	
	
	@Override
	public void bindUI() {
		
		HandlerRegistration a = this.proceedButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				ProceedActionEvent e = new ProceedActionEvent();
				fireEvent(e);
			}
		});
		
		this.childWidgetEventHandlerRegistrations.add(a);

	}
	
	
	
	
	public HandlerRegistration addProceedActionHandler(ProceedActionHandler h) {
		return this.addHandler(h, ProceedActionEvent.TYPE);
	}
	
	
	
	
	public void setSelectedStudy(String id) {
		this.selectedStudyId = id;
	}
	
	
	
	@Override
	public void refresh() {
		this.refreshSelectedStudy();
		this.refreshUploadedFiles();
	}
	
	
	
	
	@Override
	public Deferred<ChassisWidget> refreshAndCallback() {
		log.enter("refreshAndCallback");

		Deferred<ViewStudyWidget> d1 = this.refreshSelectedStudy();

		// TODO refresh files

		Deferred<ChassisWidget> deferredSelf = d1.adapt(new Function<ViewStudyWidget, ChassisWidget>() {

			public ChassisWidget apply(ViewStudyWidget in) {
				return UploadFilesWidget.this;
			}
			
		});
		
		log.leave();
		return deferredSelf;
	}




	/**
	 * 
	 */
	private void refreshUploadedFiles() {
		log.enter("refreshUploadedFiles");
		// TODO Auto-generated method stub
		
		log.leave();
	}




	private Deferred<ViewStudyWidget> refreshSelectedStudy() {
		return this.viewStudyWidget.retrieveStudy(this.selectedStudyId);
	}
	
	
	
	
	private class Memory extends MapMemory {

		
		
		private static final String KEY_STUDYID = "studyid";
		
		
		
		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.MapMemory#createMnemonicMap()
		 */
		@Override
		public Map<String, String> createMnemonicMap() {
			log.enter("createMnemonicMap");
			
			Map<String, String> map = new HashMap<String, String>();
			
			// TODO Auto-generated method stub
			
			log.leave();
			return map;
		}

		
		
		
		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.MapMemory#remember(java.util.Map)
		 */
		@Override
		public Deferred<WidgetMemory> remember(Map<String, String> mnemonic) {
			log.enter("remember");
			
			Deferred<WidgetMemory> deferredMemory;
			
			String studyId = mnemonic.get(KEY_STUDYID);
			
			log.debug("found studyId: "+studyId);
			
			if (studyId != null) {
				
				log.debug("set selected study id");
				setSelectedStudy(studyId);
				
				log.debug("refresh and call back");
				deferredMemory = refreshAndCallback().adapt(new Function<ChassisWidget, WidgetMemory>() {

					public WidgetMemory apply(ChassisWidget in) {
						return Memory.this;
					}
					
				});
			}
			
			else {
				
				log.debug("call back immediately");
				deferredMemory = new Deferred<WidgetMemory>();
				deferredMemory.callback(this);
				
			}
			
			log.leave();
			return deferredMemory;
		}
		
		
		
		
	}
	

}
