package com.tjport.common.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ComboboxResult implements Serializable{

	//代码
	private String id;
	
	//文字
	private String text;
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getText(){
		return text;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	
}
