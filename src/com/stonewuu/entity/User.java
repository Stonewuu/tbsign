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
@Table(name = "tb_user", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 32, nullable = false, unique = true)
	private Long id;
	@Column(length = 16, nullable = false, unique = true)
	private String name;
	@Column(length = 32, nullable = false)
	private String password;

	@Column(length = 32, nullable = false)
	private String salt;

	@Column(nullable = false)
	private Boolean locked = false;
	
	/**
	 * 用户角色多对多关系，由用户维护
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "t_user_role",
			joinColumns = { @JoinColumn(name = "user_id") },
			inverseJoinColumns = { @JoinColumn(name = "role_id") })
	private Set<Role>  roles;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Boolean getLock() {
		return locked;
	}

	public void setLock(Boolean lock) {
		this.locked = lock;
	}

}
