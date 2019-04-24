package com.seasy.interfaces.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.seasy.interfaces.SeasyService;
import com.seasy.interfaces.dto.ResourcesDTO;
import com.seasy.interfaces.dto.RoleResourceDTO;
import com.seasy.interfaces.dto.ResultDTO;

@Path("/resource")
@Produces(MediaType.APPLICATION_XML)
public interface ResourceService extends SeasyService {
	@GET
	@Path("selectOne/{id}")
	public ResourcesDTO selectOne(@PathParam("id") Long id);

	@POST
	@Path("insert")
	public ResultDTO insert(ResourcesDTO res);

	@POST
	@Path("update")
	public ResultDTO update(ResourcesDTO res);

	@GET
	@Path("delete/{id}")
	public boolean delete(@PathParam("id") Long id);

	
	@GET
	@Path("getAllResource")
	public List<ResourcesDTO> getAllResource();
	
	@GET
	@Path("selectByParentId/{parentId}")
	public List<ResourcesDTO> selectByParentId(@PathParam("parentId") Long parentId);
	
	@GET
	@Path("cascadeSelectByParentId/{parentId}")
	public List<ResourcesDTO> cascadeSelectByParentId(@PathParam("parentId") Long parentId);
	
	
	@GET
	@Path("selectByRoleId/{roleId}")
	public List<ResourcesDTO> selectByRoleId(@PathParam("roleId") Long roleId);
	
	@GET
	@Path("deleteRelationRole/{roleId}")
	public boolean deleteRelationRole(@PathParam("roleId") Long roleId);

	@POST
	@Path("insertRoleResource")
	public boolean insertRoleResource(List<RoleResourceDTO> list);
	
}
