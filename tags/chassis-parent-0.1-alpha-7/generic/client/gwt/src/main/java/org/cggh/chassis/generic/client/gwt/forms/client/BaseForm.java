/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.forms.client;

import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atom.client.AtomEntry;
import org.cggh.chassis.generic.atom.client.AtomFactory;
import org.cggh.chassis.generic.atom.client.AtomFeed;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisResources;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

/**
 * @author aliman
 *
 */
public abstract class BaseForm
	<E extends AtomEntry, F extends AtomFeed<E>, R extends BaseFormRenderer<E, F>> 
	extends DelegatingWidget<E, R> {

	
	
	
	private Log log = LogFactory.getLog(BaseForm.class);


	
	
	protected BaseForm() {}
	
	
	
	
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(BaseForm.class);
	}

	
	
	

	@Override
	protected E createModel() {
		ensureLog();
		log.enter("createModel");
		AtomFactory<E, F> factory = this.createAtomFactory();
		E model = factory.createEntry();
		AtomAuthor author = factory.createAuthor();
		author.setEmail(ChassisUser.getCurrentUserEmail());
		model.addAuthor(author);
		log.leave();
		return model;
	}
	
	
	
	
	protected abstract AtomFactory<E, F> createAtomFactory();
	
	
	
	
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
