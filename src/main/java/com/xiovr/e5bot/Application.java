package com.xiovr.e5bot;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.xiovr.e5bot.config.E5botConfig;
import com.xiovr.e5bot.config.HibernateConfig;
import com.xiovr.e5bot.config.JdbcConfig;
import com.xiovr.e5bot.config.MvcConfig;
import com.xiovr.e5bot.config.WebSecurityConfig;


/**
 * @author xio4	
 * MvcConfig is loaded by DispatcherServlet
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.xiovr.e5bot.config" ,
		"com.xiovr.e5bot.data", "com.xiovr.e5bot.web", "com.xiovr.e5bot.bot"})
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