/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.view.client;

import java.util.Map;
import java.util.Set;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerViewAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.model.client.StudyModel;
import org.cggh.chassis.generic.client.gwt.widget.study.model.client.StudyModelListener;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author raok
 *
 */
public class ViewStudyWidgetDefaultRenderer implements StudyModelListener {

	//Expose view elements for testing purposes.
	final Label titleLabel = new Label();
	final Label summaryLabel = new Label();
	final Panel loadingPanel = new SimplePanel();
	final Panel studyDetailsPanel = new HorizontalPanel();
	final Label editThisStudyUI = new Label("Edit Study");
	final VerticalPanel modulesListPanel = new VerticalPanel();
	final VerticalPanel authorsListPanel = new VerticalPanel();
	
	final private Panel canvas;
	final private StudyControllerViewAPI controller;
	private Map<String, String> modulesConfig;

	
	
	
	public ViewStudyWidgetDefaultRenderer(Panel canvas, StudyControllerViewAPI controller) {
		this.canvas = canvas;
		this.controller = controller;
		this.modulesConfig = ConfigurationBean.getModules();
		
		initCanvas();
	}

	
	
	
	/**
	 * @param controller2
	 */
	public ViewStudyWidgetDefaultRenderer(StudyControllerViewAPI controller) {
		this.canvas = new FlowPanel();
		this.controller = controller;
		this.modulesConfig = ConfigurationBean.getModules();
		
		initCanvas();
	}

	
	
	
	private void initCanvas() {

		//prepare loading panel
		loadingPanel.add(new Label("Loading..."));
		
		//prepare study details panel
		VerticalPanel studyDetailsVPanel = new VerticalPanel();
		
		HorizontalPanel titleHPanel = new HorizontalPanel();
		titleHPanel.add(new Label("Title: "));
		titleHPanel.add(titleLabel);
		studyDetailsVPanel.add(titleHPanel);
		
		HorizontalPanel summaryHPanel = new HorizontalPanel();
		summaryHPanel.add(new Label("Summary: "));
		summaryHPanel.add(summaryLabel);
		studyDetailsVPanel.add(summaryHPanel);

		studyDetailsVPanel.add(new Label("Data accepted:"));
		studyDetailsVPanel.add(modulesListPanel);
		
		studyDetailsVPanel.add(new Label("Study owners:"));
		studyDetailsVPanel.add(authorsListPanel);
		
		studyDetailsPanel.add(studyDetailsVPanel);
		
		//TODO add other actions
		//Create actions panel
		VerticalPanel actionsVPanel = new VerticalPanel();
		
		//add clickhandler to editStudyUI
		editThisStudyUI.addClickHandler(new EditStudyClickHandler());
		
		actionsVPanel.add(editThisStudyUI);
		
		studyDetailsPanel.add(actionsVPanel);
		
		
	}

	public void onStatusChanged(Integer before, Integer after) {
		
		if (after == StudyModel.STATUS_LOADING) {
			canvas.clear();
			canvas.add(loadingPanel);
		} else if (after == StudyModel.STATUS_LOADED) {
			canvas.clear();
			canvas.add(studyDetailsPanel);
		} else if (after == StudyModel.STATUS_ERROR) {
			// TODO handle error case (could use extra panel or pass error to parent)
		}
	}

	class EditStudyClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent arg0) {
			controller.onUserActionEditThisStudy();
		}
	}

	public void onStudyEntryChanged(Boolean isValid) {
		// TODO Auto-generated method stub
	}

	public void onSummaryChanged(String before, String after, Boolean isValid) {
		summaryLabel.setText(after);
	}

	public void onTitleChanged(String before, String after, Boolean isValid) {
		titleLabel.setText(after);
	}

	public void onModulesChanged(Set<String> before, Set<String> after, Boolean isValid) {

		modulesListPanel.clear();
		
		for (String module : after) {
			modulesListPanel.add(new Label(modulesConfig.get(module)));
		}
	}

	public void onAuthorsChanged(Set<AtomAuthor> before, Set<AtomAuthor> after, Boolean isValid) {

		authorsListPanel.clear();
		
		for (AtomAuthor atomAuthor : after) {
			authorsListPanel.add(new Label(atomAuthor.getEmail()));
		}
		
	}

	/**
	 * @return
	 */
	public Panel getCanvas() {
		return this.canvas;
	}
	
}
