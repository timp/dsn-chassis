/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.async.client.Functional;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileLink;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetFeed;
import org.cggh.chassis.generic.atomext.client.study.StudyLink;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisUtils;
import org.cggh.chassis.generic.client.gwt.common.client.CommonStyles;
import org.cggh.chassis.generic.client.gwt.forms.client.BaseFormRenderer;
import org.cggh.chassis.generic.client.gwt.forms.client.BaseForm.Resources;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.MultiSelect;
import org.cggh.chassis.generic.widget.client.MultiSelectModel;
import static org.cggh.chassis.generic.widget.client.HtmlElements.*;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * @author aliman
 *
 */
public class DatasetFormRenderer 
	extends BaseFormRenderer<DatasetEntry, DatasetFeed> {
	
	
	
	
	private Log log = LogFactory.getLog(DatasetFormRenderer.class);
	private FlowPanel studiesQuestionPanel;
	private MultiSelect selectStudies;
	private FlowPanel dataFilesQuestionPanel;
	private MultiSelect selectDataFiles;




	/**
	 * @param owner
	 */
	public DatasetFormRenderer(DatasetForm owner) {
		super(owner);
	}

	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.forms.client.BaseFormRenderer#createResources()
	 */
	@Override
	protected Resources createResources() {
		return new DatasetForm.Resources();
	}

	
	
	
	
	@Override
	public void renderUI() {
		super.renderUI();
		
		this.canvas.add(h3Widget("Studies")); // TODO i18n
		this.studiesQuestionPanel = new FlowPanel();
		this.canvas.add(this.studiesQuestionPanel);

		this.canvas.add(h3Widget("Data Files")); // TODO i18n
		this.dataFilesQuestionPanel = new FlowPanel();
		this.canvas.add(this.dataFilesQuestionPanel);

	}
	
	
	
	
	/**
	 * 
	 */
	public void syncUI() {
		log.enter("syncUI");
		
		super.syncUI();
		
		if (this.model != null) {

			this.syncDataFiles();
			this.syncStudies();
			
		}

		log.leave();
	}

	



	protected void syncStudies() {
		log.enter("syncStudies");

		this.studiesQuestionPanel.clear();
		
		this.studiesQuestionPanel.addStyleName(CommonStyles.QUESTION);
		this.studiesQuestionPanel.add(new Label("Associate this dataset with one or more studies..."));
		
		log.debug("instantiate multi select");
		this.selectStudies = new MultiSelect(new SelectStudiesModel(this.model));

		log.debug("add multi select to studies question panel");
		this.studiesQuestionPanel.add(this.selectStudies);
		
		log.leave();
	}
	
	
	
	
	


	
	
	protected void syncDataFiles() {
		log.enter("syncDataFiles");

		this.dataFilesQuestionPanel.clear();
		
		this.dataFilesQuestionPanel.addStyleName(CommonStyles.QUESTION);
		this.dataFilesQuestionPanel.add(new Label("Add/remove data files..."));
		
		log.debug("instantiate multi select");
		this.selectDataFiles = new MultiSelect(new SelectDataFilesModel(this.model));

		log.debug("add multi select to data files question panel");
		this.dataFilesQuestionPanel.add(this.selectDataFiles);
		
		log.leave();
	}
	
	
	

	class SelectStudiesModel implements MultiSelectModel {

		
		
		private Log log = LogFactory.getLog(SelectStudiesModel.class);
		
		
		private DatasetEntry entry;
		private Map<String,String> items = null;

		SelectStudiesModel(DatasetEntry entry) {
			this.entry = entry;
		}

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.MultiSelectModel#addValue(java.lang.String)
		 */
		public void addValue(String value) {
			// assume value is a link href
			this.entry.addStudyLink(value);
		}

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.MultiSelectModel#addValue(int, java.lang.String)
		 */
		public void addValue(int index, String value) {
			// assume value is a link href
			this.entry.addStudyLink(index, value);
		}

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.MultiSelectModel#getItems()
		 */
		public Map<String, String> getItems() {
			return this.items;
		}

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.MultiSelectModel#getSelectedValues()
		 */
		public List<String> getSelectedValues() {
			Function<StudyLink, String> href = new Function<StudyLink, String>() {
				public String apply(StudyLink in) {
					return in.getHref();
				}
			};
			List<String> values = new ArrayList<String>();
			Functional.map(this.entry.getStudyLinks(), values, href);
			return values;
		}

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.MultiSelectModel#loadItems(boolean)
		 */
		public Deferred<Map<String, String>> loadItems(boolean forceRefresh) {
			log.enter("loadItems");
			Deferred<Map<String,String>> deferredItems;
			if (this.items == null || forceRefresh) {
				log.debug("loading items");
				deferredItems = ChassisUtils.getMapOfStudyLinksToTitlesForCurrentUser();
				deferredItems.addCallback(new Function<Map<String,String>, Map<String,String>>() {

					public Map<String, String> apply(Map<String, String> in) {
						items = in;
						return in;
					}
					
				});
			}
			else {
				log.debug("callback immediately with existing items");
				deferredItems = new Deferred<Map<String,String>>();
				deferredItems.callback(this.items);
			}
			log.leave();
			return deferredItems;
		}

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.MultiSelectModel#removeValue(java.lang.String)
		 */
		public void removeValue(String value) {
			// assumer value is href
			this.entry.removeStudyLink(value);
		}

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.MultiSelectModel#removeValue(int)
		 */
		public void removeValue(int index) {
			this.entry.removeStudyLink(index);
		}
		
	}
	
	
	
	
	
	class SelectDataFilesModel implements MultiSelectModel {

		
		
		private Log log = LogFactory.getLog(SelectDataFilesModel.class);
		
		
		private DatasetEntry entry;
		private Map<String,String> items = null;

		SelectDataFilesModel(DatasetEntry entry) {
			this.entry = entry;
		}

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.MultiSelectModel#addValue(java.lang.String)
		 */
		public void addValue(String value) {
			// assume value is a link href
			this.entry.addDataFileLink(value);
		}

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.MultiSelectModel#addValue(int, java.lang.String)
		 */
		public void addValue(int index, String value) {
			// assume value is a link href
			this.entry.addDataFileLink(index, value);
		}

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.MultiSelectModel#getItems()
		 */
		public Map<String, String> getItems() {
			return this.items;
		}

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.MultiSelectModel#getSelectedValues()
		 */
		public List<String> getSelectedValues() {
			Function<DataFileLink, String> href = new Function<DataFileLink, String>() {
				public String apply(DataFileLink in) {
					return in.getHref();
				}
			};
			List<String> values = new ArrayList<String>();
			Functional.map(this.entry.getDataFileLinks(), values, href);
			return values;
		}

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.MultiSelectModel#loadItems(boolean)
		 */
		public Deferred<Map<String, String>> loadItems(boolean forceRefresh) {
			log.enter("loadItems");
			Deferred<Map<String,String>> deferredItems;
			if (this.items == null || forceRefresh) {
				log.debug("loading items");
				deferredItems = ChassisUtils.getMapOfDataFileLinksToTitlesForCurrentUser();
				deferredItems.addCallback(new Function<Map<String,String>, Map<String,String>>() {

					public Map<String, String> apply(Map<String, String> in) {
						items = in;
						return in;
					}
					
				});
			}
			else {
				log.debug("callback immediately with existing items");
				deferredItems = new Deferred<Map<String,String>>();
				deferredItems.callback(this.items);
			}
			log.leave();
			return deferredItems;
		}

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.MultiSelectModel#removeValue(java.lang.String)
		 */
		public void removeValue(String value) {
			// assumer value is href
			this.entry.removeDataFileLink(value);
		}

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.MultiSelectModel#removeValue(int)
		 */
		public void removeValue(int index) {
			this.entry.removeDataFileLink(index);
		}
		
	}
	
}
