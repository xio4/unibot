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
package com.xiovr.unibot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.xiovr.unibot.config.E5botConfig;
import com.xiovr.unibot.config.HibernateConfig;
import com.xiovr.unibot.config.JdbcConfig;
import com.xiovr.unibot.config.WebSecurityConfig;


/**
 * @author xio4	
 * MvcConfig is loaded by DispatcherServlet
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.xiovr.unibot.config" ,
		"com.xiovr.unibot.data", "com.xiovr.unibot.web", "com.xiovr.unibot.bot"})
@Import(value = {JdbcConfig.class, 
				HibernateConfig.class,
				WebSecurityConfig.class,
				E5botConfig.class })
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        
//        System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//        String[] beanNames = ctx.getBeanDefinitionNames();
//        Arrays.sort(beanNames);
//        for (String beanName : beanNames) {
//            System.out.println(beanName);
//        }
    }

}