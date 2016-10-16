package com.tjport.gjy.sys.vo;

import java.util.List;

import com.tjport.common.model.BaseVo;
import com.tjport.common.utils.DesUtils;
import com.tjport.gjy.sys.model.Department;
import com.tjport.gjy.sys.model.Role;
import com.tjport.gjy.sys.model.User;



public class UserVo extends BaseVo{
	
	private String username;
	
	private String name;
	
	private String password;

	private String deptId;
	
	private String deptName;
	
	private String roleId;
	
	private String roleName;
	
	public UserVo() {
		
	}
	
	public UserVo(User user) throws Exception {
		
		this.setId(user.getId());
		this.username = user.getUsername();
		this.name = user.getName();
		this.password = DesUtils.decrypt(user.getPassword());
		
		this.setCreateTime(user.getCreateTime());
		this.setUpdateTime(user.getUpdateTime());
		
		this.setOrgId(user.getOrg() != null ? user.getOrg().getId() : "");
		this.setOrgName(user.getOrg() != null ? user.getOrg().getName() : "");
		
		Department dept = user.getDept();
		if (dept != null) {
			this.deptId = dept.getId();
			this.deptName = dept.getName();
		}
		
		List<Role> roles = user.getRoles();
		if (roles.size() > 0) {
			Role role = roles.get(0);
			this.roleId = role.getId();
			this.roleName = role.getName();
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	
}
