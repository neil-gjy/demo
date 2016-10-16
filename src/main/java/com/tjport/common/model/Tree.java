package com.tjport.common.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Tree implements Serializable{
	
	private String id;
	// 节点名称
	private String text;
	
	private Tree[] nodes;
	
	public Tree(){
		
	}

	public Tree(String id, String text,Tree[] nodes){
		this.id = id;
		this.text = text;
		this.nodes = nodes;
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

	public Tree[] getNodes() {
		return nodes;
	}

	public void setNodes(Tree[] nodes) {
		this.nodes = nodes;
	}
	
}


