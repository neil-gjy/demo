package com.tjport.gjy.sys.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tjport.gjy.sys.dao.IResourceDao;
import com.tjport.gjy.sys.dao.IRoleDao;
import com.tjport.gjy.sys.dao.IUserDao;
import com.tjport.gjy.sys.model.Resource;
import com.tjport.gjy.sys.model.Role;
import com.tjport.gjy.sys.model.User;
import com.tjport.gjy.sys.service.IRoleService;



@Service("roleService")
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleDao roleDao;

	@Autowired
	private IUserDao userDao;

	@Autowired
	private IResourceDao resourceDao;

	/***
	 * 获得指定用户的所有角色名称
	 */
	@Transactional(readOnly = true)
	@Override
	public List<String> getRolesName(String userId) {
		// TODO Auto-generated method stub

		ArrayList<String> result = new ArrayList<String>();

		if (userId != null) {
			User user = userDao.get(userId);

			if (user != null) {
				List<Role> roles = user.getRoles();

				for (Role role : roles) {
					result.add(role.getName());
				}
			}
		} else {
			result.add("superAdmin");
		}

		return result;
	}

	/***
	 * 获得指定用户的所有权限信息
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Resource> getResourcesByUserId(String userId) {
		// TODO Auto-generated method stub

		if (userId != null) {
			User user = userDao.get(userId);
			HashSet result = new HashSet();

			if (user != null) {
				List<Role> roles = user.getRoles();

				for (Role role : roles) {
					List<Resource> resources = role.getResources();
					for (Resource resource : resources) {
						result.add(resource);
					}
				}
			}

			return new ArrayList<Resource>(result);
		} else {

			return this.resourceDao.findAll("seq", "asc");
		}
	}

	/***
	 * 授予角色权限
	 */
	@Transactional
	@Override
	public void grantResources(String roleId, String[] resourceIds) {
		// TODO Auto-generated method stub
		Role role = roleDao.load(roleId);

		ArrayList<Resource> resources = new ArrayList<Resource>();
		
		for (String resourceId : resourceIds) {
			Resource resource = resourceDao.load(resourceId);
			resources.add(resource);
		}
		
		role.setResources(resources);
		
		roleDao.update(role);
	}

}
