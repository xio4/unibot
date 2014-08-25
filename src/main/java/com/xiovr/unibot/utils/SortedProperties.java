package com.xiovr.unibot.utils;
import java.util.*;
import java.util.Map.Entry;

/**
 * Extension of Properties with sorted keys for alphabetic output.
 * Neither optimized for performance nor thread-safety.
 */
public class SortedProperties extends Properties {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/**
 * Called throughout by Properties, including 
 *   Properties.store(OutputStream out, String comments).
 */
@Override
public synchronized Enumeration<Object> keys() {
    return new Vector<Object>(this.keySet()).elements();
}

/**
 * Called by Properties.stringPropertyNames() and this.keys().
 */
@Override
public Set<Object> keySet() {
    Set<Object> keySet = super.keySet();
    if(keySet==null) return keySet;
    return new TreeSet<Object>(keySet);
}

/**
 * Called by Properties.toString().
 */
@Override
public Set<Map.Entry<Object, Object>> entrySet() {
    Set<Map.Entry<Object, Object>> entrySet = super.entrySet();
    if (entrySet==null) return entrySet;

    Set<Map.Entry<Object, Object>> sortedSet = new TreeSet<Entry<Object, Object>>(new EntryComparator());
    sortedSet.addAll(entrySet);
    return sortedSet;
}

/**
 * Comparator for sorting Map.Entry by key
 * Assumes non-null entries.
 */
class EntryComparator implements Comparator<Map.Entry<Object, Object>> {

    @Override
    public int compare(Map.Entry<Object, Object> entry1, Map.Entry<Object, Object> entry2) {
        return entry1.getKey().toString().compareTo(entry2.getKey().toString());
    }

}

}