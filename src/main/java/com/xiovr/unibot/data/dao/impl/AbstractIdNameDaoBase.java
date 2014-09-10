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
package com.xiovr.unibot.data.dao.impl;
import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import java.lang.reflect.ParameterizedType;

import com.xiovr.unibot.data.dao.CustomHibernateDaoSupport;
import com.xiovr.unibot.data.dao.IdNameDaoBase;
import com.xiovr.unibot.data.model.IdNameEntity;

import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.util.Assert;

/**
 * @author xio4
 *
 * @param <T> This model class
 * @param <K> This Id class
 */
public abstract class AbstractIdNameDaoBase<T extends IdNameEntity, K extends Serializable> 
	extends CustomHibernateDaoSupport
	implements IdNameDaoBase<T, K>
{
	private Class<? extends T> daoClazz;

	@SuppressWarnings("unchecked")
	public AbstractIdNameDaoBase()	{
		super();
		daoClazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
//		getHibernateTemplate().setCheckWriteOperations(false);
		
	}

	/* (non-Javadoc)
	 * @see com.xiovr.unibot.data.dao.IdNameDaoBase#save(com.xiovr.unibot.data.model.IdNameEntity)
	 */
	@Override
	public T save(T name) {
		getHibernateTemplate().save(name);
//		getSession().persist(name);
		return name;
	}

	/* (non-Javadoc)
	 * @see com.xiovr.unibot.data.dao.IdNameDaoBase#find(java.io.Serializable)
	 */
	@Override
	public T getById(K id) {
		return (T)(getHibernateTemplate().get(daoClazz, id));
	}

	//FIXME Error findAll
	/* (non-Javadoc)
	 * @see com.xiovr.unibot.data.dao.IdNameDaoBase#findAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll()
	{
		HibernateTemplate ht = getHibernateTemplate();
		DetachedCriteria dc=null ; 
		return (List<T>) ht.findByCriteria(dc);
	}
	/* (non-Javadoc)
	 * @see com.xiovr.unibot.data.dao.IdNameDaoBase#delete(java.io.Serializable)
	 */
	@Override
	public void delete(K id)
	{
		T t = getById(id);
		Assert.notNull(t);
		getHibernateTemplate().delete(t);
	}

	/* (non-Javadoc)
	 * @see com.xiovr.unibot.data.dao.IdNameDaoBase#update(com.xiovr.unibot.data.model.IdNameEntity)
	 */
	@Override
	public void update(T t)
	{
		getHibernateTemplate().update(t);
	}
}
