/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.curator.perspective;

import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspective;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveController;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveModel;
import org.cggh.chassis.wwarn.prototype.client.user.RoleNames;



/**
 * @author aliman
 *
 */
public class CuratorPerspective extends BasePerspective {


	
	public static final String WIDGET_MYSUBMISSIONS = "curator-mysubmissions";
	public static final String WIDGET_ALLSUBMISSIONS = "curator-allsubmissions";
	public static final String WIDGET_NEWSTANDARDDATADICTIONARY = "curator-newstandarddatadictionary";
	public static final String WIDGET_ALLSTANDARDDATADICTIONARIES = "curator-allstandarddatadictionaries";
	public static final String WIDGET_NEWRELEASECRITERIA = "curator-newreleasecriteria";
	public static final String WIDGET_ALLRELEASECRITERIA = "curator-allreleasecriteria";
	public static final String WIDGET_DELEGATENEWTASK = "curator-delegatenewtask";
	public static final String WIDGET_MYDELEGATEDTASKS = "curator-mydelegatedtasks";

	
	
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
