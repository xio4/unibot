package com.xiovr.unibot.data.dao.impl;

import org.springframework.stereotype.Repository;

import com.xiovr.unibot.data.dao.SysStringDao;
import com.xiovr.unibot.data.model.SysString;
@Repository
public class SysStringDaoImpl extends AbstractIdNameDaoBase<SysString, Long>
							  implements SysStringDao
{

	public SysStringDaoImpl() {
		super();
	}

}
