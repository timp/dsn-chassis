/**
 * 
 */
package org.cggh.chassis.wwarn.ui.submitter.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.miniatom.client.ext.Chassis;
import org.cggh.chassis.generic.miniatom.client.ext.ChassisHelper;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.PropertyChangeEvent;
import org.cggh.chassis.generic.widget.client.PropertyChangeHandler;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.wwarn.ui.common.client.RenderUtils;
import org.cggh.chassis.wwarn.ui.submitter.client.AddInformationWidgetModel.ReadyForInteractionStatus;
import org.cggh.chassis.wwarn.ui.submitter.client.AddInformationWidgetModel.RetrieveStudyPendingStatus;
import org.cggh.chassis.wwarn.ui.submitter.client.AddInformationWidgetModel.RetrieveSubmissionPendingStatus;
import org.cggh.chassis.wwarn.ui.submitter.client.AddInformationWidgetModel.SaveStudyPendingStatus;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class AddInformationWidgetRenderer extends
		ChassisWidgetRenderer<AddInformationWidgetModel> {

	
	
	
	private Log log = LogFactory.getLog(AddInformationWidgetRenderer.class);
	
	
	
	private AddInformationWidgetView view;



	public void setView(AddInformationWidgetView view) {
		this.view = view;
	}

	
	
	
	@Override
	protected void renderUI() {
		
		canvas.clear();
		canvas.add(view.root);
		
		//TODO Set to something sensible 
		view.studyTitleLabel.setText("FOO");
		
	}
	
	
	
	@Override
	public void registerHandlersForModelChanges() {
		super.registerHandlersForModelChanges();
		
		model.status.addChangeHandler(syncUIWithStatus);
		
		model.submissionEntryElement.addChangeHandler(new PropertyChangeHandler<Element>() {
			public void onChange(PropertyChangeEvent<Element> e) {
				syncUIWithSubmissionEntryElement(e.getAfter());
			}
		});
		
		model.studyEntryElement.addChangeHandler(new PropertyChangeHandler<Element>() {
			public void onChange(PropertyChangeEvent<Element> e) {
				syncUIWithStudyEntry(e.getAfter());
			}
		});
		
	}
	
	
	
	protected final PropertyChangeHandler<Status> syncUIWithStatus = new PropertyChangeHandler<Status>() {
		
		public void onChange(PropertyChangeEvent<Status> e) {
			log.enter("onChange");
			
			Status status = e.getAfter();
			
			// deal with retrieve submission pending panel
			boolean retrieveSubmissionPendingPanelVisibility = (status instanceof RetrieveSubmissionPendingStatus);
			view.retrieveSubmissionPendingPanel.setVisible(retrieveSubmissionPendingPanelVisibility);
			
			// deal with main content panel visibility
			if (status instanceof RetrieveSubmissionPendingStatus)
				view.mainContentPanel.setVisible(false);
			else
				view.mainContentPanel.setVisible(true);
			
			// deal with retrieve study pending panel
			if (status instanceof RetrieveStudyPendingStatus) 
				view.retrieveStudyPendingPanel.setVisible(true);
			else 
				view.retrieveStudyPendingPanel.setVisible(false);
			
			// deal with save study pending panel
			if (status instanceof SaveStudyPendingStatus) 
				view.saveStudyPendingPanel.setVisible(true);
			else 
				view.saveStudyPendingPanel.setVisible(false);
			
			// deal with study questionnaire panel
			if (status instanceof ReadyForInteractionStatus)
				view.studyQuestionnairePanel.setVisible(true);
			else 
				view.studyQuestionnairePanel.setVisible(false);
				
			
			// TODO Auto-generated method stub
			
			log.leave();
		}
		
	};




	/**
	 * @param studyEntryElement
	 */
	protected void syncUIWithStudyEntry(Element studyEntryElement) {
		log.enter("syncUIWithStudyEntry");
		
		String title = AtomHelper.getTitle(studyEntryElement);
		view.studyTitleLabel.setText(title);
		
		Element studyElement = ChassisHelper.getStudy(AtomHelper.getContent(studyEntryElement));
		log.debug(studyElement.toString());
		
		if (view.getQuestionnaire() != null) 
			view.getQuestionnaire().init(studyElement);
		
		log.leave();
	}




	/**
	 * @param submissionEntry
	 */
	protected void syncUIWithSubmissionEntryElement(Element submissionEntry) {
		log.enter("syncUIWithSubmissionEntryElement");
		
		List<Element> submissionPartLinks = AtomHelper.getLinks(submissionEntry, Chassis.REL_SUBMISSIONPART);
		List<Element> fileEntries = new ArrayList<Element>();
		for (Element e : submissionPartLinks) {
			Element fileEntry = AtomHelper.getFirstEntry(e);
			fileEntries.add(fileEntry);
		}
		
		FlexTable filesTable = renderUploadsTable(fileEntries);
		view.filesTableContainer.clear();
		view.filesTableContainer.add(filesTable);
		
		log.leave();
	}

	
	
	// TODO refactor
	
	private FlexTable renderUploadsTable(List<Element> entries) {
		
		List<List<Widget>> rows = new ArrayList<List<Widget>>();

		List<Widget> headerRow = new ArrayList<Widget>();
		headerRow.add(new Label("File Name")); // i18n
		headerRow.add(new Label("Type"));  // i18n
		headerRow.add(new Label("Size")); // i18n
		headerRow.add(new Label("Uploaded"));  // i18n
		
		rows.add(headerRow);
		
		for (Element entry : entries) {
			
			String title = AtomHelper.getTitle(entry);
			
			Element categoryElement = AtomHelper.getFirstCategory(entry, Chassis.SCHEME_FILETYPES);
			String type = getTypeLabel(categoryElement);
			
			String fileSizeAsString = AtomHelper.getMediaResourceSize(entry);
			String created = AtomHelper.getPublishedAsTime(entry);
			
			List<Widget> row = new ArrayList<Widget>();
			row.add(new Label(title));	
			row.add(new Label(type));	
			row.add(new Label(fileSizeAsString));	
			row.add(new Label(created));
			rows.add(row);
			
		}
		
		return RenderUtils.renderResultsTable(rows);
	}

	
	
	// TODO refactor
	
	private static String getTypeLabel(Element categoryElement) {

		String term = AtomHelper.getTermAttr(categoryElement);

		if (term == null) {
			return "";
		}
		else if (term.equals(Chassis.TERM_DATAFILE)) {
			return "Data File"; // TODO i18n
		}
		else if (term.equals(Chassis.TERM_DATADICTIONARY)) {
			return "Data Dictionary"; // TODO i18n
		}
		else if (term.equals(Chassis.TERM_PROTOCOL)) {
			return "Protocol"; // TODO i18n
		}
		else if (term.equals(Chassis.TERM_OTHER)) {
			String labelAttrValue = AtomHelper.getLabelAttr(categoryElement);
			return (labelAttrValue != null) ? labelAttrValue : "";
		}
		else {
			return "";
		}

	}




}
