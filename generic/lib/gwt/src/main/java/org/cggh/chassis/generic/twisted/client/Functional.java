/**
 * 
 */
package org.cggh.chassis.generic.twisted.client;

import java.util.Collection;

/**
 * @author aliman
 *
 */
public class Functional {
	
	public static <I,O> void map(Collection<I> in, Collection<O> out, Function<I,O> f) {
		for (I i : in) {
			O o = f.apply(i);
			if (o != null) {
				out.add(f.apply(i));
			}
		}
	}

}
