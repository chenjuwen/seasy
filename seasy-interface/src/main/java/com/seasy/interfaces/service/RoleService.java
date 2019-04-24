package com.seasy.interfaces.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.pagehelper.PageInfo;
import com.seasy.interfaces.SeasyService;
import com.seasy.interfaces.dto.RolesDTO;
import com.seasy.interfaces.dto.ResultDTO;

@Path("/role")
@Produces(MediaType.APPLICATION_XML)
public interface RoleService extends SeasyService {
	@GET
	@Path("selectByPage/{pageNum}/{pageSize}")
	public PageInfo<RolesDTO> selectByPage(@PathParam("pageNum") int pageNum, 
			@PathParam("pageSize") int pageSize, Object params);

	@GET
	@Path("selectById/{id}")
	public RolesDTO selectById(@PathParam("id") Long id);

	@GET
	@Path("selectByRoleNo/{roleNo}")
	public RolesDTO selectByRoleNo(@PathParam("roleNo") String roleNo);

	@POST
	@Path("insert")
	public ResultDTO insert(RolesDTO role);

	@POST
	@Path("delete/{roleId}")
	public boolean delete(@PathParam("roleId") Long roleId);

	@POST
	@Path("update")
	public ResultDTO update(RolesDTO role);


	@POST
	@Path("insertUserRoles/{userId}/{roleIds}")
	public boolean insertUserRoles(@PathParam("userId") Long userId, @PathParam("roleIds") String roleIds);
	
	@POST
	@Path("deleteUserRoles/{userId}/{roleIds}")
	public boolean deleteUserRoles(@PathParam("userId") Long userId, @PathParam("roleIds") String roleIds);
	
	
	@GET
	@Path("selectRolesByLoginName/{loginName}")
	public List<RolesDTO> selectRolesByLoginName(@PathParam("loginName") String loginName);

	@GET
	@Path("getAllRoles")
	public List<RolesDTO> getAllRoles();
	
	@GET
	@Path("getUserAvailableRoles")
	public List<RolesDTO> getUserAvailableRoles(@PathParam("userId") Long userId);
	
}
