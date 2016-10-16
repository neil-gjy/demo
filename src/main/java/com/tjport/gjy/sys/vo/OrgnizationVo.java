package com.tjport.gjy.sys.vo;

import com.tjport.gjy.sys.model.Organization;

public class OrgnizationVo {
	
	private String id;
	private String name;

	public OrgnizationVo(){
		
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

	public OrgnizationVo(Organization orgnization){
		
		this.id =orgnization.getId();
		this.name = orgnization.getName();
		
	}
	


	
}
