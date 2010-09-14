package org.cggh.chassis.generic.client.gwt.widget.study.client.sq;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetModel;
import org.cggh.chassis.generic.widget.client.ModelChangeEvent;
import org.cggh.chassis.generic.widget.client.ModelChangeHandler;





public class StudyQuestionnaireWidgetModel 
	extends AtomCrudWidgetModel<StudyEntry> {

	
	
	
	private Boolean readOnly;
	
	
	
	public static class LoadQuestionnairePendingStatus extends AsyncRequestPendingStatus {}
	
	public static final LoadQuestionnairePendingStatus STATUS_LOAD_QUESTIONNAIRE_PENDING = new LoadQuestionnairePendingStatus();		



	@Override
	public void init() {
		super.init();
		
		this.setReadOnly(true); // read-only by default
		
	}
	
	
	
	
	public void setReadOnly(Boolean readOnly) {
		ReadOnlyChangeEvent e = new ReadOnlyChangeEvent(this.readOnly, readOnly);
		this.readOnly = readOnly;
		this.fireChangeEvent(e);
	}

	



	public Boolean getReadOnly() {
		return readOnly;
	}




	public interface ReadOnlyChangeHandler extends ModelChangeHandler {
		
		public void onChange(ReadOnlyChangeEvent e);
		
	}
	
	
	
	
	public static class ReadOnlyChangeEvent extends ModelChangeEvent<Boolean, ReadOnlyChangeHandler> {

		/**
		 * @param before
		 * @param after
		 */
		public ReadOnlyChangeEvent(Boolean before, Boolean after) {
			super(before, after);
		}

		private static final Type<ReadOnlyChangeHandler> TYPE = new Type<ReadOnlyChangeHandler>();

		/* (non-Javadoc)
		 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
		 */
		@Override
		protected void dispatch(ReadOnlyChangeHandler handler) {
			handler.onChange(this);
		}

		/* (non-Javadoc)
		 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
		 */
		@Override
		public com.google.gwt.event.shared.GwtEvent.Type<ReadOnlyChangeHandler> getAssociatedType() {
			return TYPE;
		}
		
	}

	
	
	
}
