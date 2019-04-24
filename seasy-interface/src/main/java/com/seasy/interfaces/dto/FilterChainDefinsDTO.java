package com.seasy.interfaces.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.seasy.interfaces.AbstractBaseDTO;

@XmlRootElement
public class FilterChainDefinsDTO extends AbstractBaseDTO {
	private static final long serialVersionUID = 3483653246622373342L;

	private Long id; 	//ID
	private String chainName; 	//CHAIN_NAME
	private String chainDefinition; 	//CHAIN_DEFINITION
	private Integer chainOrder = 0; 	//CHAIN_ORDER
	
	public FilterChainDefinsDTO(){
		
	}
	
	public FilterChainDefinsDTO(String chainName, String chainDefinition){
		this(chainName, chainDefinition, 0);
	}
	
	public FilterChainDefinsDTO(String chainName, String chainDefinition, Integer chainOrder){
		this.chainName = chainName;
		this.chainDefinition = chainDefinition;
		this.chainOrder = chainOrder;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getChainName() {
		return chainName;
	}
	
	public void setChainName(String chainName) {
		this.chainName = chainName;
	}
	
	public String getChainDefinition() {
		return chainDefinition;
	}
	
	public void setChainDefinition(String chainDefinition) {
		this.chainDefinition = chainDefinition;
	}
	
	public Integer getChainOrder() {
		return chainOrder;
	}
	
	public void setChainOrder(Integer chainOrder) {
		this.chainOrder = chainOrder;
	}
	
}
