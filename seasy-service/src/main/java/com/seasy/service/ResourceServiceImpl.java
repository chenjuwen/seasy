package com.seasy.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.seasy.dao.mybatis.mapper.ResourcesMapper;
import com.seasy.interfaces.dto.ResourcesDTO;
import com.seasy.interfaces.dto.RoleResourceDTO;
import com.seasy.interfaces.dto.ResultDTO;
import com.seasy.interfaces.service.ResourceService;

@Service("resourceService")
public class ResourceServiceImpl extends AbstractBaseService implements ResourceService {
	@Autowired
	private ResourcesMapper resourcesMapper;

	@Override
	public ResourcesDTO selectOne(Long id) {
		return resourcesMapper.selectOne(id);
	}

	@Override
	public ResultDTO insert(ResourcesDTO res) {
		ResultDTO dto = new ResultDTO();
		if(existsResourceNo(res)){
			dto.setCode(ResultDTO.CODE_ERROR);
			dto.setMessage("资源编号 " + res.getResNo() + " 已存在！");
		}else{
			resourcesMapper.insert(res);
			dto.setCode(ResultDTO.CODE_SUCCESS);
		}
		return dto;
	}

	@Override
	public ResultDTO update(ResourcesDTO res) {
		ResultDTO dto = new ResultDTO();
		if(existsResourceNo(res)){
			dto.setCode(ResultDTO.CODE_ERROR);
			dto.setMessage("资源编号 " + res.getResNo() + " 已存在！");
		}else{
			resourcesMapper.update(res);
			dto.setCode(ResultDTO.CODE_SUCCESS);
		}
		return dto;
	}

	private boolean existsResourceNo(ResourcesDTO res) {
		Assert.hasText(res.getResNo());
		List<ResourcesDTO> list = resourcesMapper.findExistsResNo(res);
		if(!CollectionUtils.isEmpty(list)){
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(Long id) {
		resourcesMapper.deleteRelationResource(id);
		resourcesMapper.delete(id);
		return true;
	}
	
	@Override
	public List<ResourcesDTO> getAllResource() {
		return resourcesMapper.getAllResource();
	}

	@Override
	public List<ResourcesDTO> selectByParentId(Long parentId) {
		return resourcesMapper.selectByParentId(parentId);
	}

	/**
	 * 级联查询资源信息
	 */
	@Override
	public List<ResourcesDTO> cascadeSelectByParentId(Long parentId) {
		List<ResourcesDTO> list = resourcesMapper.selectByParentId(parentId);
		if(CollectionUtils.isNotEmpty(list)){
			for(ResourcesDTO dto: list){
				loadChildren(dto);
			}
		}
		return list;
	}
	
	private void loadChildren(ResourcesDTO dto){
		List<ResourcesDTO> subList = resourcesMapper.selectByParentId(dto.getId());
		if(CollectionUtils.isNotEmpty(subList)){
			dto.setChildren(subList);
			
			for(ResourcesDTO subDto: subList){
				loadChildren(subDto);
			}
		}
	}

	@Override
	public List<ResourcesDTO> selectByRoleId(Long roleId) {
		return resourcesMapper.selectByRoleId(roleId);
	}

	@Override
	public boolean deleteRelationRole(Long roleId) {
		resourcesMapper.deleteRelationRole(roleId);
		return true;
	}

	@Override
	public boolean insertRoleResource(List<RoleResourceDTO> list) {
		getMybatisDao().batchInsert("com.seasy.dao.mybatis.mapper.ResourcesMapper.insertRoleResource", list);
		return true;
	}
	
}
