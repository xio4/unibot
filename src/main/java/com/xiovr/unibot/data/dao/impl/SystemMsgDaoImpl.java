package com.xiovr.unibot.data.dao.impl;

import org.springframework.stereotype.Repository;

import com.xiovr.unibot.data.dao.SystemMsgDao;
import com.xiovr.unibot.data.model.SystemMsg;
@Repository
public class SystemMsgDaoImpl extends AbstractIdNameDaoBase<SystemMsg, Long>
							  implements SystemMsgDao
{

	public SystemMsgDaoImpl() {
		super();
	}

}
