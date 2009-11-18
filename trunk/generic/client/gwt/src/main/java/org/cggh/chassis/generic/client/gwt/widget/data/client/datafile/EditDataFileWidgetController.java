/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.atom.client.AtomService;
import org.cggh.chassis.generic.atom.client.ui.AtomCrudWidgetController;
import org.cggh.chassis.generic.atom.client.ui.AtomCrudWidgetModel;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileFeed;
import org.cggh.chassis.generic.atomext.client.datafile.DataFilePersistenceService;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileQuery;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileQueryService;
import org.cggh.chassis.generic.atomext.client.shared.AtomQueryService;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;




/**
 * @author aliman
 *
 */
public class EditDataFileWidgetController 
	extends AtomCrudWidgetController<DataFileEntry, DataFileFeed, DataFileQuery>
{



	
		
	
	
	/**
	 * @param model
	 */
	public EditDataFileWidgetController(
			EditDataFileWidget owner, 
			AtomCrudWidgetModel<DataFileEntry> model) {
		super(owner, model, ""); // use relative collection URL
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.client.ui.AtomCrudWidgetController#createAtomService()
	 */
	@Override
	public AtomService<DataFileEntry, DataFileFeed> createAtomService() {
		return new DataFilePersistenceService(Configuration.getDataFileCollectionUrl());
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.client.ui.AtomCrudWidgetController#createQueryService()
	 */
	@Override
	public AtomQueryService<DataFileEntry, DataFileFeed, DataFileQuery> createQueryService() {
		return new DataFileQueryService(Configuration.getDataFileQueryServiceUrl());
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.client.ui.AtomCrudWidgetController#createQuery()
	 */
	@Override
	public DataFileQuery createQuery() {
		return new DataFileQuery();
	}


	
	
	
	
	
}
