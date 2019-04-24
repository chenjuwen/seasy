package com.seasy.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.seasy.dao.SeasyDao;

public class AbstractBaseService {
	@Autowired(required=false)
	private SeasyDao mybatisDao;

	public SeasyDao getMybatisDao() {
		return mybatisDao;
	}
	
}
