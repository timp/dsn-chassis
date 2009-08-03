/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.curator.perspective;

import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspective;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveController;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveModel;
import org.cggh.chassis.wwarn.prototype.client.shared.RoleNames;



/**
 * @author aliman
 *
 */
public class CuratorPerspective extends BasePerspective {


	
	public static final String WIDGET_MYSUBMISSIONS = "mysubmissions";
	public static final String WIDGET_ALLSUBMISSIONS = "allsubmissions";
	public static final String WIDGET_NEWSTANDARDDATADICTIONARY = "newstandarddatadictionary";
	public static final String WIDGET_ALLSTANDARDDATADICTIONARIES = "allstandarddatadictionaries";
	public static final String WIDGET_NEWRELEASECRITERIA = "newreleasecriteria";
	public static final String WIDGET_ALLRELEASECRITERIA = "allreleasecriteria";
	public static final String WIDGET_DELEGATENEWTASK = "delegatenewtask";
	public static final String WIDGET_MYDELEGATEDTASKS = "mydelegatedtasks";

	
	
	private Renderer renderer;

	
	
	public CuratorPerspective() {
		super();
		this.log.setCurrentClass(CuratorPerspective.class.getName());
		this.init();
	}
	

	
	private void init() {
		log.enter("init");

		log.trace("init model");
		model = new BasePerspectiveModel();

		log.trace("init controller");
		controller = new BasePerspectiveController(model, this); 

		log.trace("init renderer");
		renderer = new Renderer(this, controller, model);
		model.addListener(renderer);
		
		log.leave();
	}


	
	public String getRoleName() {
		return RoleNames.CURATOR;
	}


	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}


}
