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
package com.xiovr.unibot.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiovr.unibot.data.dao.impl.ItemNameDaoImpl;
import com.xiovr.unibot.data.model.ItemName;

@Service
@Transactional()
public class TestService {
	@Autowired
	ItemNameDaoImpl itemNameDao;
	
	@Transactional
	public void test() {
		ItemName cls = itemNameDao.getById(10L);
		System.out.println("cls+"+cls);
		cls.setName("WTF");
		itemNameDao.update(cls);
		System.out.println("cls=" + cls.getName());
	}

}
