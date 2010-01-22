/**
 * 
 */
package org.cggh.chassis.generic.shared.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * @author aliman
 *
 */
public class Bijection<K,V> implements Map<K,V> {

	
	
	
	private Map<K,V> forward = new HashMap<K,V>();
	private Map<V, K> reverse = new HashMap<V,K>();
	
	
	
	public Bijection() {
		
	}

	
	
	
	/* (non-Javadoc)
	 * @see java.util.Map#clear()
	 */
	public void clear() {
		forward.clear();
		reverse.clear();
	}


	
	
	/* (non-Javadoc)
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	public boolean containsKey(Object key) {
		return forward.containsKey(key);
	}

	
	
	
	/* (non-Javadoc)
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	public boolean containsValue(Object value) {
		return forward.containsValue(value);
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see java.util.Map#entrySet()
	 */
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return forward.entrySet();
	}

	
	
	
	/* (non-Javadoc)
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public V get(Object key) {
		return forward.get(key);
	}
	
	
	
	
	public K getReverse(Object value) {
		return reverse.get(value);
	}

	
	
	
	/* (non-Javadoc)
	 * @see java.util.Map#isEmpty()
	 */
	public boolean isEmpty() {
		return forward.isEmpty();
	}

	
	
	
	/* (non-Javadoc)
	 * @see java.util.Map#keySet()
	 */
	public Set<K> keySet() {
		return forward.keySet();
	}

	
	
	
	/* (non-Javadoc)
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public V put(K key, V value) {
		V previous = forward.remove(key);
		for (K k : forward.keySet()) {
			if (forward.get(k).equals(value)) forward.remove(k);
		}
		reverse.remove(value);
		forward.put(key, value);
		reverse.put(value, key);
		return previous;
	}

	
	
	
	/* (non-Javadoc)
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	public void putAll(Map<? extends K, ? extends V> t) {
		for (Map.Entry<? extends K, ? extends V> entry : t.entrySet()) {
			this.put(entry.getKey(), entry.getValue());
		}
	}

	
	
	
	/* (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public V remove(Object key) {
		if (forward.containsKey(key)) {
			V v = forward.remove(key);
			if (v != null) reverse.remove(v);
			return v;
		}
		return null;
	}

	
	
	
	/* (non-Javadoc)
	 * @see java.util.Map#size()
	 */
	public int size() {
		return forward.size();
	}

	
	
	
	/* (non-Javadoc)
	 * @see java.util.Map#values()
	 */
	public Collection<V> values() {
		return forward.values();
	}
	
	
	
}
