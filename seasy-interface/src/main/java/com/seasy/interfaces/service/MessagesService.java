package com.seasy.interfaces.service;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.pagehelper.PageInfo;
import com.seasy.interfaces.SeasyService;
import com.seasy.interfaces.dto.MessagesDTO;

@Path("/messages")
@Produces(MediaType.APPLICATION_XML)
public interface MessagesService extends SeasyService {
	@GET
	@Path("selectById/{id}")
	public MessagesDTO selectById(@PathParam("id") Long id);

	@GET
	@Path("selectByPage/{pageNum}/{pageSize}")
	public PageInfo<MessagesDTO> selectByPage(@PathParam("pageNum") int pageNum, 
			@PathParam("pageSize") int pageSize, Object params);

	@GET
	@Path("selectMessagesByUserid/{userId}/{pageNum}/{pageSize}")
	public PageInfo<MessagesDTO> selectMessagesByUserid(
			@PathParam("userId") long userId, @PathParam("pageNum") int pageNum, 
			@PathParam("pageSize") int pageSize);

	@GET
	@Path("selectUnreadMessagesByUserid/{userId}/{pageNum}/{pageSize}")
	public PageInfo<MessagesDTO> selectUnreadMessagesByUserid(
			@PathParam("userId") long userId, @PathParam("pageNum") int pageNum, 
			@PathParam("pageSize") int pageSize);

	@GET
	@Path("selectUnreadCountByUserid/{userId}")
	public int selectUnreadCountByUserid(@PathParam("userId") long userId);

	@POST
	@Path("insert")
	public boolean insert(MessagesDTO dto);

	@POST
	@Path("delete/{id}")
	public boolean delete(@PathParam("id") Long id);

	@POST
	@Path("deleteUserMessage/{id}/{userId}")
	public boolean deleteUserMessage(@PathParam("id") Long id, @PathParam("userId") Long userId);

	@GET
	@Path("selectUserIdsByRole/{roleIds}")
	public String selectUserIdsByRole(@PathParam("roleIds") String roleIds);
	
}
