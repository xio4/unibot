package com.xiovr.unibot.data.dao.impl;

import org.springframework.stereotype.Repository;

import com.xiovr.unibot.data.dao.ClassesDao;
import com.xiovr.unibot.data.model.Classes;
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