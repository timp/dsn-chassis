package org.cggh.chassis.generic.atomui.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atom.client.AtomEntry;
import org.cggh.chassis.generic.atom.client.AtomFeed;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.WidgetMemory;

/**
 * @author aliman
 *
 */
public class AtomCrudWidgetMemory<E extends AtomEntry, F extends AtomFeed<E>> extends WidgetMemory {

	
	
	
	
	private Log log = LogFactory.getLog(AtomCrudWidgetMemory.class);
	private AtomCrudWidgetModel<E> model;
	private AtomCrudWidgetController<E, F, ?> controller;

	
	private static String RETRIEVEEXPANDED_MNEMONIC_URL_ABREVIATION = "re";
	private static String RETRIEVE_MNEMONIC_URL_ABREVIATION = "r";
	
	
	/**
	 * @param viewDataFileWidget
	 */
	public AtomCrudWidgetMemory(AtomCrudWidgetModel<E> model, AtomCrudWidgetController<E, F, ?> controller) {
		this.model = model;
		this.controller = controller;
	}

	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.WidgetMemory#createMnemonic()
	 */
	@Override
	public String createMnemonic() {
		log.enter("createMnemonic");

//		// use current entry id as mnemonic
//		String mnemonic = model.getEntryId();

		String mnemonic = "";
		
		AtomCrudRequest r = model.getLastRequest();
		
		if (r.getRequestType() == AtomCrudRequest.RequestType.RETRIEVEEXPANDED) {
			mnemonic += RETRIEVEEXPANDED_MNEMONIC_URL_ABREVIATION + ";id=" + r.getEntryId();
		}
		if (r.getRequestType() == AtomCrudRequest.RequestType.RETRIEVE) {
			mnemonic += RETRIEVE_MNEMONIC_URL_ABREVIATION + ";url=" + r.getEntryUrl();
		}
		
		log.debug("mnemonic: "+mnemonic);

		log.leave();
		return mnemonic;
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.WidgetMemory#remember(java.lang.String)
	 */
	@Override
	public Deferred<WidgetMemory> remember(String mnemonic) {
		log.enter("remember");

//		Deferred<E> deferredEntry = controller.retrieveExpandedEntry(mnemonic);

		Deferred<E> deferredEntry;
		
		if (mnemonic.startsWith(RETRIEVEEXPANDED_MNEMONIC_URL_ABREVIATION +";")) {

			String id = mnemonic.substring(6);
			log.debug("id="+id);
			deferredEntry = controller.retrieveExpandedEntry(id);

		}
		else if (mnemonic.startsWith(RETRIEVE_MNEMONIC_URL_ABREVIATION +";")) {

			String url = mnemonic.substring(6);
			log.debug("url="+url);
			deferredEntry = controller.retrieveEntry(url);

		}
		else {
			throw new Error("could not parse mnemonic: "+mnemonic);
		}
		
		final WidgetMemory self = this;
		
		Deferred<WidgetMemory> deferredSelf = deferredEntry.adapt(new Function<E, WidgetMemory>() {

			public WidgetMemory apply(E in) {
				// when async operation is complete, return self
				return self;
			}
			
		});
		
		// actually, async operation here doesn't have any impact on 
		// memory child, so could callback with self immediately - 
		// however, will leave as is for now
		
		log.leave();
		return deferredSelf;
	}

	
	
	
}