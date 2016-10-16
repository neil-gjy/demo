package com.tjport.gjy.sys.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.collect.Lists;
import com.tjport.common.hibernate.AssignedIdEntity;

/**
 * 组织机构
 * @author YinQuan
 *
 */

@Entity
@Table(name = "t_sys_d_org")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Organization extends AssignedIdEntity implements Serializable {
	
	private String name;
	
	// 部门列表
    private List<Department> depts = Lists.newArrayList();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "t_sys_d_org_dept", joinColumns = { @JoinColumn(name = "org_id") }, inverseJoinColumns = { @JoinColumn(name = "dept_id") })
    @OrderBy("id")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<Department> getDepts() {
		return depts;
	}

	public void setDepts(List<Department> depts) {
		this.depts = depts;
	}
}
