package com.tjport.gjy.sys.dao.impl;

import org.springframework.stereotype.Repository;

import com.tjport.common.hibernate.BaseDaoImpl;
import com.tjport.gjy.sys.dao.IDepartmentDao;
import com.tjport.gjy.sys.model.Department;

@Repository("departmentDao")
public class DepartmentDaoImpl extends BaseDaoImpl <Department, String> implements IDepartmentDao {
	
}
