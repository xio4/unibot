package com.xiovr.unibot.data.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.xiovr.unibot.TestBase;
import com.xiovr.unibot.data.model.IdNameEntity;

@TransactionConfiguration(transactionManager="transactionManager")
public class ModelTest extends TestBase {
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
