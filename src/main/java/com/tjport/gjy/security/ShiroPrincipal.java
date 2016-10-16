package com.tjport.gjy.security;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.tjport.gjy.sys.model.Resource;
import com.tjport.gjy.sys.model.User;



public class ShiroPrincipal implements Serializable {
	
	private static final long serialVersionUID = 1428196040744555722L;
	
	// 用户对象
	private User user;
	
	// 用户权限列表
	private List<Resource> resources = new ArrayList<Resource>();
	
	// 用户角色列表
	private List<String> roles = new ArrayList<String>();
	
	// 授权信息列表
	private List<String> authorities = null;
	
	// 是否已授权。如果已授权，则不需要再从数据库中获取权限信息，减少数据库访问
	// 这里会导致修改权限时，需要重新登录方可有效
	private boolean isAuthorized = false;

	/**
	 * 构造函数，参数为User对象 根据User对象属性，赋值给Principal相应的属性上
	 * 
	 * @param user
	 */
	public ShiroPrincipal(User user) {
		this.user = user;
	}
	
	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}


	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public boolean isAuthorized() {
		return isAuthorized;
	}

	public void setAuthorized(boolean isAuthorized) {
		this.isAuthorized = isAuthorized;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User User) {
		this.user = user;
	}

	public List<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}

	public String getUsername() {
		return this.user.getUsername();
	}

	public String getId() {
		return this.user.getId();
	}

	
	/**
	 * 获取所有授权的权限代码信息
	 * @return
	 */
	public List<String> getStringPermissions() {
		
		Set<String> result = new HashSet<String>();
		
		if(authorities==null){		
			if (this.resources != null){
				for(Resource resource : resources){
					
					String authority = resource.getAuthority();
					
					if (StringUtils.isNoneBlank(authority)) {
						result.add(authority);
					}
					
				}
			}
			
		}
		
		return  new ArrayList<String>(result);
	}
	
	
	/**
	 * <shiro:principal/>标签显示中文名称
	 */
	@Override
	public String toString() {
		return this.user.getUsername();
	}
	
	
	
	
}
