/**
 * 
 */
package org.cggh.chassis.generic.lang.client;

/**
 * @author aliman
 *
 */
public class Tuple {
	
	protected Object[] contents;
	
	public Tuple(int size) {
		contents = new Object[size];
	}
	
	public static <A,B> Pair<A,B> from(A first, B second) {
		return new Pair<A,B>(first, second);
	}

	public static <A,B,C> Triple<A,B,C> from(A first, B second, C third) {
		return new Triple<A,B,C>(first, second, third);
	}
	
	public String toString(String open, String separator, String close) {
		StringBuffer b = new StringBuffer();
		for (int i=0; i<contents.length; i++) {
			if (i==0) b.append(open);
			b.append(contents[i].toString());
			if (i<contents.length-1) b.append(separator);
			if (i==contents.length-1) b.append(close);
		}
		return b.toString();
	}
	
	@Override
	public String toString() {
		return toString("(", ",", ")");
	}
	
	public Object get(int index) {
		return contents[index];
	}
	
	public void set(int index, Object content) {
		contents[index] = content;
	}

}
