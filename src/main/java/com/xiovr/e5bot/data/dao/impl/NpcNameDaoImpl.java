package com.xiovr.e5bot.data.dao.impl;

import org.springframework.stereotype.Repository;

import com.xiovr.e5bot.data.dao.NpcNameDao;
import com.xiovr.e5bot.data.model.NpcName;
@Repository
public class NpcNameDaoImpl extends AbstractIdNameDaoBase<NpcName, Long>
							implements NpcNameDao
{

	public NpcNameDaoImpl() {
		super();
	}

}
