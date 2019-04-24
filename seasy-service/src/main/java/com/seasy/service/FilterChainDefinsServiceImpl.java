package com.seasy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.seasy.dao.Paging;
import com.seasy.dao.mybatis.mapper.FilterChainDefinsMapper;
import com.seasy.interfaces.dto.FilterChainDefinsDTO;
import com.seasy.interfaces.dto.ResultDTO;
import com.seasy.interfaces.service.FilterChainDefinsService;

@Service("filterChainDefinsService")
public class FilterChainDefinsServiceImpl extends AbstractBaseService implements FilterChainDefinsService {
	@Autowired
	private FilterChainDefinsMapper mapper;

	@Override
	public List<FilterChainDefinsDTO> findAll() {
		return mapper.findAll();
	}

	@Override
	public PageInfo<FilterChainDefinsDTO> selectByPage(int pageNum, int pageSize, Object params) {
		String key = "com.seasy.dao.mybatis.mapper.FilterChainDefinsMapper.findAll";
		Paging paging = new Paging(pageNum, pageSize);
		return getMybatisDao().selectPage(key, params, paging);
	}

	@Override
	public FilterChainDefinsDTO selectById(Long id) {
		return mapper.selectById(id);
	}
	
	@Override
	public ResultDTO insert(FilterChainDefinsDTO dto) {
		mapper.insert(dto);
		
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setCode(ResultDTO.CODE_SUCCESS);
		return resultDTO;
	}

	@Override
	public ResultDTO update(FilterChainDefinsDTO dto) {
		mapper.update(dto);
		
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setCode(ResultDTO.CODE_SUCCESS);
		return resultDTO;
	}

	@Override
	public boolean delete(Long id) {
		return 1 == mapper.delete(id);
	}
	
}
