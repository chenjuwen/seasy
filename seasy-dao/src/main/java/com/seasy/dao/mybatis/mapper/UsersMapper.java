package com.seasy.dao.mybatis.mapper;

import java.util.List;

import com.seasy.dao.mybatis.IMapper;
import com.seasy.interfaces.dto.ResourcesDTO;
import com.seasy.interfaces.dto.RolesDTO;
import com.seasy.interfaces.dto.UsersDTO;

public interface UsersMapper extends IMapper{
	 public UsersDTO selectByPrimaryKey(Long userId);
	 public List<UsersDTO> selectByLoginName(String loginName);
	 public int insert(UsersDTO user);
	 public int update(UsersDTO user);
	 public int updatePassword(UsersDTO user);
	 public int updateState(UsersDTO user);
	 public int updateLoginInfo(Long userId);
	 
	 //security
	 public List<ResourcesDTO> getAllResourceByUserId(Long userId);
	 public List<ResourcesDTO> getTopResourceByUserId(Long userId);
	 public List<RolesDTO> getAllRoleByUserId(Long userId);
	 
}
