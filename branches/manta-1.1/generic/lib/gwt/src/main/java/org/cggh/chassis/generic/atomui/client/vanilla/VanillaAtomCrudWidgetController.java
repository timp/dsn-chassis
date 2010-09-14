/**
 * 
 */
package org.cggh.chassis.generic.atomui.client.vanilla;

import org.cggh.chassis.generic.atom.client.AtomQueryService;
import org.cggh.chassis.generic.atom.client.AtomService;
import org.cggh.chassis.generic.atom.client.vanilla.VanillaAtomEntry;
import org.cggh.chassis.generic.atom.client.vanilla.VanillaAtomFeed;
import org.cggh.chassis.generic.atom.client.vanilla.VanillaAtomQuery;
import org.cggh.chassis.generic.atom.client.vanilla.VanillaAtomQueryService;
import org.cggh.chassis.generic.atom.client.vanilla.VanillaAtomService;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidget;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetController;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetModel;

/**
 * @author aliman
 *
 */
public class VanillaAtomCrudWidgetController
	extends AtomCrudWidgetController<VanillaAtomEntry, VanillaAtomFeed, VanillaAtomQuery> {
	
	
	
	
	private String queryServiceUrl;








	/**
	 * @param owner
	 * @param model
	 * @param collectionUrl
	 */
	public VanillaAtomCrudWidgetController(
			AtomCrudWidget<VanillaAtomEntry, VanillaAtomFeed, VanillaAtomQuery, ?, ?, ?> owner,
			AtomCrudWidgetModel<VanillaAtomEntry> model, 
			String collectionUrl,
			String queryServiceUrl) {
		super(owner, model, collectionUrl);
		this.queryServiceUrl = queryServiceUrl;
	}





	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomui.client.AtomCrudWidgetController#createAtomService()
	 */
	@Override
	public AtomService<VanillaAtomEntry, VanillaAtomFeed> createAtomService() {
		return new VanillaAtomService(this.collectionUrl);
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomui.client.AtomCrudWidgetController#createQuery()
	 */
	@Override
	public VanillaAtomQuery createQuery() {
		return new VanillaAtomQuery();
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomui.client.AtomCrudWidgetController#createQueryService()
	 */
	@Override
	public AtomQueryService<VanillaAtomEntry, VanillaAtomFeed, VanillaAtomQuery> createQueryService() {
		return new VanillaAtomQueryService(this.queryServiceUrl);
	}




}
