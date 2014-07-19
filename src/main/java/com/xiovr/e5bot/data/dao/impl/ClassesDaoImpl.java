package com.xiovr.e5bot.data.dao.impl;

import org.springframework.stereotype.Repository;

import com.xiovr.e5bot.data.dao.ClassesDao;
import com.xiovr.e5bot.data.model.Classes;
/**
 * @author xio4
 *
 */
@Repository
public class ClassesDaoImpl extends AbstractIdNameDaoBase<Classes, Long>
							implements ClassesDao
{

	public ClassesDaoImpl() {
		super();
	}
	
}