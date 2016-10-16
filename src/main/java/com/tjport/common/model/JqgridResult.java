package com.tjport.common.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * jqgrid 返回数据模型.
 *
 */
@SuppressWarnings("serial")
public class JqgridResult<T> implements Serializable {
    
	/**
	 * 总页数
	 */
    private long total;
    
    /**
     * 当前页面
     */
    private long page;
   
    /**
     * 总记录数
     */
    private long records;
    
    /**
     * 返回的数据
     */
    private List<T> rows = new ArrayList<T>();
    

    public JqgridResult() {
 
    }


	public JqgridResult(long total, long page, long records, List<T> rows) {
		
		this.total = total;
		this.page = page;
		this.records = records;
		this.rows = rows;
	}


	public long getTotal() {
		return total;
	}


	public void setTotal(long total) {
		this.total = total;
	}


	public long getPage() {
		return page;
	}


	public void setPage(long page) {
		this.page = page;
	}


	public long getRecords() {
		return records;
	}


	public void setRecords(long records) {
		this.records = records;
	}


	public List<T> getRows() {
		return rows;
	}


	public void setRows(List<T> rows) {
		this.rows = rows;
	}

    
    
}
