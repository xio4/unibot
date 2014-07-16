package com.xiovr.e5bot;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.xiovr.e5bot.config.HibernateConfig;
import com.xiovr.e5bot.config.JdbcConfigTest;

@Test
//@ContextConfiguration(locations= { "classpath:test_main_config.xml"})
@ContextConfiguration(loader=AnnotationConfigContextLoader.class,
classes={JdbcConfigTest.class, HibernateConfig.class})
@ActiveProfiles("test")
public class TestBase extends AbstractTestNGSpringContextTests {
}
