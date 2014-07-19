package com.xiovr.e5bot.data.dao.impl;

import org.springframework.stereotype.Repository;

import com.xiovr.e5bot.data.dao.SkillNameDao;
import com.xiovr.e5bot.data.model.SkillName;
@Repository
public class SkillNameDaoImpl extends AbstractIdNameDaoBase<SkillName, Long>
							  implements SkillNameDao
{

	public SkillNameDaoImpl() {
		super();
	}

}
