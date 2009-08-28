/**
 * 
 */
package spike.atom;

import java.util.List;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.study.client.format.StudyFeed;
import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomService;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.client.gwt.client.Configuration;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;

/**
 * @author aliman
 *
 */
public class AtomExample {

	
	
	
	public void createNewStudyEntry() {
		
		MockStudyFactory factory = new MockStudyFactory();
		
		StudyEntry study = factory.createStudyEntry();
		study.setTitle("my first study");
		study.setSummary("my first study done in the gambia");
		study.addModule("clinical");
		study.addModule("in vitro");
		
		AtomService service = new MockAtomService(factory);
		String feedURL = Configuration.getStudyFeedURL();
		
		Deferred<AtomEntry> deferred = service.postEntry(feedURL, study);
		
		deferred.addCallback(new Function<AtomEntry,AtomEntry>() {

			public AtomEntry apply(AtomEntry in) {
				
				StudyEntry newlyCreatedStudy = (StudyEntry) in;
				
				// do something with newly created study here

				return in;

			}
			
		});
		
	}
	
	
	
	
	public void getAllStudies() {
		
		MockStudyFactory factory = new MockStudyFactory();
		AtomService service = new MockAtomService(factory);
		String feedURL = Configuration.getStudyFeedURL();
		
		Deferred<AtomFeed> deferred = service.getFeed(feedURL);
		
		deferred.addCallback(new Function<AtomFeed,AtomFeed>() {

			public AtomFeed apply(AtomFeed in) {
				
				StudyFeed retrievedStudyFeed = (StudyFeed) in;
				List<StudyEntry> studies = retrievedStudyFeed.getStudyEntries();
				
				// do something with studies here

				return in;
			}
			
		});

	}
	
	
	
}
