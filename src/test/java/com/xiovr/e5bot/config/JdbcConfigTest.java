package com.xiovr.e5bot.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.hsqldb.jdbcDriver;
/**
 * @author xio4 
 * Test configuration class for jdbc driver
 */
@Configuration
@PropertySource("classpath:properties/db.properties")
//@Profile("test")
public abstract class JdbcConfigTest {
	@Resource
	Environment env;
	
	/**
	 * @return create bean for jdbc driver
	 */
	@Bean(name = "dataSource")
	public DataSource getDataSource()
	{
//		DriverManagerDataSource ds = new DriverManagerDataSource();
//		ds.setDriverClassName(env.getProperty("jdbc.driverClassName"));
//		ds.setUrl(env.getProperty("jdbc.url"));
//		ds.setUsername(env.getProperty("jdbc.username"));
//		ds.setPassword(env.getProperty("jdbc.password"));
//		return ds;
		EmbeddedDatabaseBuilder edb = new EmbeddedDatabaseBuilder();
		edb.setType(EmbeddedDatabaseType.HSQL).
			setName("e5botdb").
			addScripts("classpath:/sql/hsql_create.sql",
					"classpath:/sql/import.sql");
	
		return edb.build();
	}

}
