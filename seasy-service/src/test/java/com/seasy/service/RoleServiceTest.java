package com.seasy.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageInfo;
import com.seasy.core.test.BaseServiceTest;
import com.seasy.interfaces.dto.RolesDTO;
import com.seasy.interfaces.dto.ResultDTO;
import com.seasy.interfaces.service.RoleService;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class RoleServiceTest extends BaseServiceTest{
	@Resource
	private RoleService roleService;
	
	@Test
	@Rollback
	public void roleServiceTest() throws Exception {
		//insert
		RolesDTO role = new RolesDTO();
		role.setRoleNo("admin2");
		role.setRoleName("Administrator2");
		role.setRoleDesc("系统管理员2");
		ResultDTO dto = roleService.insert(role);
		Assert.assertTrue(ResultDTO.CODE_SUCCESS.equals(dto.getCode()));
		
		//selectByRoleNo
		role = roleService.selectByRoleNo("admin2");
		Assert.assertNotNull(role);
		
		//selectById
		role = roleService.selectById(role.getId());
		Assert.assertNotNull(role);
		
		//selectByPage
		PageInfo<RolesDTO> pageInfo = roleService.selectByPage(1, 15, null);
		Assert.assertTrue(!CollectionUtils.isEmpty(pageInfo.getList()));
		
		//selectRolesByLoginName
		List<RolesDTO> list = roleService.selectRolesByLoginName("admin");
		Assert.assertEquals(1, list.size());
		
		//update
		role.setOperateTime(new Date());
		dto = roleService.update(role);
		Assert.assertTrue(ResultDTO.CODE_SUCCESS.equals(dto.getCode()));
		
		//delete
		boolean b = roleService.delete(role.getId());
		Assert.assertTrue(b);
	}
	
	//@Test
	@Commit
	public void delete() throws Exception {
		roleService.delete(1014L);
	}
	
	//@Test
	@Commit
	public void generateDataTest() throws Exception {
		for(int i=1; i<30; i++){
			RolesDTO role = new RolesDTO();
			role.setRoleNo("no" + i);
			role.setRoleName("name" + i);
			role.setRoleDesc("desc" + 1);
			roleService.insert(role);
		}
	}
	
}
