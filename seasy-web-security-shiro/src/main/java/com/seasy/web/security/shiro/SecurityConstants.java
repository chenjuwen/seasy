package com.seasy.web.security.shiro;

public class SecurityConstants {
	public static final String HASH_ALGORITHM = "SHA-1";
	
	public static final String INPUT_USERNAME_PASSWORD = "请输入用户名或密码";
	public static final String INPUT_CAPTCHA = "请输入验证码";
	
	public static final String ERROR_CAPTCHA = "验证码错误，请重新输入";
	public static final String ERROR_USERNAME_PASSWORD = "您输入的账号或密码不正确";
	public static final String ERROR_STATUS = "您输入的登陆账号状态不合法";

	public static final String ERROR_SERVER = "请确认服务器地址及端口是否正常开启";
	public static final String ERROR_SYSTEM = "系统异常";
	public static final String ERROR_TIMEOUT = "页面超时，请刷新页面";

	public static final String SESSION_ATTR_KEY__ERRORTYPE = "errorType";
	public static final String SESSION_ATTR_KEY__ERRORMSG = "errorMessage";

	public static final String SESSION_ATTR_KEY__MENULIST = "menulist";
	public static final String SESSION_ATTR_KEY__FIRSTMENUID = "firstMenuId";
	public static final String SESSION_ATTR_KEY__SECONDMENUID = "secondMenuId";
	public static final String SESSION_ATTR_KEY__FIRSTMENUNAME = "firstMenuName";
	public static final String SESSION_ATTR_KEY__SECONDMENUNAME = "secondMenuName";

	public static final String SESSION_ATTR_KEY__RESLIST = "reslist";

}
