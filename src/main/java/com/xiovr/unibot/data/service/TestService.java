package com.xiovr.unibot.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiovr.unibot.data.dao.impl.ClassesDaoImpl;
import com.xiovr.unibot.data.dao.impl.ItemNameDaoImpl;
import com.xiovr.unibot.data.model.Classes;
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
