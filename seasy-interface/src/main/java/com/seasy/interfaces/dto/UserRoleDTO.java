package com.seasy.interfaces.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.seasy.interfaces.AbstractBaseDTO;

@XmlRootElement
public class UserRoleDTO extends AbstractBaseDTO{
	private static final long serialVersionUID = 7293534978682505319L;
	
	private Long userId; 	//用户ID
	private Long roleId; 	//角色ID
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
