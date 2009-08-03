/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.perspective;



import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspective;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveController;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveModel;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.home.SubmitterWidgetHome;
import org.cggh.chassis.wwarn.prototype.client.user.RoleNames;



/**
 * @author aliman
 *
 */
public class SubmitterPerspective extends BasePerspective {

	public static final String WIDGET_NEWSTUDY = "submitter-newstudy";
	public static final String WIDGET_MYSTUDIES = "submitter-mystudies";
	public static final String WIDGET_ALLSTUDIES = "submitter-allstudies";
	public static final String WIDGET_NEWSUBMISSION = "submitter-newsubmission";
	public static final String WIDGET_MYSUBMISSIONS = "submitter-mysubmissions";
	public static final String WIDGET_NEWDATADICTIONARY = "submitter-newdatadictionary";
	public static final String WIDGET_MYDATADICTIONARIES = "submitter-mydatadictionaries";
	public static final String WIDGET_ALLDATADICTIONARIES = "submitter-alldatadictionaries";
	public static final String ELEMENTID_APPCONTENT = "appcontent";


	
	private Renderer renderer;


	
	public SubmitterPerspective() {
		super();
		this.log.setCurrentClass(SubmitterPerspective.class.getName());
		this.init();
	}

	
	
	private void init() {
		log.enter("init");

		log.trace("init model");
		this.model = new BasePerspectiveModel();

		log.trace("init controller");
		this.controller = new BasePerspectiveController(model, this); 

		log.trace("init renderer");
		this.renderer = new Renderer(this, controller, model);
		model.addListener(renderer);
		
		log.leave();
	}


	
	public final String getRoleName() {
		return RoleNames.SUBMITTER;
	}



	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public String getDefaultMainWidgetName() {
		return SubmitterWidgetHome.class.getName();
	}

}
