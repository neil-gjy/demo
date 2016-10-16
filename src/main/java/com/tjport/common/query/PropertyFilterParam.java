package com.tjport.common.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

/**
 * 
 * @author 
 * @date   
 */
public class PropertyFilterParam {
	
    private String hql;
    
    private ArrayList<Object> param = Lists.newArrayList();

    public PropertyFilterParam() {
       
    }
    
    public PropertyFilterParam(String hql, ArrayList<Object> param) {
        this.hql = hql;
        this.param = param;
    }


	public String getHql() {
		return hql;
	}


	public void setHql(String hql) {
		this.hql = hql;
	}


	public ArrayList<Object> getParam() {
		return param;
	}


	public void setParam(ArrayList<Object> param) {
		this.param = param;
	}

    
}
