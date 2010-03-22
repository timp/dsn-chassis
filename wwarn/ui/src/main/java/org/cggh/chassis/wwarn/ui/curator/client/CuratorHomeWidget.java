package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;
import org.cggh.chassis.generic.widget.client.WidgetEvent;
import org.cggh.chassis.generic.widget.client.WidgetEventChannel;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.HTMLPanel;

/**
 *
 * @author timp
 *
 */
public class CuratorHomeWidget 
	 	extends ChassisWidget {

	private static final Log log = LogFactory.getLog(CuratorHomeWidget.class);
	
	@UiTemplate("CuratorHomeWidget.ui.xml")
	interface CuratorHomeWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, CuratorHomeWidget> {
	}

	private static CuratorHomeWidgetRendererUiBinder uiBinder =
			GWT.create(CuratorHomeWidgetRendererUiBinder.class);

	@UiField HTMLPanel mainPanel;
	@UiField HTMLPanel contentPanel;

	@UiField ListStudiesWidget listStudiesWidgetUiField;
	

	@Override
	protected void renderUI() {

		log.enter("renderUI");
		
		this.add(uiBinder.createAndBindUi(this));
		
		this.childWidgetEventHandlerRegistrations.add(
				listStudiesWidgetUiField.viewStudyNavigationEventChannel.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				viewStudyNavigationEventChannel.fireEvent(e);
			}
		}));

		log.leave();
	}


	@Override
	public void refresh() {
		log.enter("refresh");

		listStudiesWidgetUiField.refresh();
		
		log.leave();
	}
	
	
	
	public final WidgetEventChannel viewStudyNavigationEventChannel = new WidgetEventChannel(this);

	

	public void registerHandlersForChildWidgetEvents() {
		
		
	}

	

}
