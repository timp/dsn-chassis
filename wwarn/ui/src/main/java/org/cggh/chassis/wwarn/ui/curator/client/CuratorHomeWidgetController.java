
package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author timp
 *
 */
public class CuratorHomeWidgetController {
	

	private Log log = LogFactory.getLog(CuratorHomeWidgetController.class);
	
	
	private CuratorHomeWidget owner;
	private CuratorHomeWidgetModel model;


	public CuratorHomeWidgetController(CuratorHomeWidget owner, CuratorHomeWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}


	

}
