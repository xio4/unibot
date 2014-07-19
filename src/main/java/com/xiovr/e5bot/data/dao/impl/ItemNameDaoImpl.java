package com.xiovr.e5bot.data.dao.impl;
import org.springframework.stereotype.Repository;

import com.xiovr.e5bot.data.dao.ItemNameDao;
import com.xiovr.e5bot.data.model.ItemName;
@Repository
public class ItemNameDaoImpl extends AbstractIdNameDaoBase<ItemName, Long>
							 implements ItemNameDao
{

	public ItemNameDaoImpl() {
		super();
	}

}
