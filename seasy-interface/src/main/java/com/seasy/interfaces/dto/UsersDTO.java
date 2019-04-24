package com.seasy.interfaces.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.seasy.interfaces.AbstractBaseDTO;

@XmlRootElement
public class UsersDTO extends AbstractBaseDTO {
	private static final long serialVersionUID = 845288956479462514L;

	private Long id;
	private String loginName; 	//登录账号
	private String username; 	//用户姓名
	private String password; 	//密码
	private String salt; 	//密码盐值
	private String phone; 	//手机号
	private String email; 	//邮箱地址
	private Long loginTimes; 	//登录次数
	private Date lastLoginTime; 	//最后一次登录时间
	private Long enabled; 	//状态:0禁用,1启用
	private String operator;
	private Date operateTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getLoginTimes() {
		return loginTimes;
	}
	public void setLoginTimes(Long loginTimes) {
		this.loginTimes = loginTimes;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Long getEnabled() {
		return enabled;
	}
	public void setEnabled(Long enabled) {
		this.enabled = enabled;
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
