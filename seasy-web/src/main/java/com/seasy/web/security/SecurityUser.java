package com.seasy.web.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.seasy.interfaces.dto.ResourcesDTO;

public class SecurityUser implements Serializable {
	private static final long serialVersionUID = 4855568477109664774L;
	
	private Long id;
	private String loginName; 	//登录账号
	private String username; 	//用户姓名
	private String password; 	//密码
	private String salt; 	//密码盐值
	private String phone; 	//手机号
	private String email; 	//邮箱地址
	private Long enabled; 	//状态:0禁用,1启用
	private String captcha;	//验证码
	
	private List<String> allResourceUrlList = new ArrayList<String>();
	private List<String> resourceUrlList = new ArrayList<String>(); //当前用户有哪些资源URL
	private List<ResourcesDTO> resourceDTOList = new ArrayList<ResourcesDTO>(); //当前用户有哪些资源dto对象
	
	private Map<Long,ResourcesDTO> resourceIDMap = new HashMap<Long,ResourcesDTO>();
	private Map<String,ResourcesDTO> resourceUrlMap = new HashMap<String,ResourcesDTO>();
	
	private List<String> roleNoList = new ArrayList<String>(); //当前用户有哪些角色

	public SecurityUser(){
		
	}

	public SecurityUser(Long id, String loginName, String username, String captcha) {
		this.id = id;
		this.loginName = loginName;
		this.username = username;
		this.captcha = captcha;
	}

	@Override
	public String toString() {
		return "ShiroUser [id=" + id + ", loginName=" + loginName
				+ ", username=" + username +", captcha="
				+ captcha + "]";
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, "loginName");
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, "loginName");
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public List<String> getAllResourceUrlList() {
		return allResourceUrlList;
	}

	public void setAllResourceUrlList(List<String> allResourceUrlList) {
		this.allResourceUrlList = allResourceUrlList;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Long getEnabled() {
		return enabled;
	}

	public void setEnabled(Long enabled) {
		this.enabled = enabled;
	}

	public List<String> getResourceUrlList() {
		return resourceUrlList;
	}

	public void setResourceUrlList(List<String> resourceUrlList) {
		this.resourceUrlList = resourceUrlList;
	}

	public List<ResourcesDTO> getResourceDTOList() {
		return resourceDTOList;
	}

	public void setResourceDTOList(List<ResourcesDTO> resourceDTOList) {
		this.resourceDTOList = resourceDTOList;
	}

	public Map<Long, ResourcesDTO> getResourceIDMap() {
		return resourceIDMap;
	}

	public void setResourceIDMap(Map<Long, ResourcesDTO> resourceIDMap) {
		this.resourceIDMap = resourceIDMap;
	}

	public Map<String, ResourcesDTO> getResourceUrlMap() {
		return resourceUrlMap;
	}

	public void setResourceUrlMap(Map<String, ResourcesDTO> resourceUrlMap) {
		this.resourceUrlMap = resourceUrlMap;
	}

	public List<String> getRoleNoList() {
		return roleNoList;
	}

	public void setRoleNoList(List<String> roleNoList) {
		this.roleNoList = roleNoList;
	}
	
}
