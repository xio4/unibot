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
package com.xiovr.unibot.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

/**
 * @author xio4 
 * Configuration class for hibernate
 */
@Configuration
@PropertySource("classpath:properties/hibernate.properties")
public abstract class HibernateConfig {
	@Autowired
	Environment env;
	@Autowired
	DataSource dataSource;

	/**
	 * @return create bean for hibernate
	 */
	@Bean(name = "sessionFactoryBean")
	public LocalSessionFactoryBean getSessionFactoryBean()
	{
		LocalSessionFactoryBean fb = new LocalSessionFactoryBean();
		fb.setDataSource(dataSource);
		fb.setPackagesToScan("com.xiovr.unibot.data.model");
//		fb.setPackagesToScan(new String[]{"com.xiovr.unibot.data.model"});
		fb.setHibernateProperties(new Properties() {
			private static final long serialVersionUID = 1L;
			{
			setProperty("hibernate.show_sql", 
					env.getProperty("hibernate.show_sql"));
			setProperty("hibernate.format_sql", 
					env.getProperty("hibernate.format_sql"));
			setProperty("hibernate.use_sql_comments",
					env.getProperty("hibernate.use_sql_comments"));
			setProperty("hibernate.hbm2ddl.auto",
					env.getProperty("hibernate.hbm2ddl.auto"));
			setProperty("hibernate.hbm2ddl.import_files",
					env.getProperty("hibernate.hbm2ddl.import_files"));
			setProperty("hibernate.dialect",
					env.getProperty("hibernate.dialect"));
		}});
//		fb.getObject().getCurrentSession().setFlushMode(FlushMode.AUTO);
		return fb;
	}

	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager()
	{
		HibernateTransactionManager htm = new HibernateTransactionManager();
		htm.setSessionFactory(getSessionFactoryBean().getObject());
//		htm.setDataSource(dataSource);

		return htm;
	}
}