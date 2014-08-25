package com.xiovr.unibot.data.dao.impl;

import org.springframework.stereotype.Repository;

import com.xiovr.unibot.data.dao.SkillNameDao;
import com.xiovr.unibot.data.model.SkillName;
@Repository
public class SkillNameDaoImpl extends AbstractIdNameDaoBase<SkillName, Long>
							  implements SkillNameDao
{

	public SkillNameDaoImpl() {
		super();
	}

}
