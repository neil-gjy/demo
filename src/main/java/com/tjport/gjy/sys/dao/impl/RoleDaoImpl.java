package com.tjport.gjy.sys.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.tjport.common.hibernate.BaseDaoImpl;
import com.tjport.gjy.sys.dao.IRoleDao;
import com.tjport.gjy.sys.model.Resource;
import com.tjport.gjy.sys.model.Role;



@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl <Role, String> implements IRoleDao {

	public List<Resource> getResourcesByRole(String roleId) {
		
		String queryString = "select o.resources from Role o where o.id = ?";
		Query query = this.createQuery(queryString, roleId);
		
		return query.list();
	}
	
}