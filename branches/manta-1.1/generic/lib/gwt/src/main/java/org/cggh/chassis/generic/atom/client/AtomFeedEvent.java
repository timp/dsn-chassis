/**
 * 
 */
package org.cggh.chassis.generic.atom.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author aliman
 *
 */
public abstract class AtomFeedEvent
	<H extends EventHandler, E extends AtomEntry, F extends AtomFeed<E>> 
	extends GwtEvent<H> {
	
	protected F feed;
	
	public AtomFeedEvent() {}
	
	public AtomFeedEvent(F feed) {
		this.feed = feed;
	}	
	
	public void setFeed(F feed) {
		this.feed = feed;
	}
	
	public F getFeed() {
		return this.feed;
	}
	
}