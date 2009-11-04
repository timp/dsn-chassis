/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.forms.client;

import org.cggh.chassis.generic.atom.rewrite.client.AtomAuthor;
import org.cggh.chassis.generic.atom.rewrite.client.AtomEntry;
import org.cggh.chassis.generic.atom.rewrite.client.AtomFactory;
import org.cggh.chassis.generic.atom.rewrite.client.AtomFeed;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisResources;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidget;

/**
 * @author aliman
 *
 */
public abstract class BaseForm<E extends AtomEntry, F extends AtomFeed<E>> 
	extends ChassisWidget {

	
	
	
	private Log log = LogFactory.getLog(BaseForm.class);
	private E model;
	protected BaseFormRenderer<E> renderer;


	
	
	protected BaseForm() {}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#getName()
	 */
	@Override
	protected String getName() {
		return "baseForm";
	}


	
	
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(BaseForm.class);
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		this.model = this.createModel();
		this.renderer = this.createRenderer();
		this.renderer.setCanvas(this.contentBox);

		log.leave();

	}
	
	
	
	
	protected E createModel() {
		AtomFactory<E, F> factory = this.createAtomFactory();
		E model = factory.createEntry();
		AtomAuthor author = factory.createAuthor();
		author.setEmail(ChassisUser.getCurrentUserEmail());
		model.addAuthor(author);
		return model;
	}
	
	
	
	
	protected abstract AtomFactory<E, F> createAtomFactory();
	
	
	
	
	protected abstract BaseFormRenderer<E> createRenderer();


	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#renderUI()
	 */
	@Override
	protected void renderUI() {
		log.enter("renderUI");

		// delegate
		this.renderer.renderUI();

		log.leave();
	}


	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#bindUI()
	 */
	@Override
	protected void bindUI() {
		log.enter("bindUI");

		// delegate
		this.renderer.bindUI(this.model);

		log.leave();
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#syncUI()
	 */
	@Override
	protected void syncUI() {
		log.enter("syncUI");

		// delegate
		this.renderer.syncUI();

		log.leave();
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#unbindUI()
	 */
	@Override
	protected void unbindUI() {
		log.enter("unbindUI");

		// delegate
		this.renderer.unbindUI();

		log.leave();
	}

	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#destroy()
	 */
	@Override
	public void destroy() {
		log.enter("destroy");

		this.unbindUI();
		
		// TODO anything else?

		log.leave();
	}
	
	
	
	
	public void setModel(E model) {
		this.unbindUI();
		this.model = model;
		this.bindUI();
		this.syncUI();
	}
	
	
	
	
	public E getModel() {
		return this.model;
	}
	
	
	
	
	public static class Resources {
		
		public static final String HEADINGTITLEANDSUMMARY = "HEADINGTITLEANDSUMMARY";
		public static final String QUESTIONLABELTITLE = "QUESTIONLABELTITLE";
		public static final String QUESTIONLABELSUMMARY = "QUESTIONLABELSUMMARY";
		
		private String className;
		
		public Resources(String className) {
			this.className = className;
		}
		
		public String get(String key) {
			return ChassisResources.get(this.className, ChassisUser.getLang(), key);
		}
		
	}






	
}
