package com.stonewuu.service;

import java.util.List;

import com.stonewuu.entity.BDInfo;

public interface BDInfoService extends GeneralService<BDInfo>{

	/**
	 * 
	 * @Title: find
	 * @Description: 查询单条记录
	 * @author stonewuu 2017年1月21日 下午7:41:27
	 *
	 * @param bdInfoId
	 * @return
	 */
	public BDInfo find(Object bdInfoId);

	/**
	 * 
	 * @Title: findAll
	 * @Description: 查询所有贴吧用户
	 * @author stonewuu 2017年2月23日 下午9:06:12
	 *
	 * @return
	 */
	public List<BDInfo> findAll();

}
