package org.cggh.chassis.wwarn.ui.curator.client;

import static org.cggh.chassis.generic.widget.client.HtmlElements.strongWidget;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.ext.ChassisHelper;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.ObservableProperty;
import org.cggh.chassis.generic.widget.client.PropertyChangeEvent;
import org.cggh.chassis.generic.widget.client.PropertyChangeHandler;

import org.cggh.chassis.generic.widget.client.WidgetEvent;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;
import org.cggh.chassis.generic.widget.client.WidgetEventChannel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;


/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
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

	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;

	@UiField FlowPanel curatorHomePanel;
	
    @UiField ListStudiesWidget listStudiesWidgetUiField;



	public final ObservableProperty<String> message = new ObservableProperty<String>();

	public final WidgetEventChannel listStudiesViewStudyNavigationEventChannel = new WidgetEventChannel(this);



	
	@Override
	public void refresh() {
		log.enter("refresh");
		
		listStudiesWidgetUiField.refresh();


		log.leave();	
	}
	

	@Override
	protected void renderUI() {
		log.enter("renderUI");
		
		this.clear();
		this.add(uiBinder.createAndBindUi(this));
		

		log.leave();
	}


	@Override
	protected void bindUI() {
		super.bindUI();

		// Much the same as registerHandlersForChildWidgetEvents in ChassisWidgetRenderer
		// ListStudies events

		this.childWidgetEventHandlerRegistrations.add(
				listStudiesWidgetUiField.listStudiesViewStudyNavigationEventChannel.addHandler(new WidgetEventHandler<WidgetEvent>() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(ListStudiesViewStudyNavigation)");
				listStudiesViewStudyNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
 

		// Model changes


		message.addChangeHandler(new PropertyChangeHandler<String>() {
			public void onChange(PropertyChangeEvent<String> e) {
				log.enter("onChange<String>");		
				syncUIWithMessage(e.getAfter());
				log.leave();
			}
		});


	
	}



	
	

	

	@Override
	protected void syncUI() {
		log.enter("syncUI");
		
		errorPanel.setVisible(false);	
		contentPanel.setVisible(true);
		this.setVisible(true);

		log.leave();
    }




	protected void syncUIWithMessage(String message) {
		log.enter("syncUIWithMessage");

		if (message != null) {
			contentPanel.setVisible(false);
			errorMessage.clear();
			errorMessage.add(new HTML(message));
			errorPanel.setVisible(true);
		}

		log.leave();
	}
	
}
