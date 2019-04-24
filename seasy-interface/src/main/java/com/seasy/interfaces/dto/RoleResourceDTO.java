package com.seasy.interfaces.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.seasy.interfaces.AbstractBaseDTO;

@XmlRootElement
public class RoleResourceDTO extends AbstractBaseDTO{
	private static final long serialVersionUID = -6834959057470409659L;
	
	private Long roleId; 	//角色ID
	private Long resId; 	//菜单ID
	
	public Long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	public Long getResId() {
		return resId;
	}
	
	public void setResId(Long resId) {
		this.resId = resId;
	}
	
}
