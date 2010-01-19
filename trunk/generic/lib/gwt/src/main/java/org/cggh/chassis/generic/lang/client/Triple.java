/**
 * 
 */
package org.cggh.chassis.generic.lang.client;

/**
 * @author aliman
 *
 */
public class Triple<A, B, C> extends Pair<A, B> {

	public Triple() {
		super(3);
	}
	
	public Triple(A a, B b, C c) {
		super(a, b, 3);
		this.set3(c);
	}

	public void set3(C third) {
		set(2, third);
	}

	@SuppressWarnings("unchecked")
	public C get3() {
		return (C) get(2);
	}

	public Pair<A,B> extract(Variable<C> v) {
		v.set(get3());
		return new Pair<A,B>(get1(), get2());
	}

}
