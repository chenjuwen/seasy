package com.seasy.demo.aloneapp.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.seasy.core.util.JsonUtil;
import com.seasy.dao.Paging;
import com.seasy.demo.aloneapp.controller.EasyuiContants;
import com.seasy.interfaces.dto.ResultDTO;
import com.seasy.interfaces.dto.RolesDTO;
import com.seasy.interfaces.dto.UsersDTO;
import com.seasy.interfaces.service.RoleService;
import com.seasy.interfaces.service.UserService;
import com.seasy.web.security.shiro.controller.ShiroAbstractController;

@Controller
@RequestMapping("/admin/user")
public class UserController extends ShiroAbstractController {
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	/**
	 * 查询用户
	 */
	@RequestMapping(value="/list")
	public String list(){
		return "admin/user_list.jsp";
	}
	
	@RequestMapping(value="/queryForPage", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String queryForPage(HttpServletRequest request){
		String keyword = StringUtils.trimToEmpty(getParameterValue(request, "keyword", ""));
		String enabled = StringUtils.trimToEmpty(getParameterValue(request, "enabled", ""));
		
		Map<String, String> paramMap = new HashMap<String, String>();
		if(StringUtils.isNotEmpty(keyword)) {
			paramMap.put("keyword", keyword);
		}
		if(StringUtils.isNotEmpty(enabled)){
			paramMap.put("enabled", enabled);
		}
		
		//页号
		int pageNum = Integer.parseInt(getParameterValue(request, "page", "1"));
		//每页记录数
		int pageSize = Integer.parseInt(getParameterValue(request, "rows", String.valueOf(Paging.DEFAULT_PAGE_SIZE)));
		
		PageInfo<UsersDTO> pageInfo = userService.selectByPage(pageNum, pageSize, paramMap);
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(EasyuiContants.DG_TOTAL, pageInfo.getTotal());
		dataMap.put(EasyuiContants.DG_ROWS, pageInfo.getList());
		
		String jsonStr = JsonUtil.convertToJsonString(dataMap);
		return jsonStr;
	}
	
	/**
	 * 查看用户
	 */
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public String view(@RequestParam("id") Long id, ModelMap model){
		UsersDTO dto = userService.selectByPrimaryKey(id);
		List<RolesDTO> roleList = userService.getAllRoleByUserId(id);
		
		model.put("dto", dto);
		model.put("roleList", roleList);
		
		return "admin/user_view.jsp";
	}
	
	/**
	 * 用户个人信息
	 */
	@RequestMapping(value="/personalInfo", method=RequestMethod.GET)
	public String personalInfo(ModelMap model){
		Long userId = getCurrentUserId();
		UsersDTO dto = userService.selectByPrimaryKey(userId);
		model.put("dto", dto);
		return "admin/user_personalInfo.jsp";
	}

	/**
	 * 修改密码
	 */
	@RequestMapping(value="/modifyPassword", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String modifyPassword(HttpServletRequest request, ModelMap model){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			String userId = getParameterValue(request, "userId", "");
			String oldPassword = getParameterValue(request, "oldPassword", "");
			String newPassword = getParameterValue(request, "newPassword", "");
			String confirmPassword = getParameterValue(request, "confirmPassword", "");
			
			if(newPassword.equalsIgnoreCase(confirmPassword)){
				ResultDTO resultDTO = userService.updatePassword(new Long(userId), oldPassword, newPassword);
				if(ResultDTO.CODE_SUCCESS.equals(resultDTO.getCode())){
					dataMap.put(RESULT_CODE, ResultDTO.CODE_SUCCESS);
				}else{
					dataMap.put(RESULT_CODE, ResultDTO.CODE_ERROR);
				}
				
				if(StringUtils.isNotEmpty(resultDTO.getMessage())){
					dataMap.put(RESULT_MESSAGE, resultDTO.getMessage());
				}
			}else{
				dataMap.put(RESULT_CODE, ResultDTO.CODE_ERROR);
				dataMap.put(RESULT_MESSAGE, "两次输入的新密码不一致！");
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage(), ex);
			
			dataMap.put(RESULT_CODE, ResultDTO.CODE_ERROR);
			dataMap.put(RESULT_MESSAGE, "操作失败！");
		}
		
		String jsonStr = JsonUtil.convertToJsonString(dataMap);
		return jsonStr;
	}
	
	/**
	 * 添加用户
	 */
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String add(ModelMap model){
		return "admin/user_add.jsp";
	}
	
	/**
	 * 编辑用户
	 */
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public String edit(@RequestParam("id") Long id, ModelMap model){
		UsersDTO dto = userService.selectByPrimaryKey(id);
		model.put("dto", dto);
		return "admin/user_edit.jsp";
	}

	/**
	 * 用户角色分配
	 */
	@RequestMapping(value="/relateUserRole", method=RequestMethod.GET)
	public String relateUserRole(@RequestParam("userId") Long userId, ModelMap model){
		List<RolesDTO> userRoles = userService.getAllRoleByUserId(userId);
		List<RolesDTO> userAvailableRoles = roleService.getUserAvailableRoles(userId);
		
		model.put("userId", userId);
		model.put("userRoles", userRoles);
		model.put("userAvailableRoles", userAvailableRoles);
		
		return "admin/user_relateRole.jsp";
	}

	/**
	 * 添加用户角色
	 */
	@RequestMapping(value="/insertUserRoles", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String insertUserRoles(@RequestParam("userId") Long userId, 
			@RequestParam("roleIds") String roleIds, ModelMap model){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			boolean b = roleService.insertUserRoles(userId, roleIds);
			
			if(b){
				dataMap.put(RESULT_CODE, ResultDTO.CODE_SUCCESS);
			}else{
				dataMap.put(RESULT_CODE, ResultDTO.CODE_ERROR);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage(), ex);
			
			dataMap.put(RESULT_CODE, ResultDTO.CODE_ERROR);
			dataMap.put(RESULT_MESSAGE, "操作失败！");
		}
		
		String jsonStr = JsonUtil.convertToJsonString(dataMap);
		return jsonStr;
	}

	/**
	 * 删除用户角色
	 */
	@RequestMapping(value="/deleteUserRoles", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String deleteUserRoles(@RequestParam("userId") Long userId, 
			@RequestParam("roleIds") String roleIds, ModelMap model){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			boolean b = roleService.deleteUserRoles(userId, roleIds);
			
			if(b){
				dataMap.put(RESULT_CODE, ResultDTO.CODE_SUCCESS);
			}else{
				dataMap.put(RESULT_CODE, ResultDTO.CODE_ERROR);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage(), ex);
			
			dataMap.put(RESULT_CODE, ResultDTO.CODE_ERROR);
			dataMap.put(RESULT_MESSAGE, "操作失败！");
		}
		
		String jsonStr = JsonUtil.convertToJsonString(dataMap);
		return jsonStr;
	}

	/**
	 * 重置密码
	 */
	@RequestMapping(value="/resetPassword", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String resetPassword(@RequestParam("id") Long id, ModelMap model){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			boolean b = userService.resetPassword(id);
			
			if(b){
				dataMap.put(RESULT_CODE, ResultDTO.CODE_SUCCESS);
			}else{
				dataMap.put(RESULT_CODE, ResultDTO.CODE_ERROR);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage(), ex);
			
			dataMap.put(RESULT_CODE, ResultDTO.CODE_ERROR);
			dataMap.put(RESULT_MESSAGE, "操作失败！");
		}
		
		String jsonStr = JsonUtil.convertToJsonString(dataMap);
		return jsonStr;
	}
	
	/**
	 * 保存用户
	 */
	@RequestMapping(value="/saveOrUpdate", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String saveOrUpdate(UsersDTO dto){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ResultDTO resultDTO = null;
		try{
			if(dto.getId() == null){
				dto.setEnabled(1L);
				dto.setOperator(getUser().getId().toString());
				dto.setOperateTime(new Date());
				resultDTO = userService.insert(dto);
			}else{
				UsersDTO newUser = userService.selectByPrimaryKey(dto.getId());
				newUser.setUsername(dto.getUsername());
				newUser.setPhone(dto.getPhone());
				newUser.setEmail(dto.getEmail());
				newUser.setOperator(getUser().getId().toString());
				newUser.setOperateTime(new Date());
				
				resultDTO = userService.update(newUser);
			}
			
			if(ResultDTO.CODE_SUCCESS.equals(resultDTO.getCode())){
				dataMap.put(RESULT_CODE, ResultDTO.CODE_SUCCESS);
			}else{
				dataMap.put(RESULT_CODE, ResultDTO.CODE_ERROR);
				dataMap.put(RESULT_MESSAGE, resultDTO.getMessage());
			}
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage(), ex);
			
			dataMap.put(RESULT_CODE, ResultDTO.CODE_ERROR);
			dataMap.put(RESULT_MESSAGE, "操作失败！");
		}
		
		String jsonStr = JsonUtil.convertToJsonString(dataMap);
		return jsonStr;
	}
	
	/**
	 * 更新用户状态
	 */
	@RequestMapping(value="/updateState", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String updateState(@RequestParam("id") Long id, @RequestParam("enabled") Long enabled){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		try{
			UsersDTO dto = new UsersDTO();
			dto.setId(id);
			dto.setEnabled(enabled);
			
			boolean b = userService.updateState(dto);
			if(b){
				dataMap.put(RESULT_CODE, ResultDTO.CODE_SUCCESS);
			}else{
				dataMap.put(RESULT_CODE, ResultDTO.CODE_ERROR);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage(), ex);
			dataMap.put(RESULT_CODE, ResultDTO.CODE_ERROR);
		}
		
		String jsonStr = JsonUtil.convertToJsonString(dataMap);
		return jsonStr;
	}
	
}
