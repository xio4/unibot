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
