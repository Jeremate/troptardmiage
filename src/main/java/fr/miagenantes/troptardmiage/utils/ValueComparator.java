package fr.miagenantes.troptardmiage.utils;

import java.util.Comparator;
import java.util.Map;

/**
 * 
 * @author JeremyK
 * 
 * ValueComparator class to compare value in a Map
 * @param <K> : key of type K from the map
 * @param <V> : value of type V from the map. Type V must implement Comparable.
 */
public class ValueComparator<K, V extends Comparable<V>> implements Comparator<K> {
	private Map<K, V> map;

	public ValueComparator(Map<K, V> mapToOrder) {
		this.map = mapToOrder;
	}
	
	@Override
	public int compare(K o1, K o2) {
		V v1 = this.map.get(o1);
		V v2 = this.map.get(o2);
		int diff = v1.compareTo(v2);
		if(diff == 0) {
			return -1;
		} else {
			return diff;
		}
	}
	
}
