package com.seasy.demo.aloneapp.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.seasy.interfaces.dto.FilterChainDefinsDTO;
import com.seasy.interfaces.dto.ResultDTO;
import com.seasy.interfaces.service.FilterChainDefinsService;
import com.seasy.web.security.shiro.controller.ShiroAbstractController;
import com.seasy.web.security.shiro.filterchain.SeasyFilterChainService;

@Controller
@RequestMapping("/admin/filterChainDefins")
public class FilterChainDefinsController extends ShiroAbstractController {
	private static Logger log = LoggerFactory.getLogger(FilterChainDefinsController.class);
	
	@Autowired
	private FilterChainDefinsService filterChainDefinsService;
	@Autowired
	private SeasyFilterChainService filterChainService;
	
	@RequestMapping(value="/list")
	public String list(){
		return "admin/filterChainDefins_list.jsp";
	}
	
	@RequestMapping(value="/queryForPage", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String queryForPage(HttpServletRequest request){
		Map<String, String> paramMap = new HashMap<String, String>();
		
		//页号
		int pageNum = Integer.parseInt(getParameterValue(request, "page", "1"));
		//每页记录数
		int pageSize = Integer.parseInt(getParameterValue(request, "rows", String.valueOf(Paging.DEFAULT_PAGE_SIZE)));
		
		PageInfo<FilterChainDefinsDTO> pageInfo = filterChainDefinsService.selectByPage(pageNum, pageSize, paramMap);
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(EasyuiContants.DG_TOTAL, pageInfo.getTotal());
		dataMap.put(EasyuiContants.DG_ROWS, pageInfo.getList());
		
		String jsonStr = JsonUtil.convertToJsonString(dataMap);
		return jsonStr;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String add(){
		return "admin/filterChainDefins_add.jsp";
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public String edit(@RequestParam("id") Long id, ModelMap model){
		FilterChainDefinsDTO dto = filterChainDefinsService.selectById(id);
		model.put("dto", dto);
		
		return "admin/filterChainDefins_edit.jsp";
	}
	
	@RequestMapping(value="/saveOrUpdate", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String saveOrUpdate(FilterChainDefinsDTO dto){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ResultDTO resultDTO = null;
		try{
			if(dto.getId() == null){
				resultDTO = filterChainDefinsService.insert(dto);
			}else{
				resultDTO = filterChainDefinsService.update(dto);
			}
			
			filterChainService.updateFilterChainDefinitions();
			
			if(ResultDTO.CODE_SUCCESS.equals(resultDTO.getCode())){
				dataMap.put(RESULT_CODE, ResultDTO.CODE_SUCCESS);
			}else{
				dataMap.put(RESULT_CODE, ResultDTO.CODE_ERROR);
				dataMap.put(RESULT_MESSAGE, "操作失败！");
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
			boolean b = filterChainDefinsService.delete(id);
			
			filterChainService.updateFilterChainDefinitions();
			
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

	@RequestMapping(value="/loadDefinsToPlatform", produces=REQUEST_PRODUCES_JSON, method=RequestMethod.POST)
	@ResponseBody
	public String loadDefinsToPlatform(){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		try{
			filterChainService.updateFilterChainDefinitions();
			dataMap.put(RESULT_CODE, ResultDTO.CODE_SUCCESS);
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage(), ex);
			
			dataMap.put(RESULT_CODE, ResultDTO.CODE_ERROR);
		}
		
		String jsonStr = JsonUtil.convertToJsonString(dataMap);
		return jsonStr;
	}
	
}
