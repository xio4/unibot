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
