package com.tjport.gjy.sys.dao;

import java.util.List;

import com.tjport.common.hibernate.IBaseDao;
import com.tjport.gjy.sys.model.Resource;



public interface IResourceDao extends IBaseDao<Resource, String> {
	List<Resource> getSubResources(String pid);
	

}
