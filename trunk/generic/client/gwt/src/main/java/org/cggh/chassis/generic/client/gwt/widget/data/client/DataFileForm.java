/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client;

import org.cggh.chassis.generic.atom.rewrite.client.AtomAuthor;
import org.cggh.chassis.generic.atom.rewrite.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atom.rewrite.client.datafile.DataFileEntryForm;
import org.cggh.chassis.generic.atom.rewrite.client.datafile.DataFileEntryValidator;
import org.cggh.chassis.generic.atom.rewrite.client.datafile.DataFileFactory;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisResources;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author aliman
 *
 */
public class DataFileForm extends DataFileEntryForm {
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private DataFileFormRenderer renderer;
	private Panel canvas = new FlowPanel();




	public DataFileForm() {
		super();
		this.initWidget(this.canvas);
		this.initModel();
		this.validator = new Validator();
		this.renderer = new DataFileFormRenderer(this.canvas, this.model);
	}
	
	
	
	
	private void initModel() {

		DataFileFactory factory = new DataFileFactory();
		this.model = factory.createEntry();

		AtomAuthor author = factory.createAuthor();
		author.setEmail(ChassisUser.getCurrentUserEmail());
		this.model.addAuthor(author);
		
	}
	
	
	
	
	public void resetModel() {
		DataFileFactory factory = new DataFileFactory();
		DataFileEntry model = factory.createEntry();

		AtomAuthor author = factory.createAuthor();
		author.setEmail(ChassisUser.getCurrentUserEmail());
		model.addAuthor(author);
		
		this.setModel(model);
	}
	
	
	
	public void setModel(DataFileEntry model) {
		log.enter("setModel");
		this.model = model;
		this.renderer.bind(model);
		this.render();
		log.leave();
	}
	
	
	
	
	public void render() {
		this.renderer.render();
	}
	
	
	
	
	private class Validator extends DataFileEntryValidator {
		
		private Validator() {
			super();
			this.titleRequired = true;
			this.summaryRequired = true;
		}
		
	}
	
	
	
	public static class Resources {
	
		public static final String HEADINGTITLEANDSUMMARY = "HEADINGTITLEANDSUMMARY";
		public static final String QUESTIONLABELTITLE = "QUESTIONLABELTITLE";
		public static final String QUESTIONLABELSUMMARY = "QUESTIONLABELSUMMARY";
		
		public static String get(String key) {
			return ChassisResources.get(DataFileForm.class.getName(), ChassisUser.getLang(), key);
		}
		
	}



}
