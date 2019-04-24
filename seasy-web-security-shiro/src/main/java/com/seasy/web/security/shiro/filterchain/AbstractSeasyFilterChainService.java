package com.seasy.web.security.shiro.filterchain;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.seasy.web.security.shiro.filter.SeasyAccessControlFilter;

public abstract class AbstractSeasyFilterChainService implements SeasyFilterChainService {
	private static Logger logger = LoggerFactory.getLogger(AbstractSeasyFilterChainService.class);
	
	@Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean; 
	
	private String definitions = "";
	private Map<String, String> afterDefinitions;
	
	@PostConstruct
	public void initFilterChainDefinitions() {
		shiroFilterFactoryBean.setFilterChainDefinitionMap(obtainPermission());
		logger.info("Initialize shiro permission data successfully");
	}
	
	private Section obtainPermission() {  
        Ini ini = new Ini();  
        ini.load(definitions);
        Section section = ini.getSection(IniFilterChainResolverFactory.URLS); 
        if (CollectionUtils.isEmpty(section)) {
            section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        }
        
        Map<String, String> definitionsMap = initOtherFilterChainDefinitions();  
        if (!CollectionUtils.isEmpty(definitionsMap)) {
            section.putAll(definitionsMap);
        }
        
        if(afterDefinitions != null && afterDefinitions.size() > 0){
        	section.putAll(afterDefinitions);
        }
        
        return section;  
    }

	@Override
	public void updateFilterChainDefinitions() {
		synchronized (shiroFilterFactoryBean) {
			//clear
			AbstractShiroFilter shiroFilter = null;
            try {  
                shiroFilter = (AbstractShiroFilter)shiroFilterFactoryBean.getObject();  
            } catch (Exception ex) {  
            	ex.printStackTrace();
            	logger.error(ex.getMessage(), ex);
            }
   
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter  
                    .getFilterChainResolver();  
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver
            		.getFilterChainManager();  
  
            manager.getFilterChains().clear();  
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();  
  
            //reload
            initFilterChainDefinitions();

            //clear appliedPaths
            for(Entry<String, Filter> filterEntry : manager.getFilters().entrySet()) { 
            	if(filterEntry.getValue() instanceof SeasyAccessControlFilter){
            		SeasyAccessControlFilter filter = (SeasyAccessControlFilter)filterEntry.getValue();
            		filter.getAppliedPaths().clear();
            	}
            }
            
            //create chain
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {  
                String chainName = StringUtils.trimToEmpty(entry.getKey());  
                String chainDefinition = StringUtils.trimToEmpty(entry.getValue());
                
                if(StringUtils.isNotEmpty(chainName) && StringUtils.isNotEmpty(chainDefinition)){
                	manager.createChain(chainName, chainDefinition);
                }
            }
            filterChainResolver.setFilterChainManager(manager);
            
            logger.info("Update shiro permission data successfully"); 
		}
	}

	@Override
	public abstract Map<String, String> initOtherFilterChainDefinitions();

	public String getDefinitions() {
		return definitions;
	}

	public void setDefinitions(String definitions) {
		this.definitions = definitions;
	}

	public Map<String, String> getAfterDefinitions() {
		return afterDefinitions;
	}

	public void setAfterDefinitions(Map<String, String> afterDefinitions) {
		this.afterDefinitions = afterDefinitions;
	}
	
}
