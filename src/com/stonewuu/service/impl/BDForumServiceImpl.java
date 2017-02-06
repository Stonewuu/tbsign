package com.stonewuu.service.impl;

import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

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
	public boolean refreshForumList(BDInfo bdInfo){
		Map<String, String> param = tbHelper.getForumParam(bdInfo.getBduss());
		String strParam = tbHelper.sign(param);
		JSONObject json = tbHelper.getTiebaForumList(strParam);
		if("0".equals(json.get("error_code"))){
			bdForumDao.deleteById(bdInfo.getId());
			JSONArray forums = json.getJSONArray("like_forum");
			for(int i=0;i<forums.size();i++){
				JSONObject forum = forums.getJSONObject(i);
				BDForum f = new BDForum();
				f.setForumId(forum.get("forum_id").toString());
				f.setBdInfo(bdInfo);
				f.setLevel(Integer.parseInt(forum.get("level_id").toString()));
				f.setForumName(forum.get("forum_name").toString());
				f.setForumKeyWord(forum.get("forum_name").toString());
				f.setSigned("1".equals(forum.get("is_sign"))?true:false);
				bdForumDao.save(f);
			}
		}else{
			return false;
		}
		return true;
	}


	@Override
	public void update(BDForum t) {
		// TODO Auto-generated method stub
		
	}

}
