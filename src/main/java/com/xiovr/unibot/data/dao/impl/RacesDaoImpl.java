package com.xiovr.unibot.data.dao.impl;

import org.springframework.stereotype.Repository;

import com.xiovr.unibot.data.dao.RacesDao;
import com.xiovr.unibot.data.model.Races;
@Repository
public class RacesDaoImpl extends AbstractIdNameDaoBase<Races, Long>
						  implements RacesDao
{

	public RacesDaoImpl() {
		super();
	}

}
