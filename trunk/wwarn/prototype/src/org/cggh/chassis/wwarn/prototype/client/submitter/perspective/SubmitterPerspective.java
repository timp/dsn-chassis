/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.perspective;



import org.cggh.chassis.gwt.lib.study.client.format.StudyEntry;
import org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponent;
import org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponentFactory;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspective;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.PerspectiveController;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.PerspectiveModel;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.alldatadicts.SubmitterWidgetAllDataDictionaries;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.allstudies.SubmitterWidgetAllStudies;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.editssq.SubmitterWidgetEditSsq;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.editstudy.SubmitterWidgetEditStudy;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.editstudy.SubmitterWidgetEditStudyListener;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.home.SubmitterWidgetHome;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.mydatadicts.SubmitterWidgetMyDataDictionaries;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.mystudies.SubmitterWidgetMyStudies;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.mysubmissions.SubmitterWidgetMySubmissions;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.newdatadict.SubmitterWidgetNewDataDictionary;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.newstudy.SubmitterWidgetNewStudy;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.newstudy.SubmitterWidgetNewStudyListener;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.newsubmission.SubmitterWidgetNewSubmission;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.study.SubmitterWidgetStudy;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.study.SubmitterWidgetStudyListener;
import org.cggh.chassis.wwarn.prototype.client.user.RoleNames;



/**
 * @author aliman
 *
 */
public class SubmitterPerspective extends BasePerspective implements SubmitterWidgetNewStudyListener, SubmitterWidgetStudyListener, SubmitterWidgetEditStudyListener {


	
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
		FractalUIComponentFactory.register(SubmitterWidgetStudy.class.getName(), SubmitterWidgetStudy.creator);
		FractalUIComponentFactory.register(SubmitterWidgetEditStudy.class.getName(), SubmitterWidgetEditStudy.creator);
		FractalUIComponentFactory.register(SubmitterWidgetEditSsq.class.getName(), SubmitterWidgetEditSsq.creator);
		
		log.trace("init model");
		this.model = new PerspectiveModel();

		log.trace("init controller");
		this.controller = new PerspectiveController(model, this); 

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



	@Override
	public void addChild(FractalUIComponent child) {
		super.addChild(child);
	
		// register interest in child events
		
		if (child instanceof SubmitterWidgetNewStudy) {
			SubmitterWidgetNewStudy widget = (SubmitterWidgetNewStudy) child;
			widget.addListener(this);
		}
		
		if (child instanceof SubmitterWidgetStudy) {
			SubmitterWidgetStudy widget = (SubmitterWidgetStudy) child;
			widget.addListener(this);
		}
		
		if (child instanceof SubmitterWidgetEditStudy) {
			SubmitterWidgetEditStudy widget = (SubmitterWidgetEditStudy) child;
			widget.addListener(this);
		}

	}



	public void onStudyCreationSuccess(StudyEntry study) {
		log.enter("onStudyCreationSuccess");
		log.trace("study title: "+study.getTitle());
		
		controller.setMainWidget(SubmitterWidgetStudy.class.getName(), false); // do not set waypoint here
		
		for (FractalUIComponent child : children) {
			if (child instanceof SubmitterWidgetStudy) {
				SubmitterWidgetStudy widget = (SubmitterWidgetStudy) child;
				widget.setMessage("Study created OK.", false); // no waypoint here
				widget.setStudyEntry(study); // waypoint here
			}
		}

		log.leave();
	}
	
	
	
	public void onActionEditStudy(StudyEntry study, SubmitterWidgetStudy source) {
		log.enter("onActionEditStudy");
		
		controller.setMainWidget(SubmitterWidgetEditStudy.class.getName(), false); // do not set waypoint here
		
		for (FractalUIComponent child : children) {
			if (child instanceof SubmitterWidgetEditStudy) {
				SubmitterWidgetEditStudy widget = (SubmitterWidgetEditStudy) child;
				widget.setStudyEntry(study); // waypoint here
			}
		}

		log.leave();
	}



	public void onStudyUpdateSuccess(StudyEntry study) {
		log.enter("onStudyUpdateSuccess");
		log.trace("study title: "+study.getTitle());
		
		// TODO refactor with onStudyCreatedSuccess
		
		controller.setMainWidget(SubmitterWidgetStudy.class.getName(), false); // do not set waypoint here, wait until set study on child
		
		for (FractalUIComponent child : children) {
			if (child instanceof SubmitterWidgetStudy) {
				SubmitterWidgetStudy widget = (SubmitterWidgetStudy) child;
				widget.setMessage("Study updated OK.", false); // no waypoint here
				widget.setStudyEntry(study); // waypoint here
			}
		}

		log.leave();
	}



	public void onActionEditSsq(StudyEntry study, SubmitterWidgetStudy source) {
		log.enter("onActionEditSsq");
		
		controller.setMainWidget(SubmitterWidgetEditSsq.class.getName(), false); // do not set waypoint here
		
		for (FractalUIComponent child : children) {
			if (child instanceof SubmitterWidgetEditSsq) {
				SubmitterWidgetEditSsq widget = (SubmitterWidgetEditSsq) child;
				widget.setStudyEntry(study);
			}
		}

		log.leave();
	}



	public void onActionViewSsq(StudyEntry study, SubmitterWidgetStudy source) {
		// TODO Auto-generated method stub
		
	}

}
