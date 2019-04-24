package com.seasy.dao.mybatis.mapper;

import java.util.List;

import com.seasy.dao.mybatis.IMapper;
import com.seasy.interfaces.dto.FilterChainDefinsDTO;

public interface FilterChainDefinsMapper extends IMapper{
	public List<FilterChainDefinsDTO> findAll();
	public FilterChainDefinsDTO selectById(Long id);
	public int insert(FilterChainDefinsDTO dto);
	public int update(FilterChainDefinsDTO dto);
	public int delete(Long id);
}
