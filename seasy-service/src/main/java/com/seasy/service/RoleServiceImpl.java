package com.seasy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageInfo;
import com.seasy.dao.Paging;
import com.seasy.dao.mybatis.mapper.RolesMapper;
import com.seasy.interfaces.dto.RolesDTO;
import com.seasy.interfaces.dto.UserRoleDTO;
import com.seasy.interfaces.dto.ResultDTO;
import com.seasy.interfaces.service.RoleService;

@Service("roleService")
public class RoleServiceImpl extends AbstractBaseService implements RoleService {
	@Autowired
	private RolesMapper rolesMapper;
	
	@Override
	public PageInfo<RolesDTO> selectByPage(int pageNum, int pageSize, Object params) {
		String key = "com.seasy.dao.mybatis.mapper.RolesMapper.selectByPage";
		Paging paging = new Paging(pageNum, pageSize);
		return getMybatisDao().selectPage(key, params, paging);
	}

	@Override
	public RolesDTO selectById(Long id) {
		return rolesMapper.selectById(id);
	}

	@Override
	public RolesDTO selectByRoleNo(String roleNo) {
		return rolesMapper.selectByRoleNo(roleNo);
	}
	
	@Override
	public ResultDTO insert(RolesDTO role) {
		ResultDTO dto = new ResultDTO();
		if(existsRoleNo(role)){
			dto.setCode(ResultDTO.CODE_ERROR);
			dto.setMessage("角色编号 " + role.getRoleNo() + " 已存在！");
		}else{
			rolesMapper.insert(role);
			dto.setCode(ResultDTO.CODE_SUCCESS);
		}
		return dto;
	}

	@Override
	public ResultDTO update(RolesDTO role) {
		ResultDTO dto = new ResultDTO();
		if(existsRoleNo(role)){
			dto.setCode(ResultDTO.CODE_ERROR);
			dto.setMessage("角色编号 " + role.getRoleNo() + " 已存在！");
		}else{
			rolesMapper.update(role);
			dto.setCode(ResultDTO.CODE_SUCCESS);
		}
		return dto;
	}

	private boolean existsRoleNo(RolesDTO role) {
		List<RolesDTO> list = rolesMapper.findExistsRoleNo(role);
		if(!CollectionUtils.isEmpty(list)){
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(Long roleId) {
		rolesMapper.deleteRelationUser(roleId);
		rolesMapper.deleteRelationResource(roleId);
		rolesMapper.delete(roleId);
		return true;
	}

	/**
	 * 批量添加用户角色信息
	 * @param userId
	 * @param roleIds 逗号分隔的角色ID
	 */
	@Override
	public boolean insertUserRoles(Long userId, String roleIds) {
		if(StringUtils.isNotEmpty(roleIds)){
			List<UserRoleDTO> list = new ArrayList<UserRoleDTO>();
			
			String[] roleIdsArr = roleIds.split(",");
			for(String roleId : roleIdsArr){
				UserRoleDTO dto = new UserRoleDTO();
				dto.setUserId(userId);
				dto.setRoleId(new Long(roleId));
				list.add(dto);
			}
			
			getMybatisDao().batchInsert("com.seasy.dao.mybatis.mapper.RolesMapper.insertUserRoles", list);
		}
		return true;
	}

	/**
	 * 批量删除用户角色信息
	 * @param userId
	 * @param roleIds 逗号分隔的角色ID
	 */
	@Override
	public boolean deleteUserRoles(Long userId, String roleIds) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId.toString());
		map.put("roleIds", roleIds);
		
		rolesMapper.deleteUserRoles(map);
		return true;
	}

	@Override
	public List<RolesDTO> selectRolesByLoginName(String loginName) {
		return rolesMapper.selectRolesByLoginName(loginName);
	}

	@Override
	public List<RolesDTO> getAllRoles() {
		return rolesMapper.getAllRoles();
	}

	@Override
	public List<RolesDTO> getUserAvailableRoles(Long userId) {
		return rolesMapper.getUserAvailableRoles(userId);
	}
	
}
