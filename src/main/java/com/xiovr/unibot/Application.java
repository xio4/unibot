package com.xiovr.unibot;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.xiovr.unibot.config.E5botConfig;
import com.xiovr.unibot.config.HibernateConfig;
import com.xiovr.unibot.config.JdbcConfig;
import com.xiovr.unibot.config.MvcConfig;
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