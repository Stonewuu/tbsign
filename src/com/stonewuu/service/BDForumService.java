package com.stonewuu.service;

import java.util.List;
import java.util.Map;

import com.stonewuu.entity.BDForum;
import com.stonewuu.entity.BDInfo;

public interface BDForumService extends GeneralService<BDForum>{

	/**
	 * 刷新指定贴吧账号关注的贴吧列表（请求百度api）
	 * @Title: refreshForumList
	 * @Description: 刷新指定贴吧账号关注的贴吧列表（请求百度api）
	 * @author stonewuu 2017年1月22日 下午5:41:34
	 *
	 * @param 贴吧用户信息
	 * @return 
	 */
	public Map<String, Object> refreshForumList(BDInfo bdInfo,boolean fullRefresh);


	/**
	 * 查询指定用户关注的贴吧
	 * @Title: findForumByUser
	 * @Description: 查询指定用户关注的贴吧
	 * @author stonewuu 2017年2月23日 下午11:49:28
	 *
	 * @param info
	 * @return
	 */
	public List<BDForum> findForumByUser(BDInfo info);
	
	/**
	 * 查找用户7级(包括)以上的吧
	 * @Title: findForumByUserWithLevel
	 * @Description: 查找用户7级以上的吧
	 * @author stonewuu 2017年2月23日 下午9:25:03
	 *
	 * @param info
	 * @return
	 */
	public List<BDForum> findForumByUserWithHighLevel(BDInfo info);

	/**
	 * 查找用户7级以下的吧
	 * @Title: findForumByUserWithLowLevel
	 * @Description: 查找用户7级以下的吧
	 * @author stonewuu 2017年2月23日 下午9:27:56
	 *
	 * @param info
	 * @return
	 */
	public List<BDForum> findForumByUserWithLowLevel(BDInfo info);

	/**
	 * 通过贴吧用户id和吧id查找指定的吧
	 * @Title: findByBdidAndFid
	 * @Description: 通过贴吧用户id和吧id查找指定的吧
	 * @author stonewuu 2017年2月23日 下午10:06:15
	 *
	 * @param bdinfo_id 百度用户信息表主键ID：bdinfo_id
	 * @param fid 贴吧ID
	 * @return 未找到返回null
	 */
	public BDForum findByBdidAndFid(String bdinfo_id, String fid);

	
}
