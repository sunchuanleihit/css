package com.loukou.css.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tcz_admin")
public class TczAdmin {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="realname")
	private String realname;
	
	@Column(name="isuse")
	private Integer isuse;
	
	@Column(name="gid")
	private Integer gid;
	
	@Column(name="parent_id")
	private Integer parentId;
	
	@Column(name="site_id")
	private Integer siteId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Integer getIsuse() {
		return isuse;
	}

	public void setIsuse(Integer isuse) {
		this.isuse = isuse;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	
}
