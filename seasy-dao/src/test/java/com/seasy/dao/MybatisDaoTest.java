package com.seasy.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import com.github.pagehelper.PageInfo;
import com.seasy.core.test.BaseServiceTest;
import com.seasy.dao.mybatis.mapper.RolesMapper;
import com.seasy.interfaces.dto.RolesDTO;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MybatisDaoTest extends BaseServiceTest {
	@Autowired
	private SeasyDao mybatisDao;
	
	@Autowired
	private RolesMapper roleMapper;
	
	private Long roleId;
	
	@Test
	@Rollback
	public void mybatisDaoTest(){
		insert();
		selectRolesByUsername();
		selectOne();
		selectByPage();
		batchInsert();
		updateOne();
		batchUpdate();
	}
	
	private void insert(){
		RolesDTO role = new RolesDTO();
		role.setRoleNo("admin2");
		role.setRoleName("Administrator2");
		role.setRoleDesc("系统管理员2");
		
		String key = "com.seasy.dao.mybatis.mapper.RolesMapper.insert";
		int result = mybatisDao.insert(key, role);
		assertTrue(1 == result);
		
		roleId = role.getId();
	}
	
	private void selectRolesByUsername(){
		List<RolesDTO> list = roleMapper.selectRolesByLoginName("admin");
		assertTrue(CollectionUtils.isNotEmpty(list));
	}
	
	private void selectOne(){
		String key = "com.seasy.dao.mybatis.mapper.RolesMapper.selectById";
		RolesDTO role = mybatisDao.selectByPrimaryKey(key, roleId);
		assertEquals("admin2", role.getRoleNo());
	}
	
	private void selectByPage(){	
		String key = "com.seasy.dao.mybatis.mapper.RolesMapper.selectByPage";
		PageInfo<RolesDTO> pageInfo = mybatisDao.selectPage(key, null, new Paging(1, 1));
		assertEquals(3, pageInfo.getTotal());
		
		List<RolesDTO> list = pageInfo.getList();
		for(Iterator<RolesDTO> it=list.iterator(); it.hasNext(); ){
			RolesDTO roles = it.next();
			System.out.println(roles.getRoleNo() + ": " + roles.getRoleName() + ": " + roles.getRoleDesc());
		}
	}
	
	private void batchInsert(){
		List<RolesDTO> list = new ArrayList<RolesDTO>();
		for(int i=1; i<=3; i++){
			RolesDTO role = new RolesDTO();
			role.setRoleNo("test"+i);
			role.setRoleName("test"+i);
			role.setRoleDesc("test"+i);
			role.setOperator("admin");
			list.add(role);
		}
		
		Long count = mybatisDao.batchInsert("com.seasy.dao.mybatis.mapper.RolesMapper.insert", list);
		assertTrue(count == 3);
		
		//batch delete
		List<Long> list2 = new ArrayList<Long>();
		for(RolesDTO r : list){
			list2.add(r.getId());
			System.out.println(r.getId() + ", " + r.getRoleNo() + ", " + r.getRoleName() + ", " + r.getRoleDesc());
		}
		
		Long count2 = mybatisDao.batchDelete("com.seasy.dao.mybatis.mapper.RolesMapper.delete", list2);
		assertTrue(count2 == 3);
	}
	
	private void updateOne(){
		RolesDTO role = roleMapper.selectById(roleId);
		role.setOperateTime(new Date());
		int result = mybatisDao.update("com.seasy.dao.mybatis.mapper.RolesMapper.update", role);
		assertTrue(result == 1);
	}
	
	private void batchUpdate(){
		List<RolesDTO> list = new ArrayList<RolesDTO>();
		
		RolesDTO role = roleMapper.selectById(roleId);
		role.setOperateTime(new Date());
		list.add(role);
		
		Long count = mybatisDao.batchUpdate("com.seasy.dao.mybatis.mapper.RolesMapper.update", list);
		assertTrue(count == 1);
	}
	
}
