package com.xiovr.unibot.data.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.xiovr.unibot.TestBase;
import com.xiovr.unibot.data.dao.ClassesDao;
import com.xiovr.unibot.data.dao.IdNameDaoBase;
import com.xiovr.unibot.data.dao.ItemNameDao;
import com.xiovr.unibot.data.dao.NpcNameDao;
import com.xiovr.unibot.data.dao.RacesDao;
import com.xiovr.unibot.data.dao.SkillNameDao;
import com.xiovr.unibot.data.dao.SysStringDao;
import com.xiovr.unibot.data.dao.SystemMsgDao;
import com.xiovr.unibot.data.model.IdNameEntity;
import com.xiovr.unibot.data.model.ItemName;
import com.xiovr.unibot.data.model.NpcName;
import com.xiovr.unibot.data.model.SkillName;
import com.xiovr.unibot.data.model.SysString;
import com.xiovr.unibot.data.model.SystemMsg;

@TransactionConfiguration(transactionManager="transactionManager")
public class DaoTest extends TestBase {

	private static final String CHECK_TEXT = "Check new value";
	@Autowired
	ClassesDao classesDao;
	@Autowired
	ItemNameDao itemNameDao;
	@Autowired
	NpcNameDao npcNameDao;
	@Autowired
	RacesDao racesDao;
	@Autowired
	SkillNameDao skillNameDao;
	@Autowired
	SysStringDao sysStringDao;
	@Autowired
	SystemMsgDao systemMsgDao;

	private <T extends IdNameDaoBase<K,Long>,
			K extends IdNameEntity> void checkUpdateAndDelete(T t, K k)
	{
		k.setName(CHECK_TEXT);
		t.update(k);
		IdNameEntity ent= t.getById(k.getId());
		Assert.assertNotNull(ent);
		Assert.assertEquals(k, ent);
		t.delete(k.getId());
		ent= t.getById(k.getId());
		Assert.assertNull(ent);
	}
	@Test
	@Transactional(readOnly=false)
	public void testIdNameDaoBaseDependencies_check_works_getById_delete_update()
	{
		// TODO ClassesDao not checked
		Assert.assertNotNull(classesDao);
		Assert.assertNotNull(itemNameDao);
		Assert.assertNotNull(npcNameDao);
		// TODO RacesDao not checked
		Assert.assertNotNull(racesDao);
		Assert.assertNotNull(skillNameDao);
		Assert.assertNotNull(sysStringDao);
		Assert.assertNotNull(systemMsgDao);
		
		ItemName in = itemNameDao.getById(10L);
		Assert.assertNotNull(in);
		Assert.assertEquals(in.getName(), "Сумка новобранца");
		checkUpdateAndDelete(itemNameDao, in);

		NpcName nn = npcNameDao.getById(100L);
		Assert.assertNotNull(nn);
		Assert.assertEquals(nn.getName(), "Погонщик страйдеров");
		checkUpdateAndDelete(npcNameDao, nn);

		SkillName sn = skillNameDao.getById(100L);
		Assert.assertNotNull(sn);
		Assert.assertEquals(sn.getName(), "Оглушающая атака");
		checkUpdateAndDelete(skillNameDao, sn);

		SysString ss = sysStringDao.getById(10L);
		Assert.assertNotNull(ss);
		Assert.assertEquals(ss.getName(), "Хлопок");
		checkUpdateAndDelete(sysStringDao, ss);
		
		SystemMsg sm = systemMsgDao.getById(10L);
		Assert.assertNotNull(sm);
		Assert.assertEquals(sm.getName(), "$1s уже состоит в гильдии.");
		checkUpdateAndDelete(systemMsgDao, sm);
	}

}
