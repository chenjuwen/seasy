package com.seasy.dao.mybatis.mapper;

import java.util.List;

import com.seasy.dao.mybatis.IMapper;
import com.seasy.interfaces.dto.ResourcesDTO;

public interface ResourcesMapper extends IMapper{
	public ResourcesDTO selectOne(Long id);
	public int insert(ResourcesDTO res);
	public int update(ResourcesDTO res);
	public int delete(Long id);
	public List<ResourcesDTO> getAllResource();
	public List<ResourcesDTO> selectByParentId(Long parentId);
	 public List<ResourcesDTO> findExistsResNo(ResourcesDTO res);

	 //security
	public List<ResourcesDTO> selectByRoleId(Long roleId);
	public int deleteRelationRole(Long roleId);
	public int deleteRelationResource(Long resId);
}
