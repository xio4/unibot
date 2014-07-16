package com.xiovr.e5bot.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiovr.e5bot.data.dao.impl.ClassesDaoImpl;
import com.xiovr.e5bot.data.dao.impl.ItemNameDaoImpl;
import com.xiovr.e5bot.data.model.Classes;
import com.xiovr.e5bot.data.model.ItemName;

@Service
@Transactional
public class TestService {
	@Autowired
	ItemNameDaoImpl itemNameDao;
	
	@Transactional
	public void test() {
		ItemName cls = itemNameDao.find(10L);
		System.out.println("cls=" + cls.getName());
	}

}
