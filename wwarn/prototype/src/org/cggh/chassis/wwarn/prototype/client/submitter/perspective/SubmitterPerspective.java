/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.perspective;



import org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponentFactory;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspective;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveController;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveModel;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.alldatadicts.SubmitterWidgetAllDataDictionaries;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.allstudies.SubmitterWidgetAllStudies;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.home.SubmitterWidgetHome;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.mydatadicts.SubmitterWidgetMyDataDictionaries;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.mystudies.SubmitterWidgetMyStudies;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.mysubmissions.SubmitterWidgetMySubmissions;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.newdatadict.SubmitterWidgetNewDataDictionary;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.newstudy.SubmitterWidgetNewStudy;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.newsubmission.SubmitterWidgetNewSubmission;
import org.cggh.chassis.wwarn.prototype.client.user.RoleNames;



/**
 * @author aliman
 *
 */
public class SubmitterPerspective extends BasePerspective {


	
	private Renderer renderer;


	
	public SubmitterPerspective() {
		super();
		this.log.setCurrentClass(SubmitterPerspective.class.getName());
		this.init();
	}

	
	
	private void init() {
		log.enter("init");

		log.trace("register main components");
		FractalUIComponentFactory.register(SubmitterWidgetHome.class.getName(), SubmitterWidgetHome.creator);
		FractalUIComponentFactory.register(SubmitterWidgetNewStudy.class.getName(), SubmitterWidgetNewStudy.creator);
		FractalUIComponentFactory.register(SubmitterWidgetAllStudies.class.getName(), SubmitterWidgetAllStudies.creator);
		FractalUIComponentFactory.register(SubmitterWidgetMyStudies.class.getName(), SubmitterWidgetMyStudies.creator);
		FractalUIComponentFactory.register(SubmitterWidgetNewSubmission.class.getName(), SubmitterWidgetNewSubmission.creator);
		FractalUIComponentFactory.register(SubmitterWidgetMySubmissions.class.getName(), SubmitterWidgetMySubmissions.creator);
		FractalUIComponentFactory.register(SubmitterWidgetNewDataDictionary.class.getName(), SubmitterWidgetNewDataDictionary.creator);
		FractalUIComponentFactory.register(SubmitterWidgetMyDataDictionaries.class.getName(), SubmitterWidgetMyDataDictionaries.creator);
		FractalUIComponentFactory.register(SubmitterWidgetAllDataDictionaries.class.getName(), SubmitterWidgetAllDataDictionaries.creator);
		
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
		// TODO
	}



	@Override
	public String getDefaultMainWidgetName() {
		return SubmitterWidgetHome.class.getName();
	}

}
