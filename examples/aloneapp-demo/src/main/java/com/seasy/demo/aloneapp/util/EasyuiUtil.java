package com.seasy.demo.aloneapp.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.seasy.demo.aloneapp.ui.EasyuiDataNode;
import com.seasy.interfaces.dto.ResourcesDTO;

public class EasyuiUtil {
	public static List<EasyuiDataNode> convert(List<ResourcesDTO> list, List<ResourcesDTO> roleResourceList){
		//id set
		Set<String> idSet = new HashSet<String>();
		if(CollectionUtils.isNotEmpty(roleResourceList)){
			for(ResourcesDTO dto: roleResourceList){
				idSet.add(dto.getId().toString());
			}
		}
		
		List<EasyuiDataNode> newList = new ArrayList<EasyuiDataNode>();
		
		if(CollectionUtils.isNotEmpty(list)){
			for(ResourcesDTO dto : list){
				EasyuiDataNode dataNode = new EasyuiDataNode();
				dataNode.setId(dto.getId());
				dataNode.setText(dto.getResName());
				dataNode.setState("open");
				dataNode.setChecked(idSet.contains(dto.getId().toString()));
				newList.add(dataNode);
				
				convertChildren(dataNode, dto, idSet);
			}
		}
		
		return newList;
	}
	
	private static void convertChildren(EasyuiDataNode parentNode, ResourcesDTO dto, Set<String> idSet){
		List<EasyuiDataNode> childList = new ArrayList<EasyuiDataNode>();
		
		List<ResourcesDTO> subList = dto.getChildren();
		if(CollectionUtils.isNotEmpty(subList)){
			for(ResourcesDTO subDto: subList){
				EasyuiDataNode childNode = new EasyuiDataNode();
				childNode.setId(subDto.getId());
				childNode.setText(subDto.getResName());
				childNode.setState("open");
				childNode.setChecked(idSet.contains(subDto.getId().toString()));
				childList.add(childNode);
				
				convertChildren(childNode, subDto, idSet);
			}
		}
		
		parentNode.setChildren(childList);
	}
	
}
