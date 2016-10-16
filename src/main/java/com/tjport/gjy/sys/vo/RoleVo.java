package com.tjport.gjy.sys.vo;

import com.tjport.gjy.sys.model.Role;

public class RoleVo {
	
	private String id;
	
	private String name;
	
	private String descrption;

	public RoleVo() {
		
	}
	
	public RoleVo(Role role){
		this.id = role.getId();
		this.name = role.getName();
		this.descrption = role.getDescrption();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescrption() {
		return descrption;
	}

	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}
	
	
	
}
