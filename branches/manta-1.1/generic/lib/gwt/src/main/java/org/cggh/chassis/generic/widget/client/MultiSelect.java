/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import java.util.Map;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.AsyncRequestPendingStatus;

/**
 * @author aliman
 *
 */
public class MultiSelect 
	extends DelegatingWidget<AsyncWidgetModel, MultiSelectRenderer> {
	
	
	
	
	private Log log = LogFactory.getLog(MultiSelect.class);
	
	
	
	// N.B. we have two models, one is simple async model, the other has select data and state
	private MultiSelectModel selectModel;

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected AsyncWidgetModel createModel() {
		return new AsyncWidgetModel();
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected MultiSelectRenderer createRenderer() {
		return new MultiSelectRenderer();
	}

	
	
	
	
	public MultiSelect(MultiSelectModel model) {
		log.enter("<constructor>");
		this.selectModel = model;
		log.leave();
	}


	
	
	
	@Override
	public void render() {
		log.enter("render");
		
		log.debug("go round first once, so async load items is handled visibly");
		super.render();
		
		this.model.setStatus(STATUS_LOAD_ITEMS_PENDING);

		log.debug("load items for select");
		Deferred<Map<String,String>> deferredItems = this.selectModel.loadItems(true);
		
		deferredItems.addCallback(new Function<Map<String,String>, Map<String,String>>() {

			public Map<String,String> apply(Map<String,String> in) {
				log.enter("[anon callback] :: apply");
				
				model.setStatus(AsyncWidgetModel.STATUS_READY);
				
				log.debug("items retrieved, rendering select");
				renderSelect();
				
				log.leave();
				return in;
			}
			
		});
		
		deferredItems.addErrback(new AsyncErrback(this, model));
		
		log.leave();
	}
	
	
	
	
	/**
	 * Make sure we don't render select until items have been loaded.
	 */
	public void renderSelect() {
		log.enter("renderSelect");
		this.renderer.renderSelectUI();
		this.renderer.bindSelectUI(this.selectModel);
		this.renderer.syncSelectUI();
		log.leave();
	}
	
	
	
	
	
	
	public static class LoadItemsPendingStatus extends AsyncRequestPendingStatus {}
	
	public static final LoadItemsPendingStatus STATUS_LOAD_ITEMS_PENDING = new LoadItemsPendingStatus();		


	
	
	
	

}
