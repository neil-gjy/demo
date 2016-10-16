package com.tjport.gjy.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tjport.gjy.sys.dao.IDepartmentDao;
import com.tjport.gjy.sys.dao.IOrganizationDao;
import com.tjport.gjy.sys.model.Department;
import com.tjport.gjy.sys.model.Organization;
import com.tjport.gjy.sys.service.IOrganizationService;


@Service("organizationService")
public class OrganizationServiceImpl implements IOrganizationService{

	@Autowired
	private IOrganizationDao organizationDao;
	
	@Autowired
	private IDepartmentDao departmentDao;
	/***
	 * 获得指定用户的所有权限信息
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Department> getDeptsByOrgId(String orgId) {
		// TODO Auto-generated method stub

		if (orgId != null) {
			Organization org = organizationDao.get(orgId);
			List<Department> depts = new ArrayList<Department>();

			if (org != null) {
				depts = org.getDepts();
			}

			return depts;
		} else {

			return null;
		}
	}

	/***
	 * 授予角色权限
	 */
	@Transactional
	@Override
	public void addDepts(String orgId, String[] deptIds) {
		// TODO Auto-generated method stub
		Organization org = organizationDao.load(orgId);

		ArrayList<Department> depts = new ArrayList<Department>();
		
		for (String deptId : deptIds) {
			Department dept = departmentDao.load(deptId);
			depts.add(dept);
		}
		
		org.setDepts(depts);
		
		organizationDao.update(org);
	}
}
