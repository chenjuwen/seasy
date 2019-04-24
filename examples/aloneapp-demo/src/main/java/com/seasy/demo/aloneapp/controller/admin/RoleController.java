package com.seasy.demo.aloneapp.controller.admin;

import java.util.Date;
import java.util.HashMap;
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
import com.seasy.interfaces.dto.RolesDTO;
import com.seasy.interfaces.dto.ResultDTO;
import com.seasy.interfaces.service.RoleService;
import com.seasy.web.security.shiro.controller.ShiroAbstractController;

@Controller
@RequestMapping("/admin/role")
public class RoleController extends ShiroAbstractController {
	private static Logger log = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 查询角色
	 */
	@RequestMapping(value="/list")
	public String list(){
		return "admin/role_list.jsp";
	}
	
	@RequestMapping(value="/queryForPage", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String queryForPage(HttpServletRequest request){
		String roleName = StringUtils.trimToEmpty(getParameterValue(request, "roleName", ""));
		
		Map<String, String> paramMap = new HashMap<String, String>();
		if(StringUtils.isNotEmpty(roleName)) {
			paramMap.put("roleName", roleName);
		}
		
		//页号
		int pageNum = Integer.parseInt(getParameterValue(request, "page", "1"));
		//每页记录数
		int pageSize = Integer.parseInt(getParameterValue(request, "rows", String.valueOf(Paging.DEFAULT_PAGE_SIZE)));
		
		PageInfo<RolesDTO> pageInfo = roleService.selectByPage(pageNum, pageSize, paramMap);
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(EasyuiContants.DG_TOTAL, pageInfo.getTotal());
		dataMap.put(EasyuiContants.DG_ROWS, pageInfo.getList());
		
		String jsonStr = JsonUtil.convertToJsonString(dataMap);
		return jsonStr;
	}
	
	/**
	 * 添加角色
	 */
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String add(){
		return "admin/role_add.jsp";
	}
	
	/**
	 * 编辑角色
	 */
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public String edit(@RequestParam("id") Long id, ModelMap model){
		RolesDTO role = roleService.selectById(id);
		model.put("role", role);
		
		return "admin/role_edit.jsp";
	}
	
	/**
	 * 保存角色
	 */
	@RequestMapping(value="/saveOrUpdate", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String saveOrUpdate(RolesDTO role){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ResultDTO dto = null;
		try{
			if(role.getId() == null){
				role.setOperator(getUser().getId().toString());
				role.setOperateTime(new Date());
				dto = roleService.insert(role);
			}else{
				RolesDTO newRole = roleService.selectById(role.getId());
				newRole.setRoleNo(role.getRoleNo());
				newRole.setRoleName(role.getRoleName());
				newRole.setRoleDesc(role.getRoleDesc());
				newRole.setOperator(getUser().getId().toString());
				newRole.setOperateTime(new Date());
				
				dto = roleService.update(newRole);
			}
			
			if(ResultDTO.CODE_SUCCESS.equals(dto.getCode())){
				dataMap.put(RESULT_CODE, ResultDTO.CODE_SUCCESS);
			}else{
				dataMap.put(RESULT_CODE, ResultDTO.CODE_ERROR);
				dataMap.put(RESULT_MESSAGE, dto.getMessage());
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
	 * 删除角色以及相关的数据
	 */
	@RequestMapping(value="/delete", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam("id") Long id){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		try{
			boolean b = roleService.delete(id);
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
