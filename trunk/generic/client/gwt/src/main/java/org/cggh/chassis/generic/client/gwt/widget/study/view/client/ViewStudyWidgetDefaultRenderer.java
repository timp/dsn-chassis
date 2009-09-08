/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.view.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author raok
 *
 */
public class ViewStudyWidgetDefaultRenderer implements ViewStudyWidgetModelListener {

	//TODO remove when real atom service is used
	public static final String MODULE_CLINICAL = "Clinical";
	public static final String MODULE_MOLECULAR = "Molecular";
	public static final String MODULE_IN_VITRO = "In Vitro";
	public static final String MODULE_PHARMACOLOGY = "Pharmacology";
	
	
	//Expose view elements for testing purposes.
	final Label titleLabel = new Label();
	final Label summaryLabel = new Label();
	final Panel acceptsClinicalDataIndicator = new SimplePanel();
	final Panel acceptsMolecularDataIndicator = new SimplePanel();
	final Panel acceptsInVitroDataIndicator = new SimplePanel();
	final Panel acceptsPharmacologyDataIndicator = new SimplePanel();
	final Panel loadingPanel = new SimplePanel();
	final Panel studyDetailsPanel = new SimplePanel();
	
	final private Panel canvas;

	public ViewStudyWidgetDefaultRenderer(Panel canvas) {
		this.canvas = canvas;
		
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
		
	}

	public void onStatusChanged(Integer before, Integer after) {
		
		if (after == ViewStudyWidgetModel.STATUS_LOADING) {
			canvas.clear();
			canvas.add(loadingPanel);
		} else if (after == ViewStudyWidgetModel.STATUS_LOADED) {
			canvas.clear();
			canvas.add(studyDetailsPanel);
		} else if (after == ViewStudyWidgetModel.STATUS_ERROR) {
			// TODO handle error case (could use extra panel or pass error to parent)
		}
	}

	public void onStudyEntryChanged(StudyEntry before, StudyEntry after) {
		
		titleLabel.setText(after.getTitle());
		summaryLabel.setText(after.getSummary());
		acceptsClinicalDataIndicator.setVisible(after.getModules().contains(MODULE_CLINICAL));
		acceptsInVitroDataIndicator.setVisible(after.getModules().contains(MODULE_IN_VITRO));
		acceptsMolecularDataIndicator.setVisible(after.getModules().contains(MODULE_MOLECULAR));
		acceptsPharmacologyDataIndicator.setVisible(after.getModules().contains(MODULE_PHARMACOLOGY));
	}

}
