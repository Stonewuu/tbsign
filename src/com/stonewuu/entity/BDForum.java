package com.stonewuu.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_bdforum")
public class BDForum {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 32, nullable = false, unique = true)
	private Long id;

	@Column(length = 32, nullable = false)
	private String forumId;

	@Column(length = 32, nullable = false)
	private String forumName;

	@Column(length = 32, nullable = false)
	private String forumKeyWord;
	
	@Column
	private Long exp;
	
	@Column
	private int level;
	
	@Column
	private boolean signed;

	@ManyToOne(cascade = { CascadeType.REFRESH}, optional = true)
	@JoinColumn(name = "bdinfo_id")
	private BDInfo bdInfo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getForumId() {
		return forumId;
	}

	public void setForumId(String forumId) {
		this.forumId = forumId;
	}

	public String getForumName() {
		return forumName;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
	}

	public String getForumKeyWord() {
		return forumKeyWord;
	}

	public void setForumKeyWord(String forumKeyWord) {
		this.forumKeyWord = forumKeyWord;
	}

	public Long getExp() {
		return exp;
	}

	public void setExp(Long exp) {
		this.exp = exp;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public BDInfo getBdInfo() {
		return bdInfo;
	}

	public void setBdInfo(BDInfo bdInfo) {
		this.bdInfo = bdInfo;
	}

	public boolean isSigned() {
		return signed;
	}

	public void setSigned(boolean signed) {
		this.signed = signed;
	}

	
}
