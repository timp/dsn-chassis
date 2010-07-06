package org.cggh.chassis.manta.protocol;

import java.util.Collection;

public abstract class Function<I, O> {

	public abstract O apply(I input);

	public static <I,O> void map(Function<I, O> f, I[] in, Collection<O> out) {
		
		for (I i : in) {
			out.add(f.apply(i));
		}
		
	}
	
	public static <I,O> void map(Function<I, O> f, Collection<I> in, Collection<O> out) {
		
		for (I i : in) {
			out.add(f.apply(i));
		}
		
	}
	
}
