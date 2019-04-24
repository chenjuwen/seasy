package com.seasy.dao.mybatis;

import static org.springframework.util.Assert.notNull;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.support.DaoSupport;

public class SqlSessionDaoSupport extends DaoSupport {
	private SqlSessionTemplate sqlSessionTemplate;

    public final void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
    
    public SqlSessionTemplate getSqlSessionTemplate() {
        return this.sqlSessionTemplate;
    }
    
    public SqlSession getBatchSqlSession() {
        return sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
    }
    
	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
		notNull(this.sqlSessionTemplate, "Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required");
	}
}
