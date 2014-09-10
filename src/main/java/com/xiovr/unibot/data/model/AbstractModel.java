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
package com.xiovr.unibot.data.model;

import java.io.Serializable;

public abstract class AbstractModel<T extends Serializable> implements Model<T> {
	@Override
	public boolean isNew()
	{
		return getId() == null;
	}
	@Override
	@SuppressWarnings("rawtypes")
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof AbstractModel)) return false;
		AbstractModel am = (AbstractModel)o;
		if (getId() != null ? !getId().equals(am.getId()) : 
			am.getId() != null) return false;
		return true;
	}
	@Override
	public int hashCode()
	{
		return getId() != null ? getId().hashCode() : 0;
	}
}
