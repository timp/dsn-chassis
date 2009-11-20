/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyQuery;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.DatasetActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.MyDatasetsWidget;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.MyDatasetsWidgetController;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.MyDatasetsWidgetModel;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.MyDatasetsWidgetRenderer;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.ViewDatasetActionEvent;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class MyStudiesWidget 
	extends DelegatingWidget<MyStudiesWidgetModel, MyStudiesWidgetRenderer> 


{

	
	
	private Log log = LogFactory.getLog(MyStudiesWidget.class);
	private MyStudiesWidgetController controller;
	
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected MyStudiesWidgetModel createModel() {
		return new MyStudiesWidgetModel();
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected MyStudiesWidgetRenderer createRenderer() {
		return new MyStudiesWidgetRenderer(this);
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		super.init(); // this will instantiate model and renderer

		this.controller = new MyStudiesWidgetController(this.model, this);

		log.leave();
	}
	
	
	
	
	
	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(MyStudiesWidget.class);
	}




	/**
	 * 
	 */
	public void refreshStudies() {
		log.enter("refreshStudies");
		
		// delegate to controller
		this.controller.refreshStudies();
		
		log.leave();
	}




	public HandlerRegistration addViewStudyActionHandler(StudyActionHandler h) {
		return this.addHandler(h, ViewStudyActionEvent.TYPE);
	}




	
}
