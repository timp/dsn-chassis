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
public class GWTTestUpdateEntryCallback extends GWTTestCase {

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.atomui.AtomUI";
	}
	

	
	private class DummyWidget extends Widget {
		
		public HandlerRegistration addUpdateSuccessHandler(UpdateSuccessHandler h) {
			return this.addHandler(h, UpdateSuccessEvent.TYPE);
		}
		
	}
	

	
	private class DummyUpdateSuccessHandler implements UpdateSuccessHandler {

		private int called = 0;
		private UpdateSuccessEvent last = null;
		
		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.atomui.client.UpdateSuccessHandler#onUpdateSuccess(org.cggh.chassis.generic.atomui.client.UpdateSuccessEvent)
		 */
		public void onUpdateSuccess(UpdateSuccessEvent e) {
			called++;
			last = e;
		}
		
	}
	

	
	public void testApply() {
		
		DummyWidget owner = new DummyWidget();
		DummyUpdateSuccessHandler handler = new DummyUpdateSuccessHandler();
		owner.addUpdateSuccessHandler(handler);
		
		VanillaAtomEntry entry = new VanillaAtomFactory().createEntry();
		
		AtomCrudWidgetModel model = new AtomCrudWidgetModel();
		model.setStatus(AtomCrudWidgetModel.STATUS_UPDATE_PENDING);

		// establish initial state

		assertTrue(
				"status should be create pending",
				(model.getStatus() instanceof AtomCrudWidgetModel.UpdatePendingStatus)
		);
		
		assertNull(
				"entry should be null",
				model.getEntry()
		);
		
		// instantiate object to test
		UpdateEntryCallback callback = new UpdateEntryCallback(owner, model);
		
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
