package com.xiovr.e5bot.data.model;

import java.io.Serializable;

public interface Model<T extends Serializable> {
	T getId();
	boolean isNew();
}
