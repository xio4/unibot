package com.xiovr.e5bot.data.model;

import org.testng.annotations.DataProvider;

public class ModelTestData {
	@DataProvider(name="getAbstractModelExtends")
	public static Object[][] getAbstractModelExtends() {
		return new Object[][] {
				{null, Classes.class},
				{null, ItemName.class},
				{null, NpcName.class},
				{null, Races.class},
				{null, SkillName.class},
				{null, SysString.class},
				{null, SystemMsg.class}
		};
	}
}
