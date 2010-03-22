package org.cggh.chassis.wwarn.ui.curator.client;


import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.WidgetEvent;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.HTMLPanel;


/**
 * @author timp
 */
public class CuratorHomeWidgetRenderer extends
		ChassisWidgetRenderer<CuratorHomeWidgetModel> {

	private Log log = LogFactory.getLog(CuratorHomeWidgetRenderer.class);

	@UiTemplate("CuratorHomeWidget.ui.xml")
	interface CuratorHomeWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, CuratorHomeWidgetRenderer> {
	}

	private static CuratorHomeWidgetRendererUiBinder uiBinder =
			GWT.create(CuratorHomeWidgetRendererUiBinder.class);

	@UiField HTMLPanel mainPanel;
	@UiField HTMLPanel contentPanel;

	@UiField ListStudiesWidget listStudiesWidgetUiField;
	
	private CuratorHomeWidget owner;

	public CuratorHomeWidgetRenderer(CuratorHomeWidget owner) {
		this.owner = owner;
	}

	@Override
	protected void renderUI() {

		log.enter("renderUI");
		
		this.canvas.clear();
		this.canvas.add(uiBinder.createAndBindUi(this));
		
		log.leave();
	}


	
	@Override
	public void registerHandlersForModelChanges() {
		
	}


	@Override
	public void registerHandlersForChildWidgetEvents() {
		super.registerHandlersForChildWidgetEvents();	
		
		HandlerRegistration a = listStudiesWidgetUiField.viewStudyNavigationEventChannel.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				// just bubble
				owner.viewStudyNavigationEventChannel.fireEvent(e);
			}
		});
		this.childWidgetEventHandlerRegistrations.add(a);		
		
	}

}
