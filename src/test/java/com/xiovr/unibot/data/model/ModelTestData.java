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
