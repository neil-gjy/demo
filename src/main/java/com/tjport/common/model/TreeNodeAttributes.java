package com.tjport.common.model;

import java.io.Serializable;

public class TreeNodeAttributes implements Serializable{
	
	private String url;
	
	public TreeNodeAttributes(){
		
	}

	public TreeNodeAttributes(String url){
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}