package com.xiovr.unibot.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public abstract class CustomHibernateDaoSupport extends HibernateDaoSupport {
	public CustomHibernateDaoSupport() {
		super();
	}

	@Autowired
	public void setSessionFactoryBean(LocalSessionFactoryBean sessionFactoryBean)
	{
		setSessionFactory(sessionFactoryBean.getObject());
	}
}
