package com.tjport.gjy.sys.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.tjport.common.hibernate.AssignedIdEntity;



@Entity
@Table(name = "t_sys_d_resource")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Resource extends AssignedIdEntity implements Serializable {

		
		// 权限名称
		private String name;

		// 权限描述
		private String description;

		// 地址
		private String url; 
		
		// 图标
		private String icon; 
		
		// 排序号
		private String seq; 
		
		// 资源类型, 1菜单 2功能
		private Integer type; 
		
		// 父编号
		private String pid;

		// 权限code
		private String authority;
		
		// 层级
		private String level;
		
		@Column(name = "name",length = 32)
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Column(name = "description", length = 50)
		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		
		@Column(name = "url", length = 80)
		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		@Column(name = "icon", length = 50)
		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}

		@Column(name = "seq", length = 20)
		public String getSeq() {
			return seq;
		}

		public void setSeq(String seq) {
			this.seq = seq;
		}

		@Column(name = "type")
		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		@Column(name = "pid")
		public String getPid() {
			return pid;
		}

		public void setPid(String pid) {
			this.pid = pid;
		}

		@Column(name = "authority", length = 50)
		public String getAuthority() {
			return authority;
		}

		public void setAuthority(String authority) {
			this.authority = authority;
		}

		@Column(name = "lvl", length = 10)
		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
		}
	
	
}
