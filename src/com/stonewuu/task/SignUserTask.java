package com.stonewuu.task;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.stonewuu.entity.BDForum;
import com.stonewuu.entity.BDInfo;
import com.stonewuu.helper.TieBaSignHelper;
import com.stonewuu.service.BDForumService;

import net.sf.json.JSONObject;

public class SignUserTask implements Runnable{
	private static final Log log = LogFactory.getLog(SignUserTask.class);
	private BDInfo info;
	private BDForumService bdForumService;
	private TieBaSignHelper tbHelper;
	

	public SignUserTask(BDInfo info, BDForumService bdForumService, TieBaSignHelper tbHelper) {
		super();
		this.info = info;
		this.bdForumService = bdForumService;
		this.tbHelper = tbHelper;
	}


	@Override
	public void run() {
		log.info("用户 \""+info.getUser().getName()+"\" 的百度账号 \""+info.getBdName()+"\" 准备进行一键签到！");
		//刷新用户贴吧信息
		bdForumService.refreshForumList(info,false);
		//大于等于7级的贴吧列表
		List<BDForum> upForum = bdForumService.findForumByUserWithHighLevel(info);
		boolean flag = false;
		if(upForum != null){
			if(upForum.size()>=50){
				//大于等于7级的吧超过50个
				flag = true;
			}else{
				StringBuffer str = new StringBuffer();
				for(BDForum forum : upForum){
					str.append(forum.getForumId()+",");
				}
				if(str.length()>0){
					str.delete(str.length()-1, str.length());
					Map<String, String> paramMap = tbHelper.getBetchSignMap(str.toString(), info.getBduss(), info.getUid());
					String url = tbHelper.sign(paramMap);
					JSONObject json = tbHelper.doBetchSignResult(url);
					tbHelper.checkBetchSignResult(json,info.getId().toString());
				}
			}
		}
		List<BDForum> otherForum ;
		if(flag){
			//超过50个7级以上的吧不能调用一键签到，所有吧按普通签到进行签到
			otherForum = bdForumService.findForumByUser(info);
		}else{
			otherForum = bdForumService.findForumByUserWithLowLevel(info);
		}
		//小于7级的贴吧列表
		if(otherForum != null){
			for(BDForum forum : otherForum){
				Map<String, String> paramMap = tbHelper.getSignMap(forum.getForumId(), forum.getForumKeyWord(), info.getBduss());
				String url = tbHelper.sign(paramMap);
				JSONObject json = tbHelper.doSignResult(url);
				tbHelper.checkSignResult(json,info.getId().toString(), forum.getForumId());
			}
		}
		log.info("用户 \""+info.getUser().getName()+"\" 的百度账号 \""+info.getBdName()+"\" 执行一键签到完毕！");
	}
	
}
