package com.tjport.common.hibernate;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class AbstractEntity <ID extends Serializable> {
	

	public static final String DATE_FORMAT = "yyyy-MM-dd";

	public static final String TIME_FORMAT = "HH:mm:ss";

	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

	public static final String TIMEZONE = "GMT+08:00";
	
	public abstract ID getId();

    /**
     * 设置主键ID.
     *
     * @param id 主键ID
     */
    public abstract void setId(final ID id);
    
    public abstract Integer getVersion();
	
	public abstract void setVersion(Integer version);
	
	public abstract Date getCreateTime();
	
	public abstract void setCreateTime(Date createTime);
	
	public abstract Date getUpdateTime();
	
	public abstract void setUpdateTime(Date updateTime);
	
	public abstract String getStatus();

	public abstract void setStatus(String status);
	
    /*private Integer version;
	private Date createTime;
	private Date updateTime;
	private String status;*/
	
	public AbstractEntity() {
		
	}
	
	/*@Version
    @Column(name = "version")
	public Integer getVersion() {
		return version;
	}
	
	public void setVersion(Integer version) {
		this.version = version;
	}
	

	@Column(name = "create_time", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	

	@Column(name = "status", length = 10)
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}*/

	/**
     * 是否是新创建的对象.
     * @return
     */
    @JsonIgnore
    public boolean isNew() {
        return null == getId();
    }

    @Override
    public boolean equals(Object obj) {

        if (null == obj) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!getClass().equals(obj.getClass())) {
            return false;
        }

        AbstractEntity<?> that = (AbstractEntity<?>) obj;

        return null == this.getId() ? false : this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {

        int hashCode = 17;

        hashCode += null == getId() ? 0 : getId().hashCode() * 31;

        return hashCode;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": [id=" + getId() + "]";

    }
}
