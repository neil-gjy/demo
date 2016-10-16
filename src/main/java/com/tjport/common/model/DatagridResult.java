package com.tjport.common.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * easyui分页组件datagrid、combogrid数据模型.
 *
 */
@SuppressWarnings("serial")
public class DatagridResult<T> implements Serializable {
    /**
     * 总记录数
     */
    private long total;
    /**
     * 列表行
     */
    private List<T> rows = new ArrayList<T>(0);
    /**
     * 脚列表
     */
    private List<Map<String, Object>> footer = new ArrayList<Map<String, Object>>(0);

    public DatagridResult() {
 
    }

    public DatagridResult(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
        
    }
    /**
     * 总记录数
     */
    public long getTotal() {
        return total;
    }

    /**
     * 设置总记录数
     *
     * @param total 总记录数
     */
    public DatagridResult<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    /**
     * 列表行
     */
    public List<T> getRows() {
        return rows;
    }

    /**
     * 设置列表行
     *
     * @param rows 列表行
     */
    public DatagridResult<T> setRows(List<T> rows) {
        this.rows = rows;
        return this;
    }

    /**
     * 脚列表
     */
    public List<Map<String, Object>> getFooter() {
        return footer;
    }

    /**
     * 设置脚列表
     *
     * @param footer 脚列表
     */
    public DatagridResult<T> setFooter(List<Map<String, Object>> footer) {
        this.footer = footer;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
