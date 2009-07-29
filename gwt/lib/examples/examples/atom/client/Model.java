/**
 * $Id$
 */
package examples.atom.client;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomEntry;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomFeed;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class Model {

	private AtomFeed feed = null;
	private Renderer renderer = null;
	private AtomEntry selectedEntry = null;
	private String message;
	
	public void setFeed(AtomFeed feed) {
		this.feed = feed;
		this.renderer.onFeedChanged(feed);
	}

	public void setSelectedEntry(AtomEntry entry) {
		this.selectedEntry  = entry;
		this.renderer.onSelectedEntryChanged(entry);
	}
	
	public void setMessage(String message) {
		this.message = message;
		this.renderer.onMessageChanged(message);
	}
}
