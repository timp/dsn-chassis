/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.client.gwt.widget.study.client.ViewStudyWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.client.ViewStudyWidgetRenderer;
import org.cggh.chassis.generic.client.gwt.widget.study.client.sq.ViewStudyQuestionnaireWidget;




/**
 * @author aliman
 *
 */
public class CustomViewStudyWidgetRenderer extends ViewStudyWidgetRenderer {

	private ViewStudyQuestionnaireWidget viewStudyQuestionnaireWidget;



	/**
	 * @param owner
	 */
	public CustomViewStudyWidgetRenderer(ViewStudyWidget owner) {
		super(owner);
	}

	
	
	@Override
	protected void setMainPanelStyle() {
		// do nothing
	}


	
	
	@Override
	protected void renderActionsWidget() {
		// do nothing
	}


	
	
	@Override
	protected void renderDatasetsSection() {
		// do nothing
	}
	
	
	
	@Override
	protected void renderContentPanel() {
		super.renderContentPanel();
		
		this.renderStudyQuestionnaireInline();
		
	}



	/**
	 * 
	 */
	private void renderStudyQuestionnaireInline() {

		this.viewStudyQuestionnaireWidget = new CustomViewStudyQuestionnaireWidget();
		this.contentPanel.add(this.viewStudyQuestionnaireWidget);
		
	}
	
	
	
	
	/**
	 * @param entry
	 */
	@Override
	protected void syncEntryUI(StudyEntry entry) {
		super.syncEntryUI(entry);
		
		if (entry != null) {
			
			this.viewStudyQuestionnaireWidget.setEntry(entry);

		}
		else {
			
			this.viewStudyQuestionnaireWidget.reset();

		}
		
	}
	
	
	
	

	


}
