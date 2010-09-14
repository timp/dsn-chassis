/**
 * 
 */
package org.cggh.chassis.generic.lang.client;

/**
 * @author aliman
 *
 */
public class Pair<A, B> extends Single<A> {

	
	
	public Pair() {
		super(2);
	}
	
	public Pair(A a, B b) {
		super(a, 2);
		this.set2(b);
	}
	
	protected Pair(int size) {
		super(size);
	}
	
	protected Pair(A a, B b, int size) {
		super(a, size);
		this.set2(b);
	}

	public void set2(B b) {
		set(1, b);
	}

	@SuppressWarnings("unchecked")
	public B get2() {
		return (B) get(1);
	}
	
	public Single<A> extract(Variable<B> v) {
		v.set(get2());
		return new Single<A>(get1());
	}
	
	
}
