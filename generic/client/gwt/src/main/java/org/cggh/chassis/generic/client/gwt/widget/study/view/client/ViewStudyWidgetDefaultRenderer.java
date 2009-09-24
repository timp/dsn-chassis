/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.view.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.client.gwt.widget.application.client.ApplicationConstants;
import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerViewAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.model.client.StudyModel;
import org.cggh.chassis.generic.client.gwt.widget.study.model.client.StudyModelListener;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author raok
 *
 */
public class ViewStudyWidgetDefaultRenderer implements StudyModelListener {

	//Expose view elements for testing purposes.
	final Label titleLabel = new Label();
	final Label summaryLabel = new Label();
	final Panel acceptsClinicalDataIndicator = new SimplePanel();
	final Panel acceptsMolecularDataIndicator = new SimplePanel();
	final Panel acceptsInVitroDataIndicator = new SimplePanel();
	final Panel acceptsPharmacologyDataIndicator = new SimplePanel();
	final Panel loadingPanel = new SimplePanel();
	final Panel studyDetailsPanel = new HorizontalPanel();
	final Label editThisStudyUI = new Label("Edit Study");
	
	final private Panel canvas;
	final private StudyControllerViewAPI controller;

	public ViewStudyWidgetDefaultRenderer(Panel canvas, StudyControllerViewAPI controller) {
		this.canvas = canvas;
		this.controller = controller;
		
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
		
		acceptsClinicalDataIndicator.add(new Label("Accepts Clinical Data"));
		studyDetailsVPanel.add(acceptsClinicalDataIndicator);
		acceptsClinicalDataIndicator.setVisible(false);
		
		acceptsInVitroDataIndicator.add(new Label("Accepts In Vitro Data"));
		studyDetailsVPanel.add(acceptsInVitroDataIndicator);
		acceptsInVitroDataIndicator.setVisible(false);
		
		acceptsMolecularDataIndicator.add(new Label("Accepts Molecular Data"));
		studyDetailsVPanel.add(acceptsMolecularDataIndicator);
		acceptsMolecularDataIndicator.setVisible(false);
		
		acceptsPharmacologyDataIndicator.add(new Label("Accepts Pharmacology Data"));
		studyDetailsVPanel.add(acceptsPharmacologyDataIndicator);
		acceptsPharmacologyDataIndicator.setVisible(false);
		
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

	public void onStudyEntryChanged(StudyEntry before, StudyEntry after) {
		
		summaryLabel.setText(after.getSummary());
		acceptsClinicalDataIndicator.setVisible(after.getModules().contains(ApplicationConstants.MODULE_CLINICAL));
		acceptsInVitroDataIndicator.setVisible(after.getModules().contains(ApplicationConstants.MODULE_IN_VITRO));
		acceptsMolecularDataIndicator.setVisible(after.getModules().contains(ApplicationConstants.MODULE_MOLECULAR));
		acceptsPharmacologyDataIndicator.setVisible(after.getModules().contains(ApplicationConstants.MODULE_PHARMACOLOGY));
	}

	class EditStudyClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent arg0) {
			controller.onUserActionEditThisStudy();
		}
	}

	public void onAcceptClinicalDataChanged(Boolean before, Boolean after, Boolean isValid) {
		acceptsClinicalDataIndicator.setVisible(after);
	}

	public void onAcceptInVitroDataChanged(Boolean before, Boolean after, Boolean isValid) {
		acceptsInVitroDataIndicator.setVisible(after);
	}

	public void onAcceptMolecularDataChanged(Boolean before, Boolean after, Boolean isValid) {
		acceptsMolecularDataIndicator.setVisible(after);
	}

	public void onAcceptPharmacologyDataChanged(Boolean before, Boolean after, Boolean isValid) {
		acceptsPharmacologyDataIndicator.setVisible(after);
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
	
}
