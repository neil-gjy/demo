package com.tjport.gjy.sys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.tjport.common.hibernate.UUIDEntity;

/**
 * 部门
 * @author YinQuan
 *
 */
@Entity
@Table(name = "t_sys_d_dept")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Department extends UUIDEntity {
	
	// 部门名称
	private String name;
	
	// 所属组织机构
	
	
	@Column(name = "name",length = 20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "s_org_id")
	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}*/
}
