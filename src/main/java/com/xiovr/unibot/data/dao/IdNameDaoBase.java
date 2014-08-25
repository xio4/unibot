package com.xiovr.unibot.data.dao;

import java.io.Serializable;
import java.util.List;

import com.xiovr.unibot.data.model.IdNameEntity;

public interface IdNameDaoBase<T extends IdNameEntity, K extends Serializable> {
	T save(T name);
	void update(T name);
	void delete(K id);
	T getById(K id);
	List<T> findAll();
}
