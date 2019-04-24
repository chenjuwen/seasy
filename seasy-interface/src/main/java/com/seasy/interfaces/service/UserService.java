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
import com.seasy.interfaces.dto.ResourcesDTO;
import com.seasy.interfaces.dto.RolesDTO;
import com.seasy.interfaces.dto.ResultDTO;
import com.seasy.interfaces.dto.UsersDTO;

@Path("/user")
@Produces(MediaType.APPLICATION_XML)
public interface UserService extends SeasyService {
	@GET
	@Path("selectByPrimaryKey/{userId}")
	public UsersDTO selectByPrimaryKey(@PathParam("userId") Long userId);

	@GET
	@Path("selectByLoginName/{loginName}")
	public UsersDTO selectByLoginName(@PathParam("loginName") String loginName);

	@GET
	@Path("selectByPage/{pageNum}/{pageSize}")
	public PageInfo<UsersDTO> selectByPage(@PathParam("pageNum") int pageNum, 
			@PathParam("pageSize") int pageSize, Object params);
	
	@POST
	@Path("insert")
	public ResultDTO insert(UsersDTO user);

	@POST
	@Path("update")
	public ResultDTO update(UsersDTO user);

	@GET
	@Path("updatePassword/{userId}/{oldPassword}/{newPassword}")
	public ResultDTO updatePassword(@PathParam("userId") Long userId, @PathParam("oldPassword") String oldPassword, 
			@PathParam("newPassword") String newPassword);

	@POST
	@Path("resetPassword")
	public boolean resetPassword(Long userId);

	@POST
	@Path("updateState")
	public boolean updateState(UsersDTO user);

	@POST
	@Path("updateLoginInfo")
	public boolean updateLoginInfo(Long userId);
	
	
	/**
	 * Security
	 */
	@GET
	@Path("getAllResourceByUserId/{userId}")
	public List<ResourcesDTO> getAllResourceByUserId(@PathParam("userId") Long userId);

	@GET
	@Path("getTopResourceByUserId/{userId}")
	public List<ResourcesDTO> getTopResourceByUserId(@PathParam("userId") Long userId);

	@GET
	@Path("getAllRoleByUserId/{userId}")
	public List<RolesDTO> getAllRoleByUserId(@PathParam("userId") Long userId);
	
}
