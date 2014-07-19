package com.xiovr.e5bot.data.dao.impl;

import org.springframework.stereotype.Repository;

import com.xiovr.e5bot.data.dao.SysStringDao;
import com.xiovr.e5bot.data.model.SysString;
@Repository
public class SysStringDaoImpl extends AbstractIdNameDaoBase<SysString, Long>
							  implements SysStringDao
{

	public SysStringDaoImpl() {
		super();
	}

}
