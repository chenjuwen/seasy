package com.seasy.interfaces.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.seasy.interfaces.AbstractBaseDTO;

@XmlRootElement
public class RolesDTO extends AbstractBaseDTO {
	private static final long serialVersionUID = 3881618593057766194L;

	private Long id;
	private String roleNo; 	//角色编号
	private String roleName; 	//角色名称
	private String roleDesc; 	//角色描述
	private String operator;
	private Date operateTime;
	
	public RolesDTO(){
		
	}
	
	public RolesDTO(String roleNo, String roleName, String roleDesc){
		this.roleNo = roleNo;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
		this.operateTime = new Date();
	}
	
	public RolesDTO(String roleNo, String roleName, String roleDesc, String operator, Date operateTime){
		this.roleNo = roleNo;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
		this.operator = operator;
		this.operateTime = operateTime;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoleNo() {
		return roleNo;
	}
	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
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
	
}
