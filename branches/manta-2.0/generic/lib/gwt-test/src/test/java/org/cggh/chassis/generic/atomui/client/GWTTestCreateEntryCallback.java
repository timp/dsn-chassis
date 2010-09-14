/**
 * 
 */
package org.cggh.chassis.generic.atomui.client;

import org.cggh.chassis.generic.atom.client.vanilla.VanillaAtomEntry;
import org.cggh.chassis.generic.atom.client.vanilla.VanillaAtomFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class GWTTestCreateEntryCallback extends GWTTestCase {

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.atomui.AtomUI";
	}
	

	
	private class DummyWidget extends Widget {
		
		public HandlerRegistration addCreateSuccessHandler(CreateSuccessHandler h) {
			return this.addHandler(h, CreateSuccessEvent.TYPE);
		}
		
	}
	

	
	private class DummyCreateSuccessHandler implements CreateSuccessHandler {

		private int called = 0;
		private CreateSuccessEvent last = null;
		
		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.atomui.client.CreateSuccessHandler#onCreateSuccess(org.cggh.chassis.generic.atomui.client.CreateSuccessEvent)
		 */
		public void onCreateSuccess(CreateSuccessEvent e) {
			called++;
			last = e;
		}
		
	}
	

	
	public void testApply() {
		
		DummyWidget owner = new DummyWidget();
		DummyCreateSuccessHandler handler = new DummyCreateSuccessHandler();
		owner.addCreateSuccessHandler(handler);
		
		VanillaAtomEntry entry = new VanillaAtomFactory().createEntry();
		
		AtomCrudWidgetModel model = new AtomCrudWidgetModel();
		model.setStatus(AtomCrudWidgetModel.STATUS_CREATE_PENDING);

		// establish initial state

		assertTrue(
				"status should be create pending",
				(model.getStatus() instanceof AtomCrudWidgetModel.CreatePendingStatus)
		);
		
		assertNull(
				"entry should be null",
				model.getEntry()
		);
		
		// instantiate object to test
		CreateEntryCallback callback = new CreateEntryCallback(owner, model);
		
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
