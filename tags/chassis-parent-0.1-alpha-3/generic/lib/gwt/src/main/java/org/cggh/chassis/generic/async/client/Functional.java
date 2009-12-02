/**
 * 
 */
package org.cggh.chassis.generic.async.client;

import java.util.Collection;
import java.util.Map;

/**
 * @author aliman
 *
 */
public class Functional {
	
	
	
	
	public static <I,O> void map(Collection<I> in, Collection<O> out, Function<I,O> f) {
		for (I i : in) {
			O o = f.apply(i);
			if (o != null) {
				out.add(o);
			}
		}
	}

	
	
	
	
	/**
	 * @param ids
	 * @param labels
	 * @param idsToLabels
	 */
	public static <I,O> void map(Collection<I> in, Collection<O> out, Map<I, O> f) {
		for (I i : in) {
			O o = f.get(i);
			if (o != null) {
				out.add(o);
			}
		}
	}

	
	
	
}
