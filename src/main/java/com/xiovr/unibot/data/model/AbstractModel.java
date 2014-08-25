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
