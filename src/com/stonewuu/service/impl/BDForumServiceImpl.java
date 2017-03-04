package com.stonewuu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.stonewuu.dao.BDForumDao;
import com.stonewuu.entity.BDForum;
import com.stonewuu.entity.BDInfo;
import com.stonewuu.helper.TieBaSignHelper;
import com.stonewuu.service.BDForumService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class BDForumServiceImpl implements BDForumService {
	private static final Log log = LogFactory.getLog(BDForumServiceImpl.class);

	@Resource
	private BDForumDao bdForumDao;
	@Resource
	private TieBaSignHelper tbHelper;

	@Override
	public BDForum create(BDForum bDForum) {
		return bdForumDao.save(bDForum);
	}

	@Override
	public void delete(Object bDForumId) {
		BDForum p = bdForumDao.find(bDForumId);
		if (p != null) {
			bdForumDao.delete(p);
		}
	}

	@Override
	@Transactional
	public Map<String, Object> refreshForumList(BDInfo bdInfo,boolean fullRefresh) {
		Map<String, Object> result = new HashMap<>();
		Map<String, String> param = tbHelper.getForumParam(bdInfo.getBduss(),"1","50");
		String strParam = tbHelper.sign(param);
		JSONObject json = tbHelper.getTiebaAllForumList(strParam);
		if ("0".equals(json.get("error_code"))) {
			
			if(fullRefresh){
				bdForumDao.deleteById(bdInfo.getId());
			}
			JSONArray forums = json.getJSONArray("forum_list");
			if(!forums.isEmpty()){
				//添加所有的吧（无签到状态）
				JSONObject forum = null;
				BDForum f = null;
				for (int i = 0; i < forums.size(); i++) {
					forum = forums.getJSONObject(i);
					f = this.findByBdidAndFid(bdInfo.getId().toString(), forum.get("id").toString());
					//验证本地数据库中是否存在该吧的数据
					if(f == null){
						f = new BDForum();
					}
					f.setForumId(forum.get("id").toString());
					f.setBdInfo(bdInfo);
					f.setLevel(Integer.parseInt(forum.get("level_id").toString()));
					f.setExp(Long.valueOf(forum.get("cur_score").toString()));
					f.setForumName(forum.get("name").toString());
					f.setForumKeyWord(forum.get("name").toString());
					f.setSigned(false);
					//保存或更新
					bdForumDao.save(f);
				}
				//获取7级以上的吧（有签到状态）
				param = tbHelper.getForumHLevelParam(bdInfo.getBduss(), bdInfo.getUid());
				strParam = tbHelper.sign(param);
				json = tbHelper.getTiebaHLevelForumList(strParam);
				forums = json.getJSONArray("forum_info");
				for (int i = 0; i < forums.size(); i++) {
					forum = forums.getJSONObject(i);
					f = this.findByBdidAndFid(bdInfo.getId().toString(), forum.get("forum_id").toString());
					if(f == null){
						f = new BDForum();
					}
					f.setForumId(forum.get("forum_id").toString());
					f.setBdInfo(bdInfo);
					f.setLevel(Integer.parseInt(forum.get("user_level").toString()));
					f.setExp(Long.valueOf(forum.get("user_exp").toString()));
					f.setForumName(forum.get("forum_name").toString());
					f.setForumKeyWord(forum.get("forum_name").toString());
					f.setSigned("1".equals(forum.get("is_sign_in").toString()) ? true : false);
					bdForumDao.save(f);
				}
				log.info("用户 \""+bdInfo.getUser().getName()+"\" 的百度账号 \""+bdInfo.getBdName()+"\" 刷新贴吧列表成功");
				result.put("status", true);
			}
			
		} else {
			String msg = (String) json.get("error_msg");
			if ("1".equals(json.get("error_code"))) {
				result.put("status", false);
				result.put("msg", msg);
			}else{
				result.put("status", false);
				result.put("msg", "未知错误："+json.get("error_code")+"："+msg);
			}
			log.info("用户 \""+bdInfo.getUser().getName()+"\" 的百度账号 \""+bdInfo.getBdName()+"\" 刷新贴吧列表失败，原因："+msg);
		}
		return result;
	}

	@Override
	@Transactional
	public void update(BDForum t) {
		bdForumDao.update(t);
	}

	@Override
	@Transactional
	public List<BDForum> findForumByUser(BDInfo info) {
		Map<String, Object> map = new HashMap<>();
		map.put("bdinfo_id", info.getId());
		List<BDForum> forums = bdForumDao.findByPropertys(" bdinfo_id = :bdinfo_id ", map);
		return forums;
	}
	
	@Override
	@Transactional
	public List<BDForum> findForumByUserWithHighLevel(BDInfo info) {
		Map<String, Object> map = new HashMap<>();
		map.put("bdinfo_id", info.getId());
		map.put("level", 7);
		List<BDForum> forums = bdForumDao.findByPropertys(" bdinfo_id = :bdinfo_id and level >= :level", map);
		return forums;
	}

	@Override
	@Transactional
	public List<BDForum> findForumByUserWithLowLevel(BDInfo info) {
		Map<String, Object> map = new HashMap<>();
		map.put("bdinfo_id", info.getId());
		map.put("level", 7);
		List<BDForum> forums = bdForumDao.findByPropertys(" bdinfo_id = :bdinfo_id and level < :level", map);
		return forums;
	}

	@Override
	@Transactional
	public BDForum findByBdidAndFid(String bdid, String fid) {
		Map<String, Object> map = new HashMap<>();
		map.put("forumId", fid);
		map.put("bdinfo_id", bdid);
		List<BDForum> forums = bdForumDao.findByPropertys(" forumId = :forumId and bdinfo_id = :bdinfo_id ", map);
		if (forums != null && !forums.isEmpty()) {
			return forums.get(0);
		}
		return null;
	}

}
