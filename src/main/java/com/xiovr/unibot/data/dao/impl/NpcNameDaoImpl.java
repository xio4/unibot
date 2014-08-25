package com.xiovr.unibot.data.dao.impl;

import org.springframework.stereotype.Repository;

import com.xiovr.unibot.data.dao.NpcNameDao;
import com.xiovr.unibot.data.model.NpcName;
@Repository
public class NpcNameDaoImpl extends AbstractIdNameDaoBase<NpcName, Long>
							implements NpcNameDao
{

	public NpcNameDaoImpl() {
		super();
	}

}
