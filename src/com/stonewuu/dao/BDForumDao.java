package com.stonewuu.dao;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.stonewuu.entity.BDForum;

@Repository
public class BDForumDao extends GeneralDAO<BDForum>{
	public int deleteById(Long info_id){
		String jpql = "delete BDForum where bdinfo_id = :bdinfo_id";
		Query query = em.createQuery(jpql);
		query.setParameter("bdinfo_id", info_id);
		int i = query.executeUpdate();
		return i;
	}
}
