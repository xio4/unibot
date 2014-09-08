package com.xiovr.unibot;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.xiovr.unibot.bot.BotConfig;
import com.xiovr.unibot.config.HibernateConfig;
import com.xiovr.unibot.config.JdbcConfigTest;

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
