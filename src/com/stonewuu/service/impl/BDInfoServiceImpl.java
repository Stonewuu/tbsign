package com.stonewuu.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.stonewuu.dao.BDInfoDao;
import com.stonewuu.entity.BDInfo;
import com.stonewuu.service.BDInfoService;

@Service
public class BDInfoServiceImpl implements BDInfoService {

	@Resource
	private BDInfoDao bdInfoDao;

	@Override
	@Transactional
	public BDInfo create(BDInfo bdInfo) {
		List<BDInfo> list = bdInfoDao.findByProperty("uid", bdInfo.getUid());
		if(list !=null && list.size()>0){
			return null;
		}
		return bdInfoDao.save(bdInfo);
	}

	@Override
	@Transactional
	public void delete(Object bdInfoId) {
		BDInfo p = bdInfoDao.find(bdInfoId);
		if (p != null) {
			bdInfoDao.delete(p);
		}
	}


	@Override
	@Transactional
	public BDInfo find(Object bdInfoId) {
		return bdInfoDao.find(bdInfoId);
	}


	@Override
	public void update(BDInfo t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public List<BDInfo> findAll() {
		return bdInfoDao.findAll();
	}
	

}
