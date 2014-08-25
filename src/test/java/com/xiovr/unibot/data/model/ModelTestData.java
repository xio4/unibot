package com.xiovr.unibot.data.model;

import org.testng.annotations.DataProvider;

import com.xiovr.unibot.data.model.Classes;
import com.xiovr.unibot.data.model.ItemName;
import com.xiovr.unibot.data.model.NpcName;
import com.xiovr.unibot.data.model.Races;
import com.xiovr.unibot.data.model.SkillName;
import com.xiovr.unibot.data.model.SysString;
import com.xiovr.unibot.data.model.SystemMsg;

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
