/**
 * 
 */
package org.cggh.chassis.generic.atomui.client;

import org.cggh.chassis.generic.atom.client.vanilla.VanillaAtomEntry;
import org.cggh.chassis.generic.atom.client.vanilla.VanillaAtomFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class GWTTestRetrieveEntryCallback extends GWTTestCase {

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.atomui.AtomUI";
	}
	

	
	private class DummyWidget extends Widget {
		
		public HandlerRegistration addRetrieveSuccessHandler(RetrieveSuccessHandler h) {
			return this.addHandler(h, RetrieveSuccessEvent.TYPE);
		}
		
	}
	

	
	private class DummyRetrieveSuccessHandler implements RetrieveSuccessHandler {

		private int called = 0;
		private RetrieveSuccessEvent last = null;
		
		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.atomui.client.RetrieveSuccessHandler#onRetrieveSuccess(org.cggh.chassis.generic.atomui.client.RetrieveSuccessEvent)
		 */
		public void onRetrieveSuccess(RetrieveSuccessEvent e) {
			called++;
			last = e;
		}
		
	}
	

	
	public void testApply() {
		
		DummyWidget owner = new DummyWidget();
		DummyRetrieveSuccessHandler handler = new DummyRetrieveSuccessHandler();
		owner.addRetrieveSuccessHandler(handler);
		
		VanillaAtomEntry entry = new VanillaAtomFactory().createEntry();
		
		AtomCrudWidgetModel model = new AtomCrudWidgetModel();
		model.setStatus(AtomCrudWidgetModel.STATUS_RETRIEVE_PENDING);

		// establish initial state

		assertTrue(
				"status should be create pending",
				(model.getStatus() instanceof AtomCrudWidgetModel.RetrievePendingStatus)
		);
		
		assertNull(
				"entry should be null",
				model.getEntry()
		);
		
		// instantiate object to test
		RetrieveEntryCallback callback = new RetrieveEntryCallback(owner, model);
		
		// call method under test
		callback.apply(entry);
		
		// establish outcome

		assertTrue(
				"status should be ready",
				(model.getStatus() instanceof AsyncWidgetModel.ReadyStatus)
		);
		
		assertEquals(
				"entry should be set on model",
				entry,
				model.getEntry()				
		);

		assertEquals(
				"expect handler called once",
				1,
				handler.called
		);
		
		assertEquals(
				"expect entry in event",
				entry,
				handler.last.getEntry()
		);
		
	}

}
