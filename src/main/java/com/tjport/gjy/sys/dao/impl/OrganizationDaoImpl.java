package com.tjport.gjy.sys.dao.impl;

import org.springframework.stereotype.Repository;

import com.tjport.common.hibernate.BaseDaoImpl;
import com.tjport.gjy.sys.dao.IOrganizationDao;
import com.tjport.gjy.sys.model.Organization;



@Repository("organizationDao")
public class OrganizationDaoImpl extends BaseDaoImpl <Organization, String> implements IOrganizationDao {

	
	
}
