package com.seasy.web;

import java.util.ArrayList;
import java.util.List;

public class MenuResource {
	private Long id;
	private String no;
	private String name;
	private String url;
	private Long pid;
	private String img;
	private String owner;
	
	List<MenuResource> subs = new ArrayList<MenuResource>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public List<MenuResource> getSubs() {
		return subs;
	}

	public void setSubs(List<MenuResource> subs) {
		this.subs = subs;
	}
	
}
