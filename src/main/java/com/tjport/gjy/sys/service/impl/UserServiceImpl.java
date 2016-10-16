package com.tjport.gjy.sys.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tjport.common.utils.DesUtils;
import com.tjport.common.utils.StringUtils;
import com.tjport.gjy.sys.dao.IDepartmentDao;
import com.tjport.gjy.sys.dao.IOrganizationDao;
import com.tjport.gjy.sys.dao.IRoleDao;
import com.tjport.gjy.sys.dao.IUserDao;
import com.tjport.gjy.sys.model.Role;
import com.tjport.gjy.sys.model.User;
import com.tjport.gjy.sys.service.IUserService;
import com.tjport.gjy.sys.vo.UserVo;



/**
 * 用户
 * @author by Neil
 *
 */

@Service("userService")
public class UserServiceImpl implements IUserService{

	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IOrganizationDao organizationDao;
	
	@Autowired
	private IRoleDao roleDao;
	
	@Autowired
	private IDepartmentDao departmentDao;
	
	@Transactional
	public void save(UserVo vo) throws Exception{
	
		User entity = new User();
		
		entity.setId(vo.getId());
		entity.setName(vo.getName());
		entity.setUsername(vo.getUsername());
		
		entity.setPassword(DesUtils.encrypt(vo.getPassword()));
		entity.setCreateTime(vo.getCreateTime());
		
		
		if(vo.getOrgId()!=null) entity.setOrg(organizationDao.get(vo.getOrgId()));
		
		if (StringUtils.isNotBlank(vo.getDeptId())) {
			entity.setDept(departmentDao.get(vo.getDeptId()));
		}

		if (StringUtils.isNotBlank(vo.getRoleId())) {
			
			ArrayList<Role> roles = new ArrayList<Role>();
			
			Role role = roleDao.load(vo.getRoleId());
			
			roles.add(role);
			
			entity.setRoles(roles);
		}
		
		userDao.save(entity);
		
	}
	
	@Transactional
	public void update(UserVo vo) throws Exception{
		User entity = userDao.load(vo.getId());
		
		entity.setName(vo.getName());
		entity.setUsername(vo.getUsername());
		
		entity.setUpdateTime(vo.getUpdateTime());
		
		if(vo.getOrgId()!=null) entity.setOrg(organizationDao.load(vo.getOrgId()));

		if (StringUtils.isNotBlank(vo.getDeptId())) {
			entity.setDept(departmentDao.load(vo.getDeptId()));
		}

		if (StringUtils.isNotBlank(vo.getRoleId())) {
			
			ArrayList<Role> roles = new ArrayList<Role>();
			
			Role role = roleDao.load(vo.getRoleId());
			
			roles.add(role);
			
			entity.setRoles(roles);
		}
		
		userDao.update(entity);
		
	}
}
