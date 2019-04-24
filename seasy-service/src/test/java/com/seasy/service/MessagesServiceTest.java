package com.seasy.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import com.github.pagehelper.PageInfo;
import com.seasy.core.test.BaseServiceTest;
import com.seasy.core.util.MarshallerUtil;
import com.seasy.interfaces.dto.MessagesDTO;
import com.seasy.interfaces.service.MessagesService;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MessagesServiceTest extends BaseServiceTest{
	@Resource
	private MessagesService messagesService;
	
	@Test
	//@Rollback
	@Commit
	public void test() throws Exception {
		MessagesDTO dto = messagesService.selectById(1L);
		System.out.println(MarshallerUtil.marshaller(dto));
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", "ALL");
		map.put("contents", "信");
		PageInfo<MessagesDTO> pageInfo = messagesService.selectByPage(1, 2, map);
		System.out.println(pageInfo.getTotal());
		
		MessagesDTO dto2 = new MessagesDTO();
		dto2.setType("USERS");
		dto2.setContents("信息5");
		dto2.setReceiveId("1;2;3;4;5;");
		dto2.setSendUserid(1L);
		dto2.setOperator("Administrator");
		dto2.setOperateTime(new Date());
		messagesService.insert(dto2);
		System.out.println(dto2.getId());
		
		boolean b = messagesService.delete(dto2.getId());
		System.out.println(b);
	}
	
	@Test
	public void selectMessagesByUserid() throws Exception {
		PageInfo<MessagesDTO> pageInfo = messagesService.selectMessagesByUserid(2, 1, 20);
		System.out.println(pageInfo.getTotal());
		for(MessagesDTO m : pageInfo.getList()){
			System.out.println(m.getId() + ", " + m.getContents() + ", " + m.isReaded() + ", " + m.getSendUserName());
		}
	}
	
	@Test
	public void selectUnreadMessagesByUserid() throws Exception {
		PageInfo<MessagesDTO> pageInfo = messagesService.selectUnreadMessagesByUserid(2, 1, 20);
		System.out.println(pageInfo.getTotal());
		System.out.println();
		
		for(MessagesDTO m : pageInfo.getList()){
			System.out.println(m.getId() + ", " + m.getContents() + ", " + m.isReaded() + ", " + m.getSendUserName());
		}

		System.out.println();
		int count = messagesService.selectUnreadCountByUserid(2);
		System.out.println(count);
	}
	
	@Test
	@Rollback
	public void deleteUserMessage() throws Exception {
		boolean b = messagesService.deleteUserMessage(7L, 2L);
		System.out.println(b);
		
		b = messagesService.deleteUserMessage(2L, 2L);
		System.out.println(b);
	}
	
	@Test
	public void selectUserIdsByRole() throws Exception {
		String roleIds = "1,2";
		String userIds = messagesService.selectUserIdsByRole(roleIds);
		System.out.println(userIds);
	}
	
}
