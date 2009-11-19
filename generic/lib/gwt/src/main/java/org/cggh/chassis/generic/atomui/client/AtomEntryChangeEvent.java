/**
 * 
 */
package org.cggh.chassis.generic.atomui.client;

import org.cggh.chassis.generic.atom.client.AtomEntry;
import org.cggh.chassis.generic.widget.client.ModelChangeEvent;


@SuppressWarnings("unchecked")
public class AtomEntryChangeEvent
	<E extends AtomEntry> 
	extends ModelChangeEvent<E, AtomEntryChangeHandler> {

	public AtomEntryChangeEvent(E before, E after) { super(before, after); }

	@Override
	protected void dispatch(AtomEntryChangeHandler handler) { handler.onEntryChanged(this); }

	public static final Type<AtomEntryChangeHandler> TYPE = new Type<AtomEntryChangeHandler>();
	
	@Override
	public Type<AtomEntryChangeHandler> getAssociatedType() { return TYPE; }
	
}