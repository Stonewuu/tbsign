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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tb_bdinfo", uniqueConstraints = { @UniqueConstraint(columnNames = { "bduss" }) })
public class BDInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private Long id; // ID
	
	@Column(nullable = false, unique = true)
	private String bduss; // ID

	@Column(length = 32, nullable = false, unique = true)
	private String uid; // ID
	

	@Column(length = 32)
	private String bdName;

	@Column(length = 512)
	private String bdAvatar;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy="bdInfo")
	private List<BDForum> forums = new ArrayList<BDForum>();

	@ManyToOne(cascade = { CascadeType.REFRESH}, optional = true)
	@JoinColumn(name = "user_id")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBduss() {
		return bduss;
	}

	public void setBduss(String bduss) {
		this.bduss = bduss;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getBdName() {
		return bdName;
	}

	public void setBdName(String bdName) {
		this.bdName = bdName;
	}

	public String getBdAvatar() {
		return bdAvatar;
	}

	public void setBdAvatar(String bdAvatar) {
		this.bdAvatar = bdAvatar;
	}

	public List<BDForum> getForums() {
		return forums;
	}

	public void setForums(List<BDForum> forums) {
		this.forums = forums;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
