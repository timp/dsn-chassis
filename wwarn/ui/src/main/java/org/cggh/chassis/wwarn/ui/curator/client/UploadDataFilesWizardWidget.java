package org.cggh.chassis.wwarn.ui.curator.client;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.MultiWidget;
import org.cggh.chassis.generic.widget.client.ObservableProperty;
import org.cggh.chassis.generic.widget.client.WidgetEventChannel;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;

/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 * @author timp
 *
 */
public class UploadDataFilesWizardWidget 
		extends MultiWidget {

	private static final Log log = LogFactory.getLog(UploadDataFilesWizardWidget.class);

	private UploadCuratedDataFilesWidget uploadCuratedDataFilesWidget;
	private SelectDerivationFilesWidget selectDerivationFilesWidget;
	private CurationSummaryWidget curationSummaryWidget;


	public final ObservableProperty<String> studyUrl = new ObservableProperty<String>();


	public final ObservableProperty<Element> studyEntry = new ObservableProperty<Element>();




	
	@Override
	public void renderMainChildren() {
		log.enter("renderMainChildren");

		this.uploadCuratedDataFilesWidget = new UploadCuratedDataFilesWidget();
		this.mainChildren.add(this.uploadCuratedDataFilesWidget);

		// Set default child
		this.defaultChild = this.uploadCuratedDataFilesWidget;

		this.selectDerivationFilesWidget = new SelectDerivationFilesWidget();
		this.mainChildren.add(this.selectDerivationFilesWidget);


		this.curationSummaryWidget = new CurationSummaryWidget();
		this.mainChildren.add(this.curationSummaryWidget);


		log.leave();

	}


	@Override
	public void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");
		super.registerHandlersForChildWidgetEvents();
		

		// UploadCuratedDataFiles events

		// SelectDerivationFiles events

		// CurationSummary events
		log.leave();
	}
	

	
	
	@Override
	public void refresh() {
		log.enter("refresh");
		
		((ChassisWidget)this.defaultChild).refresh();

		// uploadCuratedDataFilesWidget.refresh();


		// selectDerivationFilesWidget.refresh();


		// curationSummaryWidget.refresh();

		log.leave();	
	}
	
	@Override
	public void setActiveChild(Widget child, boolean memorise) {
		log.enter("setActiveChild");
		
		super.setActiveChild(child, memorise);
		
		// override to trigger refresh if set as active child
		if (child == this.defaultChild && child instanceof ChassisWidget) {
			((ChassisWidget)child).refresh();
		}

		log.leave();
	}

	
	
}
