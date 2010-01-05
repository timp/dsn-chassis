/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import java.util.List;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.client.gwt.common.client.CommonStyles;
import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.client.gwt.common.client.widget.AtomEntryPropertiesWidget;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * @author aliman
 *
 */
public class StudyPropertiesWidget 
	extends AtomEntryPropertiesWidget<StudyEntry> {

	
	
	
	
	// UI fields
	protected FlowPanel modulesPanel, modulesListPanel;

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#renderUI()
	 */
	@Override
	public void renderUI() {

		super.renderUI();

		this.modulesListPanel = new FlowPanel();
		this.modulesPanel = RenderUtils.renderModulesPropertyPanel(this.modulesListPanel, CommonStyles.VALUE_MODULES);

		this.add(this.modulesPanel);	

	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#syncUI()
	 */
	@Override
	public void syncUI() {
		
		super.syncUI();
		
		List<String> moduleIds = (entry != null) ? entry.getStudy().getModules() : null;
		this.syncModulesLabel(moduleIds);

	}

	
	
	
	
	/**
	 * @param object
	 */
	private void syncModulesLabel(List<String> moduleIds) {

		Label answer = RenderUtils.renderModulesAsLabel(moduleIds, Configuration.getModules(), true);
		answer.addStyleName(CommonStyles.ANSWER);

		modulesListPanel.clear();
		modulesListPanel.add(answer);
		
	}
	
	
	
	


}
