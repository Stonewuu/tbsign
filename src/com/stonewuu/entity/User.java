package com.stonewuu.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
	private String name;//用户名
	@Column(length = 32, nullable = false)
	private String password;//密码

	@Column(length = 32, nullable = false)
	private String salt;//盐（随机字符串）

	@Column(length = 2, nullable = false)
	private Integer userType = 1;// 1,普通用户 2,管理员 

	@Column(length = 64, nullable = false)
	private String email;// 邮箱
	
	@Column(nullable = false)
	private Boolean validateEmail = false;// 是否已验证邮箱

	@Column(nullable = false)
	private Boolean locked = false;//是否被锁定

	/**
	 * 用户角色多对多关系，由用户维护
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "t_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	private List<Role> roles;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy="user")
	private List<BDInfo> bdInfo	= new ArrayList<BDInfo>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getValidateEmail() {
		return validateEmail;
	}

	public void setValidateEmail(Boolean validateEmail) {
		this.validateEmail = validateEmail;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<BDInfo> getBdInfo() {
		return bdInfo;
	}

	public void setBdInfo(List<BDInfo> bdInfo) {
		this.bdInfo = bdInfo;
	}


}
