package com.seasy.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.seasy.dao.Paging;
import com.seasy.dao.mybatis.mapper.MessagesMapper;
import com.seasy.interfaces.dto.MessagesDTO;
import com.seasy.interfaces.service.MessagesService;

@Service("messagesService")
public class MessagesServiceImpl extends AbstractBaseService implements MessagesService {
	@Autowired
	private MessagesMapper messagesMapper;

	@Override
	public MessagesDTO selectById(Long id) {
		return messagesMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageInfo<MessagesDTO> selectByPage(int pageNum, int pageSize, Object params) {
		String key = "com.seasy.dao.mybatis.mapper.MessagesMapper.selectByPage";
		Paging paging = new Paging(pageNum, pageSize);
		return getMybatisDao().selectPage(key, params, paging);
	}

	@Override
	public PageInfo<MessagesDTO> selectMessagesByUserid(long userId, int pageNum, int pageSize) {
		String key = "com.seasy.dao.mybatis.mapper.MessagesMapper.selectMessagesByUserid";
		Paging paging = new Paging(pageNum, pageSize);
		return getMybatisDao().selectPage(key, String.valueOf(userId), paging);
	}

	@Override
	public PageInfo<MessagesDTO> selectUnreadMessagesByUserid(long userId, int pageNum, int pageSize) {
		String key = "com.seasy.dao.mybatis.mapper.MessagesMapper.selectUnreadMessagesByUserid";
		Paging paging = new Paging(pageNum, pageSize);
		return getMybatisDao().selectPage(key, String.valueOf(userId), paging);
	}

	@Override
	public int selectUnreadCountByUserid(long userId) {
		return messagesMapper.selectUnreadCountByUserid(String.valueOf(userId));
	}

	@Override
	public boolean insert(MessagesDTO dto) {
		return 1 == messagesMapper.insert(dto);
	}

	@Override
	public boolean delete(Long id) {
		return 1 == messagesMapper.delete(id);
	}

	@Override
	public boolean deleteUserMessage(Long id, Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("userId", userId);
		
		messagesMapper.deleteUserMessageWithAll(map);
		messagesMapper.deleteUserMessageWithNoAll(map);
		return true;
	}

	@Override
	public String selectUserIdsByRole(String roleIds) {
		return messagesMapper.selectUserIdsByRole(roleIds);
	}
	
}
