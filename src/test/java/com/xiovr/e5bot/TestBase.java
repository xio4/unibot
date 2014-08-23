package com.xiovr.e5bot;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.annotations.Test;

import com.xiovr.e5bot.bot.BotConfig;
import com.xiovr.e5bot.config.HibernateConfig;
import com.xiovr.e5bot.config.JdbcConfigTest;

// ComponentScan not works as static class, only as config class!
@Test
//@ContextConfiguration(locations= { "classpath:test_main_config.xml"})
@ContextConfiguration(loader=AnnotationConfigContextLoader.class,
classes={TestBaseConfig.class, JdbcConfigTest.class, HibernateConfig.class,
	BotConfig.class})
//@ActiveProfiles("test")
public abstract class TestBase extends   AbstractTransactionalTestNGSpringContextTests {
//AbstractTestNGSpringContextTests {
}
