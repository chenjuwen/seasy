package com.seasy.dao.mybatis.mapper;

import java.util.Map;

import com.seasy.dao.mybatis.IMapper;
import com.seasy.interfaces.dto.MessagesDTO;

public interface MessagesMapper extends IMapper{
	 public MessagesDTO selectByPrimaryKey(Long id);
	 public int selectUnreadCountByUserid(String userId);
	 public int insert(MessagesDTO dto);
	 public int delete(Long id);
	 public int deleteUserMessageWithAll(Map<String, Object> map);
	 public int deleteUserMessageWithNoAll(Map<String, Object> map);
	 public String selectUserIdsByRole(String roleIds);
}
