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
import com.seasy.core.SeasyConstants;
import com.seasy.core.util.JsonUtil;
import com.seasy.core.util.PropertiesUtil;
import com.seasy.dao.Paging;
import com.seasy.demo.aloneapp.controller.EasyuiContants;
import com.seasy.interfaces.dto.MessagesDTO;
import com.seasy.interfaces.dto.ResultDTO;
import com.seasy.interfaces.dto.RolesDTO;
import com.seasy.interfaces.dto.UsersDTO;
import com.seasy.interfaces.service.MessagesService;
import com.seasy.interfaces.service.RoleService;
import com.seasy.interfaces.service.UserService;
import com.seasy.web.security.shiro.controller.ShiroAbstractController;

@Controller
@RequestMapping("/admin/message")
public class MessageController extends ShiroAbstractController {
	private static Logger log = LoggerFactory.getLogger(MessageController.class);
	
	@Autowired
	private MessagesService messagesService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/list")
	public String list(){
		return "admin/message_list.jsp";
	}
	
	@RequestMapping(value="/queryForPage", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String queryForPage(HttpServletRequest request){
		String type = StringUtils.trimToEmpty(getParameterValue(request, "type", ""));
		String contents = StringUtils.trimToEmpty(getParameterValue(request, "contents", ""));
		
		Map<String, String> paramMap = new HashMap<String, String>();
		if(StringUtils.isNotEmpty(type)) {
			paramMap.put("type", type);
		}
		if(StringUtils.isNotEmpty(contents)) {
			paramMap.put("contents", contents);
		}
		
		//页号
		int pageNum = Integer.parseInt(getParameterValue(request, "page", "1"));
		//每页记录数
		int pageSize = Integer.parseInt(getParameterValue(request, "rows", String.valueOf(Paging.DEFAULT_PAGE_SIZE)));
		
		PageInfo<MessagesDTO> pageInfo = messagesService.selectByPage(pageNum, pageSize, paramMap);
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(EasyuiContants.DG_TOTAL, pageInfo.getTotal());
		dataMap.put(EasyuiContants.DG_ROWS, pageInfo.getList());
		
		String jsonStr = JsonUtil.convertToJsonString(dataMap);
		return jsonStr;
	}
	
	@RequestMapping(value="/selectMyMessages", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String selectMyMessages(HttpServletRequest request){
		long userId = getCurrentUserId();
		
		//页号
		int pageNum = Integer.parseInt(getParameterValue(request, "page", "1"));
		//每页记录数
		int pageSize = Integer.parseInt(getParameterValue(request, "rows", String.valueOf(Paging.DEFAULT_PAGE_SIZE)));
		
		PageInfo<MessagesDTO> pageInfo = messagesService.selectMessagesByUserid(userId, pageNum, pageSize);
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(EasyuiContants.DG_TOTAL, pageInfo.getTotal());
		dataMap.put(EasyuiContants.DG_ROWS, pageInfo.getList());
		
		String jsonStr = JsonUtil.convertToJsonString(dataMap);
		return jsonStr;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String add(){
		return "admin/message_add.jsp";
	}
	
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public String view(@RequestParam("id") Long id, ModelMap model){
		MessagesDTO dto = messagesService.selectById(id);
		model.put("dto", dto);
		return "admin/message_view.jsp";
	}
	
	@RequestMapping(value="/save", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String save(HttpServletRequest request){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			String type = getParameterValue(request, "type", "");
			String roleIds = getParameterValue(request, "roleIds", "");
			String userIds = getParameterValue(request, "userIds", "");
			String contents = getParameterValue(request, "contents", "");
			System.out.println("type: " + type);
			System.out.println("roleIds: " + roleIds);
			System.out.println("userIds: " + userIds);
			System.out.println("contents: " + contents);
			
			String receiveId = "";
			if("ALL".equalsIgnoreCase(type)){
				receiveId = "";
			}else if("ADMIN".equalsIgnoreCase(type)){
				Long roleId = null;
				String adminRoleNo = PropertiesUtil.getInstance().getProperty(SeasyConstants.ADMIN_ROLENO, "");
				RolesDTO roleDTO = roleService.selectByRoleNo(adminRoleNo);
				if(roleDTO != null){
					roleId = roleDTO.getId();
				}
				
				if(roleId != null){
					receiveId = messagesService.selectUserIdsByRole(roleId.toString());
				}
				
			}else if("ROLES".equalsIgnoreCase(type)){
				if(StringUtils.isNotEmpty(roleIds)){
					receiveId = messagesService.selectUserIdsByRole(roleIds);
				}
				
			}else if("USERS".equalsIgnoreCase(type)){
				receiveId = userIds;
			}
			
			System.out.println("receiveId: " + receiveId);
			
			MessagesDTO message = new MessagesDTO();
			message.setOperator(getUser().getId().toString());
			message.setOperateTime(new Date());
			message.setType(type);
			message.setSendUserid(getCurrentUserId());
			message.setReceiveId(receiveId);
			message.setContents(contents);
			boolean b = messagesService.insert(message);
			
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
	
	@RequestMapping(value="/delete", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam("id") Long id){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		try{
			boolean b = messagesService.delete(id);
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
	
	@RequestMapping(value="/deleteMyMessageById", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String deleteMyMessageById(@RequestParam("id") Long id){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		try{
			long userId = getCurrentUserId();
			
			boolean b = messagesService.deleteUserMessage(id, userId);
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

	@RequestMapping(value="/selectRoles")
	public String selectRoles(){
		return "admin/message_selectRoles.jsp";
	}
	

	
	@RequestMapping(value="/queryForSelectRoles", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String queryForSelectRoles(HttpServletRequest request){
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
	
	@RequestMapping(value="/selectUsers")
	public String selectUsers(){
		return "admin/message_selectUsers.jsp";
	}
	
	@RequestMapping(value="/queryForSelectUsers", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String queryForSelectUsers(HttpServletRequest request){
		String keyword = StringUtils.trimToEmpty(getParameterValue(request, "keyword", ""));
		String enabled = "1";
		
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
	
}
