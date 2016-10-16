package com.tjport.gjy.sys.service;

import java.util.List;

import com.tjport.gjy.sys.model.Resource;

public interface IRoleService {
	
	List<Resource> getResourcesByUserId(String userId);
	
	List<String> getRolesName(String userId);

	void grantResources(String roleId, String[] resourcesId);
}
