/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

/**
 * @author aliman
 *
 */
public class ChassisClientController {

	private ChassisClient owner;
	private ChassisClientModel model;

	/**
	 * @param owner
	 * @param model 
	 */
	public ChassisClientController(ChassisClient owner, ChassisClientModel model) {
		this.owner = owner;
		this.model = model;
	}

	/**
	 * @param roleId
	 */
	public void switchPerspective(Integer roleId) {
		this.model.setPerspective(roleId);
	}

}
