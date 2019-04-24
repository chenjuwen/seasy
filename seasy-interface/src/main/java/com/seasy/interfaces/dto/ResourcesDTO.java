package com.seasy.interfaces.dto;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.seasy.interfaces.AbstractBaseDTO;

@XmlRootElement
public class ResourcesDTO extends AbstractBaseDTO {
	private static final long serialVersionUID = 2143675064423226370L;

	private Long id;
	private String resNo; 	//菜单编号
	private String resName; 	//菜单名称
	private String resUrl; 	//菜单URL
	private Long parentId; 	//上级菜单ID
	private String resImg; 	//菜单图标
	private String owner; 	//所属子系统
	private String remarks; 	//备注
	private String operator;
	private Date operateTime;
	
	private List<ResourcesDTO> children;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getResNo() {
		return resNo;
	}
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getResUrl() {
		return resUrl;
	}
	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getResImg() {
		return resImg;
	}
	public void setResImg(String resImg) {
		this.resImg = resImg;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public List<ResourcesDTO> getChildren() {
		return children;
	}
	public void setChildren(List<ResourcesDTO> children) {
		this.children = children;
	}
	
}
