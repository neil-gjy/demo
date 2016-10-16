package com.tjport.gjy.sys.dao;

import java.util.List;

import com.tjport.common.hibernate.IBaseDao;
import com.tjport.gjy.sys.model.Resource;
import com.tjport.gjy.sys.model.Role;



public interface IRoleDao extends IBaseDao<Role, String> {

	List<Resource> getResourcesByRole(String roleId);
}
