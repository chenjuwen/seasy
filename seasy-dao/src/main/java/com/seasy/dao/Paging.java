package com.seasy.dao;

import java.io.Serializable;

public class Paging implements Serializable {
    private static final long serialVersionUID = 4961896844177550079L;
    public static final int DEFAULT_PAGE_SIZE = 15;
    
    private int firstResult = 1;
    private int pageSize = DEFAULT_PAGE_SIZE;
    
    public Paging(){
    	
    }
    
    public Paging(int firstResult, int pageSize){
    	this.firstResult = firstResult;
    	this.pageSize = pageSize;
    }
    
	public int getFirstResult() {
		return firstResult;
	}
	public int getPageSize() {
		return pageSize;
	}

}

