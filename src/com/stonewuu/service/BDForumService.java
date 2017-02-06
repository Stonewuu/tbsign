package com.stonewuu.service;

import com.stonewuu.entity.BDForum;
import com.stonewuu.entity.BDInfo;

public interface BDForumService extends GeneralService<BDForum>{

	/**
	 * 
	 * @Title: refreshForumList
	 * @Description: 刷新指定贴吧账号关注的贴吧列表
	 * @author stonewuu 2017年1月22日 下午5:41:34
	 *
	 * @param 贴吧用户信息
	 * @return 
	 */
	public boolean refreshForumList(BDInfo bdInfo);
	
}
