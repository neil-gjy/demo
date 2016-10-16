package com.tjport.common.hibernate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class UUIDEntity extends AbstractEntity<String> {

	/**
	 * 主键ID
	 */
	protected String id;

	private Integer version;
	
	private Date createTime;
	
	private Date updateTime;
	
	private String status;
	
	public UUIDEntity() {
	}

	/**
	 * 主键ID
	 */
	@Id  
    @Column(length = 32)  
    @GeneratedValue(generator = "uuid")  
    @GenericGenerator(name = "uuid", strategy = "uuid.hex")
	public String getId() {
		return id;
	}

	/**
	 * 设置 主键ID
	 * 
	 * @param id
	 * 主键ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	@Version
    @Column(name = "version")
	@Override
	public Integer getVersion() {
		// TODO Auto-generated method stub
		return this.version;
	}

	@Override
	public void setVersion(Integer version) {
		// TODO Auto-generated method stub
		this.version = version;
	}

	@Column(name = "create_time", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@Override
	public Date getCreateTime() {
		// TODO Auto-generated method stub
		return this.createTime;
	}

	@Override
	public void setCreateTime(Date createTime) {
		// TODO Auto-generated method stub
		this.createTime = createTime;
	}

	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	@Override
	public Date getUpdateTime() {
		// TODO Auto-generated method stub
		return this.updateTime;
	}

	@Override
	public void setUpdateTime(Date updateTime) {
		// TODO Auto-generated method stub
		this.updateTime = updateTime;
	}

	@Column(name = "status", length = 10)
	@Override
	public String getStatus() {
		// TODO Auto-generated method stub
		return this.status;
	}

	@Override
	public void setStatus(String status) {
		// TODO Auto-generated method stub
		this.status = status;
	}
}

