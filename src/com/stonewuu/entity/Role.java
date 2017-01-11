package com.stonewuu.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tb_role", uniqueConstraints = { @UniqueConstraint(columnNames = { "roleName" }) })
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 32, nullable = false, unique = true)
	private Long id; // ID
	@Column(length = 32, nullable = false)
	private String name; // 角色名
	@Column(length = 32, nullable = false, unique = true)
	private String roleName; // 角色
	@Column(length = 32, nullable = true)
	private String description; // 描述

	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
	private Set<User> users;
	
	/**
	 * 角色-权限多对多关系，由角色维护
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "t_role_permission",
			joinColumns = { @JoinColumn(name = "role_id") },
			inverseJoinColumns = { @JoinColumn(name = "permiss_id") })
	private Set<Permission>  permissions;
	
	@Column(nullable = false)
	private Boolean available = true; // 是否可用

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return roleName;
	}

	public void setRole(String role) {
		this.roleName = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
	
}
