package com.xiovr.unibot.data.dao.impl;
import org.springframework.stereotype.Repository;

import com.xiovr.unibot.data.dao.ItemNameDao;
import com.xiovr.unibot.data.model.ItemName;
@Repository
public class ItemNameDaoImpl extends AbstractIdNameDaoBase<ItemName, Long>
							 implements ItemNameDao
{

	public ItemNameDaoImpl() {
		super();
	}

}
