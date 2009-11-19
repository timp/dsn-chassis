/**
 * 
 */
package org.cggh.chassis.generic.atomui.client;

import junit.framework.TestCase;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atom.client.AtomEntry;
import org.cggh.chassis.generic.atom.client.AtomQuery;
import org.cggh.chassis.generic.atom.client.AtomQueryService;
import org.cggh.chassis.generic.atom.client.AtomService;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.log.client.SystemOutLog;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;


/**
 * @author aliman
 *
 */
public class TestAtomCrudWidgetController extends TestCase {
	
	
	
	
	static {
		LogFactory.create = SystemOutLog.create;
	}
	
	
	
	private static Log log = LogFactory.getLog(TestAtomCrudWidgetController.class);
	
		
	
	
	@SuppressWarnings("unchecked")
	public void testCreateEntry() {
		log.enter("testCreateEntry");
		
		AtomCrudWidgetModel model = new AtomCrudWidgetModel();
		
		// establish initial state of model

		assertTrue(model.getStatus() instanceof AsyncWidgetModel.InitialStatus);
		assertNull(model.getEntry());
		assertNull(model.getEntryId());
		
		// setup test variables
				
		String collectionUrl = "http://example.org/atom/edit/foo";
		
		// setup mocks
		
		AtomEntry mockEntry = createMock(AtomEntry.class); 
		Function mockCallback = createMock(Function.class); 
		Function mockErrback = createMock(Function.class);
		Deferred mockDeferred = createMock(Deferred.class);
		AtomService mockService = createMock(AtomService.class); 

		// expectations 
		
		// post entry
		expect(mockService.postEntry(collectionUrl, mockEntry)).andReturn(mockDeferred);

		// add callback and errback in that order
		expect(mockDeferred.addCallback(mockCallback)).andReturn(mockDeferred);
		expect(mockDeferred.addErrback(mockErrback)).andReturn(mockDeferred);

		replay(mockEntry);
		replay(mockCallback);
		replay(mockErrback);
		replay(mockDeferred);
		replay(mockService);

		// call method under test
		AtomCrudWidgetController.createEntry(mockEntry, model, mockService, collectionUrl, mockCallback, mockErrback);
		
		// establish outcome
		
		assertTrue(
				"status should have changed",
				model.getStatus() instanceof AtomCrudWidgetModel.CreatePendingStatus
		);

		assertNull(
				"entry should be still null, won't be set until callback",
				model.getEntry()
		); 
		
		assertNull(
				"entry id should be still null, won't be set at all",
				model.getEntryId()
		); 
		
		// verify expectations on mocks
		verify(mockEntry);
		verify(mockService); 
		verify(mockDeferred); 
		verify(mockCallback); 
		verify(mockErrback);
		
		log.leave();
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public void testRetrieveEntry() {
		
		AtomCrudWidgetModel model = new AtomCrudWidgetModel();
		
		// establish initial state of model

		assertTrue(model.getStatus() instanceof AsyncWidgetModel.InitialStatus);
		assertNull(model.getEntry());
		assertNull(model.getEntryId());
		
		// setup test variables
				
		String entryUrl = "http://example.org/atom/edit/foo?id=bar";
		
		// setup mocks
		
		Function mockCallback = createMock(Function.class); 
		Function mockErrback = createMock(Function.class);
		Deferred mockDeferred = createMock(Deferred.class);
		AtomService mockService = createMock(AtomService.class); 

		// expectations 
		
		// get entry
		expect(mockService.getEntry(entryUrl)).andReturn(mockDeferred);

		// add callback and errback in that order
		expect(mockDeferred.addCallback(mockCallback)).andReturn(mockDeferred);
		expect(mockDeferred.addErrback(mockErrback)).andReturn(mockDeferred);

		replay(mockCallback);
		replay(mockErrback);
		replay(mockDeferred);
		replay(mockService);

		// call method under test
		AtomCrudWidgetController.retrieveEntry(model, mockService, entryUrl, mockCallback, mockErrback);
		
		// establish outcome
		
		assertTrue(
				"status should have changed",
				(model.getStatus() instanceof AtomCrudWidgetModel.RetrievePendingStatus)
		);

		assertNull(
				"entry should be still null, won't be set until callback",
				model.getEntry()
		); 
		
		assertNull(
				"entry id should be still null, won't be set at all",
				model.getEntryId()
		); 
		
		// verify expectations on mocks
		verify(mockService); 
		verify(mockDeferred); 
		verify(mockCallback); 
		verify(mockErrback);
		
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public void testRetrieveExpandedEntry() {
		
		AtomCrudWidgetModel model = new AtomCrudWidgetModel();
		
		// establish initial state of model

		assertTrue(model.getStatus() instanceof AsyncWidgetModel.InitialStatus);
		assertNull(model.getEntry());
		assertNull(model.getEntryId());
		
		// setup test variables
				
		String entryId = "foo";
		
		// setup mocks
		
		Function mockCallback = createMock(Function.class); 
		Function mockErrback = createMock(Function.class);
		Deferred mockDeferred = createMock(Deferred.class);
		AtomQuery mockQuery = createMock(AtomQuery.class);
		AtomQueryService mockService = createMock(AtomQueryService.class); 

		// expectations 
		
		mockQuery.setId(entryId);
		expect(mockService.queryOne(mockQuery)).andReturn(mockDeferred);

		// add callback and errback in that order
		expect(mockDeferred.addCallback(mockCallback)).andReturn(mockDeferred);
		expect(mockDeferred.addErrback(mockErrback)).andReturn(mockDeferred);

		replay(mockQuery);
		replay(mockService);
		replay(mockDeferred);
		replay(mockCallback);
		replay(mockErrback);

		// call method under test
		AtomCrudWidgetController.retrieveExpandedEntry(model, mockService, mockQuery, entryId, mockCallback, mockErrback);
		
		// establish outcome
		
		assertTrue(
				"status should have changed",
				(model.getStatus() instanceof AtomCrudWidgetModel.RetrievePendingStatus)
		);

		assertNull(
				"entry should be still null, won't be set until callback",
				model.getEntry()
		); 
		
		assertEquals(
				"entry id should be set prior to callback, to support history",
				entryId,
				model.getEntryId()
		); 
		
		// verify expectations on mocks
		verify(mockQuery); 
		verify(mockService); 
		verify(mockDeferred); 
		verify(mockCallback); 
		verify(mockErrback);
		
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public void testUpdateEntry() {
		
		AtomCrudWidgetModel model = new AtomCrudWidgetModel();
		
		// establish initial state of model

		assertTrue(model.getStatus() instanceof AsyncWidgetModel.InitialStatus);
		assertNull(model.getEntry());
		assertNull(model.getEntryId());
		
		// setup test variables
				
		String entryUrl = "http://example.org/atom/edit/foo?id=bar";
		
		// setup mocks
		
		AtomEntry mockEntry = createMock(AtomEntry.class); 
		Function mockCallback = createMock(Function.class); 
		Function mockErrback = createMock(Function.class);
		Deferred mockDeferred = createMock(Deferred.class);
		AtomService mockService = createMock(AtomService.class); 

		// expectations 
		
		// put entry
		expect(mockService.putEntry(entryUrl, mockEntry)).andReturn(mockDeferred);

		// add callback and errback in that order
		expect(mockDeferred.addCallback(mockCallback)).andReturn(mockDeferred);
		expect(mockDeferred.addErrback(mockErrback)).andReturn(mockDeferred);

		replay(mockEntry);
		replay(mockCallback);
		replay(mockErrback);
		replay(mockDeferred);
		replay(mockService);

		// call method under test
		AtomCrudWidgetController.updateEntry(model, mockService, entryUrl, mockEntry, mockCallback, mockErrback);
		
		// establish outcome
		
		assertTrue(
				"status should have changed",
				model.getStatus() instanceof AtomCrudWidgetModel.UpdatePendingStatus
		);

		assertNull(
				"entry should be still null, won't be set until callback",
				model.getEntry()
		); 
		
		assertNull(
				"entry id should be still null, won't be set at all",
				model.getEntryId()
		); 
		
		// verify expectations on mocks
		verify(mockEntry);
		verify(mockService); 
		verify(mockDeferred); 
		verify(mockCallback); 
		verify(mockErrback);
		
	}
	
	
	
	
}
