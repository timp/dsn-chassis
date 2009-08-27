/**
 * 
 */
package spike.atom;

import java.util.List;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomService;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;

/**
 * @author aliman
 *
 */
public class AtomExample {

	public void doSomething() {
		
		final AtomService service = new MockAtomService();
		
		Deferred<AtomFeed> deferredFeed = service.getFeed("http://example.com/atom/edit/myfeed");
		
		deferredFeed.addCallback(new Function<AtomFeed,Deferred<AtomEntry>>() {

			public Deferred<AtomEntry> apply(AtomFeed feed) {
				
				// do something with the feed
				List<AtomEntry> entries = feed.getEntries();
				
				AtomEntry entry = entries.get(0);
				String title = entry.getTitle();
				
				// modify
				entry.setTitle(title + "something new");
				
				Deferred<AtomEntry> deferredUpdatedEntry = service.putEntry(entry.getEditLink().getHref(), entry);

				return deferredUpdatedEntry;

			}
			
		});
			
	}
}
