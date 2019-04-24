package com.seasy.service;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import com.seasy.core.test.BaseServiceTest;
import com.seasy.core.util.MarshallerUtil;
import com.seasy.interfaces.dto.ResultDTO;
import com.seasy.interfaces.dto.UsersDTO;
import com.seasy.interfaces.service.UserService;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserServiceTest extends BaseServiceTest{
	@Resource
	private UserService userService;
	
	@Test
	@Rollback
	public void userServiceTest(){
		//insert
		UsersDTO user = new UsersDTO();
		user.setLoginName("admin");
		user.setUsername("Administrator");
		user.setPassword("123456");		
		user.setEnabled(1L);
		
		ResultDTO dto = userService.insert(user);
		System.out.println(MarshallerUtil.marshaller(dto));
		Assert.assertTrue(ResultDTO.CODE_ERROR.equals(dto.getCode()));
		
		//selectByLoginName
		user = userService.selectByLoginName("admin");
		Assert.assertTrue(user != null);
		
		//selectByPrimaryKey
		user = userService.selectByPrimaryKey(user.getId());
		Assert.assertNotNull(user);
		
		//update
		user.setPhone("13700000000");
		dto = userService.update(user);
		Assert.assertTrue(ResultDTO.CODE_SUCCESS.equals(dto.getCode()));
		
		//updatePassword
		String newPassword = "123456";
		ResultDTO resultDTO = userService.updatePassword(user.getId(), newPassword, newPassword);
		Assert.assertTrue(ResultDTO.CODE_SUCCESS.equals(resultDTO.getCode()));
		
		//updateLoginInfo
		boolean b = userService.updateLoginInfo(user.getId());
		Assert.assertTrue(b);
	}
	
	//@Test
	@Commit
	public void createData(){
		for(int i=1; i<=20; i++){
			UsersDTO user = new UsersDTO();
			user.setLoginName("user"+i);
			user.setUsername("User"+i);
			user.setPassword("123456");
			user.setEnabled(1L);
			
			ResultDTO dto = userService.insert(user);
			Assert.assertTrue(ResultDTO.CODE_SUCCESS.equals(dto.getCode()));
		}
	}
	
	//@Test
	@Commit
	public void generateDataTest(){
		//admin
		UsersDTO user = new UsersDTO();
		user.setLoginName("admin");
		user.setUsername("Administrator");
		user.setPassword("123456");
		user.setEnabled(1L);
		
		ResultDTO dto = userService.insert(user);
		Assert.assertTrue(ResultDTO.CODE_SUCCESS.equals(dto.getCode()));
		
		
		//test
		user = new UsersDTO();
		user.setLoginName("test");
		user.setUsername("Test");
		user.setPassword("123456");
		user.setEnabled(1L);
		
		dto = userService.insert(user);
		Assert.assertTrue(ResultDTO.CODE_SUCCESS.equals(dto.getCode()));
	}
	
}
