package com.seasy.demo.aloneapp.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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

import com.seasy.core.util.JsonUtil;
import com.seasy.demo.aloneapp.ui.EasyuiDataNode;
import com.seasy.demo.aloneapp.util.EasyuiUtil;
import com.seasy.interfaces.dto.ResourcesDTO;
import com.seasy.interfaces.dto.ResultDTO;
import com.seasy.interfaces.dto.RoleResourceDTO;
import com.seasy.interfaces.service.ResourceService;
import com.seasy.web.security.shiro.controller.ShiroAbstractController;

@Controller
@RequestMapping("/admin/resource")
public class ResourceController extends ShiroAbstractController {
	private static Logger log = LoggerFactory.getLogger(ResourceController.class);
	
	@Autowired
	private ResourceService resourceService;

	@RequestMapping(value="/list")
	public String list(){
		return "admin/resource_list.jsp";
	}
	
	@RequestMapping(value="/loadChildren", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String loadChildren(HttpServletRequest request){
		Long parentId = new Long(1);
		String parent_id = getParameterValue(request, "parent_id", null);
		if(StringUtils.isNotEmpty(parent_id)){
			parentId = new Long(parent_id);
		}
		
		String jsonStr = "[]";
		List<ResourcesDTO> resList = resourceService.cascadeSelectByParentId(parentId); //resourceService.selectByParentId(parentId);
		if(CollectionUtils.isNotEmpty(resList)){
			jsonStr = JsonUtil.convertToJsonArrayString(resList);
		}
		//System.out.println(jsonStr);
		return jsonStr;
	}
	
	/**
	 * 资源树：角色关联资源时使用
	 */
	@RequestMapping(value="/roleResourceTree")
	public String roleResourceTree(@RequestParam("roleId") String roleId, ModelMap model){
		model.put("roleId", roleId);
		return "admin/role_resource_tree.jsp";
	}
	
	@RequestMapping(value="/loadRoleResourceTree", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String loadRoleResourceTree(HttpServletRequest request, @RequestParam("roleId") String roleId){
		String jsonStr = "[]";
		List<ResourcesDTO> resList = resourceService.cascadeSelectByParentId(null);
		if(CollectionUtils.isNotEmpty(resList)){
			List<ResourcesDTO> roleResourceList = resourceService.selectByRoleId(Long.parseLong(roleId));
			
			List<EasyuiDataNode> nodeList = EasyuiUtil.convert(resList, roleResourceList);
			jsonStr = JsonUtil.convertToJsonArrayString(nodeList);
		}
		//System.out.println(jsonStr);
		
		return jsonStr;
	}
	
	@RequestMapping(value="/saveTree", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String saveRoleResourceTree(@RequestParam("roleId") String roleId, @RequestParam("resIds") String resIds){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			List<RoleResourceDTO> dtoList = new ArrayList<RoleResourceDTO>();

			resourceService.deleteRelationRole(Long.parseLong(roleId));
			
			if(StringUtils.isNotEmpty(resIds)){
				for(String resId : resIds.split(",")){
					RoleResourceDTO dto = new RoleResourceDTO();
					dto.setRoleId(Long.parseLong(roleId));
					dto.setResId(Long.parseLong(StringUtils.trimToEmpty(resId)));
					dtoList.add(dto);
				}
				resourceService.insertRoleResource(dtoList);
			}
			
			dataMap.put(RESULT_CODE, ResultDTO.CODE_SUCCESS);
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
	 * 添加资源
	 */
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String add(@RequestParam("parentId") Long parentId, ModelMap model){
		model.put("parentId", parentId);
		return "admin/resource_add.jsp";
	}
	
	/**
	 * 编辑资源
	 */
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public String edit(@RequestParam("id") Long id, ModelMap model){
		ResourcesDTO dto = resourceService.selectOne(id);
		model.put("dto", dto);
		return "admin/resource_edit.jsp";
	}
	
	/**
	 * 保存资源
	 */
	@RequestMapping(value="/saveOrUpdate", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String saveOrUpdate(ResourcesDTO dto){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ResultDTO resultDTO = null;
		try{
			if(dto.getId() == null){
				dto.setOperator(getUser().getId().toString());
				dto.setOperateTime(new Date());
				resultDTO = resourceService.insert(dto);
			}else{
				ResourcesDTO newRes = resourceService.selectOne(dto.getId());
				newRes.setResNo(dto.getResNo());
				newRes.setResName(dto.getResName());
				newRes.setResUrl(dto.getResUrl());
				newRes.setRemarks(dto.getRemarks());
				newRes.setOperator(getUser().getId().toString());
				newRes.setOperateTime(new Date());
				
				resultDTO = resourceService.update(newRes);
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
	 * 删除资源
	 */
	@RequestMapping(value="/delete", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam("id") Long id){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		try{
			List<ResourcesDTO> resList = resourceService.selectByParentId(id);
			if(CollectionUtils.isNotEmpty(resList)){
				dataMap.put(RESULT_CODE, ResultDTO.CODE_ERROR);
				dataMap.put(RESULT_MESSAGE, "该资源下还有子资源，不能删除！");
			}else{
				boolean b = resourceService.delete(id);
				if(b){
					dataMap.put(RESULT_CODE, ResultDTO.CODE_SUCCESS);
				}else{
					dataMap.put(RESULT_CODE, ResultDTO.CODE_ERROR);
				}
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
