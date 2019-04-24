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
import com.seasy.interfaces.dto.FilterChainDefinsDTO;
import com.seasy.interfaces.dto.ResultDTO;

@Path("/filterChainDefins")
@Produces(MediaType.APPLICATION_XML)
public interface FilterChainDefinsService extends SeasyService {
	@GET
	@Path("findAll")
	public List<FilterChainDefinsDTO> findAll();

	@GET
	@Path("selectById/{id}")
	public FilterChainDefinsDTO selectById(@PathParam("id") Long id);

	@GET
	@Path("selectByPage/{pageNum}/{pageSize}")
	public PageInfo<FilterChainDefinsDTO> selectByPage(@PathParam("pageNum") int pageNum, 
			@PathParam("pageSize") int pageSize, Object params);

	@POST
	@Path("insert")
	public ResultDTO insert(FilterChainDefinsDTO dto);

	@POST
	@Path("update")
	public ResultDTO update(FilterChainDefinsDTO dto);

	@GET
	@Path("delete/{id}")
	public boolean delete(@PathParam("id") Long id);
	
}
