package com.tjport.gjy.security;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.tjport.gjy.sys.dao.IResourceDao;
import com.tjport.gjy.sys.model.Resource;




/**
 * 授权元数据根据两部分构成：
 * 1、数据库中动态生成，由注入的resourceManager根据资源、权限构造资源-权限的键值对
 * 2、使用spring的注入filterChainDefinitions，在配置文件中定义默认的安全定义，如登录页面，首页等
 * 
 */
public class ShiroDefinitionSection implements FactoryBean<Section> {
	private final static Log logger = LogFactory.getLog(ShiroDefinitionSection.class);
	
	//注入资源管理对象	
	@Autowired
	private IResourceDao resourceDao;
	
	//注入默认的授权定义
	private String filterChainDefinitions;
	
	//格式化数据，符合shiro的格式，如：perms["admin"]
	public static final String PREMISSION_FORMAT = "perms[\"{0}\"]";
	
	@Override
	public Section getObject() throws Exception {
		
		
		Ini ini = new Ini();
		ini.load(filterChainDefinitions);
		Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		
		//由注入的资源管理对象获取所有资源数据，并且Resource的authorities的属性是EAGER的fetch类型
		List<Resource> resources = resourceDao.findAll();
		for(Resource resource : resources) {
			String url = resource.getUrl();
			String authCode = resource.getAuthority();
			
			if(isEmpty(url)||isEmpty(authCode)) {
				continue;
			}
//			if(!url.endsWith("/**")){
//				//url +="/**";
//			}
			//如果资源的值为分号分隔，则循环构造元数据。分号分隔好处是对一批相同权限的资源，不需要逐个定义
			String[] sources = url.split(";");
			for(String s : sources) {
			    putDefinitionSection(section, s.trim(), authCode);
			}
		}
		
        //section.put("/**", "user");
		return section;
	}
	
	private void putDefinitionSection(Section section, String key, String value) {
		logger.info("加载数据库权限：【key=" + key + "\tvalue=" + value + "】");
	    section.put(key, MessageFormat.format(PREMISSION_FORMAT, value));
	}
	
	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}
	
	@Override
	public Class<?> getObjectType() {
		return this.getClass();
	}
	
	@Override
	public boolean isSingleton() {
		return false;
	}

	private boolean isEmpty(String url) {
		if (url == null || "".equals(url.trim())) {
			return true;
		}
		return false;
	}
}