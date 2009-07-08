/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.study.client;

import org.cggh.chassis.gwt.lib.study.client.StudyMetadataPersistenceService.CallbackWithStudyEntry;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class TestStudyMetadataPersistenceService extends GWTTestCase {

	/**
	 * 
	 */
	public TestStudyMetadataPersistenceService() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.gwt.lib.study.Study";
	}
	
	
	public void testGetEntry() {
		
		final StudyMetadataPersistenceService service = new StudyMetadataPersistenceService("/proxyfoo?http://localhost/eXist-1.2.6-rev9165/atom/edit/studies");
		
		Timer timer = new Timer() {
			
			public void run() {

				try {
					
					service.getEntry("?id=TODO", new CallbackWithStudyEntry() {

						public void onError(Request request, Throwable exception) {
							exception.printStackTrace();
							fail("onError "+exception.getLocalizedMessage());
						}

						public void onFailure(Request request, Response response) {
							fail(Integer.toString(response.getStatusCode()));
						}

						public void onSuccess(Request request, Response response, StudyEntry entry) {
							assertTrue(entry.getTitle().equals(""));
						}
						
					});
					
				} catch (RequestException ex) {
					ex.printStackTrace();
					fail("request exception "+ex.getLocalizedMessage());
				}
				
			}
			
		}; // close Timer
		
		// Set a delay period significantly longer than the
		// event is expected to take.
		delayTestFinish(2000);

		// Schedule the event and return control to the test system.
		timer.schedule(100);
		
	}

}
