package com.seasy.dao.mybatis;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.github.pagehelper.PageInfo;
import com.seasy.dao.Paging;
import com.seasy.dao.SeasyDao;

public class MybatisDaoImpl extends SqlSessionDaoSupport implements SeasyDao {
	public <T> PageInfo<T> selectPage(String key, Object params, Paging paging) {
		RowBounds rowBounds = new RowBounds(paging.getFirstResult(), paging.getPageSize());
        List<T> list = getSqlSessionTemplate().selectList(key, params, rowBounds);
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        return pageInfo;
	}
	
	public <T> T selectByPrimaryKey(String key, Object obj) {
		return getSqlSessionTemplate().selectOne(key, obj);
	}
	
	public <T> int insert(String key, T obj) {
		return getSqlSessionTemplate().insert(key, obj);
	}
	
	public <T> Long batchInsert(String key, List<T> list) {
		if (CollectionUtils.isEmpty(list)) {
			return 0L;
		}
        
        SqlSession sqlSession = null;
        long count = 0;
        
        try{
	        sqlSession = getBatchSqlSession();
	        for (T entity : list) {
	            sqlSession.insert(key, entity);
	            ++count;
	        }
	        sqlSession.commit();
	        
        }finally{
        	if(sqlSession != null){
        		sqlSession.close();
        	}
        }
        
        return count;
	}
	
	public <T> int update(String key, T obj) {
		return getSqlSessionTemplate().update(key, obj);
	}
	
	public <T> Long batchUpdate(String key, List<T> list) {
		if (CollectionUtils.isEmpty(list)) {
			return 0L;
		}
		
		SqlSession sqlSession = null;
        long count = 0;
		
        try{
            sqlSession = getBatchSqlSession();
	        for (Object entity : list) {
	            sqlSession.update(key, entity);
	            ++count;
	        }
            sqlSession.commit();
        }finally{
        	if(sqlSession != null){
        		sqlSession.close();
        	}
        }
        return count;
	}
	
	public int delete(String key, Object params) {
		return getSqlSessionTemplate().delete(key, params);
	}
	
	public Long batchDelete(String key, List<? extends Object> list) {
		if (CollectionUtils.isEmpty(list)) {
			return 0L;
		}
		
		SqlSession sqlSession = null;
        long count = 0;
		
        try{
            sqlSession = getBatchSqlSession();
	        for (Object entity : list) {
	            sqlSession.delete(key, entity);
	            ++count;
	        }
            sqlSession.commit();
        }finally{
        	if(sqlSession != null){
        		sqlSession.close();
        	}
        }
        
        return count;
	}
	
}
