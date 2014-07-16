package com.xiovr.e5bot.data.dao;

import java.io.Serializable;
import java.util.List;

import com.xiovr.e5bot.data.model.IdNameEntity;

public interface IdNameDaoBase<T extends IdNameEntity, K extends Serializable> {
	T save(T name);
	void update(T name);
	void delete(K id);
	T find(K id);
	List<T> findAll();
}
