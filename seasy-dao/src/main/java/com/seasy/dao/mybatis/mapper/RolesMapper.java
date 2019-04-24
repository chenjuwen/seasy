package com.seasy.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.seasy.dao.mybatis.IMapper;
import com.seasy.interfaces.dto.RolesDTO;

public interface RolesMapper extends IMapper{
	 public List<RolesDTO> getAllRoles();
	 public List<RolesDTO> getUserAvailableRoles(Long userId);
	 public RolesDTO selectById(Long id);
	 public RolesDTO selectByRoleNo(String roleNo);
	 public List<RolesDTO> findExistsRoleNo(RolesDTO role);
	 public int insert(RolesDTO role);
	 public int delete(Long id);
	 public int update(RolesDTO role);

	 //UserRole
	 public int deleteUserRoles(Map<String, String> map);
	 
	 //security
	 public List<RolesDTO> selectRolesByLoginName(String loginName);
	 public int deleteRelationUser(Long roleId);
	 public int deleteRelationResource(Long roleId);
}
