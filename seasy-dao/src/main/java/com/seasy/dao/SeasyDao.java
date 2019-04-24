package com.seasy.dao;

import java.util.List;

import com.github.pagehelper.PageInfo;

public abstract interface SeasyDao {
	public <T> PageInfo<T> selectPage(String key, Object params, Paging paging);
	
	public <T> T selectByPrimaryKey(String key, Object params);
	
	public <T> int insert(String key, T obj);
	
	public <T> Long batchInsert(String key, List<T> list);
	
	public <T> int update(String key, T obj);

	public <T> Long batchUpdate(String key, List<T> list);
	
	public int delete(String key, Object params);

	public Long batchDelete(String key, List<? extends Object> list);
}

