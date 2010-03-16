
package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.Atom;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.ErrorEvent;
import org.cggh.chassis.wwarn.ui.common.client.Config;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 * @author timp
 *
 */
public class CuratorHomeWidgetController {
	
	
	private Log log = LogFactory.getLog(CuratorHomeWidgetController.class);
	
	
	private CuratorHomeWidget owner;
	private CuratorHomeWidgetModel model;


	private ListStudiesWidget listStudiesWidget;

 
	
	
	public CuratorHomeWidgetController(CuratorHomeWidget owner, CuratorHomeWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}

	
	

}
