package com.tjport.common.query;


public class Rule {
	private String op;
	private String field;
	private String data;
	
	public Rule(){
		
	}
	
	public Rule(String field,String op,String data){
		this.op = op;
		this.field = field;
		this.data = data;
	}
	
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	
	public String getData() {
		
		return this.data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
}
