package com.xiovr.e5bot.data.dao.impl;

import org.springframework.stereotype.Repository;

import com.xiovr.e5bot.data.dao.RacesDao;
import com.xiovr.e5bot.data.model.Races;
@Repository
public class RacesDaoImpl extends AbstractIdNameDaoBase<Races, Long>
						  implements RacesDao
{

	public RacesDaoImpl() {
		super();
	}

}
