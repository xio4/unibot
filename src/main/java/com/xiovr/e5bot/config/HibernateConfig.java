package com.xiovr.e5bot.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
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
		fb.setPackagesToScan("com.xiovr.e5bot.data.model");
//		fb.setPackagesToScan(new String[]{"com.xiovr.e5bot.data.model"});
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