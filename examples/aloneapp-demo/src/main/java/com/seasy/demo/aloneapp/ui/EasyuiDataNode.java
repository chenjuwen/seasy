package com.seasy.demo.aloneapp.ui;

import java.util.List;

public class EasyuiDataNode {
	private Long id;
	private String text;
	private String state;
	private boolean checked = false;
	private List<EasyuiDataNode> children;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public List<EasyuiDataNode> getChildren() {
		return children;
	}
	public void setChildren(List<EasyuiDataNode> children) {
		this.children = children;
	}
	
}
