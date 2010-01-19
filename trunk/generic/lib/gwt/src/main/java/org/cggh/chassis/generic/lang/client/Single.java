/**
 * 
 */
package org.cggh.chassis.generic.lang.client;

/**
 * @author aliman
 *
 */
public class Single<A> extends Tuple {

	
	
	public Single() {
		super(1);
	}
	
	public Single(A first) {
		super(1);
		this.set1(first);
	}
	
	protected Single(int size) {
		super(size);
	}
	
	protected Single(A first, int size) {
		super(size);
		this.set1(first);
	}

	public void set1(A a) {
		set(0, a);
	}

	@SuppressWarnings("unchecked")
	public A get1() {
		return (A) get(0);
	}
	
	public void extract(Variable<A> v) {
		v.set(get1());
	}

}
