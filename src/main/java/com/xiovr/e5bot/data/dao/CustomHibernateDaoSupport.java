package com.xiovr.e5bot.data.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public abstract class CustomHibernateDaoSupport extends HibernateDaoSupport {
	@Autowired
	public void setSessionFactoryBean(LocalSessionFactoryBean sessionFactoryBean)
	{
		setSessionFactory(sessionFactoryBean.getObject());
	}
}
