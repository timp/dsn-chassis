/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.atom.client.AtomService;
import org.cggh.chassis.generic.atom.client.ui.AtomCrudWidget;
import org.cggh.chassis.generic.atom.client.ui.AtomCrudWidgetController;
import org.cggh.chassis.generic.atom.client.ui.AtomCrudWidgetModel;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetFeed;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetPersistenceService;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetQuery;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetQueryService;
import org.cggh.chassis.generic.atomext.client.shared.AtomQueryService;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;

/**
 * @author aliman
 *
 */
public abstract class DatasetCrudWidgetController extends
		AtomCrudWidgetController<DatasetEntry, DatasetFeed, DatasetQuery> {

	
	

	/**
	 * @param owner
	 * @param model
	 * @param collectionUrl
	 */
	public DatasetCrudWidgetController(
			AtomCrudWidget<DatasetEntry, DatasetFeed, DatasetQuery, ?, ?, ?> owner,
			AtomCrudWidgetModel<DatasetEntry> model) {
		super(owner, model, ""); // use relative collection URL
	}








	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.client.ui.AtomCrudWidgetController#createAtomService()
	 */
	@Override
	public AtomService<DatasetEntry, DatasetFeed> createAtomService() {
		return new DatasetPersistenceService(Configuration.getDatasetCollectionUrl());
	}








	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.client.ui.AtomCrudWidgetController#createQuery()
	 */
	@Override
	public DatasetQuery createQuery() {
		return new DatasetQuery();
	}








	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.client.ui.AtomCrudWidgetController#createQueryService()
	 */
	@Override
	public AtomQueryService<DatasetEntry, DatasetFeed, DatasetQuery> createQueryService() {
		return new DatasetQueryService(Configuration.getDatasetQueryServiceUrl());
	}
	
	
	
	
	
}
