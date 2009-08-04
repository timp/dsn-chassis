/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.curator.perspective;

import org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponentFactory;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspective;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.PerspectiveController;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.PerspectiveModel;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.allreleasecriteria.CuratorWidgetAllReleaseCriteria;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.allstdatadicts.CuratorWidgetAllStandardDataDictionaries;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.allsubmissions.CuratorWidgetAllSubmissions;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.home.CuratorWidgetHome;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.mysubmissions.CuratorWidgetMySubmissions;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.mytasks.CuratorWidgetMyDelegatedTasks;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.newreleasecriteria.CuratorWidgetNewReleaseCriteria;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.newstdatadict.CuratorWidgetNewStandardDataDictionary;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.newtask.CuratorWidgetDelegateNewTask;
import org.cggh.chassis.wwarn.prototype.client.user.RoleNames;



/**
 * @author aliman
 *
 */
public class CuratorPerspective extends BasePerspective {


	
	private Renderer renderer;

	
	
	public CuratorPerspective() {
		super();
		this.log.setCurrentClass(CuratorPerspective.class.getName());
		this.init();
	}
	

	
	private void init() {
		log.enter("init");

		log.trace("register main components");
		FractalUIComponentFactory.register(CuratorWidgetHome.class.getName(), CuratorWidgetHome.creator);
		FractalUIComponentFactory.register(CuratorWidgetMySubmissions.class.getName(), CuratorWidgetMySubmissions.creator);
		FractalUIComponentFactory.register(CuratorWidgetAllSubmissions.class.getName(), CuratorWidgetAllSubmissions.creator);
		FractalUIComponentFactory.register(CuratorWidgetNewStandardDataDictionary.class.getName(), CuratorWidgetNewStandardDataDictionary.creator);
		FractalUIComponentFactory.register(CuratorWidgetAllStandardDataDictionaries.class.getName(), CuratorWidgetAllStandardDataDictionaries.creator);
		FractalUIComponentFactory.register(CuratorWidgetNewReleaseCriteria.class.getName(), CuratorWidgetNewReleaseCriteria.creator);
		FractalUIComponentFactory.register(CuratorWidgetAllReleaseCriteria.class.getName(), CuratorWidgetAllReleaseCriteria.creator);
		FractalUIComponentFactory.register(CuratorWidgetDelegateNewTask.class.getName(), CuratorWidgetDelegateNewTask.creator);
		FractalUIComponentFactory.register(CuratorWidgetMyDelegatedTasks.class.getName(), CuratorWidgetMyDelegatedTasks.creator);

		log.trace("init model");
		model = new PerspectiveModel();

		log.trace("init controller");
		controller = new PerspectiveController(model, this); 

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



	@Override
	public String getDefaultMainWidgetName() {
		return CuratorWidgetHome.class.getName();
	}


}
