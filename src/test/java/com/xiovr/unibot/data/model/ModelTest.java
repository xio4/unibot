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
package com.xiovr.unibot.data.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.xiovr.unibot.TestBase;
import com.xiovr.unibot.data.model.IdNameEntity;

@TransactionConfiguration(transactionManager="transactionManager")
public class ModelTest extends TestBase {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ModelTest.class);
	@Test(dataProvider="getAbstractModelExtends", dataProviderClass=ModelTestData.class)
	void testAbstractModelExtends_should_be_equal_(Object reserved, Class<?> model) throws InstantiationException, IllegalAccessException
	{
		Long id = -1L;
		IdNameEntity mod1 = (IdNameEntity)(model.newInstance());
		mod1.setId(id);
		IdNameEntity mod2 = (IdNameEntity)(model.newInstance());
		mod2.setId(id);
		Assert.assertEquals(mod1, mod2);
	}
	
	@Test(dataProvider="getAbstractModelExtends", dataProviderClass=ModelTestData.class)
	void testAbstractModelExtends_should_be_equal_hashcode(Object reserved, Class<?> model) throws InstantiationException, IllegalAccessException
	{

		Long id = -1L;
		IdNameEntity mod1 = (IdNameEntity)(model.newInstance());
		mod1.setId(id);
		IdNameEntity mod2 = (IdNameEntity)(model.newInstance());
		mod2.setId(id);
		Assert.assertEquals(mod1.hashCode(), mod2.hashCode());
	}
}
