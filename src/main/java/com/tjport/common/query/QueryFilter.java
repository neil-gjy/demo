package com.tjport.common.query;

public class QueryFilter {

	private String groupOp;
	
	private Rule[] rules;

	public QueryFilter() {
		
	}
	
	public String getGroupOp() {
		return groupOp;
	}

	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}

	public Rule[] getRules() {
		return rules;
	}

	public void setRules(Rule[] rules) {
		this.rules = rules;
	}
}

