package com.tjport.common.model;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class TreeGridResult implements Serializable{
	
	//节点唯一标识
	private String id;
	// 节点名称
	private String text;

	private String iconCls;
	
	private String state;
	
	private TreeGridResult[] children;
	
	private TreeNodeAttributes attributes;
	
	public TreeGridResult(){
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public TreeGridResult[] getChildren() {
		return children;
	}

	public void setChildren(TreeGridResult[] children) {
		this.children = children;
	}

	public TreeNodeAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(TreeNodeAttributes attributes) {
		this.attributes = attributes;
	}
}


