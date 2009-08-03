/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.curator.perspective;

import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspective;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveController;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveModel;
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
import org.cggh.chassis.wwarn.prototype.client.widget.WidgetFactory;



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
		WidgetFactory.register(CuratorWidgetHome.class.getName(), CuratorWidgetHome.creator);
		WidgetFactory.register(CuratorWidgetMySubmissions.class.getName(), CuratorWidgetMySubmissions.creator);
		WidgetFactory.register(CuratorWidgetAllSubmissions.class.getName(), CuratorWidgetAllSubmissions.creator);
		WidgetFactory.register(CuratorWidgetNewStandardDataDictionary.class.getName(), CuratorWidgetNewStandardDataDictionary.creator);
		WidgetFactory.register(CuratorWidgetAllStandardDataDictionaries.class.getName(), CuratorWidgetAllStandardDataDictionaries.creator);
		WidgetFactory.register(CuratorWidgetNewReleaseCriteria.class.getName(), CuratorWidgetNewReleaseCriteria.creator);
		WidgetFactory.register(CuratorWidgetAllReleaseCriteria.class.getName(), CuratorWidgetAllReleaseCriteria.creator);
		WidgetFactory.register(CuratorWidgetDelegateNewTask.class.getName(), CuratorWidgetDelegateNewTask.creator);
		WidgetFactory.register(CuratorWidgetMyDelegatedTasks.class.getName(), CuratorWidgetMyDelegatedTasks.creator);

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



	@Override
	public String getDefaultMainWidgetName() {
		return CuratorWidgetHome.class.getName();
	}


}
