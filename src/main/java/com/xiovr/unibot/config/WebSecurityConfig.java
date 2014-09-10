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

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

/**
 * @author xio4
 * Web security config
 */
@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter 
{
	private static final String USER = "test";
	private static final String USER_PASS = "test";
	private static final String ADMIN = "admin";
	private static final String ADMIN_PASS = "1111";
	/* (non-Javadoc)
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	// In antMathcers is not required authentication
	protected void configure(HttpSecurity http) throws Exception
	{
		http.authorizeRequests().
			antMatchers("/css/**", "/images/**").permitAll().
			anyRequest().authenticated();
		
		http.formLogin().
			usernameParameter("username").passwordParameter("password").
			loginPage("/login").defaultSuccessUrl("/").
			permitAll().
			and().
//			exceptionHandling().accessDeniedPage("/403").
//			and().
			logout().
			permitAll();
	}
//	// Password encoder 
//	private static final 
//				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
//	@Bean
//	protected PasswordEncoder getPasswordEncoder() {
//	
//		PasswordEncoder pe = new PasswordEncoder() {
//			@Override
//			public String encode(CharSequence rawPassword) {
//				System.out.println("Pass="+rawPassword);
//				String result = encoder.encode(rawPassword);
//				System.out.println("EncPass="+result);
//				return result;
//			}
//
//			@Override
//			public boolean matches(CharSequence rawPassword,
//					String encodedPassword) {
//				return encoder.matches(rawPassword, encodedPassword);
//			}
//		};
//		return pe;
//	}

	@Autowired
	DataSource dataSource;
	/* (non-Javadoc)
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.inMemoryAuthentication().
			withUser(USER).password(USER_PASS).roles("USER").
			and().
			withUser(ADMIN).password(ADMIN_PASS).roles("USER", "ADMIN");
//		auth.jdbcAuthentication().passwordEncoder(getPasswordEncoder()).
//			dataSource(dataSource).withDefaultSchema().
//			withUser(USER).password(USER_PASS).roles("USER").
//			and().
//			withUser(ADMIN).password(ADMIN_PASS).roles("USER", "ADMIN");
	}
	@Override
	public void configure(WebSecurity web)
	{
		web.ignoring().antMatchers("/favicon.ico");
	}
}
