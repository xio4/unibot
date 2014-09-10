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

/** Utilities for Array reflection.
 * @author nexsoftware */
public final class ArrayReflection {

	/** Creates a new array with the specified component type and length. */
	static public Object newInstance (Class<?> c, int size) {
		return java.lang.reflect.Array.newInstance(c, size);
	}

	/** Returns the length of the supplied array. */
	static public int getLength (Object array) {
		return java.lang.reflect.Array.getLength(array);
	}

	/** Returns the value of the indexed component in the supplied array. */
	static public Object get (Object array, int index) {
		return java.lang.reflect.Array.get(array, index);
	}

	/** Sets the value of the indexed component in the supplied array to the supplied value. */
	static public void set (Object array, int index, Object value) {
		java.lang.reflect.Array.set(array, index, value);
	}

}
