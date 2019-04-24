package com.seasy.dao.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class BooleanTypeHandler implements TypeHandler<Object> {
	@Override
	public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
		Boolean b = cs.getBoolean(columnIndex);  
        return b == true ? "1" : "0"; 
	}

	@Override
	public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
		Boolean b = rs.getBoolean(columnIndex);  
        return b == true ? "1" : "0"; 
	}

	@Override
	public Object getResult(ResultSet rs, String columnName) throws SQLException {
		String columnValue = rs.getString(columnName);  
        Boolean result = Boolean.FALSE;  
        if (columnValue.equalsIgnoreCase("1")){  
            result = Boolean.TRUE;  
        }  
        return result;
	}

	@Override
	public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
		Boolean b = (Boolean) parameter;
        String value = (b == true ? "1" : "0");  
        ps.setString(i, value);  
	}

}
