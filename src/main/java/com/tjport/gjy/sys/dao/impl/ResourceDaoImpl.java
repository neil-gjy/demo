package com.tjport.gjy.sys.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.tjport.common.hibernate.BaseDaoImpl;
import com.tjport.gjy.sys.dao.IResourceDao;
import com.tjport.gjy.sys.model.Resource;



@Repository("resourceDao")
public class ResourceDaoImpl extends BaseDaoImpl <Resource, String> implements IResourceDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Resource> getSubResources(String pid) {
		// TODO Auto-generated method stub
		
		String queryString = "from Resource  where pid = ?";
		List<Object> values = new ArrayList<Object>();
		values.add(pid);
		
		Query query = this.createQuery("seq", "asc", queryString, values.toArray());
		
		return (List<Resource>)query.list();
	}
	
}
