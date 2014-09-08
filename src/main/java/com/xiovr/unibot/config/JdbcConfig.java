package com.xiovr.unibot.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
/**
 * @author xio4 
 * Configuration class for jdbc driver
 */
@Configuration
@PropertySource("classpath:properties/db.properties")
@Profile("production")
public abstract class JdbcConfig {
	@Resource
	Environment env;
//	@Value("${jdbc.driverClassName}")
//	private String driverClassName;
//	@Value("${jdbc.url}")
//	private String url;
//	@Value("${jdbc.username")
//	private String username;
//	@Value("${jdbc.password}")
//	private String password;
	
	/**
	 * @return create bean for jdbc driver
	 */
	@Bean(name = "dataSource")
	public DataSource getDataSource()
	{
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		ds.setUrl(env.getProperty("jdbc.url"));
		ds.setUsername(env.getProperty("jdbc.username"));
		ds.setPassword(env.getProperty("jdbc.password"));
		return ds;
//		EmbeddedDatabaseBuilder edb = new EmbeddedDatabaseBuilder();
//		edb.setType(EmbeddedDatabaseType.HSQL).
//			setName("e5botdb").
//			addScripts("classpath:/sql/hsql_create.sql",
//					"classpath:/sql/hsql_data.sql");
	
//		return edb.build();
	}

}
