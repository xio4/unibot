/**
 * Copyright (c) 2014 xio4
 * Universal bot for lineage-like games (Archeage, Lineage2 etc)
 *
 * This file is part of Unibot.
 *
 * Unibot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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